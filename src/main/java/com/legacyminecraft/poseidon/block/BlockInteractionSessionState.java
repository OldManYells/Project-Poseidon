package com.legacyminecraft.poseidon.block;

/**
 * Mutable per-connection interaction state used by block dig/place packet handlers.
 */
public final class BlockInteractionSessionState {
    private int lastDropTick;
    private int dropCount;
    private Long lastPacketTimestamp;
    private int lastMaterial;
    private int lastSpawnProtectionMessageTick = Integer.MIN_VALUE;

    public BlockInteractionSessionState(int lastDropTick, int dropCount, Long lastPacketTimestamp, int lastMaterial) {
        this.lastDropTick = lastDropTick;
        this.dropCount = dropCount;
        this.lastPacketTimestamp = lastPacketTimestamp;
        this.lastMaterial = lastMaterial;
    }

    public int getLastDropTick() {
        return lastDropTick;
    }

    public void setLastDropTick(int lastDropTick) {
        this.lastDropTick = lastDropTick;
    }

    public int getDropCount() {
        return dropCount;
    }

    public void setDropCount(int dropCount) {
        this.dropCount = dropCount;
    }

    public Long getLastPacketTimestamp() {
        return lastPacketTimestamp;
    }

    public void setLastPacketTimestamp(Long lastPacketTimestamp) {
        this.lastPacketTimestamp = lastPacketTimestamp;
    }

    public int getLastMaterial() {
        return lastMaterial;
    }

    public void setLastMaterial(int lastMaterial) {
        this.lastMaterial = lastMaterial;
    }

    public int getLastSpawnProtectionMessageTick() {
        return lastSpawnProtectionMessageTick;
    }

    public void setLastSpawnProtectionMessageTick(int lastSpawnProtectionMessageTick) {
        this.lastSpawnProtectionMessageTick = lastSpawnProtectionMessageTick;
    }
}
