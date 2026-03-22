package com.legacyminecraft.poseidon.runtime;

import java.util.LinkedList;

/**
 * Tracks rolling server TPS samples for runtime observability.
 */
public final class TickTelemetry {
    private final LinkedList<Double> tpsRecords = new LinkedList<Double>();
    private long lastTick = System.currentTimeMillis();
    private int tickCount = 0;

    public LinkedList<Double> getTpsRecords() {
        return tpsRecords;
    }

    public void recordTick() {
        long currentTime = System.currentTimeMillis();
        tickCount++;

        if (currentTime - lastTick < 1000L) {
            return;
        }

        double tps = tickCount / ((currentTime - lastTick) / 1000.0D);
        tpsRecords.addFirst(tps);
        if (tpsRecords.size() > 900) {
            tpsRecords.removeLast();
        }

        tickCount = 0;
        lastTick = currentTime;
    }
}
