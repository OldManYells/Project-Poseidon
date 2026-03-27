package net.minecraft.server;

import com.legacyminecraft.compat.bukkit.WorldLightningEventBridgeBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerChunkProviderBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerEntityEntryBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerEntityIndexBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerLocalEffectBroadcastBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerNearbyPacketDispatchBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerPacketBroadcastBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerSaveLevelBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerTileEntityRangeBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerTrackerDispatchBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerWeatherBroadcastBehaviour;
import com.legacyminecraft.poseidon.world.WorldServerWeatherTransitionBehaviour;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.generator.ChunkGenerator;

import java.util.List;

// CraftBukkit start

public class WorldServer extends World implements BlockChangeDelegate {
    private static final WorldLightningEventBridgeBehaviour WORLD_LIGHTNING_EVENT_BRIDGE_BEHAVIOUR = WorldLightningEventBridgeBehaviour.getInstance();
    private static final WorldServerBehaviour WORLD_SERVER_BEHAVIOUR = WorldServerBehaviour.getInstance();
    private static final WorldServerChunkProviderBehaviour WORLD_SERVER_CHUNK_PROVIDER_BEHAVIOUR = WorldServerChunkProviderBehaviour.getInstance();
    private static final WorldServerEntityEntryBehaviour WORLD_SERVER_ENTITY_ENTRY_BEHAVIOUR = WorldServerEntityEntryBehaviour.getInstance();
    private static final WorldServerEntityIndexBehaviour WORLD_SERVER_ENTITY_INDEX_BEHAVIOUR = WorldServerEntityIndexBehaviour.getInstance();
    private static final WorldServerLocalEffectBroadcastBehaviour WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR = WorldServerLocalEffectBroadcastBehaviour.getInstance();
    private static final WorldServerNearbyPacketDispatchBehaviour WORLD_SERVER_NEARBY_PACKET_DISPATCH_BEHAVIOUR = WorldServerNearbyPacketDispatchBehaviour.getInstance();
    private static final WorldServerPacketBroadcastBehaviour WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR = WorldServerPacketBroadcastBehaviour.getInstance();
    private static final WorldServerSaveLevelBehaviour WORLD_SERVER_SAVE_LEVEL_BEHAVIOUR = WorldServerSaveLevelBehaviour.getInstance();
    private static final WorldServerTileEntityRangeBehaviour WORLD_SERVER_TILE_ENTITY_RANGE_BEHAVIOUR = WorldServerTileEntityRangeBehaviour.getInstance();
    private static final WorldServerTrackerDispatchBehaviour WORLD_SERVER_TRACKER_DISPATCH_BEHAVIOUR = WorldServerTrackerDispatchBehaviour.getInstance();
    private static final WorldServerWeatherBroadcastBehaviour WORLD_SERVER_WEATHER_BROADCAST_BEHAVIOUR = WorldServerWeatherBroadcastBehaviour.getInstance();
    private static final WorldServerWeatherTransitionBehaviour WORLD_SERVER_WEATHER_TRANSITION_BEHAVIOUR = WorldServerWeatherTransitionBehaviour.getInstance();
    // CraftBukkit end

    public ChunkProviderServer chunkProviderServer;
    public boolean weirdIsOpCache = false;
    public boolean canSave;
    public final MinecraftServer server; // CraftBukkit - private -> public final
    private EntityList G = new EntityList();

