package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

import java.util.Random;

public class BlockBookshelf extends Block {
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    public BlockBookshelf(int i, int j) {
        super(i, j, Material.WOOD);
    }

    public int a(int i) {
        return SIMPLE_BLOCK_STATE_SERVICE.resolveBookshelfTextureBySide(i, this.textureId);
    }

    public int a(Random random) {
        return SIMPLE_BLOCK_STATE_SERVICE.resolveNoDropCount();
    }
}
