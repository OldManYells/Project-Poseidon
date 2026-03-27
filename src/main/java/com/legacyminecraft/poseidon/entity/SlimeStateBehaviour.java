package com.legacyminecraft.poseidon.entity;


import java.util.Random;

public final class SlimeStateBehaviour {
    private static final SlimeStateBehaviour INSTANCE = new SlimeStateBehaviour();

    private SlimeStateBehaviour() {
    }

    public static SlimeStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int createInitialPhysicalSize(Random random) {
        return 1 << random.nextInt(3);
    }

    public int createInitialJumpDelay(Random random) {
        return random.nextInt(20) + 10;
    }

    public byte createInitialWatcherSize() {
        return (byte) 1;
    }

    public SetSizeState computeSetSizeState(int slimeSize) {
        float scaledWidth = 0.6F * (float) slimeSize;
        int scaledHealth = slimeSize * slimeSize;
        return new SetSizeState(scaledWidth, scaledHealth);
    }

    public float tickLandingSquish(EntitySlime slime, float squishAmount, boolean wasOnGround, boolean onGround, int slimeSize, Random random) {
        if (onGround && !wasOnGround) {
            spawnLandingParticles(slime, slimeSize, random);
            if (slimeSize > 2) {
                slime.world.makeSound(slime, "mob.slime", getSoundVolume(), ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }
            squishAmount = -0.5F;
        }

        return squishAmount * 0.6F;
    }

    private void spawnLandingParticles(EntitySlime slime, int slimeSize, Random random) {
        for (int particleIndex = 0; particleIndex < slimeSize * 8; ++particleIndex) {
            float angle = random.nextFloat() * 3.1415927F * 2.0F;
            float distanceScale = random.nextFloat() * 0.5F + 0.5F;
            float offsetX = MathHelper.sin(angle) * (float) slimeSize * 0.5F * distanceScale;
            float offsetZ = MathHelper.cos(angle) * (float) slimeSize * 0.5F * distanceScale;
            slime.world.a("slime", slime.locX + (double) offsetX, slime.boundingBox.b, slime.locZ + (double) offsetZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public void splitOnDeath(EntitySlime slime, int slimeSize, int health, boolean worldStatic, Random random) {
        if (worldStatic || slimeSize <= 1 || health > 0) {
            return;
        }

        for (int splitIndex = 0; splitIndex < 4; ++splitIndex) {
            float offsetX = ((float) (splitIndex % 2) - 0.5F) * (float) slimeSize / 4.0F;
            float offsetZ = ((float) (splitIndex / 2) - 0.5F) * (float) slimeSize / 4.0F;
            EntitySlime child = new EntitySlime(slime.world);
            child.setSize(slimeSize / 2);
            child.setPositionRotation(slime.locX + (double) offsetX, slime.locY + 0.5D, slime.locZ + (double) offsetZ, random.nextFloat() * 360.0F, 0.0F);
            slime.world.addEntity(child);
        }
    }

    public void attackPlayerOnContact(EntitySlime slime, EntityHuman human, int slimeSize, Random random) {
        if (slimeSize > 1 && slime.e(human) && (double) slime.f(human) < 0.6D * (double) slimeSize && human.damageEntity(slime, slimeSize)) {
            slime.world.makeSound(slime, "mob.slimeattack", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    public String getHurtSound() {
        return "mob.slime";
    }

    public String getDeathSound() {
        return "mob.slime";
    }

    public int getDropItemId(int slimeSize) {
        return slimeSize == 1 ? Item.SLIME_BALL.id : 0;
    }

    public boolean canSpawn(Chunk chunk, int slimeSize, int spawnMonstersFlag, int randomRoll, double locY) {
        return (slimeSize == 1 || spawnMonstersFlag > 0)
                && randomRoll == 0
                && chunk.a(987234911L).nextInt(10) == 0
                && locY < 16.0D;
    }

    public float getSoundVolume() {
        return 0.6F;
    }

    public static final class SetSizeState {
        public final float scaledWidth;
        public final int scaledHealth;

        public SetSizeState(float scaledWidth, int scaledHealth) {
            this.scaledWidth = scaledWidth;
            this.scaledHealth = scaledHealth;
        }
    }
}
