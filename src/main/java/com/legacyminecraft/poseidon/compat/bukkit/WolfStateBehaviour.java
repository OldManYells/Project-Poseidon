package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityWolf;
import net.minecraft.server.PathEntity;
import org.bukkit.Server;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;

/**
 * Canonical behaviour for CraftWolf state and owner/taming policy.
 */
public final class WolfStateBehaviour {
    private static final WolfStateBehaviour INSTANCE = new WolfStateBehaviour();

    private WolfStateBehaviour() {
    }

    public static WolfStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isAngry(EntityWolf wolf) {
        return wolf.isAngry();
    }

    public void setAngry(EntityWolf wolf, boolean angry) {
        wolf.setAngry(angry);
    }

    public boolean isSitting(EntityWolf wolf) {
        return wolf.isSitting();
    }

    public void setSitting(EntityWolf wolf, boolean sitting) {
        wolf.setSitting(sitting);
        wolf.setPathEntity((PathEntity) null);
    }

    public boolean isTamed(EntityWolf wolf) {
        return wolf.isTamed();
    }

    public void setTamed(EntityWolf wolf, boolean tame) {
        wolf.setTamed(tame);
    }

    public AnimalTamer resolveOwner(AnimalTamer cachedOwner, Server server, String ownerName) {
        if (cachedOwner != null) {
            return cachedOwner;
        }
        return server.getPlayer(ownerName);
    }

    public AnimalTamer applyOwner(EntityWolf wolf, AnimalTamer owner) {
        if (owner != null) {
            wolf.setTamed(true);
            wolf.setPathEntity((PathEntity) null);
            if (owner instanceof Player) {
                wolf.setOwnerName(((Player) owner).getName());
            } else {
                wolf.setOwnerName("");
            }
            return owner;
        }

        wolf.setTamed(false);
        wolf.setOwnerName("");
        return null;
    }

    public String getOwnerName(EntityWolf wolf) {
        return wolf.getOwnerName();
    }

    public void setOwnerName(EntityWolf wolf, String ownerName) {
        wolf.setOwnerName(ownerName);
    }
}
