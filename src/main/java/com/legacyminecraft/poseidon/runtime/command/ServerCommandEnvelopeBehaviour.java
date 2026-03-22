package com.legacyminecraft.poseidon.runtime.command;

import net.minecraft.server.ICommandListener;

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

    public ServerCommandState createState(String commandText, ICommandListener commandListener) {
        return new ServerCommandState(commandText, commandListener);
    }

    public static final class ServerCommandState {
        private final String commandText;
        private final ICommandListener commandListener;

        public ServerCommandState(String commandText, ICommandListener commandListener) {
            this.commandText = commandText;
            this.commandListener = commandListener;
        }

        public String getCommandText() {
            return commandText;
        }

        public ICommandListener getCommandListener() {
            return commandListener;
        }
    }
}
