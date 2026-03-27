package com.legacyminecraft.poseidon.network;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Bridge contract implemented by legacy wrappers so Poseidon network code does not reference legacy packages.
 */
public interface NetworkCompatGateway {
    String allowedCharacters();

    boolean isNetServerHandler(Object handler);

    Object createPlayerReceivePacketEvent(String username, Object packet);

    Object createPlayerSendPacketEvent(String username, Object packet);

    Object createPacketReceivedEvent(Object bukkitPlayer, Object packet);

    void callGlobalEvent(Object event);

    Object createPlayerChatEvent(Object player, String message);

    Object createPlayerCommandPreprocessEvent(Object player, String message);

    Object createPlayerItemHeldEvent(Object player, int previousSlot, int newSlot);

    Object createPlayerKickEvent(Object bukkitPlayer, String reason, String leaveMessage);

    Object createPlayerMoveEvent(Object player, Object from, Object to);

    Object createPlayerTeleportEvent(Object player, Object from, Object to);

    Object createLocation(Object world, double x, double y, double z);

    Object createLocation(Object world, double x, double y, double z, float yaw, float pitch);

    Object createChatPacket(String message);

    Object createKickPacket(String reason);

    Object createHandshakePacket(String token);

    Object createTransactionPacket(int windowId, short actionNumber, boolean accepted);

    Object createNbtTagCompound();

    Object createNbtTagList();

    Object createItemStackFromNbt(Object nbtTagCompound);

    Object[] createItemStackArray(int length);

    Object getFurnaceRecipeResult(int itemId);

    Object createNetServerHandler(Object minecraftServer, Object networkManager, Object entityPlayer);

    Object createLoginPacket(String username, int entityId, long worldSeed, byte dimension);

    Object createSpawnPositionPacket(int x, int y, int z);

    Object createUpdateTimePacket(long playerTime);

    void writePacket(Object packet, DataOutputStream output) throws IOException;
}
