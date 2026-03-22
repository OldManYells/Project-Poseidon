package net.minecraft.server;

import com.legacyminecraft.poseidon.world.RegionChunkCompressionBehaviour;
import com.legacyminecraft.poseidon.world.RegionChunkIndexBehaviour;
import com.legacyminecraft.poseidon.world.RegionChunkLocationBehaviour;
import com.legacyminecraft.poseidon.world.RegionChunkReadValidationBehaviour;
import com.legacyminecraft.poseidon.world.RegionChunkReadOrchestrationBehaviour;
import com.legacyminecraft.poseidon.world.RegionChunkWriteCommitBehaviour;
import com.legacyminecraft.poseidon.world.RegionChunkWritePlanBehaviour;
import com.legacyminecraft.poseidon.world.RegionFileBootstrapBehaviour;
import com.legacyminecraft.poseidon.world.RegionHeaderTableCodecBehaviour;
import com.legacyminecraft.poseidon.world.RegionSaveStrategyBehaviour;
import com.legacyminecraft.poseidon.world.RegionSectorAllocationBehaviour;
import com.legacyminecraft.poseidon.world.RegionSectorGrowBehaviour;
import com.legacyminecraft.poseidon.world.RegionSectorMapBehaviour;
import com.legacyminecraft.poseidon.world.RegionSectorWriteBehaviour;
import com.legacyminecraft.poseidon.world.RegionTimestampBehaviour;
import com.legacyminecraft.poseidon.world.RegionWriteStatsBehaviour;

import java.io.*;
import java.util.ArrayList;

public class RegionFile {
    private static final RegionChunkCompressionBehaviour REGION_CHUNK_COMPRESSION_BEHAVIOUR = RegionChunkCompressionBehaviour.getInstance();
    private static final RegionChunkIndexBehaviour REGION_CHUNK_INDEX_BEHAVIOUR = RegionChunkIndexBehaviour.getInstance();
    private static final RegionChunkLocationBehaviour REGION_CHUNK_LOCATION_BEHAVIOUR = RegionChunkLocationBehaviour.getInstance();
    private static final RegionChunkReadValidationBehaviour REGION_CHUNK_READ_VALIDATION_BEHAVIOUR = RegionChunkReadValidationBehaviour.getInstance();
    private static final RegionChunkReadOrchestrationBehaviour REGION_CHUNK_READ_ORCHESTRATION_BEHAVIOUR = RegionChunkReadOrchestrationBehaviour.getInstance();
    private static final RegionChunkWriteCommitBehaviour REGION_CHUNK_WRITE_COMMIT_BEHAVIOUR = RegionChunkWriteCommitBehaviour.getInstance();
    private static final RegionChunkWritePlanBehaviour REGION_CHUNK_WRITE_PLAN_BEHAVIOUR = RegionChunkWritePlanBehaviour.getInstance();
    private static final RegionFileBootstrapBehaviour REGION_FILE_BOOTSTRAP_BEHAVIOUR = RegionFileBootstrapBehaviour.getInstance();
    private static final RegionHeaderTableCodecBehaviour REGION_HEADER_TABLE_CODEC_BEHAVIOUR = RegionHeaderTableCodecBehaviour.getInstance();
    private static final RegionSaveStrategyBehaviour REGION_SAVE_STRATEGY_BEHAVIOUR = RegionSaveStrategyBehaviour.getInstance();
    private static final RegionSectorAllocationBehaviour REGION_SECTOR_ALLOCATION_BEHAVIOUR = RegionSectorAllocationBehaviour.getInstance();
    private static final RegionSectorGrowBehaviour REGION_SECTOR_GROW_BEHAVIOUR = RegionSectorGrowBehaviour.getInstance();
    private static final RegionSectorMapBehaviour REGION_SECTOR_MAP_BEHAVIOUR = RegionSectorMapBehaviour.getInstance();
    private static final RegionSectorWriteBehaviour REGION_SECTOR_WRITE_BEHAVIOUR = RegionSectorWriteBehaviour.getInstance();
    private static final RegionTimestampBehaviour REGION_TIMESTAMP_BEHAVIOUR = RegionTimestampBehaviour.getInstance();
    private static final RegionWriteStatsBehaviour REGION_WRITE_STATS_BEHAVIOUR = RegionWriteStatsBehaviour.getInstance();

    private static final byte[] a = new byte[4096];
    private final File b;
    private RandomAccessFile c;
    private final int[] d = new int[1024];
    private final int[] e = new int[1024];
    private ArrayList f;
    private int g;
    private long h = 0L;

