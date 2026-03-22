package net.minecraft.server;

import com.legacyminecraft.poseidon.commands.CommandListenerContract;

public interface ICommandListener extends CommandListenerContract {

    void sendMessage(String s);

    String getName();
}
