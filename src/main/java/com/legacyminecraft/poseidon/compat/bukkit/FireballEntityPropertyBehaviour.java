package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityFireball;
import org.bukkit.util.Vector;

/**
 * Canonical behavior for CraftFireball property and direction access/mutation.
 */
public final class FireballEntityPropertyBehaviour {
    private static final FireballEntityPropertyBehaviour INSTANCE = new FireballEntityPropertyBehaviour();

    private FireballEntityPropertyBehaviour() {
    }

    public static FireballEntityPropertyBehaviour getInstance() {
        return INSTANCE;
    }

    public float getYield(EntityFireball fireball) {
        return fireball.yield;
    }

    public void setYield(EntityFireball fireball, float yield) {
        fireball.yield = yield;
    }

    public boolean isIncendiary(EntityFireball fireball) {
        return fireball.isIncendiary;
    }

    public void setIncendiary(EntityFireball fireball, boolean incendiary) {
        fireball.isIncendiary = incendiary;
    }

    public Vector getDirection(EntityFireball fireball) {
        return new Vector(fireball.c, fireball.d, fireball.e);
    }

    public void setDirection(EntityFireball fireball, Vector direction) {
        fireball.setDirection(direction.getX(), direction.getY(), direction.getZ());
    }
}

