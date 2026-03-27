package com.legacyminecraft.poseidon.auth.login;


/**
 * Canonical orchestration for login tick evaluation and tick-action execution.
 */
public final class LoginTickOrchestrationSystem {
    private static final LoginTickOrchestrationSystem INSTANCE = new LoginTickOrchestrationSystem();

    private LoginTickOrchestrationSystem() {
    }

    public static LoginTickOrchestrationSystem getInstance() {
        return INSTANCE;
    }

    public int execute(
            Object deferredLoginPacket,
            int timeoutCounter,
            int loginTimeoutTicks,
            LoginTickPolicy loginTickPolicy,
            LoginTickExecutionSystem loginTickExecutionSystem,
            LoginTickExecutionSystem.TickActions tickActions
    ) {
        LoginTickPolicy.TickDecision tickDecision = loginTickPolicy.evaluateTick(
                deferredLoginPacket,
                timeoutCounter,
                loginTimeoutTicks
        );
        return loginTickExecutionSystem.applyTickDecision(tickDecision, tickActions);
    }
}
