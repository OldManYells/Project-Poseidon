package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyProjectileEntityWrapperThinnessTest {
    private static final Path ENTITY_FIREBALL_PATH = Paths.get("src/main/java/net/minecraft/server/EntityFireball.java");
    private static final Path ENTITY_ARROW_PATH = Paths.get("src/main/java/net/minecraft/server/EntityArrow.java");
    private static final Path ENTITY_SNOWBALL_PATH = Paths.get("src/main/java/net/minecraft/server/EntitySnowball.java");
    private static final Path ENTITY_EGG_PATH = Paths.get("src/main/java/net/minecraft/server/EntityEgg.java");
    private static final Path ENTITY_TNT_PRIMED_PATH = Paths.get("src/main/java/net/minecraft/server/EntityTNTPrimed.java");

    @Test
    public void entityFireballDelegatesDirectionNbtAndDamageReflectionStateToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_FIREBALL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FireballStateBehaviour"));
        Assert.assertTrue(text.contains("FIREBALL_STATE_BEHAVIOUR.computeDirectionFromNoisyVector"));
        Assert.assertTrue(text.contains("FIREBALL_STATE_BEHAVIOUR.writeTileAndGroundState"));
        Assert.assertTrue(text.contains("FIREBALL_STATE_BEHAVIOUR.readTileAndGroundState"));
        Assert.assertTrue(text.contains("FIREBALL_STATE_BEHAVIOUR.onDamagedByEntity"));
        Assert.assertFalse(text.contains("double d3 = (double) MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"xTile\", (short) this.f);"));
        Assert.assertFalse(text.contains("Vec3D vec3d = entity.Z();"));
    }

    @Test
    public void entityArrowDelegatesLaunchHeadingAndNbtStateCodecToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_ARROW_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ArrowStateBehaviour"));
        Assert.assertTrue(text.contains("ARROW_STATE_BEHAVIOUR.createShooterLaunchState"));
        Assert.assertTrue(text.contains("ARROW_STATE_BEHAVIOUR.computeHeading"));
        Assert.assertTrue(text.contains("ARROW_STATE_BEHAVIOUR.writePersistedState"));
        Assert.assertTrue(text.contains("ARROW_STATE_BEHAVIOUR.readPersistedState"));
        Assert.assertFalse(text.contains("this.locX -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);"));
        Assert.assertFalse(text.contains("d0 += this.random.nextGaussian() * 0.007499999832361937D * (double) f1;"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"xTile\", (short) this.d);"));
    }

    @Test
    public void entitySnowballAndEggDelegateSharedLaunchHeadingAndNbtStateCodecToCanonicalBehaviour() throws IOException {
        String snowball = new String(Files.readAllBytes(ENTITY_SNOWBALL_PATH), StandardCharsets.UTF_8);
        String egg = new String(Files.readAllBytes(ENTITY_EGG_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(snowball.contains("ThrowableProjectileStateBehaviour"));
        Assert.assertTrue(snowball.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.createShooterLaunchState"));
        Assert.assertTrue(snowball.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.computeHeading"));
        Assert.assertTrue(snowball.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.writePersistedState"));
        Assert.assertTrue(snowball.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.readPersistedState"));
        Assert.assertFalse(snowball.contains("float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);"));
        Assert.assertFalse(snowball.contains("nbttagcompound.a(\"xTile\", (short) this.b);"));

        Assert.assertTrue(egg.contains("ThrowableProjectileStateBehaviour"));
        Assert.assertTrue(egg.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.createShooterLaunchState"));
        Assert.assertTrue(egg.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.computeHeading"));
        Assert.assertTrue(egg.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.writePersistedState"));
        Assert.assertTrue(egg.contains("THROWABLE_PROJECTILE_STATE_BEHAVIOUR.readPersistedState"));
        Assert.assertFalse(egg.contains("float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);"));
        Assert.assertFalse(egg.contains("nbttagcompound.a(\"xTile\", (short) this.b);"));
    }

    @Test
    public void entityTntPrimedDelegatesSpawnFuseAndExplosionStateFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_TNT_PRIMED_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("TntPrimedBehaviour"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.initializeSpawnMotion"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.tickPreMove"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.tickPostMove"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.tickFuse"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.spawnFuseSmoke"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.explode"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.writeFuseNbt"));
        Assert.assertTrue(text.contains("TNT_PRIMED_BEHAVIOUR.readFuseNbt"));
        Assert.assertFalse(text.contains("this.motY -= 0.03999999910593033D;"));
        Assert.assertFalse(text.contains("if (this.fuseTicks-- <= 0)"));
        Assert.assertFalse(text.contains("ExplosionPrimeEvent event = new ExplosionPrimeEvent"));
    }
}
