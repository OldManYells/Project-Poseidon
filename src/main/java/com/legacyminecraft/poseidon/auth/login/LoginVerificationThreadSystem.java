package com.legacyminecraft.poseidon.auth.login;


/**
 * Role-aligned canonical facade for login verifier thread execution.
 */
public final class LoginVerificationThreadSystem {
    private static final LoginVerificationThreadSystem INSTANCE = new LoginVerificationThreadSystem();
    private final LoginVerificationThreadService delegate = LoginVerificationThreadService.getInstance();

    private LoginVerificationThreadSystem() {
    }

    public static LoginVerificationThreadSystem getInstance() {
        return INSTANCE;
    }

    public void verify(Packet1Login loginPacket, NetLoginHandler netLoginHandler, LoginProcessCallbacks loginProcessHandler) {
        delegate.verify(loginPacket, netLoginHandler, loginProcessHandler);
    }
}
