package com.legacyminecraft.poseidon.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SkeletonLifecycleBehaviour {
    private static final SkeletonLifecycleBehaviour INSTANCE = new SkeletonLifecycleBehaviour();

    private SkeletonLifecycleBehaviour() {
    }

    public static SkeletonLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public String getAmbientSound() {
        return "mob.skeleton";
    }

    public String getHurtSound() {
        return "mob.skeletonhurt";
    }

    public String getDeathSound() {
        return "mob.skeletonhurt";
    }

    public void tickSunlightCombustion(EntitySkeleton skeleton, float brightness, float randomRoll) {
        if (!skeleton.world.d()) {
            return;
        }

        boolean brightEnough = brightness > 0.5F;
        boolean chunkLoaded = skeleton.world.isChunkLoaded(MathHelper.floor(skeleton.locX), MathHelper.floor(skeleton.locY), MathHelper.floor(skeleton.locZ));
        boolean shouldIgnite = randomRoll * 30.0F < (brightness - 0.4F) * 2.0F;
        if (brightEnough && chunkLoaded && shouldIgnite) {
            EntityCombustEvent event = new EntityCombustEvent(skeleton.getBukkitEntity());
            skeleton.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                skeleton.fireTicks = 300;
            }
        }
    }

    public void attackRanged(EntitySkeleton skeleton, Entity target, float distance, boolean skeletonShootingSoundFixEnabled, Random random) {
        if (distance >= 10.0F) {
            return;
        }

        double deltaX = target.locX - skeleton.locX;
        double deltaZ = target.locZ - skeleton.locZ;
        if (skeleton.attackTicks == 0) {
            EntityArrow arrow = new EntityArrow(skeleton.world, skeleton);
            ++arrow.locY;

            double deltaY = target.locY + (double) target.t() - 0.20000000298023224D - arrow.locY;
            float arcCompensation = MathHelper.a(deltaX * deltaX + deltaZ * deltaZ) * 0.2F;

            if (skeletonShootingSoundFixEnabled) {
                skeleton.world.e(1002, MathHelper.floor(skeleton.locX), MathHelper.floor(skeleton.locY - (double) skeleton.height), MathHelper.floor(skeleton.locZ), 0);
            } else {
                skeleton.world.makeSound(skeleton, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            }

            arrow.a(deltaX, deltaY + (double) arcCompensation, deltaZ, 0.6F, 12.0F);
            skeleton.world.addEntity(arrow);
            skeleton.attackTicks = 30;
        }

        skeleton.yaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / 3.1415927410125732D) - 90.0F;
        skeleton.poseidonSetHasActiveAttackGoal(true);
    }

    public int getDropItemId() {
        return Item.ARROW.id;
    }

    public void dropDeathLoot(World world, com.legacyminecraft.compat.bukkit.entity.Entity bukkitEntity, Random random) {
        List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> drops = new ArrayList<com.legacyminecraft.compat.bukkit.inventory.ItemStack>();

        int arrowCount = random.nextInt(3);
        if (arrowCount > 0) {
            drops.add(new com.legacyminecraft.compat.bukkit.inventory.ItemStack(com.legacyminecraft.compat.bukkit.Material.ARROW, arrowCount));
        }

        int boneCount = random.nextInt(3);
        if (boneCount > 0) {
            drops.add(new com.legacyminecraft.compat.bukkit.inventory.ItemStack(com.legacyminecraft.compat.bukkit.Material.BONE, boneCount));
        }

        com.legacyminecraft.compat.bukkit.World bukkitWorld = world.getWorld();
        EntityDeathEvent event = new EntityDeathEvent(bukkitEntity, drops);
        world.getServer().getPluginManager().callEvent(event);

        for (com.legacyminecraft.compat.bukkit.inventory.ItemStack stack : event.getDrops()) {
            bukkitWorld.dropItemNaturally(bukkitEntity.getLocation(), stack);
        }
    }
}
