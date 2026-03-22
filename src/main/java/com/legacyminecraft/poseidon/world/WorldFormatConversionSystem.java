package com.legacyminecraft.poseidon.world;

import net.minecraft.server.ChunkFile;
import net.minecraft.server.IDataManager;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.RegionFile;
import net.minecraft.server.RegionFileCache;
import net.minecraft.server.ServerNBTManager;
import net.minecraft.server.WorldData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Canonical legacy world-format conversion system for WorldLoaderServer wrappers.
 */
public final class WorldFormatConversionSystem {
    private static final WorldFormatConversionSystem INSTANCE = new WorldFormatConversionSystem();

    private static final Pattern CHUNK_FOLDER_PATTERN = Pattern.compile("[0-9a-z]|([0-9a-z][0-9a-z])");
    private static final Pattern CHUNK_FILE_PATTERN = Pattern.compile("c\\.(-?[0-9a-z]+)\\.(-?[0-9a-z]+)\\.dat");
    private static final byte[] COPY_BUFFER = new byte[4096];

    private WorldFormatConversionSystem() {
    }

    public static WorldFormatConversionSystem getInstance() {
        return INSTANCE;
    }

    public IDataManager createServerDataManager(File rootDirectory, String worldName, boolean saveEnabled) {
        return new ServerNBTManager(rootDirectory, worldName, saveEnabled);
    }

    public boolean isLegacyFormatConvertable(WorldData worldData) {
        return worldData != null && worldData.i() == 0;
    }

    public ConversionWorkload scanConversionWorkload(File rootDirectory, String worldName) {
        File worldDirectory = new File(rootDirectory, worldName);
        File netherDirectory = new File(worldDirectory, "DIM-1");

        ArrayList overworldChunkFiles = new ArrayList();
        ArrayList netherChunkFiles = new ArrayList();
        ArrayList overworldFolders = new ArrayList();
        ArrayList netherFolders = new ArrayList();

        scanChunkFolders(worldDirectory, overworldChunkFiles, overworldFolders);
        if (netherDirectory.exists()) {
            scanChunkFolders(netherDirectory, netherChunkFiles, netherFolders);
        }

        return new ConversionWorkload(
                worldDirectory,
                netherDirectory,
                overworldChunkFiles,
                netherChunkFiles,
                overworldFolders,
                netherFolders
        );
    }

    public int convertChunks(File worldDirectory, List chunkFiles, int convertedCount, int totalCount, IProgressUpdate progress) {
        Collections.sort(chunkFiles);
        Iterator iterator = chunkFiles.iterator();

        while (iterator.hasNext()) {
            ChunkFile chunkFile = (ChunkFile) iterator.next();
            int chunkX = chunkFile.b();
            int chunkZ = chunkFile.c();
            RegionFile regionFile = RegionFileCache.a(worldDirectory, chunkX, chunkZ);

            if (!regionFile.c(chunkX & 31, chunkZ & 31)) {
                copyChunkData(chunkFile, regionFile, chunkX, chunkZ);
            }

            ++convertedCount;
            progress.a(progressPercent(convertedCount, totalCount));
        }

        RegionFileCache.a();
        return convertedCount;
    }

    public int cleanupConvertedFolders(List folders, int convertedCount, int totalCount, IProgressUpdate progress) {
        Iterator iterator = folders.iterator();

        while (iterator.hasNext()) {
            File folder = (File) iterator.next();
            File[] nestedFiles = folder.listFiles();
            deleteTree(nestedFiles);
            folder.delete();
            ++convertedCount;
            progress.a(progressPercent(convertedCount, totalCount));
        }

        return convertedCount;
    }

    public int totalConversionCount(ConversionWorkload workload) {
        return workload.overworldChunkFiles.size()
                + workload.netherChunkFiles.size()
                + workload.overworldFolders.size()
                + workload.netherFolders.size();
    }

    public void stampConvertedWorldVersion(WorldData worldData, int version) {
        worldData.a(version);
    }

    private void scanChunkFolders(File rootDirectory, List chunkFiles, List folders) {
        File[] levelOne = rootDirectory.listFiles();
        if (levelOne == null) {
            return;
        }

        for (File xFolder : levelOne) {
            if (!isChunkFolder(xFolder)) {
                continue;
            }

            folders.add(xFolder);
            File[] levelTwo = xFolder.listFiles();
            if (levelTwo == null) {
                continue;
            }

            for (File zFolder : levelTwo) {
                if (!isChunkFolder(zFolder)) {
                    continue;
                }

                File[] dataFiles = zFolder.listFiles();
                if (dataFiles == null) {
                    continue;
                }

                for (File dataFile : dataFiles) {
                    if (isChunkDataFile(dataFile)) {
                        chunkFiles.add(new ChunkFile(dataFile));
                    }
                }
            }
        }
    }

    private boolean isChunkFolder(File folder) {
        return folder.isDirectory() && CHUNK_FOLDER_PATTERN.matcher(folder.getName()).matches();
    }

    private boolean isChunkDataFile(File file) {
        String fileName = file.getName();
        return file.isFile() && CHUNK_FILE_PATTERN.matcher(fileName).matches();
    }

    private void copyChunkData(ChunkFile chunkFile, RegionFile regionFile, int chunkX, int chunkZ) {
        try {
            DataInputStream input = new DataInputStream(new GZIPInputStream(new FileInputStream(chunkFile.a())));
            DataOutputStream output = regionFile.b(chunkX & 31, chunkZ & 31);
            int bytesRead;
            while ((bytesRead = input.read(COPY_BUFFER)) != -1) {
                output.write(COPY_BUFFER, 0, bytesRead);
            }
            output.close();
            input.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private int progressPercent(int complete, int total) {
        if (total <= 0) {
            return 100;
        }
        return (int) Math.round(100.0D * (double) complete / (double) total);
    }

    private void deleteTree(File[] files) {
        if (files == null) {
            return;
        }

        for (int index = 0; index < files.length; ++index) {
            File file = files[index];
            if (file.isDirectory()) {
                deleteTree(file.listFiles());
            }
            file.delete();
        }
    }

    public static final class ConversionWorkload {
        private final File worldDirectory;
        private final File netherDirectory;
        private final ArrayList overworldChunkFiles;
        private final ArrayList netherChunkFiles;
        private final ArrayList overworldFolders;
        private final ArrayList netherFolders;

        private ConversionWorkload(
                File worldDirectory,
                File netherDirectory,
                ArrayList overworldChunkFiles,
                ArrayList netherChunkFiles,
                ArrayList overworldFolders,
                ArrayList netherFolders
        ) {
            this.worldDirectory = worldDirectory;
            this.netherDirectory = netherDirectory;
            this.overworldChunkFiles = overworldChunkFiles;
            this.netherChunkFiles = netherChunkFiles;
            this.overworldFolders = overworldFolders;
            this.netherFolders = netherFolders;
        }

        public File getWorldDirectory() {
            return worldDirectory;
        }

        public File getNetherDirectory() {
            return netherDirectory;
        }

        public ArrayList getOverworldChunkFiles() {
            return overworldChunkFiles;
        }

        public ArrayList getNetherChunkFiles() {
            return netherChunkFiles;
        }

        public ArrayList getOverworldFolders() {
            return overworldFolders;
        }

        public ArrayList getNetherFolders() {
            return netherFolders;
        }
    }
}
