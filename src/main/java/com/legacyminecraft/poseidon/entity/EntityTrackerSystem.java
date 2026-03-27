package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    public void trackEntity(Object entity, int maxTrackingDistance, Set trackerEntries, Object trackerIndex, List worldPlayers) {
        TrackingProfile trackingProfile = resolveTrackingProfile(entity, maxTrackingDistance);
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
            registerPlayerWithExistingEntries(trackerEntries, entity);
        }
    }

    public void registerEntity(
            Set trackerEntries,
            Object trackerIndex,
            Object entity,
            int trackingDistance,
            int maxTrackingDistance,
            int updateInterval,
            boolean moving,
            List worldPlayers
    ) {
        int entityId = getIntField(entity, "id");
        int cappedDistance = Math.min(trackingDistance, maxTrackingDistance);
        if (Boolean.TRUE.equals(invoke(trackerIndex, "b", Integer.valueOf(entityId)))) {
            return;
        }

        Object entityTrackerEntry = LegacyCompatGatewayRegistry.gateway()
                .createEntityTrackerEntry(entity, cappedDistance, updateInterval, moving);
        trackerEntries.add(entityTrackerEntry);
        invoke(trackerIndex, "a", Integer.valueOf(entityId), entityTrackerEntry);
        invoke(entityTrackerEntry, "scanPlayers", worldPlayers);
    }

    public void untrackEntity(Set trackerEntries, Object trackerIndex, Object entity) {
        if (isEntityKind(entity, "PLAYER")) {
            Iterator iterator = trackerEntries.iterator();

            while (iterator.hasNext()) {
                Object entityTrackerEntry = iterator.next();
                invoke(entityTrackerEntry, "a", entity);
            }
        }

        Object entityTrackerEntry = invoke(trackerIndex, "d", Integer.valueOf(getIntField(entity, "id")));
        if (entityTrackerEntry != null) {
            trackerEntries.remove(entityTrackerEntry);
            invoke(entityTrackerEntry, "a");
        }
    }

    public void updatePlayers(Set trackerEntries, List worldPlayers) {
        List refreshedPlayers = new ArrayList();
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            Object entityTrackerEntry = iterator.next();
            invoke(entityTrackerEntry, "track", worldPlayers);
            if (getBooleanField(entityTrackerEntry, "m")) {
                Object trackedEntity = getField(entityTrackerEntry, "tracker");
                if (trackedEntity != null && isEntityKind(trackedEntity, "PLAYER")) {
                    refreshedPlayers.add(trackedEntity);
                }
            }
        }

        for (int i = 0; i < refreshedPlayers.size(); ++i) {
            registerPlayerWithExistingEntries(trackerEntries, refreshedPlayers.get(i));
        }
    }

    public void sendPacketToTracked(Object trackerIndex, Object entity, Object packet) {
        Object entityTrackerEntry = invoke(trackerIndex, "a", Integer.valueOf(getIntField(entity, "id")));
        if (entityTrackerEntry != null) {
            invoke(entityTrackerEntry, "a", packet);
        }
    }

    public void sendPacketToTrackedAndSelf(Object trackerIndex, Object entity, Object packet) {
        Object entityTrackerEntry = invoke(trackerIndex, "a", Integer.valueOf(getIntField(entity, "id")));
        if (entityTrackerEntry != null) {
            invoke(entityTrackerEntry, "b", packet);
        }
    }

    public void untrackPlayer(Set trackerEntries, Object entityplayer) {
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            Object entityTrackerEntry = iterator.next();
            invoke(entityTrackerEntry, "c", entityplayer);
        }
    }

    public void onPlayerChunkLoad(Set trackerEntries, Object entityplayer, Object chunk) {
        int chunkX = getIntField(chunk, "x");
        int chunkZ = getIntField(chunk, "z");
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            Object entityTrackerEntry = iterator.next();
            Object trackedEntity = getField(entityTrackerEntry, "tracker");
            if (trackedEntity != entityplayer
                    && trackedEntity != null
                    && getIntField(trackedEntity, "bH") == chunkX
                    && getIntField(trackedEntity, "bJ") == chunkZ) {
                invoke(entityTrackerEntry, "b", entityplayer);
            }
        }
    }

    public void registerPlayerWithExistingEntries(Set trackerEntries, Object entityplayer) {
        Iterator iterator = trackerEntries.iterator();

        while (iterator.hasNext()) {
            Object entityTrackerEntry = iterator.next();
            if (getField(entityTrackerEntry, "tracker") != entityplayer) {
                invoke(entityTrackerEntry, "b", entityplayer);
            }
        }
    }

    public TrackingProfile resolveTrackingProfile(Object entity, int maxTrackingDistance) {
        TrackingProfile baseProfile = resolveTrackingProfile(entity);
        if (!baseProfile.isTracked()) {
            return baseProfile;
        }

        return baseProfile.withTrackingDistance(Math.min(baseProfile.getTrackingDistance(), maxTrackingDistance));
    }

    private TrackingProfile resolveTrackingProfile(Object entity) {
        if (isEntityKind(entity, "PLAYER")) return TrackingProfile.tracked(512, 2, false, true);
        if (isEntityKind(entity, "FISH")) return TrackingProfile.tracked(64, 5, true, false);
        if (isEntityKind(entity, "ARROW")) return TrackingProfile.tracked(64, 20, false, false);
        if (isEntityKind(entity, "FIREBALL")) return TrackingProfile.tracked(64, 10, false, false);
        if (isEntityKind(entity, "SNOWBALL")) return TrackingProfile.tracked(64, 10, true, false);
        if (isEntityKind(entity, "EGG")) return TrackingProfile.tracked(64, 10, true, false);
        if (isEntityKind(entity, "ITEM")) return TrackingProfile.tracked(64, 20, true, false);
        if (isEntityKind(entity, "MINECART")) return TrackingProfile.tracked(160, 5, true, false);
        if (isEntityKind(entity, "BOAT")) return TrackingProfile.tracked(160, 5, true, false);
        if (isEntityKind(entity, "SQUID")) return TrackingProfile.tracked(160, 3, true, false);
        if (isEntityKind(entity, "IANIMAL")) return TrackingProfile.tracked(160, 3, false, false);
        if (isEntityKind(entity, "TNT_PRIMED")) return TrackingProfile.tracked(160, 10, true, false);
        if (isEntityKind(entity, "FALLING_SAND")) return TrackingProfile.tracked(160, 20, true, false);
        if (isEntityKind(entity, "PAINTING")) return TrackingProfile.tracked(160, Integer.MAX_VALUE, false, false);
        return TrackingProfile.untracked();
    }

    private boolean isEntityKind(Object entity, String kind) {
        return entity != null && LegacyCompatGatewayRegistry.gateway().isEntityKind(entity, kind);
    }

    private static Object getField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to read field: " + fieldName, exception);
            }
        }

        throw new IllegalStateException("Field not found: " + fieldName);
    }

    private static int getIntField(Object target, String fieldName) {
        Object value = getField(target, fieldName);
        return value == null ? 0 : ((Number) value).intValue();
    }

    private static boolean getBooleanField(Object target, String fieldName) {
        Object value = getField(target, fieldName);
        return value != null && ((Boolean) value).booleanValue();
    }

    private static Object invoke(Object target, String methodName, Object... args) {
        if (target == null) {
            return null;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            Method[] methods = type.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != args.length) {
                    continue;
                }

                if (!parametersMatch(method.getParameterTypes(), args)) {
                    continue;
                }

                try {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                } catch (Exception ignored) {
                }
            }
            type = type.getSuperclass();
        }

        throw new IllegalStateException("Method not found: " + methodName);
    }

    private static boolean parametersMatch(Class<?>[] parameterTypes, Object[] args) {
        for (int i = 0; i < parameterTypes.length; ++i) {
            Object arg = args[i];
            if (arg == null) {
                continue;
            }

            if (!wrap(parameterTypes[i]).isAssignableFrom(arg.getClass())) {
                return false;
            }
        }

        return true;
    }

    private static Class<?> wrap(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }

        if (type == Integer.TYPE) return Integer.class;
        if (type == Boolean.TYPE) return Boolean.class;
        if (type == Long.TYPE) return Long.class;
        if (type == Double.TYPE) return Double.class;
        if (type == Float.TYPE) return Float.class;
        if (type == Short.TYPE) return Short.class;
        if (type == Byte.TYPE) return Byte.class;
        if (type == Character.TYPE) return Character.class;
        return type;
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
            return new TrackingProfile(tracked, nextTrackingDistance, updateInterval, moving, refreshExistingTrackers);
        }

        public boolean isTracked() { return tracked; }
        public int getTrackingDistance() { return trackingDistance; }
        public int getUpdateInterval() { return updateInterval; }
        public boolean isMoving() { return moving; }
        public boolean shouldRefreshExistingTrackers() { return refreshExistingTrackers; }
    }
}
