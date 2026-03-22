package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;

/**
 * Canonical behaviour for CraftEntity identity equality and hash-code policy.
 */
public final class EntityIdentityBridgeBehaviour {
    private static final EntityIdentityBridgeBehaviour INSTANCE = new EntityIdentityBridgeBehaviour();

    private EntityIdentityBridgeBehaviour() {
    }

    public static EntityIdentityBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equalsEntity(CraftEntity self, Object otherObject, CraftServer server, Entity entity) {
        if (otherObject == null) {
            return false;
        }
        if (self.getClass() != otherObject.getClass()) {
            return false;
        }

        CraftEntity other = (CraftEntity) otherObject;
        if (server != other.getServer() && (server == null || !server.equals(other.getServer()))) {
            return false;
        }
        return entity == other.getHandle() || (entity != null && entity.equals(other.getHandle()));
    }

    public int hash(CraftServer server, Entity entity) {
        int hash = 7;
        hash = 89 * hash + (server != null ? server.hashCode() : 0);
        hash = 89 * hash + (entity != null ? entity.hashCode() : 0);
        return hash;
    }
}
