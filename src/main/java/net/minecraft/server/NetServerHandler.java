package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonServer;
import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.block.BlockInteractionPacketHandler;
import com.legacyminecraft.poseidon.block.BlockInteractionSessionState;
import com.legacyminecraft.poseidon.block.SignUpdateProcessor;
import com.legacyminecraft.poseidon.entity.EntityInteractionSystem;
import com.legacyminecraft.poseidon.entity.PlayerActionPacketHandler;
import com.legacyminecraft.poseidon.inventory.HotbarSelectionBehaviour;
import com.legacyminecraft.poseidon.inventory.WindowTransactionBehaviour;
import com.legacyminecraft.poseidon.network.ClientDisconnectPacketHandler;
import com.legacyminecraft.poseidon.network.ConnectionHeartbeatExecutionSystem;
import com.legacyminecraft.poseidon.network.ConnectionHeartbeatSystem;
import com.legacyminecraft.poseidon.network.ConnectionLossExecutionSystem;
import com.legacyminecraft.poseidon.network.ConnectionLossReporter;
import com.legacyminecraft.poseidon.network.ConnectionSessionMetadata;
import com.legacyminecraft.poseidon.network.ConnectionTerminationSystem;
import com.legacyminecraft.poseidon.network.EntityActionPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.EntityActionResultExecutionSystem;
import com.legacyminecraft.poseidon.network.GroundMovementDecisionExecutionSystem;
import com.legacyminecraft.poseidon.network.GroundMovementDecisionLogSystem;
import com.legacyminecraft.poseidon.network.HotbarSwitchPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.HotbarSwitchResultExecutionSystem;
import com.legacyminecraft.poseidon.network.IncomingChatPacketHandler;
import com.legacyminecraft.poseidon.network.IncomingChatPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.IncomingChatResultExecutionSystem;
import com.legacyminecraft.poseidon.network.IncomingPacketEventSystem;
import com.legacyminecraft.poseidon.network.InvalidPositionResponseSystem;
import com.legacyminecraft.poseidon.network.MovementCheckReenableExecutionSystem;
import com.legacyminecraft.poseidon.network.MovementBranchExecutionSystem;
import com.legacyminecraft.poseidon.network.MovementPacketPolicy;
import com.legacyminecraft.poseidon.network.PacketSendPipelineSystem;
import com.legacyminecraft.poseidon.network.PacketSendExecutionSystem;
import com.legacyminecraft.poseidon.network.PacketSendResultExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerChatDispatchSystem;
import com.legacyminecraft.poseidon.network.PlayerGroundMovementSystem;
import com.legacyminecraft.poseidon.network.PlayerInputPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventDispatchSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventOutcomeSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventStateApplySystem;
import com.legacyminecraft.poseidon.network.PlayerMoveOutcomeExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerTeleportPlanApplySystem;
import com.legacyminecraft.poseidon.network.PlayerTeleportCoordinator;
import com.legacyminecraft.poseidon.network.PlayerTeleportExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerTeleportRequestExecutionSystem;
import com.legacyminecraft.poseidon.network.RespawnPacketHandler;
import com.legacyminecraft.poseidon.network.RespawnPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.RespawnResultExecutionSystem;
import com.legacyminecraft.poseidon.network.SignUpdatePacketExecutionSystem;
import com.legacyminecraft.poseidon.network.SleepingMovementPacketHandler;
import com.legacyminecraft.poseidon.network.UnexpectedPacketProtocolErrorExecutionSystem;
import com.legacyminecraft.poseidon.network.VehicleMoveOutcomeExecutionSystem;
import com.legacyminecraft.poseidon.network.VehicleMovementPacketHandler;
import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.runtime.PlayerCommandProcessor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

// CraftBukkit start
// CraftBukkit end

public class NetServerHandler extends NetHandler implements ICommandListener {

