package net.minecraft.server;

import com.legacyminecraft.poseidon.block.WoolColorStateBehaviour;

public class BlockCloth extends Block {
    private static final WoolColorStateBehaviour WOOL_COLOR_STATE_SERVICE = WoolColorStateBehaviour.getInstance();

    public BlockCloth() {
        super(35, 64, Material.CLOTH);
    }

    public int a(int i, int j) {
        return WOOL_COLOR_STATE_SERVICE.resolveTextureByData(this.textureId, j);
    }

    protected int a_(int i) {
        return WOOL_COLOR_STATE_SERVICE.resolveDroppedData(i);
    }

    public static int c(int i) {
        return WOOL_COLOR_STATE_SERVICE.invertColorData(i);
    }

    public static int d(int i) {
        return WOOL_COLOR_STATE_SERVICE.invertColorData(i);
    }
}
