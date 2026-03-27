package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftPlayer messaging/kick/send-packet bridge policy.
 */
public final class PlayerMessagingBridgeBehaviour {
    private static final PlayerMessagingBridgeBehaviour INSTANCE = new PlayerMessagingBridgeBehaviour();

    private PlayerMessagingBridgeBehaviour() {
    }

    public static PlayerMessagingBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void sendRawMessage(Object netServerHandler, String message, String playerName) {
        try {
            Object packet = createPacket3Chat(message);
            BridgeReflection.invoke(netServerHandler, "sendPacket", packet);
        } catch (Exception exception) {
            System.out.println("[Poseidon] Exception thrown when attempting to send packet to "
                    + playerName + ". Does this player exist, or are they a phantom?????");
            exception.printStackTrace();
        }
    }

    public void kickIfOnline(boolean online, Object netServerHandler, String message) {
        boolean disconnected = Boolean.TRUE.equals(BridgeReflection.getField(netServerHandler, "disconnected"));
        if (online && !disconnected) {
            BridgeReflection.invoke(netServerHandler, "disconnect", message == null ? "" : message);
        }
    }

    public void sendPacketIfOnline(Object player, Object netServerHandler, Object packet) {
        boolean online = (Boolean) BridgeReflection.invoke(player, "isOnline");
        if (online) {
            BridgeReflection.invoke(netServerHandler, "sendPacket", packet);
        }
    }

    private Object createPacket3Chat(String message) {
        try {
            Class<?> packetType = Class.forName("net.minecraft.server.Packet3Chat");
            java.lang.reflect.Constructor<?> constructor = packetType.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            return constructor.newInstance(message);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to create Packet3Chat", exception);
        }
    }
}
