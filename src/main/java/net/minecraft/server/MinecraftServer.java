package net.minecraft.server;

import com.legacyminecraft.poseidon.Poseidon;
import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.PoseidonPlugin;
import com.legacyminecraft.poseidon.runtime.ModLoaderBootstrapSystem;
import com.legacyminecraft.poseidon.runtime.ServerBootstrapPolicy;
import com.legacyminecraft.poseidon.runtime.ServerChunkSaveCoordinatorSystem;
import com.legacyminecraft.poseidon.runtime.ServerCommandDispatcher;
import com.legacyminecraft.poseidon.runtime.ServerCommandDrainLoopSystem;
import com.legacyminecraft.poseidon.runtime.ServerRuntimeLifecycle;
import com.legacyminecraft.poseidon.runtime.ServerShutdownReporter;
import com.legacyminecraft.poseidon.runtime.TickTelemetry;
import com.legacyminecraft.poseidon.runtime.ServerEntryPointSystem;
import com.legacyminecraft.poseidon.runtime.ServerMainTickSystem;
import com.legacyminecraft.poseidon.runtime.ServerNetworkStartupSystem;
import com.legacyminecraft.poseidon.runtime.ServerStartupFeedbackSystem;
import com.legacyminecraft.poseidon.runtime.ServerStopLifecycleSystem;
import com.legacyminecraft.poseidon.runtime.ServerTickLoopPlanner;
import com.legacyminecraft.poseidon.runtime.ServerTickTimingPolicy;
import com.legacyminecraft.poseidon.runtime.PlayerListTickRegistrationSystem;
import com.legacyminecraft.poseidon.runtime.ServerNetworkStartupApplySystem;
import com.legacyminecraft.poseidon.runtime.ServerCommandQueueBehaviour;
import com.legacyminecraft.poseidon.runtime.ServerConsoleMessageLogSystem;
import com.legacyminecraft.poseidon.runtime.ServerRunLoopSystem;
import com.legacyminecraft.poseidon.runtime.ServerRunFailureRecoverySystem;
import com.legacyminecraft.poseidon.runtime.ServerRunTerminationSystem;
import com.legacyminecraft.poseidon.runtime.ServerWorldBootstrapProgressSystem;
import com.legacyminecraft.poseidon.runtime.WorldTickOrchestrator;
import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;
import com.legacyminecraft.poseidon.utility.PoseidonVersionChecker;
import com.legacyminecraft.poseidon.auth.uuid.UUIDManager;
import com.legacyminecraft.poseidon.watchdog.WatchDogThread;
import com.legacyminecraft.poseidon.world.WorldBootstrapSystem;
import com.legacyminecraft.poseidon.world.WorldLookupSystem;
import jline.ConsoleReader;
import joptsimple.OptionSet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.LoggerOutputStream;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.craftbukkit.util.ServerShutdownThread;
import org.bukkit.plugin.PluginLoadOrder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// CraftBukkit start
// CraftBukkit end

public class MinecraftServer implements Runnable, ICommandListener {

    public static Logger log = Logger.getLogger("Minecraft");
    public static HashMap trackerList = new HashMap();
    public NetworkListenThread networkListenThread;
    public PropertyManager propertyManager;
    // public WorldServer[] worldServer; // CraftBukkit - removed!
    public ServerConfigurationManager serverConfigurationManager;
    public ConsoleCommandHandler consoleCommandHandler; // CraftBukkit - made public
    private boolean isRunning = true;
    public boolean isStopped = false;
    int ticks = 0;
    public String i;
    public int j;
    private final List<IUpdatePlayerListBox> playerListTickBoxes = new ArrayList<IUpdatePlayerListBox>();
    private final List<ServerCommandEnvelopeBehaviour.ServerCommandState> pendingServerCommands =
            Collections.synchronizedList(new ArrayList<ServerCommandEnvelopeBehaviour.ServerCommandState>());
    // public EntityTracker[] tracker = new EntityTracker[2]; // CraftBukkit - removed!
    public boolean onlineMode;
    public boolean spawnAnimals;
    public boolean pvpMode;
    public boolean allowFlight;

    // CraftBukkit start
    public List<WorldServer> worlds = new ArrayList<WorldServer>();
    public CraftServer server;
    public OptionSet options;
    public ColouredConsoleSender console;
    public ConsoleReader reader;
    public static int currentTick;
    // CraftBukkit end

