package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.FishHookOwnerBridgeBehaviour;
import net.minecraft.server.EntityFish;
import net.minecraft.server.EntityHuman;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LivingEntity;

public class CraftFish extends AbstractProjectile implements Fish {
    private static final FishHookOwnerBridgeBehaviour FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR =
            FishHookOwnerBridgeBehaviour.getInstance();

    public CraftFish(CraftServer server, EntityFish entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftFish";
    }

    public LivingEntity getShooter() {
        return FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR.toBukkitOwner(((EntityFish) getHandle()).owner);
    }

    public void setShooter(LivingEntity shooter) {
        EntityHuman owner = FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR.toNmsOwner(shooter);
        if (owner != null) {
            ((EntityFish) getHandle()).owner = owner;
        }
    }

}
