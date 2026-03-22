package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalLoginAdmissionBoundaryTest {
    private static final Path PLAYER_LOGIN_ADMISSION_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/auth/login/PlayerLoginAdmissionSystem.java");
    private static final Path PLAYER_LOGIN_ADMISSION_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/auth/login/PlayerLoginAdmissionService.java");
    private static final Path LOGIN_ADMISSION_EVENT_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/auth/login/LoginAdmissionEventService.java");
    private static final Path LOGIN_FLOW_ORCHESTRATOR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/auth/login/LoginFlowOrchestrator.java");
    private static final Path LOGIN_PROCESS_HANDLER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/auth/login/LoginProcessHandler.java");
    private static final Path LOGIN_TRANSITION_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/auth/login/LoginTransitionService.java");

    @Test
    public void canonicalLoginAdmissionFlowUsesBukkitServerApiInsteadOfCraftServerType() throws IOException {
        assertUsesServerApi(read(PLAYER_LOGIN_ADMISSION_SYSTEM_PATH), "Server bukkitServer,");
        assertUsesServerApi(read(PLAYER_LOGIN_ADMISSION_SERVICE_PATH), "Server bukkitServer,");
        assertUsesServerApi(read(LOGIN_ADMISSION_EVENT_SERVICE_PATH), "evaluate(Server server, PlayerLoginEvent event, LoginAdmissionPolicy.AdmissionResult admissionResult)");
        assertUsesServerApi(read(LOGIN_FLOW_ORCHESTRATOR_PATH), "startLoginFlow(NetLoginHandler loginHandler, Packet1Login loginPacket, Server server, boolean onlineMode, boolean shuttingDown, String shutdownKickMessage)");
        String loginProcessHandlerText = read(LOGIN_PROCESS_HANDLER_PATH);
        assertUsesServerApi(loginProcessHandlerText, "private final Server server;");
        Assert.assertFalse(loginProcessHandlerText.contains("NetLoginHandler.a("));
        Assert.assertTrue(loginProcessHandlerText.contains("netLoginHandler.setDeferredLoginPacket(packet1Login);"));
        assertUsesServerApi(
                read(LOGIN_TRANSITION_SERVICE_PATH),
                "startLoginFlow(\n            NetLoginHandler loginHandler,\n            Packet1Login loginPacket,\n            MinecraftServer minecraftServer,\n            Server bukkitServer,\n            String shutdownKickMessage\n    )"
        );
    }

    private static void assertUsesServerApi(String text, String signatureFragment) {
        Assert.assertTrue(text.contains("import org.bukkit.Server;"));
        Assert.assertTrue(text.contains(signatureFragment));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.CraftServer"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
