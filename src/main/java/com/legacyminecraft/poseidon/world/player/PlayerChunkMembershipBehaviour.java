package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical behaviour for player-chunk membership and teardown policy.
 */
public final class PlayerChunkMembershipBehaviour {
    private static final PlayerChunkMembershipBehaviour INSTANCE = new PlayerChunkMembershipBehaviour();

    private PlayerChunkMembershipBehaviour() {
    }

    public static PlayerChunkMembershipBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldSendPreChunkLoad(boolean chunkSubscriptionAdded) {
        return chunkSubscriptionAdded;
    }

    public boolean shouldSendPreChunkUnload(boolean chunkSubscriptionRemoved) {
        return chunkSubscriptionRemoved;
    }

    public boolean isChunkNowEmpty(int subscribedPlayers) {
        return subscribedPlayers == 0;
    }

    public boolean shouldUntrackDirtyInstance(int dirtyCount) {
        return dirtyCount > 0;
    }

    public long chunkKey(int chunkX, int chunkZ) {
        return (long) chunkX + 2147483647L | (long) chunkZ + 2147483647L << 32;
    }
}
