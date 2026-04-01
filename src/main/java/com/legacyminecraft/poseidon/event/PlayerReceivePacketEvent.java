package com.legacyminecraft.poseidon.event;

import com.legacy.minecraft.poseidon.Packet;

public class PlayerReceivePacketEvent extends PlayerPacketEvent {
    public PlayerReceivePacketEvent(String username, Packet packet) {
        super(Type.PLAYER_RECEIVE_PACKET, username, packet);
    }
}
