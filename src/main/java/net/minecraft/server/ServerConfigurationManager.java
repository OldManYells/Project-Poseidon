package net.minecraft.server;

import com.legacyminecraft.poseidon.Poseidon;
import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.auth.login.AccessListAdmissionPolicyBehaviour;
import com.legacyminecraft.poseidon.auth.login.AccessListMutationBehaviour;
import com.legacyminecraft.poseidon.auth.login.AccessListPersistence;
import com.legacyminecraft.poseidon.auth.login.PlayerLoginAdmissionSystem;
import com.legacyminecraft.poseidon.compat.bukkit.LegacyServerBootstrapBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.OperatorPermissionRefreshBridgeBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerLifecycleCoordinator;
import com.legacyminecraft.poseidon.world.player.PlayerFileDataBindingBehaviour;
import com.legacyminecraft.poseidon.world.player.ServerPlayerViewDistanceBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerSessionSystem;
import com.legacyminecraft.poseidon.world.player.PlayerWorldMoveSystem;
import com.legacyminecraft.poseidon.world.player.PlayerWorldTransferSupport;
import com.legacyminecraft.poseidon.world.player.RespawnPacketPairSystem;
import org.bukkit.Location;
import org.bukkit.Server;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

// CraftBukkit start
// CraftBukkit end

public class ServerConfigurationManager {

    public static Logger a = Logger.getLogger("Minecraft");
    public List players = new ArrayList();
    public MinecraftServer server; // CraftBukkit - private -> public
    // private PlayerManager[] d = new PlayerManager[2]; // CraftBukkit - removed
    public int maxPlayers; // CraftBukkit - private -> public
    public Set banByName = new HashSet(); // CraftBukkit - private -> public
    public Set banByIP = new HashSet(); // CraftBukkit - private -> public
    private Set h = new HashSet();
    private Set i = new HashSet();
    private File j;
    private File k;
    private File l;
    private File m;
    public PlayerFileData playerFileData; // CraftBukkit - private - >public
    public boolean o; // Craftbukkit - private -> public
    private final AccessListAdmissionPolicyBehaviour accessListAdmissionPolicyBehaviour = AccessListAdmissionPolicyBehaviour.getInstance();
    private final AccessListMutationBehaviour accessListMutationBehaviour = AccessListMutationBehaviour.getInstance();
    private final AccessListPersistence accessListPersistence = AccessListPersistence.getInstance();
    private final OperatorPermissionRefreshBridgeBehaviour operatorPermissionRefreshBridgeBehaviour = OperatorPermissionRefreshBridgeBehaviour.getInstance();
    private final PlayerLoginAdmissionSystem playerLoginAdmissionSystem = PlayerLoginAdmissionSystem.getInstance();
    private final PlayerLifecycleCoordinator playerLifecycleCoordinator = PlayerLifecycleCoordinator.getInstance();
    private final PlayerFileDataBindingBehaviour playerFileDataBindingBehaviour = PlayerFileDataBindingBehaviour.getInstance();
    private final ServerPlayerViewDistanceBehaviour serverPlayerViewDistanceBehaviour = ServerPlayerViewDistanceBehaviour.getInstance();
    private final PlayerSessionSystem playerSessionSystem = PlayerSessionSystem.getInstance();
    private final PlayerWorldMoveSystem playerWorldMoveSystem = PlayerWorldMoveSystem.getInstance();
    private final PlayerWorldTransferSupport playerWorldTransferSupport = PlayerWorldTransferSupport.getInstance();
    private final RespawnPacketPairSystem respawnPacketPairSystem = RespawnPacketPairSystem.getInstance();
    private final LegacyServerBootstrapBridgeBehaviour legacyServerBootstrapBridgeBehaviour =
            LegacyServerBootstrapBridgeBehaviour.getInstance();
    private String pendingOperatorMutationName;
    private final AccessListMutationBehaviour.MutationHooks playerBanMutationHooks =
            new AccessListMutationBehaviour.MutationHooks() {
                @Override
                public void persist() {
                    ServerConfigurationManager.this.h();
                }

                @Override
                public void afterMutation() {
                }
            };
    private final AccessListMutationBehaviour.MutationHooks ipBanMutationHooks =
            new AccessListMutationBehaviour.MutationHooks() {
                @Override
                public void persist() {
                    ServerConfigurationManager.this.j();
                }

                @Override
                public void afterMutation() {
                }
            };
    private final AccessListMutationBehaviour.MutationHooks operatorMutationHooks =
            new AccessListMutationBehaviour.MutationHooks() {
                @Override
                public void persist() {
                    ServerConfigurationManager.this.l();
                }

                @Override
                    public void afterMutation() {
                    operatorPermissionRefreshBridgeBehaviour.refreshPermissionsIfOnline(
                            ServerConfigurationManager.this.bukkitServer,
                            ServerConfigurationManager.this.pendingOperatorMutationName
                    );
                }
            };
    private final AccessListMutationBehaviour.MutationHooks whitelistMutationHooks =
            new AccessListMutationBehaviour.MutationHooks() {
                @Override
                public void persist() {
                    ServerConfigurationManager.this.n();
                }

                @Override
                public void afterMutation() {
                }
            };

