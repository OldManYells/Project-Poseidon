package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntityWolf;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.List;

public final class WolfAiBehaviour {
    private static final WolfAiBehaviour INSTANCE = new WolfAiBehaviour();

    private WolfAiBehaviour() {
    }

    public static WolfAiBehaviour getInstance() {
        return INSTANCE;
    }

    public void tickAi(EntityWolf wolf, boolean hasCustomGoal, int healthWatcherIndex, WolfFollowOwnerBehaviour followOwnerBehaviour) {
        if (!hasCustomGoal && !wolf.C() && wolf.isTamed() && wolf.vehicle == null) {
            followOwner(wolf, followOwnerBehaviour);
        } else if (wolf.target == null && !wolf.C() && !wolf.isTamed() && wolf.world.random.nextInt(100) == 0) {
            pickRandomSheepTarget(wolf);
        }

        if (wolf.ad()) {
            wolf.setSitting(false);
        }

        if (!wolf.world.isStatic) {
            wolf.poseidonSyncHealthWatcher(healthWatcherIndex);
        }
    }

    private void followOwner(EntityWolf wolf, WolfFollowOwnerBehaviour followOwnerBehaviour) {
        EntityHuman owner = wolf.world.a(wolf.getOwnerName());
        if (owner != null) {
            float ownerDistance = owner.f(wolf);
            if (ownerDistance > 5.0F) {
                followOwnerBehaviour.followOwnerOrTeleport(wolf, owner, ownerDistance);
            }
        } else if (!wolf.ad()) {
            wolf.setSitting(true);
        }
    }

    private void pickRandomSheepTarget(EntityWolf wolf) {
        List nearbySheep = wolf.world.a(
                EntitySheep.class,
                AxisAlignedBB.b(wolf.locX, wolf.locY, wolf.locZ, wolf.locX + 1.0D, wolf.locY + 1.0D, wolf.locZ + 1.0D).b(16.0D, 4.0D, 16.0D)
        );

        if (nearbySheep.isEmpty()) {
            return;
        }

        Entity selectedTarget = (Entity) nearbySheep.get(wolf.world.random.nextInt(nearbySheep.size()));
        org.bukkit.entity.Entity bukkitTarget = selectedTarget == null ? null : selectedTarget.getBukkitEntity();

        EntityTargetEvent event = new EntityTargetEvent(wolf.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.RANDOM_TARGET);
        wolf.world.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled() || event.getTarget() != null) {
            wolf.setTarget(selectedTarget);
        }
    }
}
