package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for starting the login transition flow.
 */
public final class LoginFlowStartExecutionSystem {
    private static final LoginFlowStartExecutionSystem INSTANCE = new LoginFlowStartExecutionSystem();

    private LoginFlowStartExecutionSystem() {
    }

    public static LoginFlowStartExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(LoginFlowActions loginFlowActions) {
        loginFlowActions.startLoginFlow();
    }

    public interface LoginFlowActions {
        void startLoginFlow();
    }
}
