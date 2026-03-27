package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.compat.bukkit.EntityHandleBridgeBehaviour;

import java.util.List;
import java.util.Random;

public final class PigZombieAggroBehaviour {
    private static final PigZombieAggroBehaviour INSTANCE = new PigZombieAggroBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE = EntityHandleBridgeBehaviour.getInstance();

    private PigZombieAggroBehaviour() {
    }

    public static PigZombieAggroBehaviour getInstance() {
        return INSTANCE;
    }

    public TickState tick(boolean hasTarget, int soundDelay, Random random) {
        float movementSpeed = hasTarget ? 0.95F : 0.5F;
        int updatedSoundDelay = soundDelay;
        boolean playAngrySound = false;
        float angrySoundPitch = 1.0F;

        if (updatedSoundDelay > 0 && --updatedSoundDelay == 0) {
            playAngrySound = true;
            angrySoundPitch = ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F;
        }

        return new TickState(movementSpeed, updatedSoundDelay, playAngrySound, angrySoundPitch);
    }

    public boolean canSpawn(int spawnMonstersFlag, boolean containsEntity, int overlappingEntityCount, boolean collidesWithSolid) {
        return spawnMonstersFlag > 0 && containsEntity && overlappingEntityCount == 0 && !collidesWithSolid;
    }

    public boolean shouldFindTarget(int angerLevel) {
        return angerLevel != 0;
    }

    public void propagateAggroToNearbyPigZombies(EntityPigZombie pigZombie, Entity attacker, Random random) {
        if (!(attacker instanceof EntityHuman)) {
            return;
        }

        List nearbyEntities = pigZombie.world.b(pigZombie, pigZombie.boundingBox.b(32.0D, 32.0D, 32.0D));
        for (int index = 0; index < nearbyEntities.size(); ++index) {
            Entity nearby = (Entity) nearbyEntities.get(index);
            if (nearby instanceof EntityPigZombie) {
                applyAggroTarget((EntityPigZombie) nearby, attacker, random);
            }
        }
        applyAggroTarget(pigZombie, attacker, random);
    }

    public void applyAggroTarget(EntityPigZombie pigZombie, Entity target, Random random) {
        com.legacyminecraft.compat.bukkit.entity.Entity bukkitTarget = target == null ? null : target.getBukkitEntity();
        EntityTargetEvent event = new EntityTargetEvent(pigZombie.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET);
        pigZombie.world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        if (event.getTarget() == null) {
            pigZombie.setTarget(null);
            return;
        }

        Entity resolvedTarget = ENTITY_HANDLE_BRIDGE.resolveHandle(event.getTarget());
        pigZombie.setTarget(resolvedTarget);
        pigZombie.angerLevel = 400 + random.nextInt(400);
        pigZombie.poseidonSetSoundDelay(random.nextInt(40));
    }

    public String getAmbientSound() {
        return "mob.zombiepig.zpig";
    }

    public String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    public String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    public int getDropItemId() {
        return Item.GRILLED_PORK.id;
    }

    public static final class TickState {
        public final float movementSpeed;
        public final int soundDelay;
        public final boolean playAngrySound;
        public final float angrySoundPitch;

        public TickState(float movementSpeed, int soundDelay, boolean playAngrySound, float angrySoundPitch) {
            this.movementSpeed = movementSpeed;
            this.soundDelay = soundDelay;
            this.playAngrySound = playAngrySound;
            this.angrySoundPitch = angrySoundPitch;
        }
    }
}
