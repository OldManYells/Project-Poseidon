package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.compat.bukkit.ChunkCompressionDispatchBridge;

/**
 * Canonical service for outgoing packet dispatch behavior.
 */
public final class OutboundPacketDispatchSystem {
    private static final OutboundPacketDispatchSystem INSTANCE = new OutboundPacketDispatchSystem();
    private final ChunkCompressionDispatchBridge chunkCompressionDispatchBridge = ChunkCompressionDispatchBridge.getInstance();
    private final ChatTextWrapBehaviour chatTextWrapBehaviour = ChatTextWrapBehaviour.getInstance();

    private OutboundPacketDispatchSystem() {
    }

    public static OutboundPacketDispatchSystem getInstance() {
        return INSTANCE;
    }

    public void dispatchOutgoingPacket(Object networkManager, Object entityPlayer, Object bukkitPlayer, Object packet) {
        if ("Packet6SpawnPosition".equals(packet.getClass().getSimpleName())) {
            int x = ((Number) Bridge.getField(packet, "x")).intValue();
            int y = ((Number) Bridge.getField(packet, "y")).intValue();
            int z = ((Number) Bridge.getField(packet, "z")).intValue();
            Object world = Bridge.invoke(bukkitPlayer, "getWorld");
            Object location = NetworkCompatGatewayRegistry.gateway().createLocation(world, (double) x, (double) y, (double) z);
            Bridge.setField(entityPlayer, "compassTarget", location);
        } else if ("Packet3Chat".equals(packet.getClass().getSimpleName())) {
            String message = String.valueOf(Bridge.getField(packet, "message"));
            for (String line : chatTextWrapBehaviour.wrapText(message)) {
                Object chatPacket = NetworkCompatGatewayRegistry.gateway().createChatPacket(line);
                Bridge.invoke(networkManager, "queue", chatPacket);
            }
            return;
        } else if (isLowPriorityPacket(packet)) {
            chunkCompressionDispatchBridge.sendPacket(entityPlayer, packet);
            return;
        }

        Bridge.invoke(networkManager, "queue", packet);
    }

    public boolean isLowPriorityPacket(Object packet) {
        Object flag = Bridge.getField(packet, "k");
        return Boolean.TRUE.equals(flag);
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

        private static void setField(Object target, String name, Object value) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(name);
                field.setAccessible(true);
                field.set(target, value);
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

    }
}
