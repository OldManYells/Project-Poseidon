package com.legacyminecraft.poseidon.world.map;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

public class WorldMapOrienter {

    public byte a;
    public byte b;
    public byte c;
    public byte d;

    final WorldMap e;

    public WorldMapOrienter(WorldMap worldmap, byte b0, byte b1, byte b2, byte b3) {
        this.e = worldmap;
        this.a = b0;
        this.b = b1;
        this.c = b2;
        this.d = b3;
    }
}
