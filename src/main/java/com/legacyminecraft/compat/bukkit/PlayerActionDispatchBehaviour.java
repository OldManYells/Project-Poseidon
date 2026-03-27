package com.legacyminecraft.compat.bukkit;


import java.util.List;

/**
 * Canonical behavior for CraftPlayer action and packet dispatch bridge operations.
 */
public final class PlayerActionDispatchBehaviour {
    private static final PlayerActionDispatchBehaviour INSTANCE = new PlayerActionDispatchBehaviour();

    private PlayerActionDispatchBehaviour() {
    }

    public static PlayerActionDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void chat(Object player, String message) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        BridgeReflection.invoke(netServerHandler, "chat", message);
    }

    public boolean performCommand(Object server, Object player, String command) {
        return (Boolean) BridgeReflection.invoke(server, "dispatchCommand", player, command);
    }

    public void setCompassTarget(Object player, Object location, PlayerMapPacketBehaviour mapPacketBehaviour) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        BridgeReflection.invoke(netServerHandler, "sendPacket", mapPacketBehaviour.createCompassPacket(BridgeReflection.cast(location)));
    }

    public void playRawNote(
            Object player,
            Object location,
            byte instrument,
            byte note,
            PlayerLocalEffectPacketBehaviour localEffectPacketBehaviour
    ) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        BridgeReflection.invoke(netServerHandler, "sendPacket",
                localEffectPacketBehaviour.createPlayNotePacket(BridgeReflection.cast(location), instrument, note));
    }

    public void playEffect(
            Object player,
            Object location,
            Object effect,
            int data,
            PlayerLocalEffectPacketBehaviour localEffectPacketBehaviour
    ) {
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        BridgeReflection.invoke(netServerHandler, "sendPacket",
                localEffectPacketBehaviour.createEffectPacket(BridgeReflection.cast(location), BridgeReflection.cast(effect), data));
    }

    public void sendBlockChange(
            Object player,
            Object location,
            int material,
            byte data,
            PlayerLocalEffectPacketBehaviour localEffectPacketBehaviour
    ) {
        Object packet = localEffectPacketBehaviour.createBlockChangePacket(BridgeReflection.cast(location), material, data);
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        BridgeReflection.invoke(netServerHandler, "sendPacket", packet);
    }

    public boolean sendChunkChange(
            Object player,
            Object location,
            int sx,
            int sy,
            int sz,
            byte[] data,
            PlayerChunkChangePacketBehaviour chunkChangePacketBehaviour
    ) {
        Object packet = chunkChangePacketBehaviour.createChunkChangePacket(BridgeReflection.cast(location), sx, sy, sz, data);
        if (packet == null) {
            return false;
        }
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        BridgeReflection.invoke(netServerHandler, "sendPacket", packet);
        return true;
    }

    public void sendMap(
            Object player,
            short mapId,
            Object renderData,
            PlayerMapPacketBehaviour mapPacketBehaviour
    ) {
        List<Object> packets = mapPacketBehaviour.createMapPackets(mapId, renderData);
        Object netServerHandler = BridgeReflection.getField(player, "netServerHandler");
        for (Object packet : packets) {
            BridgeReflection.invoke(netServerHandler, "sendPacket", packet);
        }
    }
}
