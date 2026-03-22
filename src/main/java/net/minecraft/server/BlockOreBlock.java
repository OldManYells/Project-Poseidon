package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

public class BlockOreBlock extends Block {
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    public BlockOreBlock(int i, int j) {
        super(i, Material.ORE);
        this.textureId = j;
    }

    public int a(int i) {
        return SIMPLE_BLOCK_STATE_SERVICE.resolveTextureIdentity(this.textureId);
    }
}
