package org.bukkit.craftbukkit.command;

import com.legacyminecraft.poseidon.compat.bukkit.ConsoleColorRenderBehaviour;
import jline.ConsoleReader;
import jline.Terminal;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.CraftServer;

import java.util.EnumMap;
import java.util.Map;

public class ColouredConsoleSender extends ConsoleCommandSender {
    private final ConsoleReader reader;
    private final Terminal terminal;
    private final Map<ChatColor, String> replacements = new EnumMap<ChatColor, String>(ChatColor.class);
    private final ChatColor[] colors = ChatColor.values();
    private final ConsoleColorRenderBehaviour consoleColorRenderBehaviour = ConsoleColorRenderBehaviour.getInstance();

    public ColouredConsoleSender(CraftServer server) {
        super(server);
        this.reader = server.getReader();
        this.terminal = reader.getTerminal();
        consoleColorRenderBehaviour.initializeDefaultReplacements(this.replacements);
    }

    @Override
    public void sendMessage(String message) {
        if (terminal.isANSISupported()) {
            String ansiMessage = consoleColorRenderBehaviour.renderAnsiMessage(message, this.colors, this.replacements);
            System.out.println(ansiMessage + consoleColorRenderBehaviour.resetAnsiCode());
        } else {
            super.sendMessage(message);
        }
    }
}
