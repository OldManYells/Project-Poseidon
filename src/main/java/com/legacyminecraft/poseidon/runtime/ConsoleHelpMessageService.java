package com.legacyminecraft.poseidon.runtime;


/**
 * Canonical formatter for legacy console help text.
 */
public final class ConsoleHelpMessageService {
    private static final ConsoleHelpMessageService INSTANCE = new ConsoleHelpMessageService();

    private ConsoleHelpMessageService() {
    }

    public static ConsoleHelpMessageService getInstance() {
        return INSTANCE;
    }

    public void sendHelp(ICommandListener listener) {
        listener.sendMessage("To run the server without a gui, start it like this:");
        listener.sendMessage("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
        listener.sendMessage("Console commands:");
        listener.sendMessage("   help  or  ?               shows this message");
        listener.sendMessage("   kick <player>             removes a player from the server");
        listener.sendMessage("   ban <player>              bans a player from the server");
        listener.sendMessage("   pardon <player>           pardons a banned player so that they can connect again");
        listener.sendMessage("   ban-ip <ip>               bans an IP address from the server");
        listener.sendMessage("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
        listener.sendMessage("   op <player>               turns a player into an op");
        listener.sendMessage("   deop <player>             removes op status from a player");
        listener.sendMessage("   tp <player1> <player2>    moves one player to the same location as another player");
        listener.sendMessage("   give <player> <id> [num]  gives a player a resource");
        listener.sendMessage("   tell <player> <message>   sends a private message to a player");
        listener.sendMessage("   stop                      gracefully stops the server");
        listener.sendMessage("   save-all                  forces a server-wide level save");
        listener.sendMessage("   save-off                  disables terrain saving (useful for backup scripts)");
        listener.sendMessage("   save-on                   re-enables terrain saving");
        listener.sendMessage("   list                      lists all currently connected players");
        listener.sendMessage("   say <message>             broadcasts a message to all players");
        listener.sendMessage("   time <add|set> <amount>   adds to or sets the world time (0-24000)");
    }
}
