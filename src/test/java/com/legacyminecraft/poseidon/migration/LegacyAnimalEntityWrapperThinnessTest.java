package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyAnimalEntityWrapperThinnessTest {
    private static final Path ENTITY_ANIMAL_PATH = Paths.get("src/main/java/net/minecraft/server/EntityAnimal.java");
    private static final Path ENTITY_WATER_ANIMAL_PATH = Paths.get("src/main/java/net/minecraft/server/EntityWaterAnimal.java");
    private static final Path ENTITY_COW_PATH = Paths.get("src/main/java/net/minecraft/server/EntityCow.java");
    private static final Path ENTITY_CHICKEN_PATH = Paths.get("src/main/java/net/minecraft/server/EntityChicken.java");
    private static final Path ENTITY_PIG_PATH = Paths.get("src/main/java/net/minecraft/server/EntityPig.java");
    private static final Path ENTITY_SHEEP_PATH = Paths.get("src/main/java/net/minecraft/server/EntitySheep.java");
    private static final Path ENTITY_SQUID_PATH = Paths.get("src/main/java/net/minecraft/server/EntitySquid.java");
    private static final Path ENTITY_WOLF_PATH = Paths.get("src/main/java/net/minecraft/server/EntityWolf.java");

    @Test
    public void entityAnimalDelegatesPathWeightSpawnAndSoundIntervalPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_ANIMAL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityAnimalBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_ANIMAL_BEHAVIOUR.resolvePathWeight"));
        Assert.assertTrue(text.contains("ENTITY_ANIMAL_BEHAVIOUR.canSpawn"));
        Assert.assertTrue(text.contains("ENTITY_ANIMAL_BEHAVIOUR.getAmbientSoundInterval"));
        Assert.assertFalse(text.contains("this.world.getTypeId(i, j - 1, k) == Block.GRASS.id ? 10.0F"));
        Assert.assertFalse(text.contains("this.world.k(i, j, k) > 8 && super.d()"));
        Assert.assertFalse(text.contains("return 120;"));
    }

    @Test
    public void entityWaterAnimalDelegatesBreathingSpawnAndSoundIntervalPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_WATER_ANIMAL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityWaterAnimalBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WATER_ANIMAL_BEHAVIOUR.canBreatheUnderwater"));
        Assert.assertTrue(text.contains("ENTITY_WATER_ANIMAL_BEHAVIOUR.canSpawn"));
        Assert.assertTrue(text.contains("ENTITY_WATER_ANIMAL_BEHAVIOUR.getAmbientSoundInterval"));
        Assert.assertFalse(text.contains("return true;"));
        Assert.assertFalse(text.contains("return this.world.containsEntity(this.boundingBox);"));
        Assert.assertFalse(text.contains("return 120;"));
    }

    @Test
    public void entityCowDelegatesSoundLootAndBucketFillPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_COW_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CowInteractionBehaviour"));
        Assert.assertTrue(text.contains("COW_INTERACTION_BEHAVIOUR.getAmbientSound"));
        Assert.assertTrue(text.contains("COW_INTERACTION_BEHAVIOUR.getHurtSound"));
        Assert.assertTrue(text.contains("COW_INTERACTION_BEHAVIOUR.getDeathSound"));
        Assert.assertTrue(text.contains("COW_INTERACTION_BEHAVIOUR.getSoundVolume"));
        Assert.assertTrue(text.contains("COW_INTERACTION_BEHAVIOUR.getDropItemId"));
        Assert.assertTrue(text.contains("COW_INTERACTION_BEHAVIOUR.tryFillBucket"));
        Assert.assertFalse(text.contains("CraftEventFactory.callPlayerBucketFillEvent"));
        Assert.assertFalse(text.contains("Item.LEATHER.id"));
    }

    @Test
    public void entityChickenDelegatesLifecycleSoundAndLootPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_CHICKEN_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ChickenLifecycleBehaviour"));
        Assert.assertTrue(text.contains("CHICKEN_LIFECYCLE_BEHAVIOUR.createInitialEggLayTimer"));
        Assert.assertTrue(text.contains("CHICKEN_LIFECYCLE_BEHAVIOUR.tick"));
        Assert.assertTrue(text.contains("CHICKEN_LIFECYCLE_BEHAVIOUR.getAmbientSound"));
        Assert.assertTrue(text.contains("CHICKEN_LIFECYCLE_BEHAVIOUR.getHurtSound"));
        Assert.assertTrue(text.contains("CHICKEN_LIFECYCLE_BEHAVIOUR.getDeathSound"));
        Assert.assertTrue(text.contains("CHICKEN_LIFECYCLE_BEHAVIOUR.getDropItemId"));
        Assert.assertFalse(text.contains("this.random.nextInt(6000) + 6000"));
        Assert.assertFalse(text.contains("this.c = (float) ((double) this.c + (double) (this.onGround ? -1 : 4) * 0.3D)"));
    }

    @Test
    public void entityPigDelegatesSaddleMountLightningAndAchievementPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_PIG_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PigLifecycleBehaviour"));
        Assert.assertTrue(text.contains("PIG_LIFECYCLE_BEHAVIOUR.getSaddleWatcherIndex"));
        Assert.assertTrue(text.contains("PIG_LIFECYCLE_BEHAVIOUR.canMount"));
        Assert.assertTrue(text.contains("PIG_LIFECYCLE_BEHAVIOUR.getDropItemId"));
        Assert.assertTrue(text.contains("PIG_LIFECYCLE_BEHAVIOUR.isSaddled"));
        Assert.assertTrue(text.contains("PIG_LIFECYCLE_BEHAVIOUR.withSaddleFlag"));
        Assert.assertTrue(text.contains("PIG_LIFECYCLE_BEHAVIOUR.onLightningStrike"));
        Assert.assertTrue(text.contains("PIG_LIFECYCLE_BEHAVIOUR.shouldAwardPigRideAchievement"));
        Assert.assertFalse(text.contains("new EntityPigZombie(this.world)"));
        Assert.assertFalse(text.contains("this.world.addEntity(entitypigzombie, SpawnReason.LIGHTNING)"));
        Assert.assertFalse(text.contains("this.fireTicks > 0 ? Item.GRILLED_PORK.id : Item.PORK.id"));
    }

    @Test
    public void entitySheepDelegatesDropsShearingAndFlagBitOperationsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_SHEEP_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SheepLifecycleBehaviour"));
        Assert.assertTrue(text.contains("SheepLifecycleBehaviour.WOOL_COLORS"));
        Assert.assertTrue(text.contains("SHEEP_LIFECYCLE_BEHAVIOUR.dropDeathLoot"));
        Assert.assertTrue(text.contains("SHEEP_LIFECYCLE_BEHAVIOUR.tryShear"));
        Assert.assertTrue(text.contains("SHEEP_LIFECYCLE_BEHAVIOUR.getColor"));
        Assert.assertTrue(text.contains("SHEEP_LIFECYCLE_BEHAVIOUR.withColor"));
        Assert.assertTrue(text.contains("SHEEP_LIFECYCLE_BEHAVIOUR.isSheared"));
        Assert.assertTrue(text.contains("SHEEP_LIFECYCLE_BEHAVIOUR.withSheared"));
        Assert.assertTrue(text.contains("SHEEP_LIFECYCLE_BEHAVIOUR.chooseSpawnColor"));
        Assert.assertFalse(text.contains("new org.bukkit.event.entity.EntityDeathEvent(entity, loot)"));
        Assert.assertFalse(text.contains("itemstack != null && itemstack.id == Item.SHEARS.id"));
        Assert.assertFalse(text.contains("(this.datawatcher.a(16) & 16) != 0"));
    }

    @Test
    public void entitySquidDelegatesMotionDropsAndSwimRetargetPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_SQUID_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SquidLifecycleBehaviour"));
        Assert.assertTrue(text.contains("SQUID_LIFECYCLE_BEHAVIOUR.createInitialTentacleSpeed"));
        Assert.assertTrue(text.contains("SQUID_LIFECYCLE_BEHAVIOUR.dropDeathLoot"));
        Assert.assertTrue(text.contains("SQUID_LIFECYCLE_BEHAVIOUR.isInWater"));
        Assert.assertTrue(text.contains("SQUID_LIFECYCLE_BEHAVIOUR.tick"));
        Assert.assertTrue(text.contains("SQUID_LIFECYCLE_BEHAVIOUR.shouldRetargetSwimDirection"));
        Assert.assertTrue(text.contains("SQUID_LIFECYCLE_BEHAVIOUR.randomSwimDirection"));
        Assert.assertFalse(text.contains("this.l = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F"));
        Assert.assertFalse(text.contains("this.world.a(this.boundingBox.b(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, this)"));
        Assert.assertFalse(text.contains("this.g += this.l;"));
        Assert.assertFalse(text.contains("this.random.nextInt(50) == 0 || !this.bA"));
    }

    @Test
    public void entityWolfDelegatesWatcherFlagsOwnerSoundAndBeggingPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_WOLF_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WolfStateBehaviour"));
        Assert.assertTrue(text.contains("WolfFollowOwnerBehaviour"));
        Assert.assertTrue(text.contains("WolfCombatInteractionBehaviour"));
        Assert.assertTrue(text.contains("WolfAiBehaviour"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.getFlagsWatcherIndex"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.getOwnerWatcherIndex"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.getHealthWatcherIndex"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.resolveAmbientSound"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.shouldBegForItem"));
        Assert.assertTrue(text.contains("WOLF_FOLLOW_OWNER_BEHAVIOUR.followOwnerOrTeleport"));
        Assert.assertTrue(text.contains("WOLF_FOLLOW_OWNER_BEHAVIOUR.shouldPausePathing"));
        Assert.assertTrue(text.contains("WOLF_COMBAT_INTERACTION_BEHAVIOUR.adjustIncomingDamage"));
        Assert.assertTrue(text.contains("WOLF_COMBAT_INTERACTION_BEHAVIOUR.onDamaged"));
        Assert.assertTrue(text.contains("WOLF_COMBAT_INTERACTION_BEHAVIOUR.findTarget"));
        Assert.assertTrue(text.contains("WOLF_COMBAT_INTERACTION_BEHAVIOUR.attackTarget"));
        Assert.assertTrue(text.contains("WOLF_COMBAT_INTERACTION_BEHAVIOUR.interact"));
        Assert.assertTrue(text.contains("WOLF_COMBAT_INTERACTION_BEHAVIOUR.spawnTameEffectParticles"));
        Assert.assertTrue(text.contains("WOLF_AI_BEHAVIOUR.tickAi"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.withSitting"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.withAngry"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.withTamed"));
        Assert.assertFalse(text.contains("this.datawatcher.a(16, Byte.valueOf((byte) 0));"));
        Assert.assertFalse(text.contains("this.datawatcher.a(17, \"\");"));
        Assert.assertFalse(text.contains("this.isAngry() ? \"mob.wolf.growl\""));
        Assert.assertFalse(text.contains("(this.datawatcher.a(16) & 4) != 0"));
        Assert.assertFalse(text.contains("PathEntity pathentity = this.world.findPath(this, entity, 16.0F);"));
        Assert.assertFalse(text.contains("CraftEventFactory.callEntityTameEvent"));
        Assert.assertFalse(text.contains("new EntityDamageByEntityEvent"));
        Assert.assertFalse(text.contains("this.world.a(s, this.locX + (double) (this.random.nextFloat() * this.length * 2.0F)"));
        Assert.assertFalse(text.contains("EntityTargetEvent.TargetReason.RANDOM_TARGET"));
    }
}
