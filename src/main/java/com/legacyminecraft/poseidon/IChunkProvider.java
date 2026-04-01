package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;

public interface IChunkProvider {

    boolean isChunkLoaded(int i, int j);

    Chunk getOrCreateChunk(int i, int j);

    Chunk getChunkAt(int i, int j);

    void getChunkAt(IChunkProvider ichunkprovider, int i, int j);

    boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate);

    boolean unloadChunks();

    boolean canSave();
}
