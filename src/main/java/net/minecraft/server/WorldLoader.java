package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldLoaderSystem;

import java.io.File;

public class WorldLoader implements Convertable {

    protected final File a;
    private final WorldLoaderSystem worldLoaderSystem = WorldLoaderSystem.getInstance();

    public WorldLoader(File file1) {
        worldLoaderSystem.ensureRootExists(file1);
        this.a = file1;
    }

    public WorldData b(String s) {
        return worldLoaderSystem.loadWorldData(this.a, s);
    }

    protected static void a(File[] afile) {
        WorldLoaderSystem.getInstance().deleteTree(afile);
    }

    public IDataManager a(String s, boolean flag) {
        return new PlayerNBTManager(this.a, s, flag);
    }

    public boolean isConvertable(String s) {
        return false;
    }

    public boolean convert(String s, IProgressUpdate iprogressupdate) {
        return false;
    }
}
