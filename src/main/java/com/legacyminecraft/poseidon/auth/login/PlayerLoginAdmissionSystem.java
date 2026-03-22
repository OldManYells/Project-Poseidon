package com.legacyminecraft.poseidon.auth.login;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetLoginHandler;
import org.bukkit.Server;

import java.util.List;
import java.util.Set;

/**
 * Role-aligned canonical facade for player login admission orchestration.
 */
public final class PlayerLoginAdmissionSystem {
    private static final PlayerLoginAdmissionSystem INSTANCE = new PlayerLoginAdmissionSystem();
    private final PlayerLoginAdmissionService delegate = PlayerLoginAdmissionService.getInstance();

    private PlayerLoginAdmissionSystem() {
    }

    public static PlayerLoginAdmissionSystem getInstance() {
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
        return delegate.admitAndCreatePlayer(
                server,
                bukkitServer,
                netLoginHandler,
                username,
                bannedNames,
                bannedIps,
                isWhitelisted,
                currentPlayerCount,
                maxPlayers,
                bannedNameKickMessage,
                bannedIpKickMessage,
                whitelistKickMessage,
                serverFullKickMessage,
                onlinePlayers
        );
    }
}
