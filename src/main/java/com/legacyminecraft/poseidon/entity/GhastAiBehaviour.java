package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.compat.bukkit.EntityHandleBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

public final class GhastAiBehaviour {
    private static final GhastAiBehaviour INSTANCE = new GhastAiBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE = EntityHandleBridgeBehaviour.getInstance();

    private GhastAiBehaviour() {
    }

    public static GhastAiBehaviour getInstance() {
        return INSTANCE;
    }

    public void tickAi(Object ghast) {
        // Legacy wrapper bridge path; canonical typed AI remains in tickAi(EntityGhast).
        if (ghast instanceof EntityGhast) {
            tickAi((EntityGhast) ghast);
        }
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
            TargetResolution resolution = resolveTargetEvent(ghast, null, "TARGET_DIED");
            if (!resolution.cancelled) {
                if (resolution.target == null) {
                    currentTarget = null;
                } else {
                    currentTarget = ENTITY_HANDLE_BRIDGE.resolveHandle(resolution.target);
                }
            }
        }

        int targetRefreshTicks = ghast.poseidonGetTargetRefreshTicks();
        if (currentTarget == null || targetRefreshTicks-- <= 0) {
            Entity nearbyPlayer = ghast.world.findNearbyPlayer(ghast, 100.0D);
            if (nearbyPlayer != null) {
                TargetResolution resolution = resolveTargetEvent(ghast, nearbyPlayer.getBukkitEntity(), "CLOSEST_PLAYER");
                if (!resolution.cancelled) {
                    if (resolution.target == null) {
                        currentTarget = null;
                    } else {
                        currentTarget = ENTITY_HANDLE_BRIDGE.resolveHandle(resolution.target);
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
        Object sampledBox = ghast.boundingBox == null ? null : ghast.boundingBox.clone();

        for (int pathStep = 1; (double) pathStep < distanceToWaypoint; ++pathStep) {
            sampledBox = translateBox(sampledBox, stepX, stepY, stepZ);
            if (hasEntitiesOnPath(ghast.world, ghast, sampledBox)) {
                return false;
            }
        }

        return true;
    }

    public boolean canTravelPath(Object ghast, double waypointX, double waypointY, double waypointZ, double distanceToWaypoint) {
        if (ghast instanceof EntityGhast) {
            return canTravelPath((EntityGhast) ghast, waypointX, waypointY, waypointZ, distanceToWaypoint);
        }
        return true;
    }

    private TargetResolution resolveTargetEvent(EntityGhast ghast, Object bukkitTarget, String reasonName) {
        try {
            Object event = LegacyCompatGatewayRegistry.gateway().createEntityTargetEvent(ghast.getBukkitEntity(), bukkitTarget, reasonName);
            Object pluginManager = ghast.world.getServer().getPluginManager();
            invokeOneArgument(pluginManager, "callEvent", event);
            boolean cancelled = LegacyCompatGatewayRegistry.gateway().isEventCancelled(event);
            Object target = LegacyCompatGatewayRegistry.gateway().getEntityTargetEventTarget(event);
            return new TargetResolution(target, cancelled);
        } catch (Exception ignored) {
            return new TargetResolution(bukkitTarget, false);
        }
    }

    private boolean hasEntitiesOnPath(Object world, Object source, Object sampledBox) {
        try {
            Object result = world.getClass()
                    .getMethod("getEntities", source.getClass().getSuperclass(), sampledBox.getClass().getSuperclass())
                    .invoke(world, source, sampledBox);
            return result instanceof java.util.List && !((java.util.List) result).isEmpty();
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private Object translateBox(Object box, double x, double y, double z) {
        if (box == null) {
            return null;
        }
        try {
            return box.getClass().getMethod("a", Double.TYPE, Double.TYPE, Double.TYPE).invoke(box, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z));
        } catch (ReflectiveOperationException ignored) {
            return box;
        }
    }

    private void invokeOneArgument(Object target, String methodName, Object argument) throws ReflectiveOperationException {
        for (java.lang.reflect.Method method : target.getClass().getMethods()) {
            if (!method.getName().equals(methodName) || method.getParameterTypes().length != 1) {
                continue;
            }
            if (argument == null || method.getParameterTypes()[0].isAssignableFrom(argument.getClass())) {
                method.invoke(target, argument);
                return;
            }
        }
        throw new NoSuchMethodException(methodName);
    }

    private static final class TargetResolution {
        private final Object target;
        private final boolean cancelled;

        private TargetResolution(Object target, boolean cancelled) {
            this.target = target;
            this.cancelled = cancelled;
        }
    }
}
