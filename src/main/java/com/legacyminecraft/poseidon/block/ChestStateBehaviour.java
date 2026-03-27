package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.inventory.IInventory;
import com.legacyminecraft.poseidon.inventory.InventoryLargeChest;

import java.util.Random;

/**
 * Canonical texture/placement/access/drop policy for legacy chest wrappers.
 */
public final class ChestStateBehaviour {
    private static final ChestStateBehaviour INSTANCE = new ChestStateBehaviour();

    private ChestStateBehaviour() {
    }

    public static ChestStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 1 ? textureId - 1 : (side == 0 ? textureId - 1 : (side == 3 ? textureId + 1 : textureId));
    }

    public int countAdjacentChests(TypeIdQuery query, int x, int y, int z, int chestBlockId) {
        int count = 0;
        if (query.getTypeId(x - 1, y, z) == chestBlockId) {
            ++count;
        }
        if (query.getTypeId(x + 1, y, z) == chestBlockId) {
            ++count;
        }
        if (query.getTypeId(x, y, z - 1) == chestBlockId) {
            ++count;
        }
        if (query.getTypeId(x, y, z + 1) == chestBlockId) {
            ++count;
        }
        return count;
    }

    public boolean hasAdjacentDoubleChest(TypeIdQuery query, int x, int y, int z, int chestBlockId) {
        if (query.getTypeId(x, y, z) != chestBlockId) {
            return false;
        }
        return query.getTypeId(x - 1, y, z) == chestBlockId
                || query.getTypeId(x + 1, y, z) == chestBlockId
                || query.getTypeId(x, y, z - 1) == chestBlockId
                || query.getTypeId(x, y, z + 1) == chestBlockId;
    }

    public boolean canPlace(int adjacentCount, boolean westDouble, boolean eastDouble, boolean northDouble, boolean southDouble) {
        return adjacentCount <= 1 && !westDouble && !eastDouble && !northDouble && !southDouble;
    }

    public boolean shouldBlockAccessFromTop(
            boolean centerBlocked,
            boolean westBlocked,
            boolean eastBlocked,
            boolean northBlocked,
            boolean southBlocked
    ) {
        return centerBlocked || westBlocked || eastBlocked || northBlocked || southBlocked;
    }

    public IInventory composeChestInventory(ChestAccess access, int x, int y, int z, int chestBlockId, IInventory center) {
        IInventory inventory = center;

        if (access.getTypeId(x - 1, y, z) == chestBlockId) {
            inventory = new InventoryLargeChest("Large chest", (IInventory) access.getChest(x - 1, y, z), inventory);
        }

        if (access.getTypeId(x + 1, y, z) == chestBlockId) {
            inventory = new InventoryLargeChest("Large chest", inventory, (IInventory) access.getChest(x + 1, y, z));
        }

        if (access.getTypeId(x, y, z - 1) == chestBlockId) {
            inventory = new InventoryLargeChest("Large chest", (IInventory) access.getChest(x, y, z - 1), inventory);
        }

        if (access.getTypeId(x, y, z + 1) == chestBlockId) {
            inventory = new InventoryLargeChest("Large chest", inventory, (IInventory) access.getChest(x, y, z + 1));
        }

        return inventory;
    }

    public float resolveDropOffset(Random random) {
        return random.nextFloat() * 0.8F + 0.1F;
    }

    public int resolveDropStackChunk(Random random, int remainingCount) {
        int chunkSize = random.nextInt(21) + 10;
        return Math.min(chunkSize, remainingCount);
    }

    public double resolveDropHorizontalMotion(Random random, float spread) {
        return (double) ((float) random.nextGaussian() * spread);
    }

    public double resolveDropVerticalMotion(Random random, float spread, double upwardBias) {
        return (double) ((float) random.nextGaussian() * spread + (float) upwardBias);
    }

    public interface TypeIdQuery {
        int getTypeId(int x, int y, int z);
    }

    public interface ChestAccess {
        int getTypeId(int x, int y, int z);

        Object getChest(int x, int y, int z);
    }
}
