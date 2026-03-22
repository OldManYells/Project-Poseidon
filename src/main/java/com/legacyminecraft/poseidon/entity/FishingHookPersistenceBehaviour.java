package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.NBTTagCompound;

/**
 * Canonical NBT persistence behaviour for fishing hook entity state.
 */
public final class FishingHookPersistenceBehaviour {
    private static final FishingHookPersistenceBehaviour INSTANCE = new FishingHookPersistenceBehaviour();

    private FishingHookPersistenceBehaviour() {
    }

    public static FishingHookPersistenceBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeToNbt(NBTTagCompound nbt,
                           int blockX,
                           int blockY,
                           int blockZ,
                           int inTileId,
                           int shakeTicks,
                           boolean inGround) {
        nbt.a("xTile", (short) blockX);
        nbt.a("yTile", (short) blockY);
        nbt.a("zTile", (short) blockZ);
        nbt.a("inTile", (byte) inTileId);
        nbt.a("shake", (byte) shakeTicks);
        nbt.a("inGround", (byte) (inGround ? 1 : 0));
    }

    public HookPersistenceState readFromNbt(NBTTagCompound nbt) {
        int blockX = nbt.d("xTile");
        int blockY = nbt.d("yTile");
        int blockZ = nbt.d("zTile");
        int inTileId = nbt.c("inTile") & 255;
        int shakeTicks = nbt.c("shake") & 255;
        boolean inGround = nbt.c("inGround") == 1;

        return new HookPersistenceState(blockX, blockY, blockZ, inTileId, shakeTicks, inGround);
    }

    public static final class HookPersistenceState {
        private final int blockX;
        private final int blockY;
        private final int blockZ;
        private final int inTileId;
        private final int shakeTicks;
        private final boolean inGround;

        public HookPersistenceState(int blockX, int blockY, int blockZ, int inTileId, int shakeTicks, boolean inGround) {
            this.blockX = blockX;
            this.blockY = blockY;
            this.blockZ = blockZ;
            this.inTileId = inTileId;
            this.shakeTicks = shakeTicks;
            this.inGround = inGround;
        }

        public int getBlockX() {
            return blockX;
        }

        public int getBlockY() {
            return blockY;
        }

        public int getBlockZ() {
            return blockZ;
        }

        public int getInTileId() {
            return inTileId;
        }

        public int getShakeTicks() {
            return shakeTicks;
        }

        public boolean isInGround() {
            return inGround;
        }
    }
}
