package org.bukkit.event.player;

import net.minecraft.server.Packet;

public class PlayerReceivePacketEvent extends PlayerPacketEvent {
    public PlayerReceivePacketEvent(String username, Packet packet) {
        super(Type.PLAYER_RECEIVE_PACKET, username, packet);
    }
}
