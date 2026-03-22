package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.bukkit.EntityHandleBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.EntityTameEventBridgeBehaviour;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.Item;
import net.minecraft.server.ItemFood;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.PathEntity;
import net.minecraft.server.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class WolfCombatInteractionBehaviour {
    private static final WolfCombatInteractionBehaviour INSTANCE = new WolfCombatInteractionBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE = EntityHandleBridgeBehaviour.getInstance();
    private static final EntityTameEventBridgeBehaviour ENTITY_TAME_EVENT_BRIDGE = EntityTameEventBridgeBehaviour.getInstance();

    private WolfCombatInteractionBehaviour() {
    }

    public static WolfCombatInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public int adjustIncomingDamage(Entity attacker, int damage) {
        if (attacker != null && !(attacker instanceof EntityHuman) && !(attacker instanceof EntityArrow)) {
            return (damage + 1) / 2;
        }
        return damage;
    }

    public void onDamaged(EntityWolf wolf, Entity attacker) {
        if (!wolf.isTamed() && !wolf.isAngry()) {
            handleUntamedRetaliation(wolf, attacker);
            return;
        }

        if (attacker != wolf && attacker != null) {
            if (wolf.isTamed() && attacker instanceof EntityHuman && ((EntityHuman) attacker).name.equalsIgnoreCase(wolf.getOwnerName())) {
                return;
            }
            wolf.setTarget(attacker);
        }
    }

    private void handleUntamedRetaliation(EntityWolf wolf, Entity attacker) {
        if (attacker instanceof EntityHuman) {
            org.bukkit.entity.Entity bukkitTarget = attacker == null ? null : attacker.getBukkitEntity();
            EntityTargetEvent event = new EntityTargetEvent(wolf.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
            wolf.world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (event.getTarget() == null) {
                    wolf.setTarget(null);
                } else {
                    wolf.setAngry(true);
                    wolf.setTarget(ENTITY_HANDLE_BRIDGE.resolveHandle(event.getTarget()));
                }
            }
        }

        attacker = resolvePackAggroAttacker(attacker);
        if (attacker instanceof EntityLiving) {
            List nearbyWolves = wolf.world.a(EntityWolf.class, AxisAlignedBB.b(wolf.locX, wolf.locY, wolf.locZ, wolf.locX + 1.0D, wolf.locY + 1.0D, wolf.locZ + 1.0D).b(16.0D, 4.0D, 16.0D));
            Iterator iterator = nearbyWolves.iterator();

            while (iterator.hasNext()) {
                Entity candidate = (Entity) iterator.next();
                EntityWolf packWolf = (EntityWolf) candidate;

                if (!packWolf.isTamed() && packWolf.target == null) {
                    org.bukkit.entity.Entity bukkitTarget = attacker.getBukkitEntity();
                    EntityTargetEvent event = new EntityTargetEvent(wolf.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
                    wolf.world.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled()) {
                        if (event.getTarget() == null) {
                            wolf.setTarget(null);
                        } else {
                            packWolf.setTarget(attacker);
                            if (attacker instanceof EntityHuman) {
                                packWolf.setAngry(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private Entity resolvePackAggroAttacker(Entity attacker) {
        if (attacker instanceof EntityArrow && ((EntityArrow) attacker).shooter != null) {
            return ((EntityArrow) attacker).shooter;
        }
        return attacker;
    }

    public Entity findTarget(EntityWolf wolf) {
        return wolf.isAngry() ? wolf.world.findNearbyPlayer(wolf, 16.0D) : null;
    }

    public void attackTarget(EntityWolf wolf, Entity target, float distance, Random random) {
        if (distance > 2.0F && distance < 6.0F && random.nextInt(10) == 0) {
            tryLeapAtTarget(wolf, target, distance);
            return;
        }

        if ((double) distance < 1.5D && target.boundingBox.e > wolf.boundingBox.b && target.boundingBox.b < wolf.boundingBox.e) {
            performMeleeAttack(wolf, target);
        }
    }

    private void tryLeapAtTarget(EntityWolf wolf, Entity target, float distance) {
        if (!wolf.onGround) {
            return;
        }

        double deltaX = target.locX - wolf.locX;
        double deltaZ = target.locZ - wolf.locZ;
        float horizontalDistance = MathHelper.a(deltaX * deltaX + deltaZ * deltaZ);

        wolf.motX = deltaX / (double) horizontalDistance * 0.5D * 0.800000011920929D + wolf.motX * 0.20000000298023224D;
        wolf.motZ = deltaZ / (double) horizontalDistance * 0.5D * 0.800000011920929D + wolf.motZ * 0.20000000298023224D;
        wolf.motY = 0.4000000059604645D;
    }

    private void performMeleeAttack(EntityWolf wolf, Entity target) {
        wolf.attackTicks = 20;
        byte damage = wolf.isTamed() ? (byte) 4 : (byte) 2;

        org.bukkit.entity.Entity damager = wolf.getBukkitEntity();
        org.bukkit.entity.Entity damagee = target == null ? null : target.getBukkitEntity();

        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage);
        wolf.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        target.damageEntity(wolf, damage);
    }

    public boolean interact(EntityWolf wolf, EntityHuman player, int currentHealthValue, int healthWatcherIndex, Random random) {
        ItemStack itemInHand = player.inventory.getItemInHand();

        if (!wolf.isTamed()) {
            return tryTame(wolf, player, itemInHand, random);
        }

        if (tryFeed(wolf, player, itemInHand, currentHealthValue)) {
            return true;
        }

        if (player.name.equalsIgnoreCase(wolf.getOwnerName())) {
            if (!wolf.world.isStatic) {
                wolf.setSitting(!wolf.isSitting());
                wolf.poseidonClearMovementIntent();
                wolf.setPathEntity((PathEntity) null);
            }
            return true;
        }

        return false;
    }

    private boolean tryTame(EntityWolf wolf, EntityHuman player, ItemStack itemInHand, Random random) {
        if (itemInHand == null || itemInHand.id != Item.BONE.id || wolf.isAngry()) {
            return false;
        }

        --itemInHand.count;
        if (itemInHand.count <= 0) {
            player.inventory.setItem(player.inventory.itemInHandIndex, (ItemStack) null);
        }

        if (!wolf.world.isStatic) {
            boolean tameSuccess = random.nextInt(3) == 0 && ENTITY_TAME_EVENT_BRIDGE.isTamingAllowed(wolf, player);
            if (tameSuccess) {
                wolf.setTamed(true);
                wolf.setPathEntity((PathEntity) null);
                wolf.setSitting(true);
                wolf.health = 20;
                wolf.setOwnerName(player.name);
                wolf.poseidonShowTameEffect(true);
                wolf.world.a(wolf, (byte) 7);
            } else {
                wolf.poseidonShowTameEffect(false);
                wolf.world.a(wolf, (byte) 6);
            }
        }

        return true;
    }

    private boolean tryFeed(EntityWolf wolf, EntityHuman player, ItemStack itemInHand, int currentHealthValue) {
        if (itemInHand == null || !(Item.byId[itemInHand.id] instanceof ItemFood)) {
            return false;
        }

        ItemFood heldFood = (ItemFood) Item.byId[itemInHand.id];
        if (!heldFood.l() || currentHealthValue >= 20) {
            return false;
        }

        --itemInHand.count;
        if (itemInHand.count <= 0) {
            player.inventory.setItem(player.inventory.itemInHandIndex, (ItemStack) null);
        }

        wolf.b(((ItemFood) Item.PORK).k(), EntityRegainHealthEvent.RegainReason.EATING);
        return true;
    }

    public void spawnTameEffectParticles(World world, EntityWolf wolf, boolean success, Random random) {
        String particleName = success ? "heart" : "smoke";
        for (int particleIndex = 0; particleIndex < 7; ++particleIndex) {
            double gaussianX = random.nextGaussian() * 0.02D;
            double gaussianY = random.nextGaussian() * 0.02D;
            double gaussianZ = random.nextGaussian() * 0.02D;

            world.a(
                    particleName,
                    wolf.locX + (double) (random.nextFloat() * wolf.length * 2.0F) - (double) wolf.length,
                    wolf.locY + 0.5D + (double) (random.nextFloat() * wolf.width),
                    wolf.locZ + (double) (random.nextFloat() * wolf.length * 2.0F) - (double) wolf.length,
                    gaussianX,
                    gaussianY,
                    gaussianZ
            );
        }
    }
}
