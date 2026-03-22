package net.minecraft.server;

import com.legacyminecraft.poseidon.block.LockedChestStateBehaviour;

import java.util.Random;

public class BlockLockedChest extends Block {
    private final LockedChestStateBehaviour lockedChestStateService = LockedChestStateBehaviour.getInstance();

    protected BlockLockedChest(int i) {
        super(i, Material.WOOD);
        this.textureId = 26;
    }

    public int a(int i) {
        return lockedChestStateService.resolveTextureBySide(i, this.textureId);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return lockedChestStateService.canPlaceAtAnyLocation();
    }

    public void a(World world, int i, int j, int k, Random random) {
        world.setTypeId(i, j, k, lockedChestStateService.expiredBlockTypeId());
    }
}
