package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behavior for CraftWorld entity-list projection to Bukkit API views.
 */
public final class CraftWorldEntityProjectionBehaviour {
    private static final CraftWorldEntityProjectionBehaviour INSTANCE = new CraftWorldEntityProjectionBehaviour();

    private CraftWorldEntityProjectionBehaviour() {
    }

    public static CraftWorldEntityProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    public List<Entity> toEntities(List<?> worldEntityList) {
        List<Entity> entities = new ArrayList<Entity>();
        for (Object value : worldEntityList) {
            if (!(value instanceof net.minecraft.server.Entity)) {
                continue;
            }

            Entity bukkitEntity = ((net.minecraft.server.Entity) value).getBukkitEntity();
            if (bukkitEntity != null) {
                entities.add(bukkitEntity);
            }
        }
        return entities;
    }

    public List<LivingEntity> toLivingEntities(List<?> worldEntityList) {
        List<LivingEntity> livingEntities = new ArrayList<LivingEntity>();
        for (Object value : worldEntityList) {
            if (!(value instanceof net.minecraft.server.Entity)) {
                continue;
            }

            Entity bukkitEntity = ((net.minecraft.server.Entity) value).getBukkitEntity();
            if (bukkitEntity instanceof LivingEntity) {
                livingEntities.add((LivingEntity) bukkitEntity);
            }
        }
        return livingEntities;
    }

    public List<Player> toPlayers(List<?> worldEntityList) {
        List<Player> players = new ArrayList<Player>();
        for (Object value : worldEntityList) {
            if (!(value instanceof net.minecraft.server.Entity)) {
                continue;
            }

            Entity bukkitEntity = ((net.minecraft.server.Entity) value).getBukkitEntity();
            if (bukkitEntity instanceof Player) {
                players.add((Player) bukkitEntity);
            }
        }
        return players;
    }
}