    //Poseidon Start
//    private WatchDogThread watchDogThread;
    private boolean modLoaderSupport = false;
    private final TickTelemetry tickTelemetry = new TickTelemetry();
    private final ServerBootstrapPolicy serverBootstrapPolicy = ServerBootstrapPolicy.getInstance();
    private final ServerCommandDispatcher commandDispatcher = ServerCommandDispatcher.getInstance();
    private final ServerCommandQueueBehaviour serverCommandQueueBehaviour = ServerCommandQueueBehaviour.getInstance();
    private final ServerConsoleMessageLogSystem serverConsoleMessageLogSystem = ServerConsoleMessageLogSystem.getInstance();
    private final ServerCommandDrainLoopSystem serverCommandDrainLoopSystem = ServerCommandDrainLoopSystem.getInstance();
    private final ServerTickLoopPlanner serverTickLoopPlanner = ServerTickLoopPlanner.getInstance();
    private final ServerTickTimingPolicy serverTickTimingPolicy = ServerTickTimingPolicy.getInstance();
    private final ServerChunkSaveCoordinatorSystem serverChunkSaveCoordinatorSystem = ServerChunkSaveCoordinatorSystem.getInstance();
    private final PlayerListTickRegistrationSystem playerListTickRegistrationSystem = PlayerListTickRegistrationSystem.getInstance();
    private final ModLoaderBootstrapSystem modLoaderBootstrapSystem = ModLoaderBootstrapSystem.getInstance();
    private final ServerNetworkStartupApplySystem serverNetworkStartupApplySystem = ServerNetworkStartupApplySystem.getInstance();
    private final ServerNetworkStartupSystem serverNetworkStartupSystem = ServerNetworkStartupSystem.getInstance();
    private final ServerStartupFeedbackSystem serverStartupFeedbackSystem = ServerStartupFeedbackSystem.getInstance();
    private final ServerStopLifecycleSystem serverStopLifecycleSystem = ServerStopLifecycleSystem.getInstance();
    private final ServerMainTickSystem serverMainTickSystem = ServerMainTickSystem.getInstance();
    private final ServerRunLoopSystem serverRunLoopSystem = ServerRunLoopSystem.getInstance();
    private final ServerRunFailureRecoverySystem serverRunFailureRecoverySystem = ServerRunFailureRecoverySystem.getInstance();
    private final ServerRunTerminationSystem serverRunTerminationSystem = ServerRunTerminationSystem.getInstance();
    private final ServerWorldBootstrapProgressSystem serverWorldBootstrapProgressSystem =
            ServerWorldBootstrapProgressSystem.getInstance();
    private final WorldTickOrchestrator worldTickOrchestrator = WorldTickOrchestrator.getInstance();
    private final WorldBootstrapSystem worldBootstrapSystem = WorldBootstrapSystem.getInstance();
    private final WorldLookupSystem worldLookupSystem = WorldLookupSystem.getInstance();
    private final ServerNetworkStartupApplySystem.StartupStateSink startupStateSink =
            new ServerNetworkStartupApplySystem.StartupStateSink() {
                @Override
                public void apply(
                        PropertyManager propertyManager,
                        boolean onlineMode,
                        boolean spawnAnimals,
                        boolean pvpMode,
                        boolean allowFlight,
                        NetworkListenThread networkListenThread
                ) {
                    MinecraftServer.this.propertyManager = propertyManager;
                    MinecraftServer.this.onlineMode = onlineMode;
                    MinecraftServer.this.spawnAnimals = spawnAnimals;
                    MinecraftServer.this.pvpMode = pvpMode;
                    MinecraftServer.this.allowFlight = allowFlight;
                    MinecraftServer.this.networkListenThread = networkListenThread;
                }
            };
    private final ServerWorldBootstrapProgressSystem.ProgressStateSink bootstrapProgressStateSink =
            new ServerWorldBootstrapProgressSystem.ProgressStateSink() {
                @Override
                public void apply(String task, int percent) {
                    MinecraftServer.this.i = task;
                    MinecraftServer.this.j = percent;
                }
            };
    private final ServerWorldBootstrapProgressSystem.CompletionActions bootstrapCompletionActions =
            new ServerWorldBootstrapProgressSystem.CompletionActions() {
                @Override
                public void resetProgress() {
                    MinecraftServer.this.i = null;
                    MinecraftServer.this.j = 0;
                }

                @Override
                public void enablePostWorldPlugins() {
                    MinecraftServer.this.server.enablePlugins(PluginLoadOrder.POSTWORLD);
                }
            };
    private final ServerRunFailureRecoverySystem.FailureActions runFailureActions =
            new ServerRunFailureRecoverySystem.FailureActions() {
                @Override
                public void drainCommandsUntilStopped() {
                    MinecraftServer.this.drainCommandsUntilStopped();
                }
            };
    private final ServerRunTerminationSystem.TerminationActions runTerminationActions =
            new ServerRunTerminationSystem.TerminationActions() {
                @Override
                public void stopServer() {
                    MinecraftServer.this.stop();
                }

                @Override
                public void markServerStopped() {
                    MinecraftServer.this.isStopped = true;
                }

                @Override
                public void exitProcess(int statusCode) {
                    System.exit(statusCode);
                }
            };
//    private PoseidonVersionChecker poseidonVersionChecker;
    //Poseidon End

