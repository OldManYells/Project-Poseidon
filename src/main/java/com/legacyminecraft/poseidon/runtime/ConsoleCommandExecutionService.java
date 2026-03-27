package com.legacyminecraft.poseidon.runtime;


import java.util.logging.Logger;

/**
 * Canonical execution pipeline for legacy console command dispatch.
 */
public final class ConsoleCommandExecutionService {
    private static final ConsoleCommandExecutionService INSTANCE = new ConsoleCommandExecutionService();

    private ConsoleCommandExecutionService() {
    }

    public static ConsoleCommandExecutionService getInstance() {
        return INSTANCE;
    }

    public boolean handleCommand(String command, ICommandListener commandListener, MinecraftServer server, CommandSupport support, Logger logger) {
        String s = command;
        ICommandListener icommandlistener = commandListener;
        String s1 = icommandlistener.getName();
        ServerConfigurationManager serverconfigurationmanager = server.serverConfigurationManager;

        if (!s.toLowerCase().startsWith("help") && !s.toLowerCase().startsWith("?")) {
            if (s.toLowerCase().startsWith("list")) {
                if (!support.checkPermission(icommandlistener, "list")) return true;
                icommandlistener.sendMessage("Connected players: " + serverconfigurationmanager.c());
            } else if (s.toLowerCase().startsWith("stop")) {
                if (!support.checkPermission(icommandlistener, "stop")) return true;
                support.print(s1, "Stopping the server..");
                server.a();
            } else {
                int i;
                WorldServer worldserver;

                if (s.toLowerCase().startsWith("save-all")) {
                    if (!support.checkPermission(icommandlistener, "save.perform")) return true;
                    support.print(s1, "Forcing save..");
                    if (serverconfigurationmanager != null) {
                        serverconfigurationmanager.savePlayers();
                    }

                    for (i = 0; i < server.worlds.size(); ++i) {
                        worldserver = server.worlds.get(i);
                        boolean save = worldserver.canSave;
                        worldserver.canSave = false;
                        worldserver.save(true, (IProgressUpdate) null);
                        worldserver.canSave = save;
                    }

                    support.print(s1, "Save complete.");
                } else if (s.toLowerCase().startsWith("save-off")) {
                    if (!support.checkPermission(icommandlistener, "save.disable")) return true;
                    support.print(s1, "Disabling level saving..");

                    for (i = 0; i < server.worlds.size(); ++i) {
                        worldserver = server.worlds.get(i);
                        worldserver.canSave = true;
                    }
                } else if (s.toLowerCase().startsWith("save-on")) {
                    if (!support.checkPermission(icommandlistener, "save.enable")) return true;
                    support.print(s1, "Enabling level saving..");

                    for (i = 0; i < server.worlds.size(); ++i) {
                        worldserver = server.worlds.get(i);
                        worldserver.canSave = false;
                    }
                } else {
                    String s2;

                    if (s.toLowerCase().startsWith("op ")) {
                        if (!support.checkPermission(icommandlistener, "op.give")) return true;
                        s2 = s.substring(s.indexOf(" ")).trim();
                        serverconfigurationmanager.e(s2);
                        support.print(s1, "Opping " + s2);
                        serverconfigurationmanager.a(s2, "\u00A7eYou are now op!");
                    } else if (s.toLowerCase().startsWith("deop ")) {
                        if (!support.checkPermission(icommandlistener, "op.take")) return true;
                        s2 = s.substring(s.indexOf(" ")).trim();
                        serverconfigurationmanager.f(s2);
                        serverconfigurationmanager.a(s2, "\u00A7eYou are no longer op!");
                        support.print(s1, "De-opping " + s2);
                    } else if (s.toLowerCase().startsWith("ban-ip ")) {
                        if (!support.checkPermission(icommandlistener, "ban.ip")) return true;
                        s2 = s.substring(s.indexOf(" ")).trim();
                        serverconfigurationmanager.c(s2);
                        support.print(s1, "Banning ip " + s2);
                    } else if (s.toLowerCase().startsWith("pardon-ip ")) {
                        if (!support.checkPermission(icommandlistener, "unban.ip")) return true;
                        s2 = s.substring(s.indexOf(" ")).trim();
                        serverconfigurationmanager.d(s2);
                        support.print(s1, "Pardoning ip " + s2);
                    } else {
                        EntityPlayer entityplayer;

                        if (s.toLowerCase().startsWith("ban ")) {
                            if (!support.checkPermission(icommandlistener, "ban.player")) return true;
                            s2 = s.substring(s.indexOf(" ")).trim();
                            serverconfigurationmanager.a(s2);
                            support.print(s1, "Banning " + s2);
                            entityplayer = serverconfigurationmanager.i(s2);
                            if (entityplayer != null) {
                                entityplayer.netServerHandler.disconnect("Banned by admin");
                            }
                        } else if (s.toLowerCase().startsWith("pardon ")) {
                            if (!support.checkPermission(icommandlistener, "unban.player")) return true;
                            s2 = s.substring(s.indexOf(" ")).trim();
                            serverconfigurationmanager.b(s2);
                            support.print(s1, "Pardoning " + s2);
                        } else {
                            int j;

                            if (s.toLowerCase().startsWith("kick ")) {
                                if (!support.checkPermission(icommandlistener, "kick")) return true;
                                String[] parts = s.split(" ");
                                s2 = parts.length >= 2 ? parts[1] : "";
                                entityplayer = null;

                                for (j = 0; j < serverconfigurationmanager.players.size(); ++j) {
                                    EntityPlayer entityplayer1 = (EntityPlayer) serverconfigurationmanager.players.get(j);

                                    if (entityplayer1.name.equalsIgnoreCase(s2)) {
                                        entityplayer = entityplayer1;
                                    }
                                }

                                if (entityplayer != null) {
                                    entityplayer.netServerHandler.disconnect("Kicked by admin");
                                    support.print(s1, "Kicking " + entityplayer.name);
                                } else {
                                    icommandlistener.sendMessage("Can\'t find user " + s2 + ". No kick.");
                                }
                            } else {
                                EntityPlayer entityplayer2;
                                String[] astring;

                                if (s.toLowerCase().startsWith("tp ")) {
                                    if (!support.checkPermission(icommandlistener, "teleport")) return true;
                                    astring = s.split(" ");
                                    if (astring.length == 3) {
                                        entityplayer = serverconfigurationmanager.i(astring[1]);
                                        entityplayer2 = serverconfigurationmanager.i(astring[2]);
                                        if (entityplayer == null) {
                                            icommandlistener.sendMessage("Can\'t find user " + astring[1] + ". No tp.");
                                        } else if (entityplayer2 == null) {
                                            icommandlistener.sendMessage("Can\'t find user " + astring[2] + ". No tp.");
                                        } else if (entityplayer.dimension != entityplayer2.dimension) {
                                            icommandlistener.sendMessage("User " + astring[1] + " and " + astring[2] + " are in different dimensions. No tp.");
                                        } else {
                                            entityplayer.netServerHandler.a(entityplayer2.locX, entityplayer2.locY, entityplayer2.locZ, entityplayer2.yaw, entityplayer2.pitch);
                                            support.print(s1, "Teleporting " + astring[1] + " to " + astring[2] + ".");
                                        }
                                    } else {
                                        icommandlistener.sendMessage("Syntax error, please provice a source and a target.");
                                    }
                                } else {
                                    String s3;
                                    int k;

                                    if (s.toLowerCase().startsWith("give ")) {
                                        if (!support.checkPermission(icommandlistener, "give")) return true;
                                        astring = s.split(" ");
                                        if (astring.length != 3 && astring.length != 4) {
                                            return true;
                                        }

                                        s3 = astring[1];
                                        entityplayer2 = serverconfigurationmanager.i(s3);
                                        if (entityplayer2 != null) {
                                            try {
                                                k = Integer.parseInt(astring[2]);
                                                if (Item.byId[k] != null) {
                                                    support.print(s1, "Giving " + entityplayer2.name + " some " + k);
                                                    int l = 1;

                                                    if (astring.length > 3) {
                                                        l = support.parseInt(astring[3], 1);
                                                    }

                                                    if (l < 1) {
                                                        l = 1;
                                                    }

                                                    if (l > 64) {
                                                        l = 64;
                                                    }

                                                    entityplayer2.b(new ItemStack(k, l, 0));
                                                } else {
                                                    icommandlistener.sendMessage("There\'s no item with id " + k);
                                                }
                                            } catch (NumberFormatException numberformatexception) {
                                                icommandlistener.sendMessage("There\'s no item with id " + astring[2]);
                                            }
                                        } else {
                                            icommandlistener.sendMessage("Can\'t find user " + s3);
                                        }
                                    } else if (s.toLowerCase().startsWith("time ")) {
                                        astring = s.split(" ");
                                        if (astring.length != 3) {
                                            return true;
                                        }

                                        s3 = astring[1];

                                        try {
                                            j = Integer.parseInt(astring[2]);
                                            WorldServer worldserver1;

                                            if ("add".equalsIgnoreCase(s3)) {
                                                if (!support.checkPermission(icommandlistener, "time.add")) return true;
                                                for (k = 0; k < server.worlds.size(); ++k) {
                                                    worldserver1 = server.worlds.get(k);
                                                    worldserver1.setTimeAndFixTicklists(worldserver1.getTime() + (long) j);
                                                }

                                                support.print(s1, "Added " + j + " to time");
                                            } else if ("set".equalsIgnoreCase(s3)) {
                                                if (!support.checkPermission(icommandlistener, "time.set")) return true;
                                                for (k = 0; k < server.worlds.size(); ++k) {
                                                    worldserver1 = server.worlds.get(k);
                                                    worldserver1.setTimeAndFixTicklists((long) j);
                                                }

                                                support.print(s1, "Set time to " + j);
                                            } else {
                                                icommandlistener.sendMessage("Unknown method, use either \"add\" or \"set\"");
                                            }
                                        } catch (NumberFormatException numberformatexception1) {
                                            icommandlistener.sendMessage("Unable to convert time value, " + astring[2]);
                                        }
                                    } else if (s.toLowerCase().startsWith("say ")) {
                                        if (!support.checkPermission(icommandlistener, "say")) return true;
                                        s = s.substring(s.indexOf(" ")).trim();
                                        logger.info("[" + s1 + "] " + s);
                                        serverconfigurationmanager.sendAll(new Packet3Chat("\u00A7d[Server] " + s));
                                    } else if (s.toLowerCase().startsWith("tell ")) {
                                        if (!support.checkPermission(icommandlistener, "tell")) return true;
                                        astring = s.split(" ");
                                        if (astring.length >= 3) {
                                            s = s.substring(s.indexOf(" ")).trim();
                                            s = s.substring(s.indexOf(" ")).trim();
                                            logger.info("[" + s1 + "->" + astring[1] + "] " + s);
                                            s = "\u00A77" + s1 + " whispers " + s;
                                            logger.info(s);
                                            if (!serverconfigurationmanager.a(astring[1], (Packet) (new Packet3Chat(s)))) {
                                                icommandlistener.sendMessage("There\'s no player by that name online.");
                                            }
                                        }
                                    } else if (s.toLowerCase().startsWith("whitelist ")) {
                                        support.handleWhitelistCommand(s1, s, icommandlistener);
                                    } else {
                                        icommandlistener.sendMessage("Unknown console command. Type \"help\" for help.");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (!support.checkPermission(icommandlistener, "help")) return true;
            support.sendHelp(icommandlistener);
        }

        return true;
    }

    public interface CommandSupport {
        boolean checkPermission(ICommandListener listener, String permissionSuffix);

        void print(String sourceName, String message);

        void handleWhitelistCommand(String sourceName, String fullCommand, ICommandListener listener);

        void sendHelp(ICommandListener listener);

        int parseInt(String value, int fallback);
    }
}
