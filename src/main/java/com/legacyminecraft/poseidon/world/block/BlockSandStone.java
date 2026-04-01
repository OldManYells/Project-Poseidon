package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.*;

public class BlockSandStone extends Block {

    public BlockSandStone(int i) {
        super(i, 192, Material.STONE);
    }

    public int a(int i) {
        return i == 1 ? this.textureId - 16 : (i == 0 ? this.textureId + 16 : this.textureId);
    }
}
