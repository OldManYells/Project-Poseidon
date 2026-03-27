package com.legacyminecraft.poseidon.auth.login;


import java.net.InetSocketAddress;

/**
 * Canonical verifier thread flow for legacy login verifier wrappers.
 */
public final class LoginVerificationThreadService {
    private static final LoginVerificationThreadService INSTANCE = new LoginVerificationThreadService();

    private LoginVerificationThreadService() {
    }

    public static LoginVerificationThreadService getInstance() {
        return INSTANCE;
    }

    public void verify(Packet1Login loginPacket, NetLoginHandler netLoginHandler, LoginProcessCallbacks loginProcessHandler) {
        try {
            MojangSessionVerifier.getInstance().verifyUserSession(
                    loginPacket.name,
                    netLoginHandler.getServerID(),
                    resolveIpAddress(netLoginHandler),
                    loginProcessHandler
            );
        } catch (Exception exception) {
            loginProcessHandler.cancelLoginProcess("Failed to verify username! [internal error " + exception + "]");
            exception.printStackTrace();
        }
    }

    private String resolveIpAddress(NetLoginHandler netLoginHandler) {
        return ((InetSocketAddress) netLoginHandler.networkManager.getSocketAddress()).getAddress().getHostAddress();
    }
}
