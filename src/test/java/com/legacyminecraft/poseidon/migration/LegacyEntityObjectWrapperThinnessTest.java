package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityObjectWrapperThinnessTest {
    private static final Path ENTITY_FALLING_SAND_PATH = Paths.get("src/main/java/net/minecraft/server/EntityFallingSand.java");
    private static final Path ENTITY_ITEM_PATH = Paths.get("src/main/java/net/minecraft/server/EntityItem.java");
    private static final Path ENTITY_PAINTING_PATH = Paths.get("src/main/java/net/minecraft/server/EntityPainting.java");
    private static final Path ENTITY_BOAT_PATH = Paths.get("src/main/java/net/minecraft/server/EntityBoat.java");
    private static final Path ENTITY_MINECART_PATH = Paths.get("src/main/java/net/minecraft/server/EntityMinecart.java");

    @Test
    public void entityFallingSandDelegatesSpawnTickPlacementAndTileNbtPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_FALLING_SAND_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FallingSandBehaviour"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.initializeSpawn"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.shouldDieForMissingBlock"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.tickPreMove"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.tickPostMove"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.resolveBlockPos"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.clearSourceBlockIfMatching"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.handleGroundImpact"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.shouldDropForTimeout"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.writeTileNbt"));
        Assert.assertTrue(text.contains("FALLING_SAND_BEHAVIOUR.readTileNbt"));
        Assert.assertFalse(text.contains("this.motY -= 0.03999999910593033D;"));
        Assert.assertFalse(text.contains("BlockSand.c_(this.world, i, j - 1, k)"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"Tile\", (byte) this.a);"));
    }

    @Test
    public void entityItemDelegatesStateValidationMotionAndNbtCodecToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_ITEM_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ItemEntityStateBehaviour"));
        Assert.assertTrue(text.contains("ItemEntityLifecycleBehaviour"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.initializeDefaultBounds"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.sanitizeInitialItemStack"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.initializeFromItemStack"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.createInitialMotion"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.isInvalidItemStack"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.applyLavaBounce"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.resolveGroundFriction"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.shouldAttemptDespawn"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.applyDamageAndGetRemainingHealth"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.writeNbt"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STATE_BEHAVIOUR.readNbt"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.updatePickupDelayClock"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.computePickupWindow"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.restoreStackCountAfterPickupProbe"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.normalizePickupDelayAfterEvent"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.shouldTryInventoryPickup"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.grantPickupAchievements"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.pickupSoundPitch"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.shouldDieAfterPickup"));
        Assert.assertTrue(text.contains("poseidonInitializeBounds"));
        Assert.assertFalse(text.contains("MinecraftException e = new MinecraftException"));
        Assert.assertFalse(text.contains("this.yaw = (float) (Math.random() * 360.0D);"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"Health\", (short) ((byte) this.f));"));
        Assert.assertFalse(text.contains("this.pickupDelay -= (currentTick - this.lastTick);"));
        Assert.assertFalse(text.contains("if (this.itemStack.id == Block.LOG.id)"));
    }

    @Test
    public void entityPaintingDelegatesBreakFlowStabilityTickAndNbtCodecToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_PAINTING_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PaintingEntityBehaviour"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.tickAndMaybeResetCounter"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.shouldRunStabilityCheck"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.breakFromWorldIfUnstable"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.breakFromEntity"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.writeNbt"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.readNbt"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.shouldBreakFromMotion"));
        Assert.assertTrue(text.contains("PAINTING_ENTITY_BEHAVIOUR.dropPaintingItem"));
        Assert.assertFalse(text.contains("PaintingBreakByWorldEvent event = new PaintingBreakByWorldEvent"));
        Assert.assertFalse(text.contains("PaintingBreakByEntityEvent event = new PaintingBreakByEntityEvent"));
        Assert.assertFalse(text.contains("nbttagcompound.setString(\"Motive\", this.e.A);"));
    }

    @Test
    public void entityBoatDelegatesDamageRiderPositionAndInteractPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_BOAT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BoatLifecycleBehaviour"));
        Assert.assertTrue(text.contains("BoatCollisionBehaviour"));
        Assert.assertTrue(text.contains("BOAT_LIFECYCLE_BEHAVIOUR.handleDamage"));
        Assert.assertTrue(text.contains("BOAT_LIFECYCLE_BEHAVIOUR.updatePassengerPosition"));
        Assert.assertTrue(text.contains("BOAT_LIFECYCLE_BEHAVIOUR.interact"));
        Assert.assertTrue(text.contains("BOAT_COLLISION_BEHAVIOUR.collide"));
        Assert.assertTrue(text.contains("poseidonPassengerYOffset"));
        Assert.assertTrue(text.contains("poseidonSuperCollide"));
        Assert.assertFalse(text.contains("VehicleDamageEvent event = new VehicleDamageEvent"));
        Assert.assertFalse(text.contains("VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent"));
        Assert.assertFalse(text.contains("VehicleEnterEvent event = new VehicleEnterEvent"));
        Assert.assertFalse(text.contains("VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent"));
        Assert.assertFalse(text.contains("this.passenger.setPosition(this.locX + d0"));
    }

    @Test
    public void entityMinecartDelegatesDamageDestroyDropAndInteractBranchingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_MINECART_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MinecartLifecycleBehaviour"));
        Assert.assertTrue(text.contains("MinecartCollisionBehaviour"));
        Assert.assertTrue(text.contains("MinecartNbtBehaviour"));
        Assert.assertTrue(text.contains("MINECART_LIFECYCLE_BEHAVIOUR.handleDamage"));
        Assert.assertTrue(text.contains("MINECART_LIFECYCLE_BEHAVIOUR.interact"));
        Assert.assertTrue(text.contains("MINECART_LIFECYCLE_BEHAVIOUR.dropInventoryContents"));
        Assert.assertTrue(text.contains("MINECART_COLLISION_BEHAVIOUR.handleCollision"));
        Assert.assertTrue(text.contains("MINECART_NBT_BEHAVIOUR.writeNbt"));
        Assert.assertTrue(text.contains("MINECART_NBT_BEHAVIOUR.readNbt"));
        Assert.assertTrue(text.contains("poseidonMarkDamaged"));
        Assert.assertTrue(text.contains("poseidonDropEntityItem"));
        Assert.assertTrue(text.contains("poseidonCollisionReductionFactor"));
        Assert.assertTrue(text.contains("poseidonApplyCollisionPush"));
        Assert.assertTrue(text.contains("poseidonRandomFloat"));
        Assert.assertTrue(text.contains("poseidonRandomInt"));
        Assert.assertTrue(text.contains("poseidonRandomGaussian"));
        Assert.assertFalse(text.contains("VehicleDamageEvent event = new VehicleDamageEvent"));
        Assert.assertFalse(text.contains("VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent"));
        Assert.assertFalse(text.contains("VehicleEnterEvent event = new VehicleEnterEvent"));
        Assert.assertFalse(text.contains("this.a(Item.MINECART.id, 1, 0.0F);"));
        Assert.assertFalse(text.contains("EntityItem entityitem = new EntityItem(this.world"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"Type\", this.type);"));
    }
}
