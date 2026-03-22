package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Canonical coordinator for legacy entity tracker registration and fan-out.
 */
public final class EntityTrackerSystem {
    private static final EntityTrackerSystem INSTANCE = new EntityTrackerSystem();

    private EntityTrackerSystem() {
    }

    public static EntityTrackerSystem getInstance() {
        return INSTANCE;
    }

    public void trackEntity(Entity entity, int maxTrackingDistance, Set trackerEntries, EntityList trackerIndex, List worldPlayers) {
        TrackingProfile trackingProfile = resolveTrackingProfile(entity.getClass(), maxTrackingDistance);
        if (!trackingProfile.isTracked()) {
            return;
        }

        registerEntity(
                trackerEntries,
                trackerIndex,
                entity,
                trackingProfile.getTrackingDistance(),
                maxTrackingDistance,
                trackingProfile.getUpdateInterval(),
                trackingProfile.isMoving(),
                worldPlayers
        );

        if (trackingProfile.shouldRefreshExistingTrackers()) {
            registerPlayerWithExistingEntries(trackerEntries, (EntityPlayer) entity);
        }
    }

    public void registerEntity(
            Set trackerEntries,
            EntityList trackerIndex,
            Entity entity,
            int trackingDistance,
            int maxTrackingDistance,
            int updateInterval,
            boolean moving,
            List worldPlayers
    ) {
        int cappedDistance = Math.min(trackingDistance, maxTrackingDistance);
        if (trackerIndex.b(entity.id)) {
            return;
        }

        EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, cappedDistance, updateInterval, moving);
        trackerEntries.add(entitytrackerentry);
        trackerIndex.a(entity.id, entitytrackerentry);
        entitytrackerentry.scanPlayers(worldPlayers);
    }

    public void untrackEntity(Set trackerEntries, EntityList trackerIndex, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            Iterator iterator = trackerEntries.iterator();

            while (iterator.hasNext()) {
                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();
                entitytrackerentry.a(entityplayer);
            }
        }

        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) trackerIndex.d(entity.id);
        if (entitytrackerentry != null) {
            trackerEntries.remove(entitytrackerentry);
            entitytrackerentry.a();
        }
    }

    public void updatePlayers(Set trackerEntries, List worldPlayers) {
        List refreshedPlayers = new ArrayList();
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();
            entitytrackerentry.track(worldPlayers);
            if (entitytrackerentry.m && entitytrackerentry.tracker instanceof EntityPlayer) {
                refreshedPlayers.add((EntityPlayer) entitytrackerentry.tracker);
            }
        }

        for (int i = 0; i < refreshedPlayers.size(); ++i) {
            registerPlayerWithExistingEntries(trackerEntries, (EntityPlayer) refreshedPlayers.get(i));
        }
    }

    public void sendPacketToTracked(EntityList trackerIndex, Entity entity, Packet packet) {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) trackerIndex.a(entity.id);
        if (entitytrackerentry != null) {
            entitytrackerentry.a(packet);
        }
    }

    public void sendPacketToTrackedAndSelf(EntityList trackerIndex, Entity entity, Packet packet) {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) trackerIndex.a(entity.id);
        if (entitytrackerentry != null) {
            entitytrackerentry.b(packet);
        }
    }

    public void untrackPlayer(Set trackerEntries, EntityPlayer entityplayer) {
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();
            entitytrackerentry.c(entityplayer);
        }
    }

    public void onPlayerChunkLoad(Set trackerEntries, EntityPlayer entityplayer, Chunk chunk) {
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();
            if (entitytrackerentry.tracker != entityplayer
                    && entitytrackerentry.tracker.bH == chunk.x
                    && entitytrackerentry.tracker.bJ == chunk.z) {
                entitytrackerentry.b(entityplayer);
            }
        }
    }

    public void registerPlayerWithExistingEntries(Set trackerEntries, EntityPlayer entityplayer) {
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

            if (entitytrackerentry.tracker != entityplayer) {
                entitytrackerentry.b(entityplayer);
            }
        }
    }

    public TrackingProfile resolveTrackingProfile(Class entityClass, int maxTrackingDistance) {
        TrackingProfile baseProfile = resolveTrackingProfile(entityClass);
        if (!baseProfile.isTracked()) {
            return baseProfile;
        }

        return baseProfile.withTrackingDistance(Math.min(baseProfile.getTrackingDistance(), maxTrackingDistance));
    }

    private TrackingProfile resolveTrackingProfile(Class entityClass) {
        if (EntityPlayer.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(512, 2, false, true);
        } else if (EntityFish.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(64, 5, true, false);
        } else if (EntityArrow.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(64, 20, false, false);
        } else if (EntityFireball.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(64, 10, false, false);
        } else if (EntitySnowball.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(64, 10, true, false);
        } else if (EntityEgg.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(64, 10, true, false);
        } else if (EntityItem.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(64, 20, true, false);
        } else if (EntityMinecart.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(160, 5, true, false);
        } else if (EntityBoat.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(160, 5, true, false);
        } else if (EntitySquid.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(160, 3, true, false);
        } else if (IAnimal.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(160, 3, false, false);
        } else if (EntityTNTPrimed.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(160, 10, true, false);
        } else if (EntityFallingSand.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(160, 20, true, false);
        } else if (EntityPainting.class.isAssignableFrom(entityClass)) {
            return TrackingProfile.tracked(160, Integer.MAX_VALUE, false, false);
        }

        return TrackingProfile.untracked();
    }

    public static final class TrackingProfile {
        private final boolean tracked;
        private final int trackingDistance;
        private final int updateInterval;
        private final boolean moving;
        private final boolean refreshExistingTrackers;

        private TrackingProfile(
                boolean tracked,
                int trackingDistance,
                int updateInterval,
                boolean moving,
                boolean refreshExistingTrackers
        ) {
            this.tracked = tracked;
            this.trackingDistance = trackingDistance;
            this.updateInterval = updateInterval;
            this.moving = moving;
            this.refreshExistingTrackers = refreshExistingTrackers;
        }

        public static TrackingProfile untracked() {
            return new TrackingProfile(false, 0, 0, false, false);
        }

        public static TrackingProfile tracked(int trackingDistance, int updateInterval, boolean moving, boolean refreshExistingTrackers) {
            return new TrackingProfile(true, trackingDistance, updateInterval, moving, refreshExistingTrackers);
        }

        public TrackingProfile withTrackingDistance(int nextTrackingDistance) {
            return new TrackingProfile(
                    tracked,
                    nextTrackingDistance,
                    updateInterval,
                    moving,
                    refreshExistingTrackers
            );
        }

        public boolean isTracked() {
            return tracked;
        }

        public int getTrackingDistance() {
            return trackingDistance;
        }

        public int getUpdateInterval() {
            return updateInterval;
        }

        public boolean isMoving() {
            return moving;
        }

        public boolean shouldRefreshExistingTrackers() {
            return refreshExistingTrackers;
        }
    }
}
