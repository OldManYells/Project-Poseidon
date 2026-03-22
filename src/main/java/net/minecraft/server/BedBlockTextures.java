package net.minecraft.server;

import com.legacyminecraft.poseidon.block.BedTextureOrientationBehaviour;

public class BedBlockTextures {
    private static final BedTextureOrientationBehaviour BED_TEXTURE_ORIENTATION_BEHAVIOUR = BedTextureOrientationBehaviour.getInstance();

    public static final int[] a = BED_TEXTURE_ORIENTATION_BEHAVIOUR.getHeadAndFootFaces();
    public static final int[] b = BED_TEXTURE_ORIENTATION_BEHAVIOUR.getSideFaces();
    public static final int[][] c = BED_TEXTURE_ORIENTATION_BEHAVIOUR.getRotationTextureMap();

    public BedBlockTextures() {}
}
