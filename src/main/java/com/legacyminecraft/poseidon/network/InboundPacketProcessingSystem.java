package com.legacyminecraft.poseidon.network;


import java.util.List;

/**
 * Canonical inbound queue processing loop for NetworkManager.
 */
public final class InboundPacketProcessingSystem {
    private static final InboundPacketProcessingSystem INSTANCE = new InboundPacketProcessingSystem();
    private final IncomingPlayerPacketDispatchSystem incomingPlayerPacketDispatchSystem = IncomingPlayerPacketDispatchSystem.getInstance();

    private InboundPacketProcessingSystem() {
    }

    public static InboundPacketProcessingSystem getInstance() {
        return INSTANCE;
    }

    public int processInboundQueue(List inboundQueue, int processingBudget, Object handler, boolean firePacketEvents, String username) {
        int processed = 0;
        int remaining = processingBudget;
        while (!inboundQueue.isEmpty() && remaining-- >= 0) {
            Object packet = inboundQueue.remove(0);
            IncomingPlayerPacketDispatchSystem.DispatchDecision dispatchDecision =
                    incomingPlayerPacketDispatchSystem.resolve(firePacketEvents, handler, username, packet);
            if (dispatchDecision.shouldDispatch()) {
                Bridge.dispatch(dispatchDecision.getPacket(), handler);
            }
            processed++;
        }
        return processed;
    }

    private static final class Bridge {
        private static void dispatch(Object packet, Object handler) {
            try {
                java.lang.reflect.Method dispatchMethod = packet.getClass().getMethod("a", handler.getClass().getInterfaces().length > 0
                        ? handler.getClass().getInterfaces()[0]
                        : handler.getClass());
                dispatchMethod.invoke(packet, handler);
            } catch (NoSuchMethodException noSuchMethodException) {
                try {
                    java.lang.reflect.Method[] methods = packet.getClass().getMethods();
                    for (java.lang.reflect.Method method : methods) {
                        if (method.getName().equals("a") && method.getParameterTypes().length == 1) {
                            method.invoke(packet, handler);
                            return;
                        }
                    }
                    throw new IllegalStateException("No dispatch method found for packet");
                } catch (Exception exception) {
                    throw new IllegalStateException(exception);
                }
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