    public static Logger a = Logger.getLogger("Minecraft");
    public NetworkManager networkManager;
    public boolean disconnected = false;
    private MinecraftServer minecraftServer;
    public EntityPlayer player; // CraftBukkit - private -> public
    private int f;
    private int g;
    private int h;
    private boolean i;
    private double x;
    private double y;
    private double z;
    private boolean checkMovement = true;
    private Map n = new HashMap();
    private final ClientDisconnectPacketHandler clientDisconnectPacketHandler = ClientDisconnectPacketHandler.getInstance();
    private final ConnectionHeartbeatExecutionSystem connectionHeartbeatExecutionSystem = ConnectionHeartbeatExecutionSystem.getInstance();
    private final ConnectionHeartbeatSystem connectionHeartbeatSystem = ConnectionHeartbeatSystem.getInstance();
    private final ConnectionLossExecutionSystem connectionLossExecutionSystem = ConnectionLossExecutionSystem.getInstance();
    private final ConnectionLossReporter connectionLossReporter = ConnectionLossReporter.getInstance();
    private final ConnectionSessionMetadata sessionMetadata = new ConnectionSessionMetadata();
    private final ConnectionTerminationSystem connectionTerminationSystem = ConnectionTerminationSystem.getInstance();
    private final EntityActionPacketExecutionSystem entityActionPacketExecutionSystem = EntityActionPacketExecutionSystem.getInstance();
    private final EntityActionResultExecutionSystem entityActionResultExecutionSystem = EntityActionResultExecutionSystem.getInstance();
    private final GroundMovementDecisionExecutionSystem groundMovementDecisionExecutionSystem = GroundMovementDecisionExecutionSystem.getInstance();
    private final GroundMovementDecisionLogSystem groundMovementDecisionLogSystem = GroundMovementDecisionLogSystem.getInstance();
    private final HotbarSwitchPacketExecutionSystem hotbarSwitchPacketExecutionSystem = HotbarSwitchPacketExecutionSystem.getInstance();
    private final HotbarSwitchResultExecutionSystem hotbarSwitchResultExecutionSystem = HotbarSwitchResultExecutionSystem.getInstance();
    private final IncomingChatPacketHandler incomingChatPacketHandler = IncomingChatPacketHandler.getInstance();
    private final IncomingChatPacketExecutionSystem incomingChatPacketExecutionSystem = IncomingChatPacketExecutionSystem.getInstance();
    private final IncomingChatResultExecutionSystem incomingChatResultExecutionSystem = IncomingChatResultExecutionSystem.getInstance();
    private final IncomingPacketEventSystem incomingPacketEventSystem = IncomingPacketEventSystem.getInstance();
    private final InvalidPositionResponseSystem invalidPositionResponseSystem = InvalidPositionResponseSystem.getInstance();
    private final MovementBranchExecutionSystem movementBranchExecutionSystem = MovementBranchExecutionSystem.getInstance();
    private final MovementCheckReenableExecutionSystem movementCheckReenableExecutionSystem =
            MovementCheckReenableExecutionSystem.getInstance();
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();
    private final PacketSendExecutionSystem packetSendExecutionSystem = PacketSendExecutionSystem.getInstance();
    private final PacketSendPipelineSystem packetSendPipelineSystem = PacketSendPipelineSystem.getInstance();
    private final PacketSendResultExecutionSystem packetSendResultExecutionSystem = PacketSendResultExecutionSystem.getInstance();
    private final PlayerChatDispatchSystem playerChatDispatchSystem = PlayerChatDispatchSystem.getInstance();
    private final PlayerGroundMovementSystem playerGroundMovementSystem = PlayerGroundMovementSystem.getInstance();
    private final PlayerInputPacketExecutionSystem playerInputPacketExecutionSystem = PlayerInputPacketExecutionSystem.getInstance();
    private final PlayerMoveEventDispatchSystem playerMoveEventDispatchSystem = PlayerMoveEventDispatchSystem.getInstance();
    private final PlayerMoveEventExecutionSystem playerMoveEventExecutionSystem = PlayerMoveEventExecutionSystem.getInstance();
    private final PlayerMoveEventOutcomeSystem playerMoveEventOutcomeSystem = PlayerMoveEventOutcomeSystem.getInstance();
    private final PlayerMoveEventStateApplySystem playerMoveEventStateApplySystem = PlayerMoveEventStateApplySystem.getInstance();
    private final PlayerMoveOutcomeExecutionSystem playerMoveOutcomeExecutionSystem = PlayerMoveOutcomeExecutionSystem.getInstance();
    private final PlayerTeleportPlanApplySystem playerTeleportPlanApplySystem = PlayerTeleportPlanApplySystem.getInstance();
    private final PlayerTeleportCoordinator playerTeleportCoordinator = PlayerTeleportCoordinator.getInstance();
    private final PlayerTeleportExecutionSystem playerTeleportExecutionSystem = PlayerTeleportExecutionSystem.getInstance();
    private final PlayerTeleportRequestExecutionSystem playerTeleportRequestExecutionSystem =
            PlayerTeleportRequestExecutionSystem.getInstance();
    private final RespawnPacketHandler respawnPacketHandler = RespawnPacketHandler.getInstance();
    private final RespawnPacketExecutionSystem respawnPacketExecutionSystem = RespawnPacketExecutionSystem.getInstance();
    private final RespawnResultExecutionSystem respawnResultExecutionSystem = RespawnResultExecutionSystem.getInstance();
    private final SignUpdatePacketExecutionSystem signUpdatePacketExecutionSystem = SignUpdatePacketExecutionSystem.getInstance();
    private final SleepingMovementPacketHandler sleepingMovementPacketHandler = SleepingMovementPacketHandler.getInstance();
    private final UnexpectedPacketProtocolErrorExecutionSystem unexpectedPacketProtocolErrorExecutionSystem =
            UnexpectedPacketProtocolErrorExecutionSystem.getInstance();
    private final VehicleMoveOutcomeExecutionSystem vehicleMoveOutcomeExecutionSystem = VehicleMoveOutcomeExecutionSystem.getInstance();
    private final VehicleMovementPacketHandler vehicleMovementPacketHandler = VehicleMovementPacketHandler.getInstance();
    private final WindowTransactionBehaviour windowTransactionBehaviour = WindowTransactionBehaviour.getInstance();
    private final HotbarSelectionBehaviour hotbarSelectionBehaviour = HotbarSelectionBehaviour.getInstance();
    private final BlockInteractionPacketHandler blockInteractionPacketHandler = BlockInteractionPacketHandler.getInstance();
    private final BlockInteractionSessionState blockInteractionState = new BlockInteractionSessionState(MinecraftServer.currentTick, 0, null, -1);
    private final SignUpdateProcessor signUpdateProcessor = SignUpdateProcessor.getInstance();
    private final EntityInteractionSystem entityInteractionSystem = EntityInteractionSystem.getInstance();
    private final PlayerActionPacketHandler playerActionPacketHandler = PlayerActionPacketHandler.getInstance();
    private final PlayerCommandProcessor playerCommandProcessor = PlayerCommandProcessor.getInstance();
    private final ConnectionTerminationSystem.PacketSender disconnectPacketSender = new ConnectionTerminationSystem.PacketSender() {
        @Override
        public void sendPacket(Packet packet) {
            NetServerHandler.this.sendPacket(packet);
        }
    };
    private final ConnectionHeartbeatExecutionSystem.HeartbeatActions heartbeatActions =
            new ConnectionHeartbeatExecutionSystem.HeartbeatActions() {
                @Override
                public void pollNetwork() {
                    NetServerHandler.this.networkManager.b();
                }

                @Override
                public void sendPacket(Packet packet) {
                    NetServerHandler.this.sendPacket(packet);
                }
            };
    private final GroundMovementDecisionLogSystem.ConsoleLogSink groundMovementConsoleLogSink = new GroundMovementDecisionLogSystem.ConsoleLogSink() {
        @Override
        public void println(String logLine) {
            System.out.println(logLine);
        }
    };
    private final GroundMovementDecisionExecutionSystem.DecisionActions groundMovementDecisionActions =
            new GroundMovementDecisionExecutionSystem.DecisionActions() {
                @Override
                public void disconnect(String reason) {
                    NetServerHandler.this.disconnect(reason);
                }

                @Override
                public void teleportToLastGood(float yaw, float pitch) {
                    NetServerHandler.this.a(NetServerHandler.this.x, NetServerHandler.this.y, NetServerHandler.this.z, yaw, pitch);
                }

                @Override
                public void applyMovement(int floatingTicks, boolean onGround, double fallDeltaY) {
                    NetServerHandler.this.h = floatingTicks;
                    NetServerHandler.this.player.onGround = onGround;
                    NetServerHandler.this.minecraftServer.serverConfigurationManager.d(NetServerHandler.this.player);
                    NetServerHandler.this.player.b(fallDeltaY, onGround);
                }
            };
    private final PlayerMoveOutcomeExecutionSystem.MoveOutcomeActions moveOutcomeActions =
            new PlayerMoveOutcomeExecutionSystem.MoveOutcomeActions() {
                @Override
                public void sendRollbackPacket(Packet13PlayerLookMove rollbackPacket) {
                    NetServerHandler.this.player.netServerHandler.sendPacket(rollbackPacket);
                }

                @Override
                public void teleportPlayer(Location location) {
                    NetServerHandler.this.player.getBukkitEntity().teleport(location);
                }
            };
    private final VehicleMoveOutcomeExecutionSystem.VehicleMoveActions vehicleMoveActions =
            new VehicleMoveOutcomeExecutionSystem.VehicleMoveActions() {
                @Override
                public void logCrashWarning(String warningMessage) {
                    a.warning(warningMessage);
                }

                @Override
                public void kickPlayer(String kickMessage) {
                    NetServerHandler.this.getPlayer().kickPlayer(kickMessage);
                }

                @Override
                public void updateLastKnownPosition(double x, double y, double z) {
                    NetServerHandler.this.x = x;
                    NetServerHandler.this.y = y;
                    NetServerHandler.this.z = z;
                }
            };
    private final PlayerMoveEventStateApplySystem.MovementStateSink moveEventStateSink =
            new PlayerMoveEventStateApplySystem.MovementStateSink() {
                @Override
                public void apply(double lastPosX, double lastPosY, double lastPosZ, float lastYaw, float lastPitch, boolean justTeleported) {
                    NetServerHandler.this.lastPosX = lastPosX;
                    NetServerHandler.this.lastPosY = lastPosY;
                    NetServerHandler.this.lastPosZ = lastPosZ;
                    NetServerHandler.this.lastYaw = lastYaw;
                    NetServerHandler.this.lastPitch = lastPitch;
                    NetServerHandler.this.justTeleported = justTeleported;
                }
            };
    private final PlayerMoveEventExecutionSystem.MoveEventFlow moveEventFlow =
            new PlayerMoveEventExecutionSystem.MoveEventFlow() {
                @Override
                public PlayerMoveEventDispatchSystem.MovementEventState createMovementEventState() {
                    return new PlayerMoveEventDispatchSystem.MovementEventState(
                            NetServerHandler.this.lastPosX,
                            NetServerHandler.this.lastPosY,
                            NetServerHandler.this.lastPosZ,
                            NetServerHandler.this.lastYaw,
                            NetServerHandler.this.lastPitch,
                            NetServerHandler.this.justTeleported
                    );
                }

                @Override
                public PlayerMoveEventDispatchSystem.MoveEventResult dispatchMoveEvent(
                        PlayerMoveEventDispatchSystem.MovementEventState movementEventState
                ) {
                    return playerMoveEventDispatchSystem.processMoveEvent(
                            NetServerHandler.this.bukkitServer,
                            NetServerHandler.this.player,
                            requirePendingMoveEventPlayer(),
                            requirePendingMoveEventPacket(),
                            movementEventState,
                            NetServerHandler.this.pendingMoveEventCheckMovement
                    );
                }

                @Override
                public void applyMovementState(PlayerMoveEventDispatchSystem.MovementEventState movementEventState) {
                    playerMoveEventStateApplySystem.applyState(movementEventState, NetServerHandler.this.moveEventStateSink);
                }

                @Override
                public PlayerMoveEventOutcomeSystem.MoveOutcomeDecision resolveMoveOutcome(
                        PlayerMoveEventDispatchSystem.MoveEventResult moveEventResult
                ) {
                    return playerMoveEventOutcomeSystem.resolve(moveEventResult);
                }

                @Override
                public boolean executeMoveOutcome(PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision) {
                    return playerMoveOutcomeExecutionSystem.executeOutcome(moveOutcomeDecision, NetServerHandler.this.moveOutcomeActions);
                }
            };
    private final IncomingChatResultExecutionSystem.ChatActions incomingChatActions =
            new IncomingChatResultExecutionSystem.ChatActions() {
                @Override
                public void disconnect(String message) {
                    NetServerHandler.this.disconnect(message);
                }

                @Override
                public void dispatchNormalizedChat(String normalizedMessage) {
                    NetServerHandler.this.chat(normalizedMessage);
                }
            };
    private final HotbarSwitchResultExecutionSystem.SwitchActions hotbarSwitchActions =
            new HotbarSwitchResultExecutionSystem.SwitchActions() {
                @Override
                public void disconnect(String message) {
                    NetServerHandler.this.disconnect(message);
                }
            };
    private final HotbarSwitchPacketExecutionSystem.SwitchResolver hotbarSwitchResolver =
            new HotbarSwitchPacketExecutionSystem.SwitchResolver() {
                @Override
                public HotbarSelectionBehaviour.SwitchResult resolve() {
                    Packet16BlockItemSwitch packet16blockitemswitch = requirePendingHotbarSwitchPacket();
                    return hotbarSelectionBehaviour.handleSwitch(
                            NetServerHandler.this.bukkitServer,
                            NetServerHandler.this.player,
                            packet16blockitemswitch
                    );
                }
            };
    private final RespawnResultExecutionSystem.RespawnActions respawnActions =
            new RespawnResultExecutionSystem.RespawnActions() {
                @Override
                public void applyRespawnedPlayer(EntityPlayer player) {
                    NetServerHandler.this.player = player;
                    NetServerHandler.this.getPlayer().setHandle(NetServerHandler.this.player);
                }
            };
    private final RespawnPacketExecutionSystem.RespawnResultResolver respawnResultResolver =
            new RespawnPacketExecutionSystem.RespawnResultResolver() {
                @Override
                public RespawnPacketHandler.RespawnResult resolve() {
                    return respawnPacketHandler.handleRespawnPacket(
                            NetServerHandler.this.minecraftServer,
                            NetServerHandler.this.player
                    );
                }
            };
    private final EntityActionResultExecutionSystem.EntityActionResultActions entityActionResultActions =
            new EntityActionResultExecutionSystem.EntityActionResultActions() {
                @Override
                public void disableMovementCheck() {
                    NetServerHandler.this.checkMovement = false;
                }
            };
    private final EntityActionPacketExecutionSystem.EntityActionResultResolver entityActionResultResolver =
            new EntityActionPacketExecutionSystem.EntityActionResultResolver() {
                @Override
                public boolean resolve() {
                    Packet19EntityAction packet19entityaction = requirePendingEntityActionPacket();
                    return playerActionPacketHandler.handleEntityActionPacket(
                            NetServerHandler.this.bukkitServer,
                            NetServerHandler.this.player,
                            packet19entityaction
                    );
                }
            };
    private final PlayerTeleportPlanApplySystem.TeleportPlanActions teleportPlanActions =
            new PlayerTeleportPlanApplySystem.TeleportPlanActions() {
                @Override
                public void applyMovementState(
                        double x,
                        double y,
                        double z,
                        float yaw,
                        float pitch,
                        boolean justTeleported,
                        boolean movementCheckEnabled
                ) {
                    NetServerHandler.this.lastPosX = x;
                    NetServerHandler.this.lastPosY = y;
                    NetServerHandler.this.lastPosZ = z;
                    NetServerHandler.this.lastYaw = yaw;
                    NetServerHandler.this.lastPitch = pitch;
                    NetServerHandler.this.justTeleported = justTeleported;
                    NetServerHandler.this.checkMovement = movementCheckEnabled;
                    NetServerHandler.this.x = x;
                    NetServerHandler.this.y = y;
                    NetServerHandler.this.z = z;
                }

                @Override
                public void applyPlayerLocation(double x, double y, double z, float yaw, float pitch) {
                    NetServerHandler.this.player.setLocation(x, y, z, yaw, pitch);
                }

                @Override
                public void sendTeleportPacket(Packet13PlayerLookMove teleportPacket) {
                    NetServerHandler.this.player.netServerHandler.sendPacket(teleportPacket);
                }
            };
    private final PlayerTeleportRequestExecutionSystem.TeleportActions teleportRequestActions =
            new PlayerTeleportRequestExecutionSystem.TeleportActions() {
                @Override
                public void teleport(Location destination) {
                    NetServerHandler.this.teleport(destination);
                }
            };
    private final PlayerTeleportRequestExecutionSystem.TeleportDestinationResolver teleportDestinationResolver =
            new PlayerTeleportRequestExecutionSystem.TeleportDestinationResolver() {
                @Override
                public Location resolveDestination() {
                    return playerTeleportCoordinator.resolveTeleportDestination(
                            NetServerHandler.this.bukkitServer,
                            NetServerHandler.this.getPlayer(),
                            NetServerHandler.this.pendingTeleportX,
                            NetServerHandler.this.pendingTeleportY,
                            NetServerHandler.this.pendingTeleportZ,
                            NetServerHandler.this.pendingTeleportYaw,
                            NetServerHandler.this.pendingTeleportPitch
                    );
                }
            };
    private final PacketSendResultExecutionSystem.PacketSendActions packetSendActions =
            new PacketSendResultExecutionSystem.PacketSendActions() {
                @Override
                public void markPacketSent() {
                    NetServerHandler.this.g = NetServerHandler.this.f;
                }
            };
    private final PacketSendExecutionSystem.PacketSendResultResolver packetSendResultResolver =
            new PacketSendExecutionSystem.PacketSendResultResolver() {
                @Override
                public boolean resolve() {
                    Packet packet = requirePendingSendPacket();
                    return packetSendPipelineSystem.sendPacket(
                            NetServerHandler.this.networkManager,
                            NetServerHandler.this.player,
                            NetServerHandler.this.getPlayer(),
                            packet,
                            NetServerHandler.this.firePacketEvents
                    );
                }
            };
    private final SignUpdatePacketExecutionSystem.SignUpdateActions signUpdateActions =
            new SignUpdatePacketExecutionSystem.SignUpdateActions() {
                @Override
                public void process(Packet130UpdateSign packet130updateSign) {
                    signUpdateProcessor.processSignUpdate(
                            NetServerHandler.this.minecraftServer,
                            NetServerHandler.this.bukkitServer,
                            NetServerHandler.this.player,
                            packet130updateSign
                    );
                }
            };
    private final UnexpectedPacketProtocolErrorExecutionSystem.ProtocolErrorActions protocolErrorActions =
            new UnexpectedPacketProtocolErrorExecutionSystem.ProtocolErrorActions() {
                @Override
                public void disconnect(String message) {
                    NetServerHandler.this.disconnect(message);
                }
            };
    private final PlayerInputPacketExecutionSystem.InputActions playerInputActions =
            new PlayerInputPacketExecutionSystem.InputActions() {
                @Override
                public void applyInput(
                        float primaryX,
                        float primaryY,
                        boolean primaryFlag,
                        boolean secondaryFlag,
                        float secondaryX,
                        float secondaryY
                ) {
                    NetServerHandler.this.player.a(primaryX, primaryY, primaryFlag, secondaryFlag, secondaryX, secondaryY);
                }
            };
    private final MovementBranchExecutionSystem.MovementBranches movementBranches =
            new MovementBranchExecutionSystem.MovementBranches() {
                @Override
                public boolean handleVehicleMovement() {
                    if (NetServerHandler.this.player.vehicle == null) {
                        return false;
                    }
                    WorldServer worldserver = requirePendingMovementWorld();
                    Packet10Flying packet10flying = requirePendingMovementPacket();
                    VehicleMovementPacketHandler.VehicleMoveResult vehicleMoveResult =
                            vehicleMovementPacketHandler.handleVehicleMovement(
                                    NetServerHandler.this.player,
                                    worldserver,
                                    NetServerHandler.this.minecraftServer.serverConfigurationManager,
                                    packet10flying
                            );
                    vehicleMoveOutcomeExecutionSystem.executeOutcome(
                            vehicleMoveResult,
                            vehicleMovementPacketHandler,
                            NetServerHandler.this.player.name,
                            NetServerHandler.this.player.vehicle,
                            NetServerHandler.this.vehicleMoveActions
                    );
                    return true;
                }

                @Override
                public boolean handleSleepingMovement() {
                    WorldServer worldserver = requirePendingMovementWorld();
                    return sleepingMovementPacketHandler.handleSleepingMovement(
                            NetServerHandler.this.player,
                            worldserver,
                            NetServerHandler.this.x,
                            NetServerHandler.this.y,
                            NetServerHandler.this.z
                    );
                }

                @Override
                public PlayerGroundMovementSystem.GroundMovementDecision resolveGroundMovementDecision() {
                    WorldServer worldserver = requirePendingMovementWorld();
                    Packet10Flying packet10flying = requirePendingMovementPacket();
                    return playerGroundMovementSystem.processGroundMovement(
                            NetServerHandler.this.player,
                            worldserver,
                            packet10flying,
                            NetServerHandler.this.x,
                            NetServerHandler.this.y,
                            NetServerHandler.this.z,
                            NetServerHandler.this.checkMovement,
                            NetServerHandler.this.minecraftServer.allowFlight,
                            NetServerHandler.this.h
                    );
                }

                @Override
                public void logGroundMovementDecision(PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision) {
                    NetServerHandler.this.groundMovementDecisionLogSystem.emitLogs(
                            groundMovementDecision,
                            a,
                            NetServerHandler.this.groundMovementConsoleLogSink
                    );
                }

                @Override
                public boolean executeGroundMovementDecision(PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision) {
                    return NetServerHandler.this.groundMovementDecisionExecutionSystem.executeDecision(
                            groundMovementDecision,
                            NetServerHandler.this.groundMovementDecisionActions
                    );
                }
            };
    private final PlayerChatDispatchSystem.CommandDispatcher commandDispatcher = new PlayerChatDispatchSystem.CommandDispatcher() {
        @Override
        public void dispatch(String message) {
            handleCommand(message);
        }
    };
    private boolean firePacketEvents;
    private Packet16BlockItemSwitch pendingHotbarSwitchPacket;
    private Packet19EntityAction pendingEntityActionPacket;
    private Packet10Flying pendingMovementPacket;
    private WorldServer pendingMovementWorld;
    private Packet10Flying pendingMoveEventPacket;
    private Player pendingMoveEventPlayer;
    private boolean pendingMoveEventCheckMovement;
    private Packet pendingSendPacket;
    private double pendingTeleportX;
    private double pendingTeleportY;
    private double pendingTeleportZ;
    private float pendingTeleportYaw;
    private float pendingTeleportPitch;
    
