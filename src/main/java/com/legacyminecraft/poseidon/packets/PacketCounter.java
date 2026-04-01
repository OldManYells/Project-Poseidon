package com.legacyminecraft.poseidon.packets;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

class PacketCounter {

    private int a;
    private long b;

    PacketCounter() {}

    public void a(int i) {
        ++this.a;
        this.b += (long) i;
    }

}
