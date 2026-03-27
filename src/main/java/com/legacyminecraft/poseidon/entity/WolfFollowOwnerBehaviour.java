package com.legacyminecraft.poseidon.entity;


public final class WolfFollowOwnerBehaviour {
    private static final WolfFollowOwnerBehaviour INSTANCE = new WolfFollowOwnerBehaviour();
    private static final float TELEPORT_DISTANCE = 12.0F;

    private WolfFollowOwnerBehaviour() {
    }

    public static WolfFollowOwnerBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldPausePathing(boolean sitting, boolean shaking) {
        return sitting || shaking;
    }

    public void followOwnerOrTeleport(EntityWolf wolf, Entity owner, float ownerDistance) {
        PathEntity pathToOwner = wolf.world.findPath(wolf, owner, 16.0F);

        if (pathToOwner == null && ownerDistance > TELEPORT_DISTANCE) {
            int ownerBlockX = MathHelper.floor(owner.locX) - 2;
            int ownerBlockZ = MathHelper.floor(owner.locZ) - 2;
            int ownerBlockY = MathHelper.floor(owner.boundingBox.b);

            for (int offsetX = 0; offsetX <= 4; ++offsetX) {
                for (int offsetZ = 0; offsetZ <= 4; ++offsetZ) {
                    boolean edgeOfSearchGrid = offsetX < 1 || offsetZ < 1 || offsetX > 3 || offsetZ > 3;
                    int candidateX = ownerBlockX + offsetX;
                    int candidateY = ownerBlockY;
                    int candidateZ = ownerBlockZ + offsetZ;
                    boolean hasGround = wolf.world.e(candidateX, candidateY - 1, candidateZ);
                    boolean bodyClear = !wolf.world.e(candidateX, candidateY, candidateZ);
                    boolean headClear = !wolf.world.e(candidateX, candidateY + 1, candidateZ);

                    if (edgeOfSearchGrid && hasGround && bodyClear && headClear) {
                        wolf.setPositionRotation((double) ((float) candidateX + 0.5F), (double) candidateY, (double) ((float) candidateZ + 0.5F), wolf.yaw, wolf.pitch);
                        return;
                    }
                }
            }
        } else {
            wolf.setPathEntity(pathToOwner);
        }
    }
}
