package org.bukkit.craftbukkit.command;

import com.legacyminecraft.compat.bukkit.CommandSenderBackedListener;
import com.legacyminecraft.compat.bukkit.ServerCommandListenerBehaviour;
import net.minecraft.server.ICommandListener;
import org.bukkit.command.CommandSender;

public class ServerCommandListener implements ICommandListener, CommandSenderBackedListener {
    private final CommandSender commandSender;
    private final String prefix;
    private final ServerCommandListenerBehaviour serverCommandListenerBehaviour =
            ServerCommandListenerBehaviour.getInstance();

    public ServerCommandListener(CommandSender commandSender) {
        this.commandSender = commandSender;
        this.prefix = serverCommandListenerBehaviour.resolvePrefix(commandSender);
    }

    public void sendMessage(String msg) {
        this.commandSender.sendMessage(msg);
    }

    public CommandSender getSender() {
        return commandSender;
    }

    public String getName() {
        return serverCommandListenerBehaviour.resolveNameOrPrefix(this.commandSender, this.prefix);
    }
}
