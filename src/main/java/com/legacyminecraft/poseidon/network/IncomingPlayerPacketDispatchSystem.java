package com.legacyminecraft.poseidon.network;


/**
 * Canonical service for NetworkManager incoming packet dispatch resolution.
 */
public final class IncomingPlayerPacketDispatchSystem {
    private static final IncomingPlayerPacketDispatchSystem INSTANCE = new IncomingPlayerPacketDispatchSystem();

    private IncomingPlayerPacketDispatchSystem() {
    }

    public static IncomingPlayerPacketDispatchSystem getInstance() {
        return INSTANCE;
    }

    public DispatchDecision resolve(boolean firePacketEvents, Object netHandler, String username, Object packet) {
        if (!firePacketEvents || !Bridge.isNetServerHandler(netHandler)) {
            return DispatchDecision.dispatch(packet);
        }

        Object event = Bridge.newReceiveEvent(username, packet);
        Bridge.callBukkitEvent(event);
        if (Bridge.isEventCancelled(event)) {
            return DispatchDecision.skipDispatch();
        }
        return DispatchDecision.dispatch(Bridge.getEventPacket(event));
    }

    public static final class DispatchDecision {
        private final boolean shouldDispatch;
        private final Object packet;

        private DispatchDecision(boolean shouldDispatch, Object packet) {
            this.shouldDispatch = shouldDispatch;
            this.packet = packet;
        }

        public static DispatchDecision dispatch(Object packet) {
            return new DispatchDecision(true, packet);
        }

        public static DispatchDecision skipDispatch() {
            return new DispatchDecision(false, null);
        }

        public boolean shouldDispatch() {
            return shouldDispatch;
        }

        public Object getPacket() {
            return packet;
        }
    }

    private static final class Bridge {
        private static boolean isNetServerHandler(Object handler) {
            return handler != null && NetworkCompatGatewayRegistry.gateway().isNetServerHandler(handler);
        }

        private static Object newReceiveEvent(String username, Object packet) {
            return NetworkCompatGatewayRegistry.gateway().createPlayerReceivePacketEvent(username, packet);
        }

        private static void callBukkitEvent(Object event) {
            NetworkCompatGatewayRegistry.gateway().callGlobalEvent(event);
        }

        private static boolean isEventCancelled(Object event) {
            try {
                return Boolean.TRUE.equals(event.getClass().getMethod("isCancelled").invoke(event));
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static Object getEventPacket(Object event) {
            try {
                return event.getClass().getMethod("getPacket").invoke(event);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
