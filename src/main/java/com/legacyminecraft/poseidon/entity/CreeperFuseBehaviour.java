package com.legacyminecraft.poseidon.entity;


public final class CreeperFuseBehaviour {
    private static final CreeperFuseBehaviour INSTANCE = new CreeperFuseBehaviour();

    private CreeperFuseBehaviour() {
    }

    public static CreeperFuseBehaviour getInstance() {
        return INSTANCE;
    }

    public void tickFuseCooldownWhileIdleServer(EntityCreeper creeper) {
        if (!creeper.world.isStatic && creeper.poseidonGetFuseTicks() > 0) {
            creeper.poseidonSetFuseDirection(-1);
            creeper.poseidonSetFuseTicks(Math.max(0, creeper.poseidonGetFuseTicks() - 1));
        }
    }

    public void tickBeforeSuper(EntityCreeper creeper) {
        creeper.poseidonSetLastFuseTicks(creeper.poseidonGetFuseTicks());
        if (!creeper.world.isStatic) {
            return;
        }

        int fuseDirection = creeper.poseidonGetFuseDirection();
        if (fuseDirection > 0 && creeper.poseidonGetFuseTicks() == 0) {
            creeper.world.makeSound(creeper, "random.fuse", 1.0F, 0.5F);
        }

        creeper.poseidonSetFuseTicks(creeper.poseidonGetFuseTicks() + fuseDirection);
        if (creeper.poseidonGetFuseTicks() < 0) {
            creeper.poseidonSetFuseTicks(0);
        }

        if (creeper.poseidonGetFuseTicks() >= 30) {
            creeper.poseidonSetFuseTicks(30);
        }
    }

    public void tickAfterSuper(EntityCreeper creeper) {
        if (creeper.target == null && creeper.poseidonGetFuseTicks() > 0) {
            creeper.poseidonSetFuseDirection(-1);
            creeper.poseidonSetFuseTicks(Math.max(0, creeper.poseidonGetFuseTicks() - 1));
        }
    }

    public void handleProximityFuse(EntityCreeper creeper, Entity target, float distance) {
        if (creeper.world.isStatic) {
            return;
        }

        int fuseDirection = creeper.poseidonGetFuseDirection();
        if ((fuseDirection > 0 || distance >= 3.0F) && (fuseDirection <= 0 || distance >= 7.0F)) {
            creeper.poseidonSetFuseDirection(-1);
            creeper.poseidonSetFuseTicks(Math.max(0, creeper.poseidonGetFuseTicks() - 1));
            return;
        }

        if (creeper.poseidonGetFuseTicks() == 0) {
            creeper.world.makeSound(creeper, "random.fuse", 1.0F, 0.5F);
        }

        creeper.poseidonSetFuseDirection(1);
        creeper.poseidonSetFuseTicks(creeper.poseidonGetFuseTicks() + 1);
        if (creeper.poseidonGetFuseTicks() >= 30) {
            triggerExplosionOrResetFuse(creeper);
        }

        creeper.poseidonSetHasActiveAttackGoal(true);
    }

    private void triggerExplosionOrResetFuse(EntityCreeper creeper) {
        float radius = creeper.isPowered() ? 6.0F : 3.0F;
        ExplosionPrimeEvent event = new ExplosionPrimeEvent(creeper.getBukkitEntity(), radius, false);
        creeper.world.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            creeper.world.createExplosion(creeper, creeper.locX, creeper.locY, creeper.locZ, event.getRadius(), event.getFire());
            creeper.die();
        } else {
            creeper.poseidonSetFuseTicks(0);
        }
    }
}
