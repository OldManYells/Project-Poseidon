package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyMonsterEntityWrapperThinnessTest {
    private static final Path ENTITY_MONSTER_PATH = Paths.get("src/main/java/net/minecraft/server/EntityMonster.java");
    private static final Path ENTITY_ZOMBIE_PATH = Paths.get("src/main/java/net/minecraft/server/EntityZombie.java");
    private static final Path ENTITY_GIANT_ZOMBIE_PATH = Paths.get("src/main/java/net/minecraft/server/EntityGiantZombie.java");
    private static final Path ENTITY_SKELETON_PATH = Paths.get("src/main/java/net/minecraft/server/EntitySkeleton.java");
    private static final Path ENTITY_SPIDER_PATH = Paths.get("src/main/java/net/minecraft/server/EntitySpider.java");
    private static final Path ENTITY_CREEPER_PATH = Paths.get("src/main/java/net/minecraft/server/EntityCreeper.java");
    private static final Path ENTITY_PIG_ZOMBIE_PATH = Paths.get("src/main/java/net/minecraft/server/EntityPigZombie.java");
    private static final Path ENTITY_SLIME_PATH = Paths.get("src/main/java/net/minecraft/server/EntitySlime.java");
    private static final Path ENTITY_GHAST_PATH = Paths.get("src/main/java/net/minecraft/server/EntityGhast.java");

    @Test
    public void entityMonsterDelegatesCoreTickTargetAndMeleePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_MONSTER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MonsterCoreBehaviour"));
        Assert.assertTrue(text.contains("MONSTER_CORE_BEHAVIOUR.updateSunlightPenalty"));
        Assert.assertTrue(text.contains("MONSTER_CORE_BEHAVIOUR.shouldDespawnWhenMonstersDisabled"));
        Assert.assertTrue(text.contains("MONSTER_CORE_BEHAVIOUR.findTarget"));
        Assert.assertTrue(text.contains("MONSTER_CORE_BEHAVIOUR.onDamaged"));
        Assert.assertTrue(text.contains("MONSTER_CORE_BEHAVIOUR.attackTarget"));
        Assert.assertTrue(text.contains("MONSTER_CORE_BEHAVIOUR.resolvePathWeight"));
        Assert.assertFalse(text.contains("EntityTargetEvent event = new EntityTargetEvent"));
        Assert.assertFalse(text.contains("new EntityDamageByEntityEvent"));
        Assert.assertFalse(text.contains("return 0.5F - this.world.n(i, j, k);"));
    }

    @Test
    public void entityZombieDelegatesSunlightCombustAndSoundDropPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_ZOMBIE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ZombieLifecycleBehaviour"));
        Assert.assertTrue(text.contains("ZOMBIE_LIFECYCLE_BEHAVIOUR.tickSunlightCombustion"));
        Assert.assertTrue(text.contains("ZOMBIE_LIFECYCLE_BEHAVIOUR.getAmbientSound"));
        Assert.assertTrue(text.contains("ZOMBIE_LIFECYCLE_BEHAVIOUR.getHurtSound"));
        Assert.assertTrue(text.contains("ZOMBIE_LIFECYCLE_BEHAVIOUR.getDeathSound"));
        Assert.assertTrue(text.contains("ZOMBIE_LIFECYCLE_BEHAVIOUR.getDropItemId"));
        Assert.assertFalse(text.contains("new EntityCombustEvent"));
        Assert.assertFalse(text.contains("return \"mob.zombie\";"));
    }

    @Test
    public void entityGiantZombieDelegatesScaleDamageAndPathWeightPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_GIANT_ZOMBIE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("GiantZombieScaleBehaviour"));
        Assert.assertTrue(text.contains("GIANT_ZOMBIE_SCALE_BEHAVIOUR.getAttackDamage"));
        Assert.assertTrue(text.contains("GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleHealth"));
        Assert.assertTrue(text.contains("GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleEyeHeight"));
        Assert.assertTrue(text.contains("GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleLength"));
        Assert.assertTrue(text.contains("GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleWidth"));
        Assert.assertTrue(text.contains("GIANT_ZOMBIE_SCALE_BEHAVIOUR.resolvePathWeight"));
        Assert.assertFalse(text.contains("this.damage = 50;"));
        Assert.assertFalse(text.contains("this.health *= 10;"));
        Assert.assertFalse(text.contains("return this.world.n(i, j, k) - 0.5F;"));
    }

    @Test
    public void entitySkeletonDelegatesSunlightRangedAttackAndDeathDropPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_SKELETON_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SkeletonLifecycleBehaviour"));
        Assert.assertTrue(text.contains("SKELETON_LIFECYCLE_BEHAVIOUR.tickSunlightCombustion"));
        Assert.assertTrue(text.contains("SKELETON_LIFECYCLE_BEHAVIOUR.attackRanged"));
        Assert.assertTrue(text.contains("CombatFixConfigPolicy"));
        Assert.assertTrue(text.contains("COMBAT_FIX_CONFIG_POLICY"));
        Assert.assertTrue(text.contains("SKELETON_LIFECYCLE_BEHAVIOUR.getDropItemId"));
        Assert.assertTrue(text.contains("SKELETON_LIFECYCLE_BEHAVIOUR.dropDeathLoot"));
        Assert.assertTrue(text.contains("poseidonSetHasActiveAttackGoal"));
        Assert.assertFalse(text.contains("\"world.settings.skeleton-shooting-sound-fix.enabled\""));
        Assert.assertFalse(text.contains("new EntityCombustEvent"));
        Assert.assertFalse(text.contains("new EntityArrow(this.world, this)"));
        Assert.assertFalse(text.contains("new EntityDeathEvent(entity, loot)"));
    }

    @Test
    public void entitySpiderDelegatesTargetSelectionAndAttackBranchingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_SPIDER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SpiderCombatBehaviour"));
        Assert.assertTrue(text.contains("SPIDER_COMBAT_BEHAVIOUR.findTarget"));
        Assert.assertTrue(text.contains("SPIDER_COMBAT_BEHAVIOUR.handleAttack"));
        Assert.assertTrue(text.contains("SPIDER_COMBAT_BEHAVIOUR.getAmbientSound"));
        Assert.assertTrue(text.contains("SPIDER_COMBAT_BEHAVIOUR.getHurtSound"));
        Assert.assertTrue(text.contains("SPIDER_COMBAT_BEHAVIOUR.getDeathSound"));
        Assert.assertTrue(text.contains("SPIDER_COMBAT_BEHAVIOUR.getDropItemId"));
        Assert.assertFalse(text.contains("EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.FORGOT_TARGET)"));
        Assert.assertFalse(text.contains("this.world.findNearbyPlayer(this, d0)"));
        Assert.assertFalse(text.contains("this.motY = 0.4000000059604645D;"));
    }

    @Test
    public void entityCreeperDelegatesPoweredStateAndLightningPowerPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_CREEPER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CreeperPowerBehaviour"));
        Assert.assertTrue(text.contains("CreeperFuseBehaviour"));
        Assert.assertTrue(text.contains("CREEPER_POWER_BEHAVIOUR.createInitialFuseDirectionWatcherValue"));
        Assert.assertTrue(text.contains("CREEPER_POWER_BEHAVIOUR.createInitialPoweredWatcherValue"));
        Assert.assertTrue(text.contains("CREEPER_POWER_BEHAVIOUR.shouldPersistPoweredTag"));
        Assert.assertTrue(text.contains("CREEPER_POWER_BEHAVIOUR.readPoweredFlag"));
        Assert.assertTrue(text.contains("CREEPER_POWER_BEHAVIOUR.resolvePoweredWatcherValue"));
        Assert.assertTrue(text.contains("CREEPER_POWER_BEHAVIOUR.dropMusicDiscIfKilledBySkeleton"));
        Assert.assertTrue(text.contains("CREEPER_POWER_BEHAVIOUR.onLightningStrike"));
        Assert.assertTrue(text.contains("CREEPER_FUSE_BEHAVIOUR.tickFuseCooldownWhileIdleServer"));
        Assert.assertTrue(text.contains("CREEPER_FUSE_BEHAVIOUR.tickBeforeSuper"));
        Assert.assertTrue(text.contains("CREEPER_FUSE_BEHAVIOUR.tickAfterSuper"));
        Assert.assertTrue(text.contains("CREEPER_FUSE_BEHAVIOUR.handleProximityFuse"));
        Assert.assertTrue(text.contains("poseidonGetFuseDirection"));
        Assert.assertTrue(text.contains("poseidonSetFuseDirection"));
        Assert.assertFalse(text.contains("CreeperPowerEvent event = new CreeperPowerEvent"));
        Assert.assertFalse(text.contains("this.b(Item.GOLD_RECORD.id + this.random.nextInt(2), 1)"));
        Assert.assertFalse(text.contains("this.datawatcher.watch(17, Byte.valueOf((byte) (nbttagcompound.m(\"powered\") ? 1 : 0)))"));
        Assert.assertFalse(text.contains("ExplosionPrimeEvent event = new ExplosionPrimeEvent"));
        Assert.assertFalse(text.contains("this.fuseTicks += i;"));
    }

    @Test
    public void entityPigZombieDelegatesAggroPropagationAndSpawnPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_PIG_ZOMBIE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PigZombieAggroBehaviour"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.tick"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.canSpawn"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.shouldFindTarget"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.propagateAggroToNearbyPigZombies"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.applyAggroTarget"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.getAmbientSound"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.getHurtSound"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.getDeathSound"));
        Assert.assertTrue(text.contains("PIG_ZOMBIE_AGGRO_BEHAVIOUR.getDropItemId"));
        Assert.assertTrue(text.contains("poseidonSetSoundDelay"));
        Assert.assertFalse(text.contains("EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET)"));
        Assert.assertFalse(text.contains("this.angerLevel = 400 + this.random.nextInt(400);"));
    }

    @Test
    public void entitySlimeDelegatesSizingLandingSpawnAndSplitPoliciesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_SLIME_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SlimeStateBehaviour"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.createInitialPhysicalSize"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.createInitialJumpDelay"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.createInitialWatcherSize"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.computeSetSizeState"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.tickLandingSquish"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.splitOnDeath"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.attackPlayerOnContact"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.getDropItemId"));
        Assert.assertTrue(text.contains("SLIME_STATE_BEHAVIOUR.canSpawn"));
        Assert.assertFalse(text.contains("int i = 1 << this.random.nextInt(3);"));
        Assert.assertFalse(text.contains("this.world.a(\"slime\""));
        Assert.assertFalse(text.contains("entityhuman.damageEntity(this, i)"));
    }

    @Test
    public void entityGhastDelegatesWatcherTextureAndSpawnSoundPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_GHAST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("GhastCoreBehaviour"));
        Assert.assertTrue(text.contains("GhastAiBehaviour"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.createInitialAttackWatcher"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.resolveTexture"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.shouldDespawnWhenMonstersDisabled"));
        Assert.assertTrue(text.contains("GHAST_AI_BEHAVIOUR.tickAi"));
        Assert.assertTrue(text.contains("poseidonCanTravelToWaypoint"));
        Assert.assertTrue(text.contains("poseidonGetTargetEntity"));
        Assert.assertTrue(text.contains("poseidonSetTargetEntity"));
        Assert.assertTrue(text.contains("poseidonGetTargetRefreshTicks"));
        Assert.assertTrue(text.contains("poseidonSetTargetRefreshTicks"));
        Assert.assertTrue(text.contains("poseidonGetAttackWatcher"));
        Assert.assertTrue(text.contains("poseidonSetAttackWatcher"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.getAmbientSound"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.getHurtSound"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.getDeathSound"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.getDropItemId"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.getSoundVolume"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.canSpawn"));
        Assert.assertTrue(text.contains("GHAST_CORE_BEHAVIOUR.getMaxClusterSize"));
        Assert.assertFalse(text.contains("this.texture = b0 == 1 ? \"/mob/ghast_fire.png\" : \"/mob/ghast.png\""));
        Assert.assertFalse(text.contains("return this.random.nextInt(20) == 0 && super.d() && this.world.spawnMonsters > 0;"));
        Assert.assertFalse(text.contains("EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED)"));
        Assert.assertFalse(text.contains("EntityFireball entityfireball = new EntityFireball"));
    }
}
