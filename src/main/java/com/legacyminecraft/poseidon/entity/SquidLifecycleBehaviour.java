package com.legacyminecraft.poseidon.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SquidLifecycleBehaviour {
    private static final SquidLifecycleBehaviour INSTANCE = new SquidLifecycleBehaviour();
    private static final float PI = 3.1415927F;
    private static final float TWO_PI = 6.2831855F;

    private SquidLifecycleBehaviour() {
    }

    public static SquidLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public float createInitialTentacleSpeed(Random random) {
        return 1.0F / (random.nextFloat() + 1.0F) * 0.2F;
    }

    public String getAmbientSound() {
        return null;
    }

    public String getHurtSound() {
        return null;
    }

    public String getDeathSound() {
        return null;
    }

    public float getSoundVolume() {
        return 0.4F;
    }

    public int getDropItemId() {
        return 0;
    }

    public void dropDeathLoot(World world, com.legacyminecraft.compat.bukkit.entity.Entity bukkitEntity, Random random) {
        List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> loot = new ArrayList<com.legacyminecraft.compat.bukkit.inventory.ItemStack>();
        int inkSackCount = random.nextInt(3) + 1;
        if (inkSackCount > 0) {
            loot.add(new com.legacyminecraft.compat.bukkit.inventory.ItemStack(com.legacyminecraft.compat.bukkit.Material.INK_SACK, inkSackCount));
        }

        com.legacyminecraft.compat.bukkit.World bukkitWorld = world.getWorld();
        EntityDeathEvent event = new EntityDeathEvent(bukkitEntity, loot);
        world.getServer().getPluginManager().callEvent(event);

        for (com.legacyminecraft.compat.bukkit.inventory.ItemStack stack : event.getDrops()) {
            bukkitWorld.dropItemNaturally(bukkitEntity.getLocation(), stack);
        }
    }

    public boolean isInWater(World world, AxisAlignedBB boundingBox, Entity squidEntity) {
        return world.a(boundingBox.b(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, squidEntity);
    }

    public SquidMotionState tick(SquidMotionState state) {
        float previousPitch = state.pitch;
        float previousBodyYaw = state.bodyYaw;
        float previousTentacleAngle = state.tentacleAngle;
        float previousRotationVelocity = state.rotationVelocity;

        float updatedTentacleAngle = state.tentacleAngle + state.tentacleSpeed;
        float updatedTentacleSpeed = state.tentacleSpeed;
        if (updatedTentacleAngle > TWO_PI) {
            updatedTentacleAngle -= TWO_PI;
            if (state.random.nextInt(10) == 0) {
                updatedTentacleSpeed = createInitialTentacleSpeed(state.random);
            }
        }

        float updatedRotationVelocity;
        float updatedSwimVelocity = state.swimVelocity;
        float updatedBodyBob = state.bodyBob;
        double updatedMotionX = state.motionX;
        double updatedMotionY = state.motionY;
        double updatedMotionZ = state.motionZ;
        float updatedRenderYawOffset = state.renderYawOffset;
        float updatedYaw = state.yaw;
        float updatedBodyYaw = state.bodyYaw;
        float updatedPitch = state.pitch;

        if (state.inWater) {
            if (updatedTentacleAngle < PI) {
                float animationPhase = updatedTentacleAngle / PI;
                updatedRotationVelocity = MathHelper.sin(animationPhase * animationPhase * PI) * PI * 0.25F;
                if ((double) animationPhase > 0.75D) {
                    updatedSwimVelocity = 1.0F;
                    updatedBodyBob = 1.0F;
                } else {
                    updatedBodyBob *= 0.8F;
                }
            } else {
                updatedRotationVelocity = 0.0F;
                updatedSwimVelocity *= 0.9F;
                updatedBodyBob *= 0.99F;
            }

            if (!state.hasExternalMotionControl) {
                updatedMotionX = (double) (state.swimDirectionX * updatedSwimVelocity);
                updatedMotionY = (double) (state.swimDirectionY * updatedSwimVelocity);
                updatedMotionZ = (double) (state.swimDirectionZ * updatedSwimVelocity);
            }

            float horizontalSpeed = MathHelper.a(updatedMotionX * updatedMotionX + updatedMotionZ * updatedMotionZ);
            updatedRenderYawOffset += (-((float) Math.atan2(updatedMotionX, updatedMotionZ)) * 180.0F / PI - updatedRenderYawOffset) * 0.1F;
            updatedYaw = updatedRenderYawOffset;
            updatedBodyYaw += PI * updatedBodyBob * 1.5F;
            updatedPitch += (-((float) Math.atan2((double) horizontalSpeed, updatedMotionY)) * 180.0F / PI - updatedPitch) * 0.1F;
        } else {
            updatedRotationVelocity = MathHelper.abs(MathHelper.sin(updatedTentacleAngle)) * PI * 0.25F;
            if (!state.hasExternalMotionControl) {
                updatedMotionX = 0.0D;
                updatedMotionY -= 0.08D;
                updatedMotionY *= 0.9800000190734863D;
                updatedMotionZ = 0.0D;
            }

            updatedPitch = (float) ((double) state.pitch + (double) (-90.0F - state.pitch) * 0.02D);
        }

        return new SquidMotionState(
                updatedPitch,
                previousPitch,
                updatedBodyYaw,
                previousBodyYaw,
                updatedTentacleAngle,
                previousTentacleAngle,
                updatedSwimVelocity,
                previousRotationVelocity,
                updatedRotationVelocity,
                updatedTentacleSpeed,
                updatedBodyBob,
                state.swimDirectionX,
                state.swimDirectionY,
                state.swimDirectionZ,
                updatedMotionX,
                updatedMotionY,
                updatedMotionZ,
                updatedRenderYawOffset,
                updatedYaw,
                state.inWater,
                state.hasExternalMotionControl,
                state.random
        );
    }

    public boolean shouldRetargetSwimDirection(int retargetRoll, boolean wasInWater, float swimDirectionX, float swimDirectionY, float swimDirectionZ) {
        return retargetRoll == 0 || !wasInWater || swimDirectionX == 0.0F && swimDirectionY == 0.0F && swimDirectionZ == 0.0F;
    }

    public SwimDirection randomSwimDirection(Random random) {
        float directionAngle = random.nextFloat() * PI * 2.0F;
        float directionX = MathHelper.cos(directionAngle) * 0.2F;
        float directionY = -0.1F + random.nextFloat() * 0.2F;
        float directionZ = MathHelper.sin(directionAngle) * 0.2F;
        return new SwimDirection(directionX, directionY, directionZ);
    }

    public static final class SwimDirection {
        public final float x;
        public final float y;
        public final float z;

        public SwimDirection(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static final class SquidMotionState {
        public final float pitch;
        public final float previousPitch;
        public final float bodyYaw;
        public final float previousBodyYaw;
        public final float tentacleAngle;
        public final float previousTentacleAngle;
        public final float swimVelocity;
        public final float previousRotationVelocity;
        public final float rotationVelocity;
        public final float tentacleSpeed;
        public final float bodyBob;
        public final float swimDirectionX;
        public final float swimDirectionY;
        public final float swimDirectionZ;
        public final double motionX;
        public final double motionY;
        public final double motionZ;
        public final float renderYawOffset;
        public final float yaw;
        public final boolean inWater;
        public final boolean hasExternalMotionControl;
        public final Random random;

        public SquidMotionState(float pitch, float previousPitch, float bodyYaw, float previousBodyYaw, float tentacleAngle, float previousTentacleAngle, float swimVelocity, float previousRotationVelocity, float rotationVelocity, float tentacleSpeed, float bodyBob, float swimDirectionX, float swimDirectionY, float swimDirectionZ, double motionX, double motionY, double motionZ, float renderYawOffset, float yaw, boolean inWater, boolean hasExternalMotionControl, Random random) {
            this.pitch = pitch;
            this.previousPitch = previousPitch;
            this.bodyYaw = bodyYaw;
            this.previousBodyYaw = previousBodyYaw;
            this.tentacleAngle = tentacleAngle;
            this.previousTentacleAngle = previousTentacleAngle;
            this.swimVelocity = swimVelocity;
            this.previousRotationVelocity = previousRotationVelocity;
            this.rotationVelocity = rotationVelocity;
            this.tentacleSpeed = tentacleSpeed;
            this.bodyBob = bodyBob;
            this.swimDirectionX = swimDirectionX;
            this.swimDirectionY = swimDirectionY;
            this.swimDirectionZ = swimDirectionZ;
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
            this.renderYawOffset = renderYawOffset;
            this.yaw = yaw;
            this.inWater = inWater;
            this.hasExternalMotionControl = hasExternalMotionControl;
            this.random = random;
        }
    }
}
