package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.ExplosionEntityImpactSystem;
import net.minecraft.server.Vec3D;
import org.junit.Test;

import java.util.Collections;

public class ExplosionEntityImpactSystemTest {
    private final ExplosionEntityImpactSystem explosionEntityImpactSystem =
            ExplosionEntityImpactSystem.getInstance();

    @Test
    public void applyImpactsNoOpsForNullEntityList() {
        explosionEntityImpactSystem.applyImpacts(
                null,
                null,
                4.0F,
                0.0D,
                0.0D,
                0.0D,
                Vec3D.create(0.0D, 0.0D, 0.0D),
                null,
                false,
                false,
                null
        );
    }

    @Test
    public void applyImpactsNoOpsForEmptyEntityList() {
        explosionEntityImpactSystem.applyImpacts(
                null,
                null,
                4.0F,
                0.0D,
                0.0D,
                0.0D,
                Vec3D.create(0.0D, 0.0D, 0.0D),
                Collections.emptyList(),
                true,
                true,
                null
        );
    }
}