    // CraftBukkit start
    private Server bukkitServer;
    private final String msgKickBanned, msgKickIPBanned, msgKickWhitelist, msgKickServerFull, msgPlayerJoin, msgPlayerLeave;

    public ServerConfigurationManager(MinecraftServer minecraftserver) {
        this.bukkitServer = legacyServerBootstrapBridgeBehaviour.bootstrap(minecraftserver, this);
        // CraftBukkit end
        this.msgKickBanned = PoseidonConfig.getInstance().getConfigString("message.kick.banned");
        this.msgKickIPBanned = PoseidonConfig.getInstance().getConfigString("message.kick.ip-banned");
        this.msgKickWhitelist = PoseidonConfig.getInstance().getConfigString("message.kick.not-whitelisted");
        this.msgKickServerFull = PoseidonConfig.getInstance().getConfigString("message.kick.full");
        this.msgPlayerJoin = PoseidonConfig.getInstance().getConfigString("message.player.join");
        this.msgPlayerLeave = PoseidonConfig.getInstance().getConfigString("message.player.leave");

        this.server = minecraftserver;
        this.j = minecraftserver.a("banned-players.txt");
        this.k = minecraftserver.a("banned-ips.txt");
        this.l = minecraftserver.a("ops.txt");
        this.m = minecraftserver.a("white-list.txt");
        int i = minecraftserver.propertyManager.getInt("view-distance", 10);

        // CraftBukkit - removed playermanagers
        this.maxPlayers = minecraftserver.propertyManager.getInt("max-players", 20);
        this.o = minecraftserver.propertyManager.getBoolean("white-list", false);
        this.g();
        this.i();
        this.k();
        this.m();
        this.h();
        this.j();
        this.l();
        this.n();
    }

    public void setPlayerFileData(WorldServer[] aworldserver) {
        this.playerFileData = playerFileDataBindingBehaviour.bindIfAbsent(
                this.playerFileData,
                new PlayerFileDataBindingBehaviour.PlayerFileDataSupplier() {
                    @Override
                    public PlayerFileData resolve() {
                        return aworldserver[0].p().d();
                    }
                }
        );
    }

    public void a(EntityPlayer entityplayer) {
        playerLifecycleCoordinator.registerPlayerInWorldManagers(this.server, entityplayer);
    }

    public int a() {
        return serverPlayerViewDistanceBehaviour.resolveFurthestViewableBlock(this.server);
    }

    public void b(EntityPlayer entityplayer) {
        this.playerFileData.b(entityplayer);
    }

    public void c(EntityPlayer entityplayer) {
        playerLifecycleCoordinator.onPlayerJoin(this.server, this.bukkitServer, this.players, entityplayer, this.msgPlayerJoin);
    }

    public void d(EntityPlayer entityplayer) {
        playerLifecycleCoordinator.onPlayerMoved(this.server, entityplayer);
    }

    public String disconnect(EntityPlayer entityplayer) { // CraftBukkit - changed return type
        return playerLifecycleCoordinator.onPlayerDisconnect(
                this.server,
                this.bukkitServer,
                this.playerFileData,
                this.players,
                entityplayer,
                this.msgPlayerLeave
        );
    }

    public EntityPlayer a(NetLoginHandler netloginhandler, String s) {
        return playerLoginAdmissionSystem.admitAndCreatePlayer(
                this.server,
                this.bukkitServer,
                netloginhandler,
                s,
                this.banByName,
                this.banByIP,
                this.isWhitelisted(s),
                this.players.size(),
                this.maxPlayers,
                this.msgKickBanned,
                this.msgKickIPBanned,
                this.msgKickWhitelist,
                this.msgKickServerFull,
                this.players
        );
    }

