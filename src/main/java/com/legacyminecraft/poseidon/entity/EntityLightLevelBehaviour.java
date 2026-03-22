package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;

/**
 * Canonical behaviour for entity ambient-light sampling.
 */
public final class EntityLightLevelBehaviour {
    private static final EntityLightLevelBehaviour INSTANCE = new EntityLightLevelBehaviour();

    private EntityLightLevelBehaviour() {
    }

    public static EntityLightLevelBehaviour getInstance() {
        return INSTANCE;
    }

    public float sampleAmbientBrightness(
            World world,
            AxisAlignedBB boundingBox,
            double locX,
            double locY,
            double locZ,
            float height,
            float minimumBrightness
    ) {
        int blockX = MathHelper.floor(locX);
        double headProbeOffset = (boundingBox.e - boundingBox.b) * 0.66D;
        int blockY = MathHelper.floor(locY - (double) height + headProbeOffset);
        int blockZ = MathHelper.floor(locZ);

        if (!world.a(
                MathHelper.floor(boundingBox.a),
                MathHelper.floor(boundingBox.b),
                MathHelper.floor(boundingBox.c),
                MathHelper.floor(boundingBox.d),
                MathHelper.floor(boundingBox.e),
                MathHelper.floor(boundingBox.f))) {
            return minimumBrightness;
        }

        float worldBrightness = world.n(blockX, blockY, blockZ);
        return Math.max(worldBrightness, minimumBrightness);
    }
}
