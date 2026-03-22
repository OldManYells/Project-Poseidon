package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyServerConfigurationManagerWrapperThinnessTest {
    private static final Path SERVER_CONFIGURATION_MANAGER_PATH =
            Paths.get("src/main/java/net/minecraft/server/ServerConfigurationManager.java");

    @Test
    public void serverConfigurationManagerDelegatesAdmissionPolicyChecksToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(SERVER_CONFIGURATION_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("AccessListAdmissionPolicyBehaviour"));
        Assert.assertTrue(text.contains("AccessListMutationBehaviour"));
        Assert.assertTrue(text.contains("PlayerLoginAdmissionSystem"));
        Assert.assertTrue(text.contains("LegacyServerBootstrapBridgeBehaviour"));
        Assert.assertTrue(text.contains("playerLoginAdmissionSystem"));
        Assert.assertTrue(text.contains("ServerPlayerViewDistanceBehaviour"));
        Assert.assertTrue(text.contains("OperatorPermissionRefreshBridgeBehaviour"));
        Assert.assertTrue(text.contains("PlayerFileDataBindingBehaviour"));
        Assert.assertTrue(text.contains("playerFileDataBindingBehaviour.bindIfAbsent("));
        Assert.assertTrue(text.contains("serverPlayerViewDistanceBehaviour.resolveFurthestViewableBlock("));
        Assert.assertTrue(text.contains("accessListAdmissionPolicyBehaviour.isWhitelisted("));
        Assert.assertTrue(text.contains("accessListAdmissionPolicyBehaviour.isOperator("));
        Assert.assertTrue(text.contains("accessListMutationBehaviour.addAndPersist("));
        Assert.assertTrue(text.contains("accessListMutationBehaviour.removeAndPersist("));
        Assert.assertTrue(text.contains("playerBanMutationHooks"));
        Assert.assertTrue(text.contains("ipBanMutationHooks"));
        Assert.assertTrue(text.contains("operatorMutationHooks"));
        Assert.assertTrue(text.contains("whitelistMutationHooks"));
        Assert.assertTrue(text.contains("pendingOperatorMutationName"));
        Assert.assertTrue(text.contains("private Server bukkitServer;"));
        Assert.assertTrue(text.contains("legacyServerBootstrapBridgeBehaviour.bootstrap(minecraftserver, this);"));
        Assert.assertTrue(text.contains("operatorPermissionRefreshBridgeBehaviour.refreshPermissionsIfOnline("));
        Assert.assertTrue(text.contains("playerSessionSystem."));
        Assert.assertTrue(text.contains("playerWorldMoveSystem.moveToWorld("));
        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit.CraftServer;"));
        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit.command.ColouredConsoleSender;"));
        Assert.assertFalse(text.contains("return !this.o || this.h.contains(s) || this.i.contains(s);"));
        Assert.assertFalse(text.contains("return this.h.contains(accessListPersistence.normalize(s));"));
        Assert.assertFalse(text.contains("accessListPersistence.addNormalized("));
        Assert.assertFalse(text.contains("accessListPersistence.removeNormalized("));
        Assert.assertFalse(text.contains("if (this.server.worlds.size() == 0)"));
        Assert.assertFalse(text.contains("if (this.playerFileData != null) return;"));
        Assert.assertFalse(text.contains("player.recalculatePermissions();"));
        Assert.assertFalse(text.contains("refreshPermissionsIfOnline(ServerConfigurationManager.this.cserver, s)"));
        Assert.assertFalse(text.contains("playerLoginAdmissionService"));
        Assert.assertFalse(text.contains("playerSessionService"));
        Assert.assertFalse(text.contains("playerWorldMoveService"));
    }
}
