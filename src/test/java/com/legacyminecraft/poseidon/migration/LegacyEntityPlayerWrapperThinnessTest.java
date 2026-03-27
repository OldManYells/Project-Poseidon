package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityPlayerWrapperThinnessTest {
    private static final Path ENTITY_PLAYER_PATH =
            Paths.get("src/main/java/net/minecraft/server/EntityPlayer.java");

    @Test
    public void entityPlayerDelegatesSessionAndLifecycleFlowsToCanonicalSystems() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_PLAYER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PlayerChunkSyncCoordinator"));
        Assert.assertTrue(text.contains("PlayerCollectionPacketSystem"));
        Assert.assertTrue(text.contains("PlayerDamagePolicySystem"));
        Assert.assertTrue(text.contains("PlayerDeathHandlingSystem"));
        Assert.assertTrue(text.contains("PlayerEquipmentSyncSystem"));
        Assert.assertTrue(text.contains("PlayerAttachmentSyncSystem"));
        Assert.assertTrue(text.contains("PlayerContainerSyncBehaviour"));
        Assert.assertTrue(text.contains("PlayerSessionTickSystem"));
        Assert.assertTrue(text.contains("PlayerSleepSynchronizationSystem"));
        Assert.assertTrue(text.contains("PlayerStatisticPacketSystem"));
        Assert.assertTrue(text.contains("PlayerWindowLifecycleBehaviour"));

        Assert.assertTrue(text.contains("playerDeathHandlingService.handleDeath(this);"));
        Assert.assertTrue(text.contains("playerDamagePolicyService.shouldApplyDamage("));
        Assert.assertTrue(text.contains("playerEquipmentSyncService.syncTrackedEquipment(this, this.bN);"));
        Assert.assertTrue(text.contains("playerCollectionPacketService.sendCollectPacketIfNeeded(this, entity);"));
        Assert.assertTrue(text.contains("playerCollectionPacketService.refreshActiveContainer(this);"));
        Assert.assertTrue(text.contains("playerSleepSynchronizationService.sendSleepStartAnimationIfNeeded(this);"));
        Assert.assertTrue(text.contains("playerSleepSynchronizationService.syncBedEnterResult(this, enumbederror, i, j, k);"));
        Assert.assertTrue(text.contains("playerSleepSynchronizationService.sendWakeAnimationIfSleeping(this);"));
        Assert.assertTrue(text.contains("playerSleepSynchronizationService.syncPositionIfConnected(this);"));
        Assert.assertTrue(text.contains("playerAttachmentSyncService.syncPassengerAttachment(this);"));
        Assert.assertTrue(text.contains("playerSessionTickService.evaluatePortalTick("));
        Assert.assertTrue(text.contains("playerSessionTickService.evaluateHealthSync(this.health, this.bL)"));
        Assert.assertTrue(text.contains("playerWindowLifecycleService.nextWindowId(this.bO)"));
        Assert.assertTrue(text.contains("playerContainerSyncService.sendSlotUpdate(this, container, i, itemstack);"));
        Assert.assertTrue(text.contains("playerContainerSyncService.sendWindowItems(this, container, list);"));
        Assert.assertTrue(text.contains("playerContainerSyncService.sendProgressUpdate(this, container, i, j);"));
        Assert.assertTrue(text.contains("playerStatisticPacketService.sendStatisticPackets(this, statistic, i);"));

        Assert.assertFalse(text.contains("this.netServerHandler.sendPacket(new Packet22Collect(entity.id, this.id));"));
        Assert.assertFalse(text.contains("if (this.bM > 0) {"));
        Assert.assertFalse(text.contains("this.bM = 60;"));
        Assert.assertFalse(text.contains("if (this.E) {"));
    }
}
