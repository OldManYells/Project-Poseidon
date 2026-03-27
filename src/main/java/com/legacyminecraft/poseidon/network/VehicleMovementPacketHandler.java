package com.legacyminecraft.poseidon.network;


/**
 * Canonical coordinator for processing movement packets while a player is riding a vehicle.
 */
public final class VehicleMovementPacketHandler {
    private static final VehicleMovementPacketHandler INSTANCE = new VehicleMovementPacketHandler();
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();
    private final MovementPacketSentinelPolicy movementPacketSentinelPolicy = MovementPacketSentinelPolicy.getInstance();

    private VehicleMovementPacketHandler() {
    }

    public static VehicleMovementPacketHandler getInstance() {
        return INSTANCE;
    }

    public VehicleMoveResult handleVehicleMovement(
            Object player,
            Object worldServer,
            Object serverConfigurationManager,
            Object packet10flying
    ) {
        float yaw = ((Number) getField(player, "yaw")).floatValue();
        float pitch = ((Number) getField(player, "pitch")).floatValue();

        Object vehicle = getField(player, "vehicle");
        invoke(vehicle, "f");
        double anchorX = ((Number) getField(player, "locX")).doubleValue();
        double anchorY = ((Number) getField(player, "locY")).doubleValue();
        double anchorZ = ((Number) getField(player, "locZ")).doubleValue();

        double moveX = 0.0D;
        double moveZ = 0.0D;
        if (Boolean.TRUE.equals(getField(packet10flying, "hasLook"))) {
            yaw = ((Number) getField(packet10flying, "yaw")).floatValue();
            pitch = ((Number) getField(packet10flying, "pitch")).floatValue();
        }

        boolean hasPosition = Boolean.TRUE.equals(getField(packet10flying, "h"));
        double packetY = ((Number) getField(packet10flying, "y")).doubleValue();
        double packetStance = ((Number) getField(packet10flying, "stance")).doubleValue();
        if (hasPosition && movementPacketSentinelPolicy.isMotionOnlySentinel(packetY, packetStance)) {
            moveX = ((Number) getField(packet10flying, "x")).doubleValue();
            moveZ = ((Number) getField(packet10flying, "z")).doubleValue();

            if (movementPacketPolicy.isVehicleCrashAttempt(moveX, moveZ)) {
                return VehicleMoveResult.crashAttempt();
            }
        }

        setField(player, "onGround", getField(packet10flying, "g"));
        invoke(player, "a", true);
        invoke(player, "move", moveX, 0.0D, moveZ);
        invoke(player, "setLocation", anchorX, anchorY, anchorZ, yaw, pitch);
        setField(player, "motX", moveX);
        setField(player, "motZ", moveZ);

        if (vehicle != null) {
            invoke(worldServer, "vehicleEnteredWorld", vehicle, true);
        }

        if (vehicle != null) {
            invoke(vehicle, "f");
            setField(vehicle, "airBorne", true);
        }

        invoke(serverConfigurationManager, "d", player);
        invoke(worldServer, "playerJoinedWorld", player);
        double finalX = ((Number) getField(player, "locX")).doubleValue();
        double finalY = ((Number) getField(player, "locY")).doubleValue();
        double finalZ = ((Number) getField(player, "locZ")).doubleValue();
        return VehicleMoveResult.handled(finalX, finalY, finalZ);
    }

    public String createVehicleCrashLogMessage(String playerName, Object vehicle) {
        return "[Poseidon]" + playerName + " tried crashing server on entity " + vehicle + ". They have been kicked.";
    }

    public String getVehicleCrashKickMessage() {
        return "Boat crash attempt detected!";
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

    private void setField(Object target, String name, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            field.setAccessible(true);
            field.set(target, value);
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

    public static final class VehicleMoveResult {
        private final boolean handled;
        private final boolean crashAttempt;
        private final double x;
        private final double y;
        private final double z;

        private VehicleMoveResult(boolean handled, boolean crashAttempt, double x, double y, double z) {
            this.handled = handled;
            this.crashAttempt = crashAttempt;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static VehicleMoveResult handled(double x, double y, double z) {
            return new VehicleMoveResult(true, false, x, y, z);
        }

        public static VehicleMoveResult crashAttempt() {
            return new VehicleMoveResult(false, true, 0.0D, 0.0D, 0.0D);
        }

        public boolean isHandled() {
            return handled;
        }

        public boolean isCrashAttempt() {
            return crashAttempt;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }
}
