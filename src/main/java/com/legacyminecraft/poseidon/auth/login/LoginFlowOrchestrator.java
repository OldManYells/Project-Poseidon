package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.compat.projectposeidon.LegacyLoginProcessBootstrap;

/**
 * Canonical login flow orchestration before transitioning to play state.
 */
public final class LoginFlowOrchestrator {
    private static final LoginFlowOrchestrator INSTANCE = new LoginFlowOrchestrator();

    private LoginFlowOrchestrator() {
    }

    public static LoginFlowOrchestrator getInstance() {
        return INSTANCE;
    }

    public void startLoginFlow(Object loginHandler, Object loginPacket, Object server, boolean onlineMode, boolean shuttingDown, String shutdownKickMessage) {
        if (shuttingDown) {
            Bridge.invoke(loginHandler, "disconnect", shutdownKickMessage);
            return;
        }

        Bridge.startLegacyLogin(loginHandler, loginPacket, server, onlineMode);
    }

    private static final class Bridge {
        private static Object invoke(Object target, String methodName, Object... args) {
            try {
                for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(target, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static void startLegacyLogin(Object loginHandler, Object loginPacket, Object server, boolean onlineMode) {
            try {
                LegacyLoginProcessBootstrap.start(
                        (NetLoginHandler) loginHandler,
                        (Packet1Login) loginPacket,
                        (Server) server,
                        onlineMode
                );
            } catch (ClassCastException classCastException) {
                throw new IllegalStateException("Unexpected login flow bridge types", classCastException);
            }
        }
    }
}
