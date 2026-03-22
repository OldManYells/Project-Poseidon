package com.legacyminecraft.poseidon.block;

import java.util.List;

/**
 * Canonical state and rule evaluation for legacy redstone torch wrappers.
 */
public final class RedstoneTorchStateBehaviour {
    private static final RedstoneTorchStateBehaviour INSTANCE = new RedstoneTorchStateBehaviour();

    private RedstoneTorchStateBehaviour() {
    }

    public static RedstoneTorchStateBehaviour getInstance() {
        return INSTANCE;
    }

    public void notifyAdjacentBlocks(NeighborNotifier notifier, int x, int y, int z, int blockId) {
        notifier.applyPhysics(x, y - 1, z, blockId);
        notifier.applyPhysics(x, y + 1, z, blockId);
        notifier.applyPhysics(x - 1, y, z, blockId);
        notifier.applyPhysics(x + 1, y, z, blockId);
        notifier.applyPhysics(x, y, z - 1, blockId);
        notifier.applyPhysics(x, y, z + 1, blockId);
    }

    public boolean canProvidePower(boolean torchIsOn, int attachmentData, int queryFace) {
        if (!torchIsOn) {
            return false;
        }

        return !(attachmentData == 5 && queryFace == 1
                || attachmentData == 3 && queryFace == 3
                || attachmentData == 4 && queryFace == 2
                || attachmentData == 1 && queryFace == 5
                || attachmentData == 2 && queryFace == 4);
    }

    public boolean isReceivingIndirectPower(int attachmentData, int x, int y, int z, IndirectPowerQuery query) {
        return attachmentData == 5 && query.isBlockFaceIndirectlyPowered(x, y - 1, z, 0)
                || attachmentData == 3 && query.isBlockFaceIndirectlyPowered(x, y, z - 1, 2)
                || attachmentData == 4 && query.isBlockFaceIndirectlyPowered(x, y, z + 1, 3)
                || attachmentData == 1 && query.isBlockFaceIndirectlyPowered(x - 1, y, z, 4)
                || attachmentData == 2 && query.isBlockFaceIndirectlyPowered(x + 1, y, z, 5);
    }

    public void purgeExpiredUpdates(List updates, long now, long maxAge) {
        while (updates.size() > 0 && now - ((UpdateInfo) updates.get(0)).getTime() > maxAge) {
            updates.remove(0);
        }
    }

    public boolean recordAndCheckBurnout(List updates, int x, int y, int z, long timestamp, boolean record, int threshold) {
        if (record) {
            updates.add(new UpdateInfo(x, y, z, timestamp));
        }

        int matches = 0;
        for (int i = 0; i < updates.size(); ++i) {
            UpdateInfo update = (UpdateInfo) updates.get(i);
            if (update.getX() == x && update.getY() == y && update.getZ() == z) {
                ++matches;
                if (matches >= threshold) {
                    return true;
                }
            }
        }
        return false;
    }

    public interface NeighborNotifier {
        void applyPhysics(int x, int y, int z, int blockId);
    }

    public interface IndirectPowerQuery {
        boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face);
    }

    public static final class UpdateInfo {
        private final int x;
        private final int y;
        private final int z;
        private final long time;

        public UpdateInfo(int x, int y, int z, long time) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.time = time;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public long getTime() {
            return time;
        }
    }
}
