package com.legacyminecraft.poseidon.world.core;
import com.legacyminecraft.poseidon.api.*;
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

public class ChunkPosition {

    public final int x;
    public final int y;
    public final int z;

    public ChunkPosition(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ChunkPosition)) {
            return false;
        } else {
            ChunkPosition chunkposition = (ChunkPosition) object;

            return chunkposition.x == this.x && chunkposition.y == this.y && chunkposition.z == this.z;
        }
    }

    public int hashCode() {
        return this.x * 8976890 + this.y * 981131 + this.z;
    }
}
