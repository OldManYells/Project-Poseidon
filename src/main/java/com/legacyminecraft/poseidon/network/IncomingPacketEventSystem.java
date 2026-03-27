package com.legacyminecraft.poseidon.network;


/**
 * Canonical service for incoming packet event emission and cancellation checks.
 */
public final class IncomingPacketEventSystem {
    private static final IncomingPacketEventSystem INSTANCE = new IncomingPacketEventSystem();
    private final PacketEventPolicy eventPolicy = PacketEventPolicy.getInstance();

    private IncomingPacketEventSystem() {
    }

    public static IncomingPacketEventSystem getInstance() {
        return INSTANCE;
    }

    public boolean allowIncomingPacket(Object server, Object player, Object packet) {
        Object bukkitPlayer = invoke(server, "getPlayer", String.valueOf(getField(player, "name")));
        Object event = NetworkCompatGatewayRegistry.gateway().createPacketReceivedEvent(bukkitPlayer, packet);
        Object pluginManager = invoke(server, "getPluginManager");
        invoke(pluginManager, "callEvent", event);
        boolean cancelled = Boolean.TRUE.equals(invoke(event, "isCancelled"));
        return eventPolicy.isIncomingAllowed(cancelled);
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

}
