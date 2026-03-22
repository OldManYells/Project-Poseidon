package com.legacyminecraft.poseidon.block;

import net.minecraft.server.NBTTagCompound;

/**
 * Canonical NBT serialization behaviour for piston tile-entity state.
 */
public final class PistonTileNbtBehaviour {
    private static final PistonTileNbtBehaviour INSTANCE = new PistonTileNbtBehaviour();

    private PistonTileNbtBehaviour() {
    }

    public static PistonTileNbtBehaviour getInstance() {
        return INSTANCE;
    }

    public PistonTileState readState(NBTTagCompound nbt) {
        int movedBlockId = nbt.e("blockId");
        int movedBlockData = nbt.e("blockData");
        int facing = nbt.e("facing");
        float progress = nbt.g("progress");
        boolean extending = nbt.m("extending");
        return new PistonTileState(movedBlockId, movedBlockData, facing, progress, extending);
    }

    public void writeState(NBTTagCompound nbt,
                           int movedBlockId,
                           int movedBlockData,
                           int facing,
                           float progress,
                           boolean extending) {
        nbt.a("blockId", movedBlockId);
        nbt.a("blockData", movedBlockData);
        nbt.a("facing", facing);
        nbt.a("progress", progress);
        nbt.a("extending", extending);
    }

    public static final class PistonTileState {
        private final int movedBlockId;
        private final int movedBlockData;
        private final int facing;
        private final float progress;
        private final boolean extending;

        public PistonTileState(int movedBlockId, int movedBlockData, int facing, float progress, boolean extending) {
            this.movedBlockId = movedBlockId;
            this.movedBlockData = movedBlockData;
            this.facing = facing;
            this.progress = progress;
            this.extending = extending;
        }

        public int getMovedBlockId() {
            return movedBlockId;
        }

        public int getMovedBlockData() {
            return movedBlockData;
        }

        public int getFacing() {
            return facing;
        }

        public float getProgress() {
            return progress;
        }

        public boolean isExtending() {
            return extending;
        }
    }
}
