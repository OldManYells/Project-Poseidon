package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local server command event scaffold.
 */
public class ServerCommandEvent {
    private final ConsoleCommandSender sender;
    private String command;

    public ServerCommandEvent(ConsoleCommandSender sender, String command) {
        this.sender = sender;
        this.command = command;
    }

    public ConsoleCommandSender getSender() {
        return sender;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
