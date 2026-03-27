package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local console sender facade.
 */
public class ConsoleCommandSender extends Player implements ICommandListener {
    @Override
    public String getName() {
        return "CONSOLE";
    }
}
