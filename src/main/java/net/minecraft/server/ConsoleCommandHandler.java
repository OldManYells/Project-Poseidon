package net.minecraft.server;

import com.legacyminecraft.compat.bukkit.CommandSenderBackedListener;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

// CraftBukkit start
// CraftBukkit end

public class ConsoleCommandHandler {

    private static Logger a = Logger.getLogger("Minecraft");
    private MinecraftServer server;
    private ICommandListener listener; // CraftBukkit

    public ConsoleCommandHandler(MinecraftServer minecraftserver) {
        this.server = minecraftserver;
    }

    private boolean checkPermission(ICommandListener listener, String command) {
        String permissionNode = "bukkit.command." + command;
        if (hasPermission(listener, permissionNode)) {
            return true;
        }

        listener.sendMessage("You do not have permission to use this command.");
        return false;
    }

    public boolean handle(ServerCommand servercommand) { // CraftBukkit - returns boolean
        String s = servercommand.command;
        ICommandListener icommandlistener = servercommand.b;
        this.listener = icommandlistener;
        String sourceName = icommandlistener.getName();
        ServerConfigurationManager scm = this.server.serverConfigurationManager;

        if (!s.toLowerCase().startsWith("help") && !s.toLowerCase().startsWith("?")) {
            if (s.toLowerCase().startsWith("list")) {
                if (!checkPermission(icommandlistener, "list")) return true;
                icommandlistener.sendMessage("Connected players: " + scm.c());
            } else if (s.toLowerCase().startsWith("stop")) {
                if (!checkPermission(icommandlistener, "stop")) return true;
                print(sourceName, "Stopping the server..");
                this.server.a();
            } else {
                int i;
                WorldServer worldserver;
                if (s.toLowerCase().startsWith("save-all")) {
                    if (!checkPermission(icommandlistener, "save.perform")) return true;
                    print(sourceName, "Forcing save..");
                    if (scm != null) scm.savePlayers();
                    for (i = 0; i < this.server.worlds.size(); ++i) {
                        worldserver = this.server.worlds.get(i);
                        boolean save = worldserver.canSave;
                        worldserver.canSave = false;
                        worldserver.save(true, (IProgressUpdate) null);
                        worldserver.canSave = save;
                    }
                    print(sourceName, "Save complete.");
                } else if (s.toLowerCase().startsWith("save-off")) {
                    if (!checkPermission(icommandlistener, "save.disable")) return true;
                    print(sourceName, "Disabling level saving..");
                    for (i = 0; i < this.server.worlds.size(); ++i) {
                        worldserver = this.server.worlds.get(i);
                        worldserver.canSave = true;
                    }
                } else if (s.toLowerCase().startsWith("save-on")) {
                    if (!checkPermission(icommandlistener, "save.enable")) return true;
                    print(sourceName, "Enabling level saving..");
                    for (i = 0; i < this.server.worlds.size(); ++i) {
                        worldserver = this.server.worlds.get(i);
                        worldserver.canSave = false;
                    }
                } else if (s.toLowerCase().startsWith("op ")) {
                    if (!checkPermission(icommandlistener, "op.give")) return true;
                    String player = s.substring(s.indexOf(" ")).trim();
                    scm.e(player);
                    print(sourceName, "Opping " + player);
                    scm.a(player, "\u00A7eYou are now op!");
                } else if (s.toLowerCase().startsWith("deop ")) {
                    if (!checkPermission(icommandlistener, "op.take")) return true;
                    String player = s.substring(s.indexOf(" ")).trim();
                    scm.f(player);
                    scm.a(player, "\u00A7eYou are no longer op!");
                    print(sourceName, "De-opping " + player);
                } else if (s.toLowerCase().startsWith("ban-ip ")) {
                    if (!checkPermission(icommandlistener, "ban.ip")) return true;
                    String ip = s.substring(s.indexOf(" ")).trim();
                    scm.c(ip);
                    print(sourceName, "Banning ip " + ip);
                } else if (s.toLowerCase().startsWith("pardon-ip ")) {
                    if (!checkPermission(icommandlistener, "unban.ip")) return true;
                    String ip = s.substring(s.indexOf(" ")).trim();
                    scm.d(ip);
                    print(sourceName, "Pardoning ip " + ip);
                } else if (s.toLowerCase().startsWith("ban ")) {
                    if (!checkPermission(icommandlistener, "ban.player")) return true;
                    String player = s.substring(s.indexOf(" ")).trim();
                    scm.a(player);
                    print(sourceName, "Banning " + player);
                    EntityPlayer target = scm.i(player);
                    if (target != null) target.netServerHandler.disconnect("Banned by admin");
                } else if (s.toLowerCase().startsWith("pardon ")) {
                    if (!checkPermission(icommandlistener, "unban.player")) return true;
                    String player = s.substring(s.indexOf(" ")).trim();
                    scm.b(player);
                    print(sourceName, "Pardoning " + player);
                } else if (s.toLowerCase().startsWith("kick ")) {
                    if (!checkPermission(icommandlistener, "kick")) return true;
                    String[] parts = s.split(" ");
                    String name = parts.length >= 2 ? parts[1] : "";
                    EntityPlayer target = null;
                    for (int j = 0; j < scm.players.size(); ++j) {
                        EntityPlayer p = (EntityPlayer) scm.players.get(j);
                        if (p.name.equalsIgnoreCase(name)) {
                            target = p;
                            break;
                        }
                    }
                    if (target != null) {
                        target.netServerHandler.disconnect("Kicked by admin");
                        print(sourceName, "Kicking " + target.name);
                    } else {
                        icommandlistener.sendMessage("Can't find user " + name + ". No kick.");
                    }
                } else if (s.toLowerCase().startsWith("say ")) {
                    if (!checkPermission(icommandlistener, "say")) return true;
                    String message = s.substring(s.indexOf(" ")).trim();
                    a.info("[" + sourceName + "] " + message);
                    scm.sendAll(new Packet3Chat("\u00A7d[Server] " + message));
                } else if (s.toLowerCase().startsWith("whitelist ")) {
                    a(sourceName, s, icommandlistener);
                } else {
                    icommandlistener.sendMessage("Unknown console command. Type \"help\" for help.");
                    return false;
                }
            }
        } else {
            a(icommandlistener);
        }
        return true;
    }

