package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SandstoneTextureBehaviour;

public class BlockSandStone extends Block {
    private final SandstoneTextureBehaviour sandstoneTextureService = SandstoneTextureBehaviour.getInstance();

    public BlockSandStone(int i) {
        super(i, 192, Material.STONE);
    }

    public int a(int i) {
        return sandstoneTextureService.resolveTextureBySide(i, this.textureId);
    }
}
