package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.bukkit.EntityHandleBridgeBehaviour;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Vec3D;
import org.bukkit.event.entity.EntityTargetEvent;

public final class GhastAiBehaviour {
    private static final GhastAiBehaviour INSTANCE = new GhastAiBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE = EntityHandleBridgeBehaviour.getInstance();

    private GhastAiBehaviour() {
    }

    public static GhastAiBehaviour getInstance() {
        return INSTANCE;
    }

    public void tickAi(EntityGhast ghast) {
        ghast.e = ghast.f;

        updateWaypointAndMotion(ghast);
        Entity currentTarget = updateTargetSelection(ghast);
        updateAttackAndLook(ghast, currentTarget);
        syncAttackWatcher(ghast);
    }

    private void updateWaypointAndMotion(EntityGhast ghast) {
        double deltaX = ghast.b - ghast.locX;
        double deltaY = ghast.c - ghast.locY;
        double deltaZ = ghast.d - ghast.locZ;
        double distanceToWaypoint = (double) MathHelper.a(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

        if (distanceToWaypoint < 1.0D || distanceToWaypoint > 60.0D) {
            ghast.b = ghast.locX + (double) ((ghast.poseidonRandomFloat() * 2.0F - 1.0F) * 16.0F);
            ghast.c = ghast.locY + (double) ((ghast.poseidonRandomFloat() * 2.0F - 1.0F) * 16.0F);
            ghast.d = ghast.locZ + (double) ((ghast.poseidonRandomFloat() * 2.0F - 1.0F) * 16.0F);
            return;
        }

        if (ghast.a-- <= 0) {
            ghast.a += ghast.poseidonRandomInt(5) + 2;
            if (ghast.poseidonCanTravelToWaypoint(ghast.b, ghast.c, ghast.d, distanceToWaypoint)) {
                ghast.motX += deltaX / distanceToWaypoint * 0.1D;
                ghast.motY += deltaY / distanceToWaypoint * 0.1D;
                ghast.motZ += deltaZ / distanceToWaypoint * 0.1D;
            } else {
                ghast.b = ghast.locX;
                ghast.c = ghast.locY;
                ghast.d = ghast.locZ;
            }
        }
    }

    private Entity updateTargetSelection(EntityGhast ghast) {
        Entity currentTarget = ghast.poseidonGetTargetEntity();
        if (currentTarget != null && currentTarget.dead) {
            EntityTargetEvent event = new EntityTargetEvent(ghast.getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
            ghast.world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (event.getTarget() == null) {
                    currentTarget = null;
                } else {
                    currentTarget = ENTITY_HANDLE_BRIDGE.resolveHandle(event.getTarget());
                }
            }
        }

        int targetRefreshTicks = ghast.poseidonGetTargetRefreshTicks();
        if (currentTarget == null || targetRefreshTicks-- <= 0) {
            Entity nearbyPlayer = ghast.world.findNearbyPlayer(ghast, 100.0D);
            if (nearbyPlayer != null) {
                EntityTargetEvent event = new EntityTargetEvent(ghast.getBukkitEntity(), nearbyPlayer.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
                ghast.world.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    if (event.getTarget() == null) {
                        currentTarget = null;
                    } else {
                        currentTarget = ENTITY_HANDLE_BRIDGE.resolveHandle(event.getTarget());
                    }
                }
            }

            if (currentTarget != null) {
                targetRefreshTicks = 20;
            }
        }

        ghast.poseidonSetTargetEntity(currentTarget);
        ghast.poseidonSetTargetRefreshTicks(targetRefreshTicks);
        return currentTarget;
    }

    private void updateAttackAndLook(EntityGhast ghast, Entity currentTarget) {
        double attackRange = 64.0D;

        if (currentTarget != null && currentTarget.g(ghast) < attackRange * attackRange) {
            double targetDeltaX = currentTarget.locX - ghast.locX;
            double targetDeltaY = currentTarget.boundingBox.b + (double) (currentTarget.width / 2.0F) - (ghast.locY + (double) (ghast.width / 2.0F));
            double targetDeltaZ = currentTarget.locZ - ghast.locZ;

            ghast.K = ghast.yaw = -((float) Math.atan2(targetDeltaX, targetDeltaZ)) * 180.0F / 3.1415927F;
            if (ghast.e(currentTarget)) {
                if (ghast.f == 10) {
                    ghast.world.makeSound(ghast, "mob.ghast.charge", ghast.poseidonGetSoundVolume(), (ghast.poseidonRandomFloat() - ghast.poseidonRandomFloat()) * 0.2F + 1.0F);
                }

                ++ghast.f;
                if (ghast.f == 20) {
                    ghast.world.makeSound(ghast, "mob.ghast.fireball", ghast.poseidonGetSoundVolume(), (ghast.poseidonRandomFloat() - ghast.poseidonRandomFloat()) * 0.2F + 1.0F);
                    EntityFireball fireball = new EntityFireball(ghast.world, ghast, targetDeltaX, targetDeltaY, targetDeltaZ);
                    double fireballOffset = 4.0D;
                    Vec3D look = ghast.b(1.0F);

                    fireball.locX = ghast.locX + look.a * fireballOffset;
                    fireball.locY = ghast.locY + (double) (ghast.width / 2.0F) + 0.5D;
                    fireball.locZ = ghast.locZ + look.c * fireballOffset;
                    ghast.world.addEntity(fireball);
                    ghast.f = -40;
                }
            } else if (ghast.f > 0) {
                --ghast.f;
            }
        } else {
            ghast.K = ghast.yaw = -((float) Math.atan2(ghast.motX, ghast.motZ)) * 180.0F / 3.1415927F;
            if (ghast.f > 0) {
                --ghast.f;
            }
        }
    }

    private void syncAttackWatcher(EntityGhast ghast) {
        if (!ghast.world.isStatic) {
            byte watcherValue = ghast.poseidonGetAttackWatcher();
            byte desiredWatcherValue = (byte) (ghast.f > 10 ? 1 : 0);
            if (watcherValue != desiredWatcherValue) {
                ghast.poseidonSetAttackWatcher(desiredWatcherValue);
            }
        }
    }

    public boolean canTravelPath(EntityGhast ghast, double waypointX, double waypointY, double waypointZ, double distanceToWaypoint) {
        double stepX = (waypointX - ghast.locX) / distanceToWaypoint;
        double stepY = (waypointY - ghast.locY) / distanceToWaypoint;
        double stepZ = (waypointZ - ghast.locZ) / distanceToWaypoint;
        AxisAlignedBB sampledBox = ghast.boundingBox.clone();

        for (int pathStep = 1; (double) pathStep < distanceToWaypoint; ++pathStep) {
            sampledBox.d(stepX, stepY, stepZ);
            if (ghast.world.getEntities(ghast, sampledBox).size() > 0) {
                return false;
            }
        }

        return true;
    }
}
