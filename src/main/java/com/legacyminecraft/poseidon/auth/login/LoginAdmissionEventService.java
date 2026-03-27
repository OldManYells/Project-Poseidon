package com.legacyminecraft.poseidon.auth.login;


/**
 * Canonical bridge between admission-policy decisions and Bukkit PlayerLoginEvent outcomes.
 */
public final class LoginAdmissionEventService {
    private static final LoginAdmissionEventService INSTANCE = new LoginAdmissionEventService();

    private LoginAdmissionEventService() {
    }

    public static LoginAdmissionEventService getInstance() {
        return INSTANCE;
    }

    public AdmissionEventResult evaluate(Server server, PlayerLoginEvent event, LoginAdmissionPolicy.AdmissionResult admissionResult) {
        event.disallow(toLegacyLoginResult(admissionResult.getDecision()), admissionResult.getKickMessage());
        server.getPluginManager().callEvent(event);
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return AdmissionEventResult.denied(event.getKickMessage());
        }
        return AdmissionEventResult.allowed();
    }

    public PlayerLoginEvent.Result toLegacyLoginResult(LoginAdmissionPolicy.Decision decision) {
        if (decision == LoginAdmissionPolicy.Decision.KICK_BANNED) {
            return PlayerLoginEvent.Result.KICK_BANNED;
        }
        if (decision == LoginAdmissionPolicy.Decision.KICK_BANNED_IP) {
            return PlayerLoginEvent.Result.KICK_BANNED_IP;
        }
        if (decision == LoginAdmissionPolicy.Decision.KICK_WHITELIST) {
            return PlayerLoginEvent.Result.KICK_WHITELIST;
        }
        if (decision == LoginAdmissionPolicy.Decision.KICK_FULL) {
            return PlayerLoginEvent.Result.KICK_FULL;
        }
        return PlayerLoginEvent.Result.ALLOWED;
    }

    public static final class AdmissionEventResult {
        private final boolean allowed;
        private final String kickMessage;

        private AdmissionEventResult(boolean allowed, String kickMessage) {
            this.allowed = allowed;
            this.kickMessage = kickMessage;
        }

        public static AdmissionEventResult allowed() {
            return new AdmissionEventResult(true, null);
        }

        public static AdmissionEventResult denied(String kickMessage) {
            return new AdmissionEventResult(false, kickMessage);
        }

        public boolean isAllowed() {
            return allowed;
        }

        public String getKickMessage() {
            return kickMessage;
        }
    }
}
