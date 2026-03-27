package com.legacyminecraft.compat.bukkit;


import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Canonical behavior for projectile bounce state storage.
 */
public final class ProjectileBounceBehaviour {
    private static final ProjectileBounceBehaviour INSTANCE = new ProjectileBounceBehaviour();
    private final Map<Object, Boolean> bounceStates =
            Collections.synchronizedMap(new IdentityHashMap<Object, Boolean>());

    private ProjectileBounceBehaviour() {
    }

    public static ProjectileBounceBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean doesBounce(Object projectile) {
        return bounceStates.containsKey(projectile);
    }

    public void setBounce(Object projectile, boolean doesBounce) {
        if (doesBounce) {
            bounceStates.put(projectile, Boolean.TRUE);
        } else {
            bounceStates.remove(projectile);
        }
    }
}
