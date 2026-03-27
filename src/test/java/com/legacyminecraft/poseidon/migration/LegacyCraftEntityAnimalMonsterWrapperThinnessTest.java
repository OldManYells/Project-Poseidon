package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftEntityAnimalMonsterWrapperThinnessTest {
    private static final Path CRAFT_ANIMALS_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftAnimals.java");
    private static final Path CRAFT_CHICKEN_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftChicken.java");
    private static final Path CRAFT_COW_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftCow.java");
    private static final Path CRAFT_MONSTER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftMonster.java");
    private static final Path CRAFT_GIANT_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftGiant.java");
    private static final Path CRAFT_ZOMBIE_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftZombie.java");
    private static final Path CRAFT_SPIDER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSpider.java");
    private static final Path CRAFT_SKELETON_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSkeleton.java");
    private static final Path CRAFT_CREEPER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftCreeper.java");
    private static final Path CRAFT_PIG_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftPig.java");
    private static final Path CRAFT_SHEEP_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSheep.java");
    private static final Path CRAFT_PIG_ZOMBIE_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftPigZombie.java");
    private static final Path CRAFT_SLIME_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSlime.java");
    private static final Path CRAFT_CREATURE_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftCreature.java");
    private static final Path CRAFT_WOLF_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftWolf.java");
    private static final Path CREATURE_TARGET_BRIDGE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CreatureTargetBridgeBehaviour.java");
    private static final Path ENTITY_HANDLE_BRIDGE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityHandleBridgeBehaviour.java");
    private static final Path CRAFT_ENTITY_IDENTITY_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftEntityIdentityBehaviour.java");

    @Test
    public void craftAnimalAndMonsterWrappersDelegateIdentityAndTypedHandleGlueToCanonicalBehaviours() throws IOException {
        String craftAnimalsText = read(CRAFT_ANIMALS_PATH);
        String craftChickenText = read(CRAFT_CHICKEN_PATH);
        String craftCowText = read(CRAFT_COW_PATH);
        String craftMonsterText = read(CRAFT_MONSTER_PATH);
        String craftGiantText = read(CRAFT_GIANT_PATH);
        String craftZombieText = read(CRAFT_ZOMBIE_PATH);
        String craftSpiderText = read(CRAFT_SPIDER_PATH);
        String craftSkeletonText = read(CRAFT_SKELETON_PATH);
        String craftCreeperText = read(CRAFT_CREEPER_PATH);
        String craftPigText = read(CRAFT_PIG_PATH);
        String craftSheepText = read(CRAFT_SHEEP_PATH);
        String craftPigZombieText = read(CRAFT_PIG_ZOMBIE_PATH);
        String craftSlimeText = read(CRAFT_SLIME_PATH);
        String craftCreatureText = read(CRAFT_CREATURE_PATH);
        String craftWolfText = read(CRAFT_WOLF_PATH);
        String creatureTargetBridgeText = read(CREATURE_TARGET_BRIDGE_PATH);
        String entityHandleBridgeText = read(ENTITY_HANDLE_BRIDGE_PATH);
        String craftEntityIdentityText = read(CRAFT_ENTITY_IDENTITY_PATH);

        assertAnimalWrapperThinness(craftAnimalsText);
        assertMonsterWrapperThinness(craftMonsterText);
        assertIdentityOnlyWrapperThinness(craftChickenText, "CraftChicken");
        assertIdentityOnlyWrapperThinness(craftCowText, "CraftCow");
        assertIdentityOnlyWrapperThinness(craftGiantText, "CraftGiant");
        assertIdentityOnlyWrapperThinness(craftZombieText, "CraftZombie");
        assertIdentityOnlyWrapperThinness(craftSpiderText, "CraftSpider");
        assertIdentityOnlyWrapperThinness(craftSkeletonText, "CraftSkeleton");
        assertWrapperStringBehaviourThinness(craftCreeperText, "CraftCreeper");
        assertWrapperStringBehaviourThinness(craftPigText, "CraftPig");
        assertWrapperStringBehaviourThinness(craftSheepText, "CraftSheep");
        assertWrapperStringBehaviourThinness(craftPigZombieText, "CraftPigZombie");
        assertWrapperStringBehaviourThinness(craftSlimeText, "CraftSlime");
        assertCreatureWrapperThinness(craftCreatureText);
        assertWolfWrapperThinness(craftWolfText);

        Assert.assertTrue(entityHandleBridgeText.contains("resolveAnimalHandle(Entity entity)"));
        Assert.assertTrue(entityHandleBridgeText.contains("resolveMonsterHandle(Entity entity)"));
        Assert.assertTrue(entityHandleBridgeText.contains("resolveLivingHandle(LivingEntity livingEntity)"));
        Assert.assertTrue(entityHandleBridgeText.contains("return (EntityAnimal) entity;"));
        Assert.assertTrue(entityHandleBridgeText.contains("return (EntityMonster) entity;"));
        Assert.assertTrue(creatureTargetBridgeText.contains("ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveLivingHandle(target)"));
        Assert.assertTrue(creatureTargetBridgeText.contains("ENTITY_BUKKIT_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftLivingEntity((EntityLiving) creature.target)"));
        Assert.assertFalse(creatureTargetBridgeText.contains("target instanceof CraftLivingEntity"));
        Assert.assertFalse(creatureTargetBridgeText.contains("((CraftLivingEntity) target).getHandle()"));
        Assert.assertFalse(creatureTargetBridgeText.contains("(CraftLivingEntity) creature.target.getBukkitEntity()"));

        Assert.assertTrue(craftEntityIdentityText.contains("toString(CraftEntity craftEntity)"));
        Assert.assertTrue(craftEntityIdentityText.contains("getClass().getSimpleName()"));
    }

    private static void assertAnimalWrapperThinness(String text) {
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("CraftEntityIdentityBehaviour"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityAnimal.class)"));
        Assert.assertTrue(text.contains("toString(this)"));
        Assert.assertFalse(text.contains("return \"CraftAnimals\";"));
        Assert.assertFalse(text.contains("return (EntityAnimal) entity;"));
    }

    private static void assertMonsterWrapperThinness(String text) {
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("CraftEntityIdentityBehaviour"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityMonster.class)"));
        Assert.assertTrue(text.contains("toString(this)"));
        Assert.assertFalse(text.contains("return \"CraftMonster\";"));
        Assert.assertFalse(text.contains("return (EntityMonster) entity;"));
    }

    private static void assertIdentityOnlyWrapperThinness(String text, String wrapperName) {
        Assert.assertTrue(text.contains("CraftEntityIdentityBehaviour"));
        Assert.assertTrue(text.contains("toString(this)"));
        Assert.assertFalse(text.contains("return \"" + wrapperName + "\";"));
    }

    private static void assertWrapperStringBehaviourThinness(String text, String wrapperName) {
        Assert.assertTrue(text.contains("EntityWrapperStringBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_STRING_BEHAVIOUR"));
        Assert.assertTrue(text.contains("toString(\"" + wrapperName + "\")"));
        Assert.assertFalse(text.contains("return \"" + wrapperName + "\";"));
    }

    private static void assertWolfWrapperThinness(String text) {
        Assert.assertTrue(text.contains("EntityWrapperDescriptionBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityWolf.class)"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.refreshOwnerCache(owner, getServer(), getOwnerName())"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR.getOwnerForView(owner)"));
        Assert.assertTrue(text.contains("craftWolfToString(isAngry(), getOwner(), isTamed(), isSitting())"));
        Assert.assertFalse(text.contains("return owner;"));
        Assert.assertFalse(text.contains("return (EntityWolf) entity;"));
        Assert.assertFalse(text.contains("return \"CraftWolf[anger=\" + isAngry() + \",owner=\" + getOwner() + \",tame=\" + isTamed() + \",sitting=\" + isSitting() + \"]\";"));
    }

    private static void assertCreatureWrapperThinness(String text) {
        Assert.assertTrue(text.contains("EntityWrapperStringBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_STRING_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("toString(\"CraftCreature\")"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityCreature.class)"));
        Assert.assertFalse(text.contains("return (EntityCreature) entity;"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