    public MinecraftServer(OptionSet options) { // CraftBukkit - adds argument OptionSet
        new ThreadSleepForever(this);

        // CraftBukkit start
        this.options = options;
        try {
            this.reader = new ConsoleReader();
        } catch (IOException ex) {
            Logger.getLogger(MinecraftServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(this));
        // CraftBukkit end

        ServerRuntimeLifecycle.getInstance().onServerConstructed(this);
    }

    private boolean init() throws UnknownHostException { // CraftBukkit - added throws UnknownHostException
        this.consoleCommandHandler = new ConsoleCommandHandler(this);
        ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);

        threadcommandreader.setDaemon(true);
        threadcommandreader.start();
        ConsoleLogManager.init(this); // CraftBukkit

        // CraftBukkit start
        System.setOut(new PrintStream(new LoggerOutputStream(log, Level.INFO), true));
        System.setErr(new PrintStream(new LoggerOutputStream(log, Level.SEVERE), true));
        // CraftBukkit end

        serverStartupFeedbackSystem.applyDebugConfigIfRequested(options, log);

        modLoaderSupport = PoseidonConfig.getInstance().getBoolean("settings.support.modloader.enable", false);
        if (!modLoaderBootstrapSystem.initializeIfEnabled(
                modLoaderSupport,
                log,
                new ModLoaderBootstrapSystem.ModLoaderHooks() {
                    @Override
                    public boolean isModLoaderPresent() {
                        return modLoaderBootstrapSystem.isModLoaderClassPresent();
                    }

                    @Override
                    public void initializeModLoader() {
                        net.minecraft.server.ModLoader.Init(MinecraftServer.this);
                    }
                }
        )) {
            return false;
        }

        log.info("Starting minecraft server version Beta 1.7.3");
        serverStartupFeedbackSystem.warnLowMemoryIfNeeded(Runtime.getRuntime().maxMemory(), serverBootstrapPolicy, log);

        ServerNetworkStartupSystem.StartupResult startupResult = serverNetworkStartupSystem.initializeNetwork(
                this,
                this.options,
                serverBootstrapPolicy,
                log
        );
        if (!startupResult.isSuccessful()) {
            return false;
        }
        serverNetworkStartupApplySystem.applyStartupResult(startupResult, this.startupStateSink);

        this.serverConfigurationManager = new ServerConfigurationManager(this);
        // CraftBukkit - removed trackers
        long j = System.nanoTime();
        String s1 = this.propertyManager.getString("level-name", "world");
        String s2 = this.propertyManager.getString("level-seed", "");
        long k = serverBootstrapPolicy.resolveLevelSeed(s2, (new Random()).nextLong());

        log.info("Preparing level \"" + s1 + "\"");
        this.a(new WorldLoaderServer(new File(".")), s1, k);

        //Project Poseidon Start
        ServerRuntimeLifecycle.getInstance().onServerInitialized();
        //Project Poseidon End

        // CraftBukkit start
        serverStartupFeedbackSystem.logStartupCompleted(j, log);
        serverStartupFeedbackSystem.startDailyLogRotatorIfEnabled();
        serverStartupFeedbackSystem.migrateLegacySpawnProtectionIfNeeded(serverBootstrapPolicy, this.propertyManager, this.server, log);
        return true;
    }

    private void a(Convertable convertable, String s, long i) {
        worldBootstrapSystem.bootstrapWorlds(
                this,
                convertable,
                s,
                i,
                log,
                new WorldBootstrapSystem.ProgressReporter() {
                    @Override
                    public void report(String task, int percent) {
                        MinecraftServer.this.a(task, percent);
                    }
                },
                new WorldBootstrapSystem.CompletionHook() {
                    @Override
                    public void onBootstrapComplete() {
                        MinecraftServer.this.e();
                    }
                }
        );
    }

    private void a(String s, int i) {
        serverWorldBootstrapProgressSystem.applyProgress(s, i, log, this.bootstrapProgressStateSink);
    }

    private void e() {
        serverWorldBootstrapProgressSystem.completeBootstrap(this.bootstrapCompletionActions);
    }

