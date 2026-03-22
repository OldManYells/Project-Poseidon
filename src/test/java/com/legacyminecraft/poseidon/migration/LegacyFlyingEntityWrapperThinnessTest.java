package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyFlyingEntityWrapperThinnessTest {
    private static final Path ENTITY_FLYING_PATH = Paths.get("src/main/java/net/minecraft/server/EntityFlying.java");

    @Test
    public void entityFlyingDelegatesFrictionTravelFactorAndAnimationMathToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_FLYING_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FlyingMovementBehaviour"));
        Assert.assertTrue(text.contains("FLYING_MOVEMENT_BEHAVIOUR.resolveGroundFriction"));
        Assert.assertTrue(text.contains("FLYING_MOVEMENT_BEHAVIOUR.resolveTravelFactor"));
        Assert.assertTrue(text.contains("FLYING_MOVEMENT_BEHAVIOUR.updateAnimation"));
        Assert.assertFalse(text.contains("float f3 = 0.16277136F / (f2 * f2 * f2);"));
        Assert.assertFalse(text.contains("Block.byId[i].frictionFactor * 0.91F"));
        Assert.assertFalse(text.contains("float f4 = MathHelper.a(d0 * d0 + d1 * d1) * 4.0F;"));
    }
}