    public RegionFile(File file1) {
        this.b = file1;
        this.b("REGION LOAD " + this.b);
        this.g = 0;

        try {
            if (file1.exists()) {
                this.h = file1.lastModified();
            }

            this.c = new RandomAccessFile(file1, "rw");
            this.g += REGION_FILE_BOOTSTRAP_BEHAVIOUR.ensureHeaderTables(this.c);
            REGION_FILE_BOOTSTRAP_BEHAVIOUR.alignToSectorBoundary(this.c);

            int i = (int) this.c.length() / 4096;
            this.f = REGION_FILE_BOOTSTRAP_BEHAVIOUR.createSectorUsageMap(i);
            int j;
            this.c.seek(0L);

            REGION_HEADER_TABLE_CODEC_BEHAVIOUR.readTable(this.c, this.d);
            for (j = 0; j < 1024; ++j) {
                int k = this.d[j];
                REGION_CHUNK_LOCATION_BEHAVIOUR.markAllocatedSectorsForLocation(this.f, k);
            }

            REGION_HEADER_TABLE_CODEC_BEHAVIOUR.readTable(this.c, this.e);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public synchronized int a() {
        int i = REGION_WRITE_STATS_BEHAVIOUR.pullAndResetBytesWritten(this.g);

        this.g = 0;
        return i;
    }

    private void a(String s) {}

    private void b(String s) {
        this.a(s + "\n");
    }

    private void a(String s, int i, int j, String s1) {
        this.a("REGION " + s + " " + this.b.getName() + "[" + i + "," + j + "] = " + s1);
    }

    private void a(String s, int i, int j, int k, String s1) {
        this.a("REGION " + s + " " + this.b.getName() + "[" + i + "," + j + "] " + k + "B = " + s1);
    }

    private void b(String s, int i, int j, String s1) {
        this.a(s, i, j, s1 + "\n");
    }

    public synchronized DataInputStream a(int i, int j) {
        if (this.d(i, j)) {
            this.b("READ", i, j, "out of bounds");
            return null;
        } else {
            try {
                int k = this.e(i, j);

                if (k == 0) {
                    return null;
                } else {
                    RegionChunkReadOrchestrationBehaviour.ReadOutcome readOutcome =
                            REGION_CHUNK_READ_ORCHESTRATION_BEHAVIOUR.readChunkInputStream(
                                    k,
                                    this.f.size(),
                                    this.c,
                                    REGION_CHUNK_LOCATION_BEHAVIOUR,
                                    REGION_CHUNK_READ_VALIDATION_BEHAVIOUR,
                                    REGION_CHUNK_COMPRESSION_BEHAVIOUR
                            );
                    if (!readOutcome.isSuccessful()) {
                        this.b("READ", i, j, readOutcome.getFailureReason());
                        return null;
                    }

                    return readOutcome.getStream();
                }
            } catch (IOException ioexception) {
                this.b("READ", i, j, "exception");
                return null;
            }
        }
    }

    public DataOutputStream b(int i, int j) {
        return REGION_CHUNK_COMPRESSION_BEHAVIOUR.createChunkOutputStream(this.d(i, j), new ChunkBuffer(this, i, j));
    }

    public final void poseidonWriteChunkData(int i, int j, byte[] abyte, int k) {
        this.a(i, j, abyte, k);
    }

    protected synchronized void a(int i, int j, byte[] abyte, int k) {
        try {
            int l = this.e(i, j);
            RegionChunkWritePlanBehaviour.WritePlan writePlan = REGION_CHUNK_WRITE_PLAN_BEHAVIOUR.planWrite(
                    l,
                    k,
                    this.f,
                    REGION_CHUNK_LOCATION_BEHAVIOUR,
                    REGION_SECTOR_ALLOCATION_BEHAVIOUR,
                    REGION_SECTOR_MAP_BEHAVIOUR,
                    REGION_SAVE_STRATEGY_BEHAVIOUR
            );
            REGION_CHUNK_WRITE_COMMIT_BEHAVIOUR.commitWrite(
                    i,
                    j,
                    abyte,
                    k,
                    writePlan,
                    this.f,
                    this.c,
                    a,
                    REGION_CHUNK_LOCATION_BEHAVIOUR,
                    REGION_SECTOR_GROW_BEHAVIOUR,
                    REGION_SECTOR_MAP_BEHAVIOUR,
                    REGION_TIMESTAMP_BEHAVIOUR,
                    new RegionChunkWriteCommitBehaviour.CommitCallbacks() {
                        public void logSave(int payloadSizeBytes, String strategyName) {
                            RegionFile.this.a("SAVE", i, j, payloadSizeBytes, strategyName);
                        }

                        public void writeChunkPayload(int sectorOffset, byte[] compressedPayload, int payloadSizeBytes) throws IOException {
                            RegionFile.this.a(sectorOffset, compressedPayload, payloadSizeBytes);
                        }

                        public void writeChunkLocation(int chunkX, int chunkZ, int packedLocation) throws IOException {
                            RegionFile.this.a(chunkX, chunkZ, packedLocation);
                        }

                        public void writeChunkTimestamp(int chunkX, int chunkZ, int timestampSeconds) throws IOException {
                            RegionFile.this.b(chunkX, chunkZ, timestampSeconds);
                        }

                        public void incrementWrittenBytes(int bytesAdded) {
                            RegionFile.this.g += bytesAdded;
                        }
                    }
            );
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private void a(int i, byte[] abyte, int j) throws IOException {
        this.b(" " + i);
        REGION_SECTOR_WRITE_BEHAVIOUR.writeCompressedChunk(this.c, i, abyte, j);
    }

    private boolean d(int i, int j) {
        return REGION_CHUNK_INDEX_BEHAVIOUR.isOutOfBounds(i, j);
    }

    private int e(int i, int j) {
        return REGION_CHUNK_INDEX_BEHAVIOUR.getChunkOffset(this.d, i, j);
    }

    public boolean c(int i, int j) {
        return REGION_CHUNK_INDEX_BEHAVIOUR.hasChunk(this.d, i, j);
    }

    private void a(int i, int j, int k) throws IOException {
        int tableIndex = REGION_CHUNK_INDEX_BEHAVIOUR.toTableIndex(i, j);
        this.d[tableIndex] = k;
        REGION_HEADER_TABLE_CODEC_BEHAVIOUR.writeTableEntry(this.c, 0L, tableIndex, k);
    }

    private void b(int i, int j, int k) throws IOException {
        int tableIndex = REGION_CHUNK_INDEX_BEHAVIOUR.toTableIndex(i, j);
        this.e[tableIndex] = k;
        REGION_HEADER_TABLE_CODEC_BEHAVIOUR.writeTableEntry(this.c, 4096L, tableIndex, k);
    }

    public void b() throws IOException {
        this.c.close();
    }
}
