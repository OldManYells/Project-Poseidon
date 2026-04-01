package com.legacyminecraft.poseidon.event;

import com.legacy.minecraft.poseidon.Packet;

public class PlayerSendPacketEvent extends PlayerPacketEvent {

    public PlayerSendPacketEvent(String username, Packet packet) {
        super(Type.PLAYER_SEND_PACKET, username, packet);
    }
}
