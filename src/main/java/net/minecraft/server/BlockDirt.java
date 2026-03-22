package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

public class BlockDirt extends Block {
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    protected BlockDirt(int i, int j) {
        super(i, SIMPLE_BLOCK_STATE_SERVICE.resolveTextureIdentity(j), Material.EARTH);
    }
}
