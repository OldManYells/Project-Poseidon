package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WorldLoader implements Convertable {

    protected final File a;

    public WorldLoader(File file1) {
        if (!file1.exists()) {
            file1.mkdirs();
        }
        this.a = file1;
    }

    public WorldData b(String s) {
        File file1 = new File(this.a, s);
        if (!file1.exists()) {
            return null;
        }

        File primary = new File(file1, "level.dat");
        if (primary.exists()) {
            WorldData worldData = this.loadWorldDataFromFile(primary);
            if (worldData != null) {
                return worldData;
            }
        }

        File backup = new File(file1, "level.dat_old");
        if (backup.exists()) {
            return this.loadWorldDataFromFile(backup);
        }

        return null;
    }

    protected static void a(File[] afile) {
        for (int i = 0; i < afile.length; ++i) {
            if (afile[i].isDirectory()) {
                a(afile[i].listFiles());
            }
            afile[i].delete();
        }
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

    private WorldData loadWorldDataFromFile(File dataFile) {
        try {
            NBTTagCompound rootTag = CompressedStreamTools.a((InputStream) (new FileInputStream(dataFile)));
            NBTTagCompound dataTag = rootTag.k("Data");
            return new WorldData(dataTag);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
