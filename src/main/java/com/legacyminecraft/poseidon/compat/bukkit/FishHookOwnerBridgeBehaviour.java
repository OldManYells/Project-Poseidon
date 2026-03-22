package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityHuman;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.LivingEntity;

/**
 * Canonical bridge for CraftFish owner conversion between Bukkit and NMS types.
 */
public final class FishHookOwnerBridgeBehaviour {
    private static final FishHookOwnerBridgeBehaviour INSTANCE = new FishHookOwnerBridgeBehaviour();

    private FishHookOwnerBridgeBehaviour() {
    }

    public static FishHookOwnerBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public LivingEntity toBukkitOwner(EntityHuman owner) {
        if (owner == null) {
            return null;
        }
        return (LivingEntity) owner.getBukkitEntity();
    }

    public EntityHuman toNmsOwner(LivingEntity shooter) {
        if (!(shooter instanceof CraftHumanEntity)) {
            return null;
        }
        return ((CraftHumanEntity) shooter).getHandle();
    }
}

