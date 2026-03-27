package com.legacyminecraft.poseidon.world.player;


/**
 * Canonical bed/sleep packet synchronization for legacy EntityPlayer wrappers.
 */
public final class PlayerSleepSynchronizationSystem {
    private static final PlayerSleepSynchronizationSystem INSTANCE = new PlayerSleepSynchronizationSystem();

    private PlayerSleepSynchronizationSystem() {
    }

    public static PlayerSleepSynchronizationSystem getInstance() {
        return INSTANCE;
    }

    public void sendSleepStartAnimationIfNeeded(EntityPlayer player) {
        if (!shouldSendSleepStart(player.p)) {
            return;
        }

        player.q = -1;
        player.p = true;
        tracker(player).a(player, new Packet18ArmAnimation(player, 1));
    }

    public void syncBedEnterResult(EntityPlayer player, EnumBedError bedError, int x, int y, int z) {
        if (bedError != EnumBedError.OK) {
            return;
        }

        Packet17 bedPacket = new Packet17(player, 0, x, y, z);
        tracker(player).a(player, bedPacket);
        player.netServerHandler.a(player.locX, player.locY, player.locZ, player.yaw, player.pitch);
        player.netServerHandler.sendPacket(bedPacket);
    }

    public void sendWakeAnimationIfSleeping(EntityPlayer player) {
        if (!shouldBroadcastWakeAnimation(player.isSleeping())) {
            return;
        }
        tracker(player).sendPacketToEntity(player, new Packet18ArmAnimation(player, 3));
    }

    public void syncPositionIfConnected(EntityPlayer player) {
        if (!shouldSyncPosition(player.netServerHandler != null)) {
            return;
        }
        player.netServerHandler.a(player.locX, player.locY, player.locZ, player.yaw, player.pitch);
    }

    public boolean shouldSendSleepStart(boolean sleepStartAlreadySent) {
        return !sleepStartAlreadySent;
    }

    public boolean shouldBroadcastWakeAnimation(boolean sleeping) {
        return sleeping;
    }

    public boolean shouldSyncPosition(boolean hasNetServerHandler) {
        return hasNetServerHandler;
    }

    private EntityTracker tracker(EntityPlayer player) {
        return player.b.getTracker(player.dimension);
    }
}