    void saveChunks() { // CraftBukkit - private -> default
        serverChunkSaveCoordinatorSystem.saveChunks(
                this.worlds,
                this.server,
                new ServerChunkSaveCoordinatorSystem.SavePlayersAction() {
                    @Override
                    public void savePlayers() {
                        MinecraftServer.this.serverConfigurationManager.savePlayers();
                    }
                },
                log
        );
    }

    public synchronized void stop() { // CraftBukkit - private -> public
        if (this.isStopped) {
            return;
        }
        this.isStopped = true;
        this.isRunning = false;

        serverStopLifecycleSystem.stopServer(
                this,
                log,
                new ServerStopLifecycleSystem.SaveChunksAction() {
                    @Override
                    public void saveChunks() {
                        MinecraftServer.this.saveChunks();
                    }
                }
        );
    }

    public void a() {
        this.isRunning = false;
    }

    public void run() {
        try {
            if (this.init()) {
                serverRunLoopSystem.executeLoop(
                        modLoaderSupport,
                        serverTickTimingPolicy,
                        serverTickLoopPlanner,
                        new ServerRunLoopSystem.LoopHooks() {
                            @Override
                            public boolean isServerRunning() {
                                return MinecraftServer.this.isRunning;
                            }

                            @Override
                            public boolean isEveryoneDeeplySleeping() {
                                return !MinecraftServer.this.worlds.isEmpty() && MinecraftServer.this.worlds.get(0).everyoneDeeplySleeping();
                            }

                            @Override
                            public void onModLoaderTick() {
                                net.minecraft.server.ModLoader.OnTick(MinecraftServer.this);
                            }

                            @Override
                            public void updateWatchdog() {
                                MinecraftServer.currentTick = serverTickLoopPlanner.computeCurrentTick(System.currentTimeMillis());
                                getWatchdog().tickUpdate(); // Project Poseidon
                            }

                            @Override
                            public void runMainTick() {
                                MinecraftServer.this.h();
                            }
                        },
                        log
                );
            } else {
                serverRunFailureRecoverySystem.executeOnInitFailure(this.runFailureActions);
            }
        } catch (Throwable throwable) {
            serverRunFailureRecoverySystem.executeOnException(throwable, log, this.runFailureActions);
        } finally {
            serverRunTerminationSystem.executeTermination(this.runTerminationActions);
        }
    }

    public LinkedList<Double> getTpsRecords() {
        return tickTelemetry.getTpsRecords();
    }

    private void h() {
        this.ticks = serverMainTickSystem.executeMainTick(
                this,
                this.ticks,
                trackerList,
                this.playerListTickBoxes,
                tickTelemetry,
                worldTickOrchestrator,
                new ServerMainTickSystem.CommandDrainAction() {
                    @Override
                    public void drainCommands() {
                        MinecraftServer.this.b();
                    }
                },
                log
        );
    }

    public void issueCommand(String s, ICommandListener icommandlistener) {
        serverCommandQueueBehaviour.enqueue(this.pendingServerCommands, s, icommandlistener);
    }

    public void b() {
        commandDispatcher.drainQueuedCommands(this.pendingServerCommands, this.console, this.server);
    }

    private void drainCommandsUntilStopped() {
        serverCommandDrainLoopSystem.drainUntilStopped(new ServerCommandDrainLoopSystem.LoopControl() {
            @Override
            public boolean isRunning() {
                return MinecraftServer.this.isRunning;
            }

            @Override
            public void drainCommands() {
                MinecraftServer.this.b();
            }

            @Override
            public void handleInterruptedSleep(InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }, 10L);
    }

    public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
        playerListTickRegistrationSystem.register(this.playerListTickBoxes, iupdateplayerlistbox);
    }

    public static void main(final OptionSet options) { // CraftBukkit - replaces main(String args[])
        ServerEntryPointSystem.getInstance().launch(options, log);
    }

    public File a(String s) {
        return new File(s);
    }

    public void sendMessage(String s) {
        serverConsoleMessageLogSystem.logInfo(s, log);
    }

    public void c(String s) {
        serverConsoleMessageLogSystem.logWarning(s, log);
    }

    public String getName() {
        return "CONSOLE";
    }

    public WorldServer getWorldServer(int i) {
        return worldLookupSystem.getWorldServer(this.worlds, i);
    }

    public EntityTracker getTracker(int i) {
        return worldLookupSystem.getTracker(this.worlds, i);
    }

    public static boolean isRunning(MinecraftServer minecraftserver) {
        return minecraftserver.isRunning;
    }

    public WatchDogThread getWatchdog() {
        return Poseidon.getServer().getWatchDogThread();
    }
}
