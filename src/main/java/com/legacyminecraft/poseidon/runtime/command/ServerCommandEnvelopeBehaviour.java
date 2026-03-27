package com.legacyminecraft.poseidon.runtime.command;

/**
 * Canonical behaviour for legacy server command envelope state.
 */
public final class ServerCommandEnvelopeBehaviour {
    private static final ServerCommandEnvelopeBehaviour INSTANCE = new ServerCommandEnvelopeBehaviour();

    private ServerCommandEnvelopeBehaviour() {
    }

    public static ServerCommandEnvelopeBehaviour getInstance() {
        return INSTANCE;
    }

    public ServerCommandState createState(String commandText, Object commandListener) {
        return new ServerCommandState(commandText == null ? "" : commandText, commandListener);
    }

    public static final class ServerCommandState {
        private final String commandText;
        private final Object commandListener;

        public ServerCommandState(String commandText, Object commandListener) {
            this.commandText = commandText;
            this.commandListener = commandListener;
        }

        public String getCommandText() {
            return commandText;
        }

        public <T> T getCommandListener() {
            @SuppressWarnings("unchecked")
            T listener = (T) commandListener;
            return listener;
        }
    }
}
