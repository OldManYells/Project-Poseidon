package com.legacyminecraft.poseidon.network;


/**
 * Canonical service for outgoing packet event emission and packet substitution/cancellation.
 */
public final class OutgoingPacketEventSystem {
    private static final OutgoingPacketEventSystem INSTANCE = new OutgoingPacketEventSystem();
    private final PacketEventPolicy eventPolicy = PacketEventPolicy.getInstance();

    private OutgoingPacketEventSystem() {
    }

    public static OutgoingPacketEventSystem getInstance() {
        return INSTANCE;
    }

    public Object filterOutgoingPacket(boolean firePacketEvents, Object player, Object packet) {
        if (eventPolicy.shouldDropOutgoingPacket(Bridge.cast(packet))) {
            return null;
        }
        if (eventPolicy.shouldBypassOutgoingEventDispatch(firePacketEvents)) {
            return packet;
        }

        String playerName = String.valueOf(Bridge.getField(player, "name"));
        Object event = NetworkCompatGatewayRegistry.gateway().createPlayerSendPacketEvent(playerName, packet);
        NetworkCompatGatewayRegistry.gateway().callGlobalEvent(event);
        boolean cancelled = Boolean.TRUE.equals(Bridge.invoke(event, "isCancelled"));
        if (cancelled) {
            return null;
        }
        return Bridge.invoke(event, "getPacket");
    }

    private static final class Bridge {
        private static Object getField(Object target, String name) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(name);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static Object invoke(Object target, String methodName, Object... args) {
            try {
                java.lang.reflect.Method[] methods = target.getClass().getMethods();
                for (java.lang.reflect.Method method : methods) {
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

        @SuppressWarnings("unchecked")
        private static <T> T cast(Object value) {
            return (T) value;
        }
    }
}
