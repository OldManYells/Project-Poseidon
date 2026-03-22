package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.network.ConnectionAddressParser;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetLoginHandler;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;
import java.util.Set;

/**
 * Canonical admission flow for constructing and admitting connecting players.
 */
public final class PlayerLoginAdmissionService {
    private static final PlayerLoginAdmissionService INSTANCE = new PlayerLoginAdmissionService();

    private final LoginAdmissionEventService loginAdmissionEventService = LoginAdmissionEventService.getInstance();

    private PlayerLoginAdmissionService() {
    }

    public static PlayerLoginAdmissionService getInstance() {
        return INSTANCE;
    }

    public EntityPlayer admitAndCreatePlayer(
            MinecraftServer server,
            Server bukkitServer,
            NetLoginHandler netLoginHandler,
            String username,
            Set bannedNames,
            Set bannedIps,
            boolean isWhitelisted,
            int currentPlayerCount,
            int maxPlayers,
            String bannedNameKickMessage,
            String bannedIpKickMessage,
            String whitelistKickMessage,
            String serverFullKickMessage,
            List onlinePlayers
    ) {
        EntityPlayer entity = new EntityPlayer(
                server,
                server.getWorldServer(0),
                username,
                new ItemInWorldManager(server.getWorldServer(0))
        );
        Player player = (entity == null) ? null : (Player) entity.getBukkitEntity();
        PlayerLoginEvent event = new PlayerLoginEvent(player, netLoginHandler);

        String hostAddress = ConnectionAddressParser.extractHost(netLoginHandler.networkManager.getSocketAddress().toString());
        LoginAdmissionPolicy.AdmissionResult admission = LoginAdmissionPolicy.getInstance().evaluate(
                username,
                hostAddress,
                bannedNames,
                bannedIps,
                isWhitelisted,
                currentPlayerCount,
                maxPlayers,
                bannedNameKickMessage,
                bannedIpKickMessage,
                whitelistKickMessage,
                serverFullKickMessage
        );

        LoginAdmissionEventService.AdmissionEventResult admissionEventResult =
                loginAdmissionEventService.evaluate(bukkitServer, event, admission);
        if (!admissionEventResult.isAllowed()) {
            netLoginHandler.disconnect(admissionEventResult.getKickMessage());
            return null;
        }

        DuplicateLoginGuard.disconnectDuplicateUsernameSessions(onlinePlayers, username);
        return entity;
    }
}
