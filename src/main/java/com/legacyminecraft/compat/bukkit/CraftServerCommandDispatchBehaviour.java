package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer dispatch-command wrapper glue.
 */
public final class CraftServerCommandDispatchBehaviour {
    private static final CraftServerCommandDispatchBehaviour INSTANCE = new CraftServerCommandDispatchBehaviour();

    private CraftServerCommandDispatchBehaviour() {
    }

    public static CraftServerCommandDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean dispatchCommand(SimpleCommandMap commandMap, CommandSender sender, ServerCommand serverCommand) {
        return dispatchCommand(commandMap, sender, serverCommand.command);
    }

    public boolean dispatchCommand(SimpleCommandMap commandMap, CommandSender sender, String commandLine) {
        if (commandMap.dispatch(sender, commandLine)) {
            return true;
        }

        sender.sendMessage("Unknown command. Type \"help\" for help.");

        return false;
    }
}
