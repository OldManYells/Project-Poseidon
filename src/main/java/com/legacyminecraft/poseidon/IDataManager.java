package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.world.generation.WorldProvider;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface IDataManager {

    WorldData c();

    void b();

    IChunkLoader a(WorldProvider worldprovider);

    void a(WorldData worlddata, List list);

    void a(WorldData worlddata);

    PlayerFileData d();

    void e();

    File b(String s);

    UUID getUUID(); // CraftBukkit
}