    private void a(String s, String s1, ICommandListener icommandlistener) {
        String[] arguments = s1.split(" ");
        if (arguments.length < 2) {
            return;
        }
        String mode = arguments[1].toLowerCase();
        if ("on".equals(mode)) {
            if (!checkPermission(icommandlistener, "whitelist.enable")) return;
            print(s, "Turned on white-listing");
            this.server.propertyManager.b("white-list", true);
        } else if ("off".equals(mode)) {
            if (!checkPermission(icommandlistener, "whitelist.disable")) return;
            print(s, "Turned off white-listing");
            this.server.propertyManager.b("white-list", false);
        } else if ("list".equals(mode)) {
            if (!checkPermission(icommandlistener, "whitelist.list")) return;
            Set whiteList = this.server.serverConfigurationManager.e();
            String listedPlayers = "";
            for (Iterator iterator = whiteList.iterator(); iterator.hasNext(); listedPlayers = listedPlayers + (String) iterator.next() + " ") {
            }
            icommandlistener.sendMessage("White-listed players: " + listedPlayers);
        } else if ("add".equals(mode) && arguments.length == 3) {
            if (!checkPermission(icommandlistener, "whitelist.add")) return;
            String playerName = arguments[2].toLowerCase();
            this.server.serverConfigurationManager.k(playerName);
            print(s, "Added " + playerName + " to white-list");
        } else if ("remove".equals(mode) && arguments.length == 3) {
            if (!checkPermission(icommandlistener, "whitelist.remove")) return;
            String playerName = arguments[2].toLowerCase();
            this.server.serverConfigurationManager.l(playerName);
            print(s, "Removed " + playerName + " from white-list");
        } else if ("reload".equals(mode)) {
            if (!checkPermission(icommandlistener, "whitelist.reload")) return;
            this.server.serverConfigurationManager.f();
            print(s, "Reloaded white-list from file");
        }
    }

    private void a(ICommandListener icommandlistener) {
        icommandlistener.sendMessage("To run the server without a gui, start it like this:");
        icommandlistener.sendMessage("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
        icommandlistener.sendMessage("Console commands:");
        icommandlistener.sendMessage("   help  or  ?               shows this message");
        icommandlistener.sendMessage("   stop                      gracefully stops the server");
        icommandlistener.sendMessage("   save-all                  forces a server-wide level save");
        icommandlistener.sendMessage("   save-off                  disables terrain saving");
        icommandlistener.sendMessage("   save-on                   re-enables terrain saving");
        icommandlistener.sendMessage("   list                      lists all currently connected players");
        icommandlistener.sendMessage("   say <message>             broadcasts a message to all players");
    }

    private void print(String s, String s1) {
        String formatted = s + ": " + s1;
        this.listener.sendMessage(s1);
        informOps("\u00A77(" + formatted + ")");
        if (!(this.listener instanceof MinecraftServer)) {
            a.info(formatted);
        }
    }

    // CraftBukkit start
    private void informOps(String msg) {
        Packet3Chat packet3chat = new Packet3Chat(msg);
        String senderName = null;
        if (this.listener instanceof CommandSenderBackedListener) {
            Object senderObject = ((CommandSenderBackedListener) this.listener).getSender();
            CommandSender sender = senderObject instanceof CommandSender ? (CommandSender) senderObject : null;
            if (sender != null) {
                senderName = sender.getName();
            }
        }

        for (int i = 0; i < this.server.serverConfigurationManager.players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) this.server.serverConfigurationManager.players.get(i);
            if ((senderName == null || !senderName.equalsIgnoreCase(entityplayer.name))
                    && this.server.serverConfigurationManager.isOp(entityplayer.name)) {
                entityplayer.netServerHandler.sendPacket(packet3chat);
            }
        }
    }
    // CraftBukkit end

    private boolean hasPermission(ICommandListener listener, String permissionNode) {
        if (listener instanceof CommandSenderBackedListener) {
            Object senderObject = ((CommandSenderBackedListener) listener).getSender();
            return senderObject instanceof CommandSender && ((CommandSender) senderObject).hasPermission(permissionNode);
        } else if (listener instanceof NetServerHandler) {
            return ((NetServerHandler) listener).getPlayer().hasPermission(permissionNode);
        } else if ((listener instanceof ServerGUI) || (listener instanceof MinecraftServer)) {
            return this.server.console.hasPermission(permissionNode);
        }
        return false;
    }

    private int a(String s, int i) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException numberformatexception) {
            return i;
        }
    }
}
