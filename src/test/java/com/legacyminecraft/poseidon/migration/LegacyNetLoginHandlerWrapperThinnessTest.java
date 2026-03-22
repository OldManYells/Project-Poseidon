package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNetLoginHandlerWrapperThinnessTest {
    private static final Path NET_LOGIN_HANDLER_PATH = Paths.get("src/main/java/net/minecraft/server/NetLoginHandler.java");

    @Test
    public void netLoginHandlerDelegatesTickExecutionAndUsesRoleAlignedNames() throws IOException {
        String text = new String(Files.readAllBytes(NET_LOGIN_HANDLER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("loginTickExecutionSystem"));
        Assert.assertTrue(text.contains("loginTickOrchestrationSystem"));
        Assert.assertTrue(text.contains("loginTickActions"));
        Assert.assertTrue(text.contains("loginTickOrchestrationSystem.execute("));
        Assert.assertTrue(text.contains("LoginConnectionLifecycleSystem"));
        Assert.assertTrue(text.contains("loginConnectionLifecycleSystem"));
        Assert.assertTrue(text.contains("LoginIdentityPolicy"));
        Assert.assertTrue(text.contains("loginIdentityPolicy"));
        Assert.assertTrue(text.contains("LoginTickPolicy"));
        Assert.assertTrue(text.contains("loginTickPolicy"));
        Assert.assertTrue(text.contains("LoginPacketGatekeepingPolicy"));
        Assert.assertTrue(text.contains("loginPacketGatekeepingPolicy"));
        Assert.assertTrue(text.contains("LoginTransitionSystem"));
        Assert.assertFalse(text.contains("LoginTransitionService"));
        Assert.assertTrue(text.contains("loginTransitionSystem"));
        Assert.assertTrue(text.contains("loginDisconnectExecutionSystem"));
        Assert.assertTrue(text.contains("loginDisconnectActions"));
        Assert.assertTrue(text.contains("loginDisconnectExecutionSystem.execute("));
        Assert.assertTrue(text.contains("loginConnectionLossExecutionSystem"));
        Assert.assertTrue(text.contains("loginConnectionLossActions"));
        Assert.assertTrue(text.contains("loginConnectionLossExecutionSystem.execute("));
        Assert.assertTrue(text.contains("loginCompletionStateApplySystem"));
        Assert.assertTrue(text.contains("loginCompletionStateSink"));
        Assert.assertTrue(text.contains("loginCompletionStateApplySystem.applyCompletionState("));
        Assert.assertTrue(text.contains("loginFlowStartExecutionSystem"));
        Assert.assertTrue(text.contains("loginFlowStartActions"));
        Assert.assertTrue(text.contains("loginFlowStartExecutionSystem.execute("));
        Assert.assertTrue(text.contains("loginAuthenticatedSessionExecutionSystem"));
        Assert.assertTrue(text.contains("loginAuthenticatedSessionActions"));
        Assert.assertTrue(text.contains("loginAuthenticatedSessionExecutionSystem.execute("));
        Assert.assertTrue(text.contains("LoginPendingPacketExecutionSystem"));
        Assert.assertTrue(text.contains("loginPendingPacketExecutionSystem"));
        Assert.assertTrue(text.contains("loginFlowPendingPacketState"));
        Assert.assertTrue(text.contains("authenticatedSessionPendingPacketState"));
        Assert.assertTrue(text.contains("executeLoginFlowStart"));
        Assert.assertTrue(text.contains("executeAuthenticatedSessionCompletion"));
        Assert.assertTrue(text.contains("loginPendingPacketExecutionSystem.execute("));
        Assert.assertTrue(text.contains("public void setDeferredLoginPacket(Packet1Login loginPacket)"));
        Assert.assertTrue(text.contains("netloginhandler.setDeferredLoginPacket(packet1login);"));
        Assert.assertTrue(text.contains("loginProtocolErrorExecutionSystem"));
        Assert.assertTrue(text.contains("loginProtocolErrorActions"));
        Assert.assertTrue(text.contains("loginProtocolErrorExecutionSystem.execute("));
        Assert.assertTrue(text.contains("loginGatekeepingExecutionSystem"));
        Assert.assertTrue(text.contains("loginPacketExecutionSystem"));
        Assert.assertTrue(text.contains("loginPacketExecutionSystem.execute("));
        Assert.assertTrue(text.contains("loginGatekeepingActions"));
        Assert.assertTrue(text.contains("loginProxyAssignmentResolver"));
        Assert.assertTrue(text.contains("loginStartActions"));
        Assert.assertTrue(text.contains("loginHandshakeExecutionSystem"));
        Assert.assertTrue(text.contains("loginHandshakeActions"));
        Assert.assertTrue(text.contains("loginHandshakeExecutionSystem.executeHandshake("));
        Assert.assertTrue(text.contains("loginProxySessionApplySystem"));
        Assert.assertTrue(text.contains("loginProxySessionStateSink"));
        Assert.assertTrue(text.contains("loginHandshakePacketHandler"));
        Assert.assertTrue(text.contains("loginSessionStateSystem"));
        Assert.assertTrue(text.contains("loginProxyAssignmentSystem"));
        Assert.assertTrue(text.contains("private MinecraftServer minecraftServer;"));
        Assert.assertFalse(text.contains("loginHandshakePacketService"));
        Assert.assertFalse(text.contains("loginSessionStateService"));
        Assert.assertFalse(text.contains("loginProxyAssignmentService"));
        Assert.assertFalse(text.contains("private final LoginConnectionLifecycleService loginConnectionLifecycleService"));
        Assert.assertFalse(text.contains("private final LoginIdentityService loginIdentityService"));
        Assert.assertFalse(text.contains("private final LoginTickService loginTickService"));
        Assert.assertFalse(text.contains("private final LoginPacketGatekeepingService loginPacketGatekeepingService"));
        Assert.assertFalse(text.contains("private final LoginTransitionService loginTransitionService"));
        Assert.assertFalse(text.contains("private MinecraftServer server;"));
        Assert.assertFalse(text.contains("loginConnectionLifecycleService.disconnect(this.networkManager, a, this.b(), s);"));
        Assert.assertFalse(text.contains("if (tickDecision.shouldDisconnectForTimeout())"));
        Assert.assertFalse(text.contains("LoginTickService.TickDecision tickDecision ="));
        Assert.assertFalse(text.contains("if (!gatekeepingResult.isAccepted())"));
        Assert.assertFalse(text.contains("LoginPacketGatekeepingService.GatekeepingResult gatekeepingResult ="));
        Assert.assertFalse(text.contains("loginPacketGatekeepingService.evaluate(packet1login, receivedLoginPacket)"));
        Assert.assertFalse(text.contains("loginProxyAssignmentSystem.resolveProxy(this, packet1login)"));
        Assert.assertFalse(text.contains("this.finishLogin(packet1login);"));
        Assert.assertFalse(text.contains("LoginHandshakePacketHandler.HandshakeDecision handshakeDecision ="));
        Assert.assertFalse(text.contains("this.networkManager.queue(handshakeDecision.getResponsePacket());"));
        Assert.assertFalse(text.contains("this.g = gatekeepingResult.getUsername();"));
        Assert.assertFalse(text.contains("this.connectionType = proxyState.getConnectionType();"));
        Assert.assertFalse(text.contains("this.rawConnectionType = proxyState.getRawConnectionType();"));
        Assert.assertFalse(text.contains("this.usingReleaseToBeta = proxyState.isUsingReleaseToBeta();"));
        Assert.assertFalse(text.contains("this.c = completionResult.shouldMarkLoginComplete();"));
        Assert.assertFalse(text.contains("loginConnectionLifecycleService.reportConnectionLost(a, this.b());"));
        Assert.assertFalse(text.contains("this.disconnect(loginConnectionLifecycleService.getProtocolErrorMessage());"));
        Assert.assertFalse(text.contains("loginFlowStartExecutionSystem.execute(new LoginFlowStartExecutionSystem.LoginFlowActions()"));
        Assert.assertFalse(text.contains("loginAuthenticatedSessionExecutionSystem.execute(new LoginAuthenticatedSessionExecutionSystem.CompletionActions()"));
        Assert.assertFalse(text.contains("this.pendingLoginFlowPacket = packet1login;"));
        Assert.assertFalse(text.contains("this.pendingAuthenticatedSessionPacket = packet1login;"));
    }
}
