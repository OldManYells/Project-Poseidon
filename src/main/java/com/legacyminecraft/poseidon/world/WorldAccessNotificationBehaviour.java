package com.legacyminecraft.poseidon.world;


import java.util.List;

/**
 * Canonical behaviour for fan-out dispatch to world-access listeners.
 */
public final class WorldAccessNotificationBehaviour {
    private static final WorldAccessNotificationBehaviour INSTANCE = new WorldAccessNotificationBehaviour();

    private WorldAccessNotificationBehaviour() {
    }

    public static WorldAccessNotificationBehaviour getInstance() {
        return INSTANCE;
    }

    public void notifyBlockChanged(List worldAccessListeners, int x, int y, int z) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(x, y, z);
        }
    }

    public void notifyBlockRange(List worldAccessListeners, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(minX, minY, minZ, maxX, maxY, maxZ);
        }
    }

    public void notifySingleBlockRange(List worldAccessListeners, int x, int y, int z) {
        this.notifyBlockRange(worldAccessListeners, x, y, z, x, y, z);
    }

    public void notifySound(List worldAccessListeners, String sound, double x, double y, double z, float volume, float pitch) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(sound, x, y, z, volume, pitch);
        }
    }

    public void notifyLevelEvent(List worldAccessListeners, String event, int x, int y, int z) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(event, x, y, z);
        }
    }

    public void notifyParticle(List worldAccessListeners, String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(particle, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    public void notifyEntityAdded(List worldAccessListeners, Entity entity) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(entity);
        }
    }

    public void notifyEntityRemoved(List worldAccessListeners, Entity entity) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).b(entity);
        }
    }

    public void notifyLightLevelChanged(List worldAccessListeners) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a();
        }
    }

    public void notifyTileEntityChanged(List worldAccessListeners, int x, int y, int z, TileEntity tileEntity) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(x, y, z, tileEntity);
        }
    }

    public void notifyAuxEffect(List worldAccessListeners, EntityHuman source, int effectId, int x, int y, int data, int extraData) {
        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(source, effectId, x, y, data, extraData);
        }
    }

    public void addWorldAccessListener(List worldAccessListeners, IWorldAccess worldAccessListener) {
        worldAccessListeners.add(worldAccessListener);
    }
}
