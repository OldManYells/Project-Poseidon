package com.legacyminecraft.poseidon.world.stats;


import java.util.List;
import java.util.Map;

/**
 * Canonical statistic registration service.
 */
public final class StatisticRegistry {
    private static final StatisticRegistry INSTANCE = new StatisticRegistry();

    private StatisticRegistry() {
    }

    public static StatisticRegistry getInstance() {
        return INSTANCE;
    }

    public Object register(Object statistic, Map registryById, List allStatistics) {
        int statisticId = Bridge.readInt(statistic, "e");
        String statisticName = Bridge.readString(statistic, "f");
        if (registryById.containsKey(Integer.valueOf(statisticId))) {
            Object previous = registryById.get(Integer.valueOf(statisticId));
            throw new RuntimeException(
                    "Duplicate stat id: \"" + Bridge.readString(previous, "f")
                            + "\" and \"" + statisticName + "\" at id " + statisticId);
        }

        allStatistics.add(statistic);
        registryById.put(Integer.valueOf(statisticId), statistic);
        Bridge.writeField(statistic, "h", AchievementMap.a(statisticId));
        return statistic;
    }

    private static final class Bridge {
        private static int readInt(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                return field.getInt(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static String readString(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                return (String) field.get(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static void writeField(Object target, String fieldName, Object value) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                field.set(target, value);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
