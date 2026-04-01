package com.legacyminecraft.poseidon.event;
import com.legacyminecraft.poseidon.packets.*;

import com.legacyminecraft.poseidon.packets.Packet;

public class PlayerSendPacketEvent extends PlayerPacketEvent {

    public PlayerSendPacketEvent(String username, Packet packet) {
        super(Type.PLAYER_SEND_PACKET, username, packet);
    }
}
