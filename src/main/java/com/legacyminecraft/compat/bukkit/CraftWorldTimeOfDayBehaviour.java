package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for CraftWorld day-time and full-time conversion math.
 */
public final class CraftWorldTimeOfDayBehaviour {
    private static final CraftWorldTimeOfDayBehaviour INSTANCE = new CraftWorldTimeOfDayBehaviour();

    private CraftWorldTimeOfDayBehaviour() {
    }

    public static CraftWorldTimeOfDayBehaviour getInstance() {
        return INSTANCE;
    }

    public long getDayTime(long fullTime) {
        long dayTime = fullTime % 24000L;
        if (dayTime < 0) {
            dayTime += 24000L;
        }
        return dayTime;
    }

    public long resolveFullTimeForRequestedDayTime(long currentFullTime, long requestedDayTime) {
        long dayTimeMargin = (requestedDayTime - currentFullTime) % 24000L;
        if (dayTimeMargin < 0) {
            dayTimeMargin += 24000L;
        }
        return currentFullTime + dayTimeMargin;
    }
}
