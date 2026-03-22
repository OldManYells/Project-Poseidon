package com.legacyminecraft.poseidon.world.player;

import com.legacyminecraft.poseidon.Poseidon;
import com.legacyminecraft.poseidon.PoseidonConfig;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet3Chat;
import net.minecraft.server.PlayerFileData;
import net.minecraft.server.WorldServer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * Canonical player lifecycle orchestration used by legacy server wrappers.
 */
public final class PlayerLifecycleCoordinator {
    private static final PlayerLifecycleCoordinator INSTANCE = new PlayerLifecycleCoordinator();

    private final PlayerSessionSystem playerSessionSystem = PlayerSessionSystem.getInstance();

    private PlayerLifecycleCoordinator() {
    }

    public static PlayerLifecycleCoordinator getInstance() {
        return INSTANCE;
    }

    public void registerPlayerInWorldManagers(MinecraftServer server, EntityPlayer entityplayer) {
        for (WorldServer world : server.worlds) {
            if (world.manager.managedPlayers.contains(entityplayer)) {
                world.manager.removePlayer(entityplayer);
                break;
            }
        }

        WorldServer worldserver = server.getWorldServer(entityplayer.dimension);
        worldserver.manager.addPlayer(entityplayer);
        worldserver.chunkProviderServer.getChunkAt((int) entityplayer.locX >> 4, (int) entityplayer.locZ >> 4);
    }

    public void onPlayerJoin(MinecraftServer server, Server bukkitServer, List players, EntityPlayer entityplayer, String joinMessageTemplate) {
        players.add(entityplayer);
        WorldServer worldserver = server.getWorldServer(entityplayer.dimension);

        worldserver.chunkProviderServer.getChunkAt((int) entityplayer.locX >> 4, (int) entityplayer.locZ >> 4);

        if ((boolean) PoseidonConfig.getInstance().getConfigOption("world-settings.teleport-to-highest-safe-block")) {
            while (worldserver.getEntities(entityplayer, entityplayer.boundingBox).size() != 0) {
                entityplayer.setPosition(entityplayer.locX, entityplayer.locY + 1.0D, entityplayer.locZ);
            }
        }

        Player player = (Player) entityplayer.getBukkitEntity();
        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player, joinMessageTemplate.replace("%player%", entityplayer.name));
        bukkitServer.getPluginManager().callEvent(playerJoinEvent);

        String joinMessage = playerJoinEvent.getJoinMessage();
        if (joinMessage != null) {
            playerSessionSystem.sendPacketToAll(players, new Packet3Chat(joinMessage));
        }

        if (shouldNotifyUpdate(player)) {
            String updateMessage = buildUpdateAvailableMessage(
                    PoseidonConfig.getInstance().getConfigString("message.update.available"),
                    Poseidon.getServer().getNewestVersion(),
                    Poseidon.getServer().getReleaseVersion());
            player.sendMessage(updateMessage);
        }

        worldserver.addEntity(entityplayer);
        worldserver.manager.addPlayer(entityplayer);
    }

    public void removePlayerFromCurrentManager(MinecraftServer server, EntityPlayer entityplayer) {
        server.getWorldServer(entityplayer.dimension).manager.removePlayer(entityplayer);
    }

    public void addPlayerToCurrentManager(MinecraftServer server, EntityPlayer entityplayer) {
        server.getWorldServer(entityplayer.dimension).manager.addPlayer(entityplayer);
    }

    public void onPlayerMoved(MinecraftServer server, EntityPlayer entityplayer) {
        server.getWorldServer(entityplayer.dimension).manager.movePlayer(entityplayer);
    }

    public void flushPlayerManagers(MinecraftServer server) {
        for (int i = 0; i < server.worlds.size(); ++i) {
            server.worlds.get(i).manager.flush();
        }
    }

    public void flagDirtyBlock(MinecraftServer server, int x, int y, int z, int dimension) {
        server.getWorldServer(dimension).manager.flagDirty(x, y, z);
    }

    public String onPlayerDisconnect(MinecraftServer server, Server bukkitServer, PlayerFileData playerFileData, List players, EntityPlayer entityplayer, String leaveMessageTemplate) {
        server.getWorldServer(entityplayer.dimension).manager.removePlayer(entityplayer);
        PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent((Player) entityplayer.getBukkitEntity(), leaveMessageTemplate.replace("%player%", entityplayer.name));
        bukkitServer.getPluginManager().callEvent(playerQuitEvent);

        playerFileData.a(entityplayer);
        server.getWorldServer(entityplayer.dimension).kill(entityplayer);
        players.remove(entityplayer);
        server.getWorldServer(entityplayer.dimension).manager.removePlayer(entityplayer);

        return playerQuitEvent.getQuitMessage();
    }

    public boolean shouldNotifyUpdate(Player player) {
        if (player == null) {
            return false;
        }
        if (!PoseidonConfig.getInstance().getConfigBoolean("settings.update-checker.notify-staff.enabled", true)) {
            return false;
        }
        if (Poseidon.getServer() == null || !Poseidon.getServer().isUpdateAvailable()) {
            return false;
        }
        return player.isOp() || player.hasPermission("poseidon.update");
    }

    public String buildUpdateAvailableMessage(String template, String newestVersion, String currentVersion) {
        return template.replace("%newversion%", newestVersion).replace("%currentversion%", currentVersion);
    }
}
