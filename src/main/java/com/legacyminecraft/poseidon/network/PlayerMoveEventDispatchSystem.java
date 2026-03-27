package com.legacyminecraft.poseidon.network;


/**
 * Canonical dispatcher for PlayerMoveEvent construction and outcome resolution.
 */
public final class PlayerMoveEventDispatchSystem {
    private static final PlayerMoveEventDispatchSystem INSTANCE = new PlayerMoveEventDispatchSystem();
    private final PlayerMoveEventPolicy playerMoveEventPolicy = PlayerMoveEventPolicy.getInstance();

    private PlayerMoveEventDispatchSystem() {
    }

    public static PlayerMoveEventDispatchSystem getInstance() {
        return INSTANCE;
    }

    public MoveEventResult processMoveEvent(
            Object server,
            Object entityPlayer,
            Object player,
            Object packet10flying,
            MovementEventState state,
            boolean checkMovement
    ) {
        Object playerWorld = invoke(player, "getWorld");
        Location from = new Location(playerWorld, state.getLastPosX(), state.getLastPosY(), state.getLastPosZ(), state.getLastYaw(), state.getLastPitch());
        Object playerLocation = invoke(player, "getLocation");
        Location to = toLocation(playerLocation).clone();

        boolean hasPosition = asBoolean(getField(packet10flying, "h"));
        double packetY = asDouble(getField(packet10flying, "y"));
        double packetStance = asDouble(getField(packet10flying, "stance"));
        if (playerMoveEventPolicy.shouldApplyPositionFromPacket(hasPosition, packetY, packetStance)) {
            to.setX(asDouble(getField(packet10flying, "x")));
            to.setY(packetY);
            to.setZ(asDouble(getField(packet10flying, "z")));
        }

        if (asBoolean(getField(packet10flying, "hasLook"))) {
            to.setYaw(asFloat(getField(packet10flying, "yaw")));
            to.setPitch(asFloat(getField(packet10flying, "pitch")));
        }

        boolean significantMoveEventDelta = playerMoveEventPolicy.hasSignificantMoveEventDelta(
                state.getLastPosX(),
                state.getLastPosY(),
                state.getLastPosZ(),
                state.getLastYaw(),
                state.getLastPitch(),
                to
        );
        if (!playerMoveEventPolicy.shouldProcessMoveEvent(significantMoveEventDelta, checkMovement, asBoolean(getField(entityPlayer, "dead")))) {
            return MoveEventResult.continueProcessing();
        }

        state.setLastPosX(to.getX());
        state.setLastPosY(to.getY());
        state.setLastPosZ(to.getZ());
        state.setLastYaw(to.getYaw());
        state.setLastPitch(to.getPitch());

        if (!playerMoveEventPolicy.hasInitializedMoveFromLocation(from)) {
            return MoveEventResult.continueProcessing();
        }

        Object event = NetworkCompatGatewayRegistry.gateway().createPlayerMoveEvent(player, from, to);
        Object pluginManager = invoke(server, "getPluginManager");
        invoke(pluginManager, "callEvent", event);

        if (asBoolean(invoke(event, "isCancelled"))) {
            return MoveEventResult.cancelAndRollback(from);
        }

        Location eventTo = toLocation(invoke(event, "getTo"));
        if (!to.equals(eventTo)) {
            return MoveEventResult.teleportToEventDestination(eventTo);
        }

        Location currentLocation = toLocation(invoke(player, "getLocation"));
        if (playerMoveEventPolicy.shouldAbortAfterPluginTeleport(from, currentLocation, state.isJustTeleported())) {
            state.setJustTeleported(false);
            return MoveEventResult.abortAfterPluginTeleport();
        }

        return MoveEventResult.continueProcessing();
    }

    public static final class MovementEventState {
        private double lastPosX;
        private double lastPosY;
        private double lastPosZ;
        private float lastYaw;
        private float lastPitch;
        private boolean justTeleported;

        public MovementEventState(
                double lastPosX,
                double lastPosY,
                double lastPosZ,
                float lastYaw,
                float lastPitch,
                boolean justTeleported
        ) {
            this.lastPosX = lastPosX;
            this.lastPosY = lastPosY;
            this.lastPosZ = lastPosZ;
            this.lastYaw = lastYaw;
            this.lastPitch = lastPitch;
            this.justTeleported = justTeleported;
        }

        public double getLastPosX() {
            return lastPosX;
        }

        public void setLastPosX(double lastPosX) {
            this.lastPosX = lastPosX;
        }

        public double getLastPosY() {
            return lastPosY;
        }

        public void setLastPosY(double lastPosY) {
            this.lastPosY = lastPosY;
        }

        public double getLastPosZ() {
            return lastPosZ;
        }

        public void setLastPosZ(double lastPosZ) {
            this.lastPosZ = lastPosZ;
        }

        public float getLastYaw() {
            return lastYaw;
        }

        public void setLastYaw(float lastYaw) {
            this.lastYaw = lastYaw;
        }

        public float getLastPitch() {
            return lastPitch;
        }

        public void setLastPitch(float lastPitch) {
            this.lastPitch = lastPitch;
        }

        public boolean isJustTeleported() {
            return justTeleported;
        }

        public void setJustTeleported(boolean justTeleported) {
            this.justTeleported = justTeleported;
        }
    }

    public static final class MoveEventResult {
        public enum Action {
            CONTINUE,
            CANCEL_AND_ROLLBACK,
            TELEPORT_TO_EVENT_DESTINATION,
            ABORT_AFTER_PLUGIN_TELEPORT
        }

        private final Action action;
        private final Location rollbackLocation;
        private final Location teleportDestination;

        private MoveEventResult(Action action, Location rollbackLocation, Location teleportDestination) {
            this.action = action;
            this.rollbackLocation = rollbackLocation;
            this.teleportDestination = teleportDestination;
        }

        public static MoveEventResult continueProcessing() {
            return new MoveEventResult(Action.CONTINUE, null, null);
        }

        public static MoveEventResult cancelAndRollback(Location rollbackLocation) {
            return new MoveEventResult(Action.CANCEL_AND_ROLLBACK, rollbackLocation, null);
        }

        public static MoveEventResult teleportToEventDestination(Location teleportDestination) {
            return new MoveEventResult(Action.TELEPORT_TO_EVENT_DESTINATION, null, teleportDestination);
        }

        public static MoveEventResult abortAfterPluginTeleport() {
            return new MoveEventResult(Action.ABORT_AFTER_PLUGIN_TELEPORT, null, null);
        }

        public Action getAction() {
            return action;
        }

        public Location getRollbackLocation() {
            return rollbackLocation;
        }

        public Location getTeleportDestination() {
            return teleportDestination;
        }
    }

    private Object getField(Object target, String name) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private Object invoke(Object target, String methodName, Object... args) {
        try {
            for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private Location toLocation(Object location) {
        if (location instanceof Location) {
            return (Location) location;
        }
        Object world = invoke(location, "getWorld");
        double x = asDouble(invoke(location, "getX"));
        double y = asDouble(invoke(location, "getY"));
        double z = asDouble(invoke(location, "getZ"));
        float yaw = asFloat(invoke(location, "getYaw"));
        float pitch = asFloat(invoke(location, "getPitch"));
        return new Location(world, x, y, z, yaw, pitch);
    }

    private boolean asBoolean(Object value) {
        return Boolean.TRUE.equals(value);
    }

    private double asDouble(Object value) {
        return ((Number) value).doubleValue();
    }

    private float asFloat(Object value) {
        return ((Number) value).floatValue();
    }
}
