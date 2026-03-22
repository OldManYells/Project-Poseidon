package net.minecraft.server;

import com.legacyminecraft.poseidon.block.WorkbenchStateBehaviour;

public class BlockWorkbench extends Block {
    private final WorkbenchStateBehaviour workbenchStateService = WorkbenchStateBehaviour.getInstance();

    protected BlockWorkbench(int i) {
        super(i, Material.WOOD);
        this.textureId = 59;
    }

    public int a(int i) {
        return workbenchStateService.resolveTextureBySide(i, this.textureId, Block.WOOD.a(0));
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (workbenchStateService.shouldIgnoreClientInteraction(world.isStatic)) {
            return true;
        } else {
            entityhuman.b(i, j, k);
            return true;
        }
    }
}