    // CraftBukkit start
    public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i) {
        return this.moveToWorld(entityplayer, i, null);
    }

    public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, Location location) {
        return playerWorldMoveSystem.moveToWorld(
                this.server,
                this.bukkitServer,
                this.players,
                entityplayer,
                i,
                location,
                playerLifecycleCoordinator,
                playerSessionSystem,
                playerWorldTransferSupport,
                respawnPacketPairSystem
        );
    }

    public void f(EntityPlayer entityplayer) {
        PlayerWorldTransferSupport.PortalResolution portalResolution =
                playerWorldTransferSupport.resolvePortalDestination(this.server, entityplayer);
        if (portalResolution == null) {
            return;
        }
        this.moveToWorld(entityplayer, portalResolution.getDestinationDimension(), portalResolution.getLocation());
    }

    public void b() {
        playerLifecycleCoordinator.flushPlayerManagers(this.server);
    }

    public void flagDirty(int i, int j, int k, int l) {
        playerLifecycleCoordinator.flagDirtyBlock(this.server, i, j, k, l);
    }

    public void sendAll(Packet packet) {
        playerSessionSystem.sendPacketToAll(this.players, packet);
    }

    public void a(Packet packet, int i) {
        playerSessionSystem.sendPacketToDimension(this.players, i, packet);
    }

    public String c() {
        return playerSessionSystem.buildPlayerNameList(this.players);
    }

    public void a(String s) {
        accessListMutationBehaviour.addAndPersist(this.banByName, s, this.accessListPersistence, this.playerBanMutationHooks);
    }

    public void b(String s) {
        accessListMutationBehaviour.removeAndPersist(this.banByName, s, this.accessListPersistence, this.playerBanMutationHooks);
    }

    private void g() {
        accessListPersistence.loadNormalizedSet(this.banByName, this.j, a, "Failed to load ban list: ");
    }

    private void h() {
        accessListPersistence.saveSet(this.banByName, this.j, a, "Failed to save ban list: ");
    }

    public void c(String s) {
        accessListMutationBehaviour.addAndPersist(this.banByIP, s, this.accessListPersistence, this.ipBanMutationHooks);
    }

    public void d(String s) {
        accessListMutationBehaviour.removeAndPersist(this.banByIP, s, this.accessListPersistence, this.ipBanMutationHooks);
    }

    private void i() {
        accessListPersistence.loadNormalizedSet(this.banByIP, this.k, a, "Failed to load ip ban list: ");
    }

    private void j() {
        accessListPersistence.saveSet(this.banByIP, this.k, a, "Failed to save ip ban list: ");
    }

    public void e(String s) {
        this.pendingOperatorMutationName = s;
        try {
            accessListMutationBehaviour.addAndPersist(this.h, s, this.accessListPersistence, this.operatorMutationHooks);
        } finally {
            this.pendingOperatorMutationName = null;
        }
    }

    public void f(String s) {
        this.pendingOperatorMutationName = s;
        try {
            accessListMutationBehaviour.removeAndPersist(this.h, s, this.accessListPersistence, this.operatorMutationHooks);
        } finally {
            this.pendingOperatorMutationName = null;
        }
    }

    private void k() {
        accessListPersistence.loadNormalizedSet(this.h, this.l, a, "Failed to load ops: ");
    }

    private void l() {
        accessListPersistence.saveSet(this.h, this.l, a, "Failed to save ops: ");
    }

    private void m() {
        accessListPersistence.loadNormalizedSet(this.i, this.m, a, "Failed to load white-list: ");
    }

    private void n() {
        accessListPersistence.saveSet(this.i, this.m, a, "Failed to save white-list: ");
    }

    public boolean isWhitelisted(String s) {
        return accessListAdmissionPolicyBehaviour.isWhitelisted(
                s,
                this.o,
                this.h,
                this.i,
                this.accessListPersistence
        );
    }

    public boolean isOp(String s) {
        return accessListAdmissionPolicyBehaviour.isOperator(s, this.h, this.accessListPersistence);
    }

    public EntityPlayer i(String s) {
        return playerSessionSystem.findPlayer(this.players, s);
    }

    public void a(String s, String s1) {
        playerSessionSystem.sendChatToPlayer(this.players, s, s1);
    }

    public void sendPacketNearby(double d0, double d1, double d2, double d3, int i, Packet packet) {
        this.sendPacketNearby((EntityHuman) null, d0, d1, d2, d3, i, packet);
    }

    public void sendPacketNearby(EntityHuman entityhuman, double d0, double d1, double d2, double d3, int i, Packet packet) {
        playerSessionSystem.sendPacketNearby(this.players, entityhuman, d0, d1, d2, d3, i, packet);
    }

    public void j(String s) {
        playerSessionSystem.sendPacketToOperators(this.players, this.h, s);
    }

    public boolean a(String s, Packet packet) {
        return playerSessionSystem.sendPacketToPlayer(this.players, s, packet);
    }

    public void savePlayers() {
        playerSessionSystem.savePlayers(this.playerFileData, this.players);
    }

    public void a(int i, int j, int k, TileEntity tileentity) {
    }

    public void k(String s) {
        accessListMutationBehaviour.addAndPersist(this.i, s, this.accessListPersistence, this.whitelistMutationHooks);
    }

    public void l(String s) {
        accessListMutationBehaviour.removeAndPersist(this.i, s, this.accessListPersistence, this.whitelistMutationHooks);
    }

    public Set e() {
        return this.i;
    }

    public void f() {
        this.m();
    }

    public void a(EntityPlayer entityplayer, WorldServer worldserver) {
        playerSessionSystem.sendWorldState(entityplayer, worldserver);
    }

    public void updateClient(EntityPlayer entityplayer) {
        playerSessionSystem.refreshClient(entityplayer);
    }
}