    private final String msgPlayerLeave;

    public boolean isReceivedKeepAlive() {
        return sessionMetadata.isReceivedKeepAlive();
    }

    public void setReceivedKeepAlive(boolean receivedKeepAlive) {
        this.sessionMetadata.setReceivedKeepAlive(receivedKeepAlive);
    }

    public NetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        this.minecraftServer = minecraftserver;
        this.networkManager = networkmanager;
        networkmanager.a((NetHandler) this);
        this.player = entityplayer;
        entityplayer.netServerHandler = this;

        // CraftBukkit start
        this.bukkitServer = minecraftserver.server;
        this.firePacketEvents = PoseidonConfig.getInstance().getBoolean("settings.packet-events.enabled", false); //Poseidon
        this.msgPlayerLeave = PoseidonConfig.getInstance().getConfigString("message.player.leave");
    }

    //Project Poseidon - Start
    public boolean isUsingReleaseToBeta() {
        return sessionMetadata.isUsingReleaseToBeta();
    }

    public void setUsingReleaseToBeta(boolean usingReleaseToBeta) {
        this.sessionMetadata.setUsingReleaseToBeta(usingReleaseToBeta);
    }

    public com.projectposeidon.ConnectionType getConnectionType() {
        return com.projectposeidon.ConnectionType.fromCanonical(sessionMetadata.getConnectionType());
    }

    public ConnectionType getCanonicalConnectionType() {
        return sessionMetadata.getConnectionType();
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.sessionMetadata.setConnectionType(connectionType);
    }

    public void setConnectionType(com.projectposeidon.ConnectionType connectionType) {
        this.sessionMetadata.setConnectionType(connectionType.toCanonical());
    }

    public void setRawConnectionType(int rawConnectionType) {
        this.sessionMetadata.setRawConnectionType(rawConnectionType);
    }

    public int getRawConnectionType() {
        return sessionMetadata.getRawConnectionType();
    }


    //Project Poseidon - End

    private final Server bukkitServer;
    private int lastTick = MinecraftServer.currentTick;
    private static final int PLACE_DISTANCE_SQUARED = 6 * 6;

    // Get position of last block hit for BlockDamageLevel.STOPPED
    private double lastPosX = Double.MAX_VALUE;
    private double lastPosY = Double.MAX_VALUE;
    private double lastPosZ = Double.MAX_VALUE;
    private float lastPitch = Float.MAX_VALUE;
    private float lastYaw = Float.MAX_VALUE;
    private boolean justTeleported = false;

    public CraftPlayer getPlayer() {
        return (this.player == null) ? null : (CraftPlayer) this.player.getBukkitEntity();
    }
    // CraftBukkit end

    public void a() {
        this.i = connectionHeartbeatExecutionSystem.applyHeartbeat(
                this.f,
                this.g,
                20,
                this.i,
                this.connectionHeartbeatSystem,
                this.heartbeatActions
        );
    }

    public void disconnect(String s) {
        this.disconnected = connectionTerminationSystem.terminate(
                this.disconnected,
                this.bukkitServer,
                this.player,
                s,
                this.msgPlayerLeave,
                this.disconnectPacketSender,
                this.networkManager,
                this.minecraftServer
        );
    }

    public void a(Packet27 packet27) {
        if (!allowIncomingPacket(packet27)) {
            return;
        }

        playerInputPacketExecutionSystem.execute(packet27, this.playerInputActions);
    }

    public void a(Packet10Flying packet10flying) {
        if (!allowIncomingPacket(packet10flying)) {
            return;
        }

        WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);

        this.i = true;

        this.checkMovement = movementCheckReenableExecutionSystem.apply(
                this.checkMovement,
                packet10flying.x,
                packet10flying.y,
                packet10flying.z,
                this.x,
                this.y,
                this.z,
                movementPacketPolicy
        );

        // CraftBukkit start
        Player player = this.getPlayer();
        this.pendingMoveEventPacket = packet10flying;
        this.pendingMoveEventPlayer = player;
        this.pendingMoveEventCheckMovement = this.checkMovement;
        try {
            if (playerMoveEventExecutionSystem.execute(this.moveEventFlow)) {
                return;
            }
        } finally {
            this.pendingMoveEventPacket = null;
            this.pendingMoveEventPlayer = null;
            this.pendingMoveEventCheckMovement = false;
        }

        if (invalidPositionResponseSystem.handleInvalidPositionIfNeeded(packet10flying, player, disconnected)) {
            return;
        }

        if (this.checkMovement && !this.player.dead) {
            // CraftBukkit end
            this.x = this.player.locX;
            this.y = this.player.locY;
            this.z = this.player.locZ;
            this.pendingMovementWorld = worldserver;
            this.pendingMovementPacket = packet10flying;
            try {
                if (movementBranchExecutionSystem.execute(this.movementBranches)) {
                    return;
                }
            } finally {
                this.pendingMovementWorld = null;
                this.pendingMovementPacket = null;
            }
        }
    }

    public void a(double d0, double d1, double d2, float f, float f1) {
        this.pendingTeleportX = d0;
        this.pendingTeleportY = d1;
        this.pendingTeleportZ = d2;
        this.pendingTeleportYaw = f;
        this.pendingTeleportPitch = f1;
        try {
            playerTeleportRequestExecutionSystem.execute(this.teleportDestinationResolver, this.teleportRequestActions);
        } finally {
            this.pendingTeleportX = 0.0D;
            this.pendingTeleportY = 0.0D;
            this.pendingTeleportZ = 0.0D;
            this.pendingTeleportYaw = 0.0F;
            this.pendingTeleportPitch = 0.0F;
        }
    }

    public void teleport(Location dest) {
        PlayerTeleportExecutionSystem.TeleportExecutionPlan plan = playerTeleportExecutionSystem.createExecutionPlan(dest);
        playerTeleportPlanApplySystem.applyPlan(plan, this.teleportPlanActions);
    }

    public void a(Packet14BlockDig packet14blockdig) {
        if (!allowIncomingPacket(packet14blockdig)) {
            return;
        }

        blockInteractionPacketHandler.handleBlockDig(
                this.bukkitServer,
                this.minecraftServer,
                this.player,
                packet14blockdig,
                this.blockInteractionState,
                a,
                MinecraftServer.currentTick
        );
    }

    public void a(Packet15Place packet15place) {
        if (!allowIncomingPacket(packet15place)) {
            return;
        }

        blockInteractionPacketHandler.handleBlockPlace(
                this.minecraftServer,
                this.player,
                packet15place,
                this.blockInteractionState,
                PLACE_DISTANCE_SQUARED
        );
    }

    public void a(String s, Object[] aobject) {
        this.disconnected = connectionLossExecutionSystem.executeConnectionLoss(
                this.disconnected,
                this.connectionLossReporter,
                this.minecraftServer,
                this.player,
                s,
                a
        );
    }

    public void a(Packet packet) {
        unexpectedPacketProtocolErrorExecutionSystem.execute(this.getClass(), packet, a, this.protocolErrorActions);
    }

    public void sendPacket(Packet packet) {
        this.pendingSendPacket = packet;
        try {
            packetSendExecutionSystem.execute(
                    this.packetSendResultResolver,
                    packetSendResultExecutionSystem,
                    this.packetSendActions
            );
        } finally {
            this.pendingSendPacket = null;
        }
    }

    public void a(Packet16BlockItemSwitch packet16blockitemswitch) {
        if (!allowIncomingPacket(packet16blockitemswitch)) {
            return;
        }

        this.pendingHotbarSwitchPacket = packet16blockitemswitch;
        try {
            hotbarSwitchPacketExecutionSystem.execute(
                    this.hotbarSwitchResolver,
                    hotbarSwitchResultExecutionSystem,
                    this.player.name,
                    a,
                    this.hotbarSwitchActions
            );
        } finally {
            this.pendingHotbarSwitchPacket = null;
        }
    }

    public void a(Packet3Chat packet3chat) {
        if (!allowIncomingPacket(packet3chat)) {
            return;
        }

        if (!incomingChatPacketExecutionSystem.execute(
                packet3chat.message,
                100,
                FontAllowedCharacters.allowedCharacters,
                incomingChatPacketHandler,
                incomingChatResultExecutionSystem,
                this.incomingChatActions
        )) {
            return;
        }
    }

    public boolean chat(String s) {
        return playerChatDispatchSystem.dispatchValidatedChat(
                this.bukkitServer,
                this.minecraftServer,
                this.getPlayer(),
                this.player.dead,
                s,
                this.commandDispatcher
        );
        // CraftBukkit end
    }

    private void handleCommand(String s) {
        playerCommandProcessor.handlePlayerCommand(this.bukkitServer, this.getPlayer(), s, a);

        /* CraftBukkit start - No longer neaded av we have already handled it server.dispatchCommand above.
        if (s.toLowerCase().startsWith("/me ")) {
            s = "* " + this.player.name + " " + s.substring(s.indexOf(" ")).trim();
            a.info(s);
            this.minecraftServer.serverConfigurationManager.sendAll(new Packet3Chat(s));
        } else if (s.toLowerCase().startsWith("/kill")) {
            this.player.damageEntity(this.player, 1000); // CraftBukkit - replace null entity with player entity; TODO: decide if we want damage with a null source to fire an event.
        } else if (s.toLowerCase().startsWith("/tell ")) {
            String[] astring = s.split(" ");

            if (astring.length >= 3) {
                s = s.substring(s.indexOf(" ")).trim();
                s = s.substring(s.indexOf(" ")).trim();
                s = "\u00A77" + this.player.name + " whispers " + s;
                a.info(s + " to " + astring[1]);
                if (!this.minecraftServer.serverConfigurationManager.a(astring[1], (Packet) (new Packet3Chat(s)))) {
                    this.sendPacket(new Packet3Chat("\u00A7cThere\'s no player by that name online."));
                }
            }
        } else {
            String s1;

            if (this.minecraftServer.serverConfigurationManager.isOp(this.player.name)) {
                s1 = s.substring(1);
                a.info(this.player.name + " issued server command: " + s1);
                this.minecraftServer.issueCommand(s1, this);
            } else {
                s1 = s.substring(1);
                a.info(this.player.name + " tried command: " + s1);
            }
        }
        // CraftBukkit end */
    }

    public void a(Packet18ArmAnimation packet18armanimation) {
        if (!allowIncomingPacket(packet18armanimation)) {
            return;
        }

        playerActionPacketHandler.handleArmAnimationPacket(this.bukkitServer, this.player, packet18armanimation);
    }

    public void a(Packet19EntityAction packet19entityaction) {
        if (!allowIncomingPacket(packet19entityaction)) {
            return;
        }

        this.pendingEntityActionPacket = packet19entityaction;
        try {
            entityActionPacketExecutionSystem.execute(
                    this.entityActionResultResolver,
                    entityActionResultExecutionSystem,
                    this.entityActionResultActions
            );
        } finally {
            this.pendingEntityActionPacket = null;
        }
    }

    public void a(Packet0KeepAlive packet0KeepAlive) {
        this.sessionMetadata.setReceivedKeepAlive(true);
    }

    public void a(Packet255KickDisconnect packet255kickdisconnect) {
        if (!allowIncomingPacket(packet255kickdisconnect)) {
            return;
        }

        clientDisconnectPacketHandler.handleClientDisconnect(this.networkManager);
    }

    public int b() {
        return this.networkManager.e();
    }

    public void sendMessage(String s) {
        this.sendPacket(new Packet3Chat("\u00A77" + s));
    }

    public String getName() {
        return this.player.name;
    }

    public void a(Packet7UseEntity packet7useentity) {
        if (!allowIncomingPacket(packet7useentity)) {
            return;
        }

        entityInteractionSystem.handleUseEntityPacket(this.minecraftServer, this.bukkitServer, this.player, packet7useentity);
    }

    public void a(Packet9Respawn packet9respawn) {
        if (!allowIncomingPacket(packet9respawn)) {
            return;
        }

        respawnPacketExecutionSystem.execute(this.respawnResultResolver, respawnResultExecutionSystem, this.respawnActions);
    }

    public void a(Packet101CloseWindow packet101closewindow) {
        windowTransactionBehaviour.handleCloseWindow(this.player);
    }

    public void a(Packet102WindowClick packet102windowclick) {
        if (!allowIncomingPacket(packet102windowclick)) {
            return;
        }

        windowTransactionBehaviour.handleWindowClick(this.player, packet102windowclick, this.n);
    }

    public void a(Packet106Transaction packet106transaction) {
        if (!allowIncomingPacket(packet106transaction)) {
            return;
        }

        windowTransactionBehaviour.handleTransactionConfirmation(this.player, packet106transaction, this.n);
    }

    public void a(Packet130UpdateSign packet130updatesign) {
        if (!allowIncomingPacket(packet130updatesign)) {
            return;
        }

        signUpdatePacketExecutionSystem.execute(this.player.dead, packet130updatesign, this.signUpdateActions);
    }

    private boolean allowIncomingPacket(Packet packet) {
        if (!this.firePacketEvents) {
            return true;
        }
        return incomingPacketEventSystem.allowIncomingPacket(this.bukkitServer, this.player, packet);
    }

    public boolean c() {
        return true;
    }

    private Packet16BlockItemSwitch requirePendingHotbarSwitchPacket() {
        if (this.pendingHotbarSwitchPacket == null) {
            throw new IllegalStateException("Missing pending hotbar switch packet");
        }
        return this.pendingHotbarSwitchPacket;
    }

    private Packet19EntityAction requirePendingEntityActionPacket() {
        if (this.pendingEntityActionPacket == null) {
            throw new IllegalStateException("Missing pending entity action packet");
        }
        return this.pendingEntityActionPacket;
    }

    private Packet10Flying requirePendingMoveEventPacket() {
        if (this.pendingMoveEventPacket == null) {
            throw new IllegalStateException("Missing pending move-event packet");
        }
        return this.pendingMoveEventPacket;
    }

    private Player requirePendingMoveEventPlayer() {
        if (this.pendingMoveEventPlayer == null) {
            throw new IllegalStateException("Missing pending move-event player");
        }
        return this.pendingMoveEventPlayer;
    }

    private Packet10Flying requirePendingMovementPacket() {
        if (this.pendingMovementPacket == null) {
            throw new IllegalStateException("Missing pending movement packet");
        }
        return this.pendingMovementPacket;
    }

    private WorldServer requirePendingMovementWorld() {
        if (this.pendingMovementWorld == null) {
            throw new IllegalStateException("Missing pending movement world");
        }
        return this.pendingMovementWorld;
    }

    private Packet requirePendingSendPacket() {
        if (this.pendingSendPacket == null) {
            throw new IllegalStateException("Missing pending send packet");
        }
        return this.pendingSendPacket;
    }
}
