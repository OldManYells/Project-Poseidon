package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ServerNbtManagerBehaviour;

import java.io.File;
import java.util.List;

public class ServerNBTManager extends PlayerNBTManager {
    private static final ServerNbtManagerBehaviour SERVER_NBT_MANAGER_BEHAVIOUR = ServerNbtManagerBehaviour.getInstance();

    public ServerNBTManager(File file1, String s, boolean flag) {
        super(file1, s, flag);
    }

    public IChunkLoader a(WorldProvider worldprovider) {
        return SERVER_NBT_MANAGER_BEHAVIOUR.createChunkLoader(this.a(), worldprovider);
    }

    public void a(WorldData worlddata, List list) {
        SERVER_NBT_MANAGER_BEHAVIOUR.stampWorldVersion(worlddata, list);
        super.a(worlddata, list);
    }

    public void e() {
        SERVER_NBT_MANAGER_BEHAVIOUR.flushRegionCache();
    }
}
