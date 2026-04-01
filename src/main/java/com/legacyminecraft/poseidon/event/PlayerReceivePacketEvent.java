package com.legacyminecraft.poseidon.event;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.packets.*;

import com.legacyminecraft.poseidon.packets.Packet;

public class PlayerReceivePacketEvent extends PlayerPacketEvent {
    public PlayerReceivePacketEvent(String username, Packet packet) {
        super(Type.PLAYER_RECEIVE_PACKET, username, packet);
    }
}
