package com.legacyminecraft.poseidon.entity;


public final class PigLifecycleBehaviour {
    private static final PigLifecycleBehaviour INSTANCE = new PigLifecycleBehaviour();
    private static final int SADDLE_WATCHER_INDEX = 16;
    private static final int SADDLE_FLAG_MASK = 1;
    private static final float ACHIEVEMENT_FALL_DISTANCE = 5.0F;

    private PigLifecycleBehaviour() {
    }

    public static PigLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public int getSaddleWatcherIndex() {
        return SADDLE_WATCHER_INDEX;
    }

    public String getAmbientSound() {
        return "mob.pig";
    }

    public String getHurtSound() {
        return "mob.pig";
    }

    public String getDeathSound() {
        return "mob.pigdeath";
    }

    public boolean canMount(boolean hasSaddle, boolean worldStatic, Entity currentPassenger, EntityHuman player) {
        return hasSaddle && !worldStatic && (currentPassenger == null || currentPassenger == player);
    }

    public int getDropItemId(int fireTicks) {
        return fireTicks > 0 ? Item.GRILLED_PORK.id : Item.PORK.id;
    }

    public boolean isSaddled(byte watcherByte) {
        return (watcherByte & SADDLE_FLAG_MASK) != 0;
    }

    public byte withSaddleFlag(boolean saddled) {
        return saddled ? (byte) 1 : (byte) 0;
    }

    public void onLightningStrike(EntityPig pig, EntityWeatherStorm lightning) {
        if (pig.world.isStatic) {
            return;
        }

        EntityPigZombie pigZombie = new EntityPigZombie(pig.world);
        PigZapEvent event = new PigZapEvent(pig.getBukkitEntity(), lightning.getBukkitEntity(), pigZombie.getBukkitEntity());
        pig.world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        pigZombie.setPositionRotation(pig.locX, pig.locY, pig.locZ, pig.yaw, pig.pitch);
        pig.world.addEntity(pigZombie, CreatureSpawnEvent.SpawnReason.LIGHTNING);
        pig.die();
    }

    public boolean shouldAwardPigRideAchievement(float fallDistance, Entity passenger) {
        return fallDistance > ACHIEVEMENT_FALL_DISTANCE && passenger instanceof EntityHuman;
    }

    public void awardPigRideAchievement(Entity passenger) {
        if (passenger instanceof EntityHuman) {
            ((EntityHuman) passenger).a(AchievementList.u);
        }
    }
}
