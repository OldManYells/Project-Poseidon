package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.entity.EntityHuman;

/**
 * Item-behaviour view of a player entity.
 */
public class EntityPlayer extends EntityHuman {
    private final Player bukkitPlayer = new Player();

    @Override
    public Object getBukkitEntity() {
        return bukkitPlayer;
    }
}
