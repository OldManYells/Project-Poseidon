package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldFormatConversionSystem;

import java.io.File;

public class WorldLoaderServer extends WorldLoader {
    private static final int CONVERTED_WORLD_VERSION = 19132;
    private static final WorldFormatConversionSystem WORLD_FORMAT_CONVERSION_SYSTEM = WorldFormatConversionSystem.getInstance();

    public WorldLoaderServer(File file1) {
        super(file1);
    }

    public IDataManager a(String s, boolean flag) {
        return WORLD_FORMAT_CONVERSION_SYSTEM.createServerDataManager(this.a, s, flag);
    }

    public boolean isConvertable(String s) {
        WorldData worlddata = this.b(s);

        return WORLD_FORMAT_CONVERSION_SYSTEM.isLegacyFormatConvertable(worlddata);
    }

    public boolean convert(String s, IProgressUpdate iprogressupdate) {
        iprogressupdate.a(0);
        System.out.println("Scanning folders...");
        WorldFormatConversionSystem.ConversionWorkload workload =
                WORLD_FORMAT_CONVERSION_SYSTEM.scanConversionWorkload(this.a, s);
        int totalConversionCount = WORLD_FORMAT_CONVERSION_SYSTEM.totalConversionCount(workload);

        System.out.println("Total conversion count is " + totalConversionCount);
        int convertedCount = 0;
        convertedCount = WORLD_FORMAT_CONVERSION_SYSTEM.convertChunks(
                workload.getWorldDirectory(),
                workload.getOverworldChunkFiles(),
                convertedCount,
                totalConversionCount,
                iprogressupdate
        );
        convertedCount = WORLD_FORMAT_CONVERSION_SYSTEM.convertChunks(
                workload.getNetherDirectory(),
                workload.getNetherChunkFiles(),
                convertedCount,
                totalConversionCount,
                iprogressupdate
        );
        WorldData worlddata = this.b(s);

        WORLD_FORMAT_CONVERSION_SYSTEM.stampConvertedWorldVersion(worlddata, CONVERTED_WORLD_VERSION);
        IDataManager idatamanager = this.a(s, false);

        idatamanager.a(worlddata);
        convertedCount = WORLD_FORMAT_CONVERSION_SYSTEM.cleanupConvertedFolders(
                workload.getOverworldFolders(),
                convertedCount,
                totalConversionCount,
                iprogressupdate
        );
        if (workload.getNetherDirectory().exists()) {
            WORLD_FORMAT_CONVERSION_SYSTEM.cleanupConvertedFolders(
                    workload.getNetherFolders(),
                    convertedCount,
                    totalConversionCount,
                    iprogressupdate
            );
        }

        return true;
    }
}
