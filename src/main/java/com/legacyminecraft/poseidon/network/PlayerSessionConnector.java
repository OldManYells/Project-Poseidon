package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.api.network.ConnectionType;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet1Login;
import net.minecraft.server.Packet4UpdateTime;
import net.minecraft.server.Packet6SpawnPosition;
import net.minecraft.server.WorldServer;

/**
 * Canonical authenticated-session connection flow.
 */
public final class PlayerSessionConnector {
    private static final PlayerSessionConnector INSTANCE = new PlayerSessionConnector();

    private PlayerSessionConnector() {
    }

    public static PlayerSessionConnector getInstance() {
        return INSTANCE;
    }

    public void connectAuthenticatedPlayer(
            NetLoginHandler loginHandler,
            Packet1Login loginPacket,
            MinecraftServer minecraftServer,
            boolean usingReleaseToBeta,
            ConnectionType connectionType,
            int rawConnectionType,
            boolean receivedKeepAlive) {
        EntityPlayer entityplayer = minecraftServer.serverConfigurationManager.a(loginHandler, loginPacket.name);

        if (entityplayer == null) {
            return;
        }

        minecraftServer.serverConfigurationManager.b(entityplayer);
        NetLoginHandler.a.info(loginHandler.b() + " logged in with entity id " + entityplayer.id + " at ([" + entityplayer.world.worldData.name + "] " + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
        WorldServer worldserver = (WorldServer) entityplayer.world;
        ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
        NetServerHandler netserverhandler = new NetServerHandler(minecraftServer, loginHandler.networkManager, entityplayer);
        netserverhandler.setUsingReleaseToBeta(usingReleaseToBeta);
        netserverhandler.setConnectionType(connectionType);
        netserverhandler.setRawConnectionType(rawConnectionType);
        netserverhandler.setReceivedKeepAlive(receivedKeepAlive);
        netserverhandler.sendPacket(new Packet1Login("", entityplayer.id, worldserver.getSeed(), (byte) worldserver.worldProvider.dimension));
        netserverhandler.sendPacket(new Packet6SpawnPosition(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z));
        minecraftServer.serverConfigurationManager.a(entityplayer, worldserver);
        minecraftServer.serverConfigurationManager.c(entityplayer);
        netserverhandler.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        minecraftServer.networkListenThread.a(netserverhandler);
        netserverhandler.sendPacket(new Packet4UpdateTime(entityplayer.getPlayerTime()));
        entityplayer.syncInventory();
        if (PoseidonConfig.getInstance().getBoolean("settings.support.modloader.enable", false)) {
            net.minecraft.server.ModLoaderMp.HandleAllLogins(entityplayer);
        }
    }
}
