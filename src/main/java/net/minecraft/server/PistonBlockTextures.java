package net.minecraft.server;

import com.legacyminecraft.poseidon.block.PistonTextureOrientationBehaviour;

public class PistonBlockTextures {
    private static final PistonTextureOrientationBehaviour PISTON_TEXTURE_ORIENTATION_BEHAVIOUR = PistonTextureOrientationBehaviour.getInstance();

    public static final int[] a = PISTON_TEXTURE_ORIENTATION_BEHAVIOUR.getFaceRotationMap();
    public static final int[] b = PISTON_TEXTURE_ORIENTATION_BEHAVIOUR.getXOffsets();
    public static final int[] c = PISTON_TEXTURE_ORIENTATION_BEHAVIOUR.getYOffsets();
    public static final int[] d = PISTON_TEXTURE_ORIENTATION_BEHAVIOUR.getZOffsets();

    public PistonBlockTextures() {}
}
