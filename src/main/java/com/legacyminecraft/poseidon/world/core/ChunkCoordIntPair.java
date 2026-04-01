package com.legacyminecraft.poseidon.world.core;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.server.network.*;
import com.legacyminecraft.poseidon.world.block.*;
import com.legacyminecraft.poseidon.world.block.entity.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.generation.*;
import com.legacyminecraft.poseidon.world.inventory.*;
import com.legacyminecraft.poseidon.world.item.*;
import com.legacyminecraft.poseidon.world.map.*;
import com.legacyminecraft.poseidon.world.storage.*;
import com.legacyminecraft.poseidon.world.storage.nbt.*;

public class ChunkCoordIntPair {

    public final int x;
    public final int z;

    public ChunkCoordIntPair(int i, int j) {
        this.x = i;
        this.z = j;
    }

    public static int a(int i, int j) {
        return (i < 0 ? Integer.MIN_VALUE : 0) | (i & 32767) << 16 | (j < 0 ? '\u8000' : 0) | j & 32767;
    }

    public int hashCode() {
        return a(this.x, this.z);
    }

    public boolean equals(Object object) {
        ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) object;

        return chunkcoordintpair.x == this.x && chunkcoordintpair.z == this.z;
    }
}