    // CraftBukkit start - change signature
    public WorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, long j, org.bukkit.World.Environment env, ChunkGenerator gen) {
        super(idatamanager, s, j, WorldProvider.byDimension(env.getId()), gen, env);
        this.server = minecraftserver;

        this.dimension = i;
        this.pvpMode = minecraftserver.pvpMode;
        this.manager = new PlayerManager(minecraftserver, this.dimension, minecraftserver.propertyManager.getInt("view-distance", 10));
    }

    public final int dimension;
    public EntityTracker tracker;
    public PlayerManager manager;
    // CraftBukkit end

    public void entityJoinedWorld(Entity entity, boolean flag) {
        /* CraftBukkit start - We prevent spawning in general, so this butchering is not needed
        if (!this.server.spawnAnimals && (entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal)) {
            entity.die();
        }
        // CraftBukkit end */

        if (WORLD_SERVER_ENTITY_ENTRY_BEHAVIOUR.shouldEnterWorld(bridge(entity))) {
            super.entityJoinedWorld(entity, flag);
        }
    }

    public void vehicleEnteredWorld(Entity entity, boolean flag) {
        super.entityJoinedWorld(entity, flag);
    }

    protected IChunkProvider b() {
        IChunkLoader ichunkloader = this.w.a(this.worldProvider);

        // CraftBukkit start
        this.chunkProviderServer = bridge(
                WORLD_SERVER_CHUNK_PROVIDER_BEHAVIOUR.createChunkProvider(
                        bridge(this),
                        bridge(ichunkloader),
                        bridge(this.worldProvider),
                        bridge(this.generator),
                        this.getSeed()
                )
        );
        // CraftBukkit end

        return this.chunkProviderServer;
    }

    public List getTileEntities(int i, int j, int k, int l, int i1, int j1) {
        return WORLD_SERVER_TILE_ENTITY_RANGE_BEHAVIOUR.collectInRange(this.c, i, j, k, l, i1, j1);
    }

    public boolean a(EntityHuman entityhuman, int i, int j, int k) {
        // CraftBukkit - Configurable spawn protection
        return WORLD_SERVER_BEHAVIOUR.canBypassSpawnProtection(
                this.worldData.c(),
                this.worldData.e(),
                i,
                k,
                this.getServer().getSpawnRadius(),
                this.server.serverConfigurationManager.isOp(entityhuman.name)
        );
    }

    protected void c(Entity entity) {
        super.c(entity);
        WORLD_SERVER_ENTITY_INDEX_BEHAVIOUR.indexEntity(bridge(this.G), bridge(entity));
    }

    protected void d(Entity entity) {
        super.d(entity);
        WORLD_SERVER_ENTITY_INDEX_BEHAVIOUR.unindexEntity(bridge(this.G), bridge(entity));
    }

    public Entity getEntity(int i) {
        return bridge(WORLD_SERVER_ENTITY_INDEX_BEHAVIOUR.getById(bridge(this.G), i));
    }

    public boolean strikeLightning(Entity entity) {
        // CraftBukkit start
        if (WORLD_LIGHTNING_EVENT_BRIDGE_BEHAVIOUR.shouldCancelLightning(bridge(this), bridge(entity))) {
            return false;
        }

        if (super.strikeLightning(entity)) {
            WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR.broadcastLightningEffect(
                    bridge(this.server.serverConfigurationManager),
                    WORLD_SERVER_NEARBY_PACKET_DISPATCH_BEHAVIOUR,
                    WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR,
                    this.dimension,
                    bridge(entity)
            );
            // CraftBukkit end
            return true;
        } else {
            return false;
        }
    }

    public void a(Entity entity, byte b0) {
        Packet38EntityStatus packet38entitystatus = bridge(
                WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR.createEntityStatusPacket(bridge(entity), b0)
        );

        // CraftBukkit
        WORLD_SERVER_TRACKER_DISPATCH_BEHAVIOUR.sendPacketToTrackedEntity(
                bridge(this.server),
                this.dimension,
                bridge(entity),
                bridge(packet38entitystatus)
        );
    }

    //Project Poseidon Start
    public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, EntityDamageEvent.DamageCause customDamageCause) {
        Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag, customDamageCause);

        WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR.broadcastExplosionEffectIfNeeded(
                bridge(this.server.serverConfigurationManager),
                WORLD_SERVER_NEARBY_PACKET_DISPATCH_BEHAVIOUR,
                WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR,
                this.dimension,
                d0,
                d1,
                d2,
                f,
                bridge(explosion)
        );

        return explosion;
    }
    //Project Poseidon End

    public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag) {
        // CraftBukkit start
        Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag);

        /* Remove
        explosion.a = flag;
        explosion.a();
        explosion.a(false);
        */
        WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR.broadcastExplosionEffectIfNeeded(
                bridge(this.server.serverConfigurationManager),
                WORLD_SERVER_NEARBY_PACKET_DISPATCH_BEHAVIOUR,
                WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR,
                this.dimension,
                d0,
                d1,
                d2,
                f,
                bridge(explosion)
        );
        // CraftBukkit end
        return explosion;
    }

    public void playNote(int i, int j, int k, int l, int i1) {
        super.playNote(i, j, k, l, i1);
        // CraftBukkit
        WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR.broadcastNoteEffect(
                bridge(this.server.serverConfigurationManager),
                WORLD_SERVER_NEARBY_PACKET_DISPATCH_BEHAVIOUR,
                WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR,
                this.dimension,
                i,
                j,
                k,
                l,
                i1
        );
    }

    public void saveLevel() {
        WORLD_SERVER_SAVE_LEVEL_BEHAVIOUR.flushDataManager(bridge(this.w));
    }

    protected void i() {
        boolean flag = this.v();

        super.i();
        // CraftBukkit start - only sending weather packets to those affected
        WORLD_SERVER_WEATHER_TRANSITION_BEHAVIOUR.broadcastWeatherTransitionIfNeeded(
                WORLD_SERVER_BEHAVIOUR,
                WORLD_SERVER_WEATHER_BROADCAST_BEHAVIOUR,
                this.players,
                bridge(this),
                flag,
                this.v()
        );
        // CraftBukkit end
    }

    @SuppressWarnings("unchecked")
    private static <T> T bridge(Object value) {
        return (T) value;
    }
    
    // Poseidon
    public PlayerManager getPlayerManager() {
        return this.manager;
    }
}
