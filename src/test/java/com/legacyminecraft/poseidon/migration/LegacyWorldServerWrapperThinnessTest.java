package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldServerWrapperThinnessTest {
    private static final Path WORLD_SERVER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldServer.java");

    @Test
    public void worldServerDelegatesPolicyAndRangeChecksToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_SERVER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.compat.bukkit.WorldLightningEventBridgeBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerChunkProviderBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerEntityEntryBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerEntityIndexBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerLocalEffectBroadcastBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerNearbyPacketDispatchBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerPacketBroadcastBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerSaveLevelBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerTileEntityRangeBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerTrackerDispatchBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerWeatherBroadcastBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.WorldServerWeatherTransitionBehaviour;"));
        Assert.assertTrue(text.contains("WORLD_SERVER_BEHAVIOUR.canBypassSpawnProtection("));
        Assert.assertTrue(text.contains("this.worldData.c(),"));
        Assert.assertTrue(text.contains("this.worldData.e(),"));
        Assert.assertTrue(text.contains("this.getServer().getSpawnRadius(),"));
        Assert.assertTrue(text.contains("this.server.serverConfigurationManager.isOp(entityhuman.name)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_WEATHER_TRANSITION_BEHAVIOUR.broadcastWeatherTransitionIfNeeded("));
        Assert.assertTrue(text.contains("WORLD_SERVER_CHUNK_PROVIDER_BEHAVIOUR.createChunkProvider(this, ichunkloader, this.worldProvider, this.generator, this.getSeed())"));
        Assert.assertTrue(text.contains("WORLD_SERVER_TILE_ENTITY_RANGE_BEHAVIOUR.collectInRange(this.c, i, j, k, l, i1, j1)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_ENTITY_ENTRY_BEHAVIOUR.shouldEnterWorld(entity)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_ENTITY_INDEX_BEHAVIOUR.indexEntity(this.G, entity)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_ENTITY_INDEX_BEHAVIOUR.unindexEntity(this.G, entity)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_ENTITY_INDEX_BEHAVIOUR.getById(this.G, i)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR.broadcastLightningEffect("));
        Assert.assertTrue(text.contains("WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR.broadcastExplosionEffectIfNeeded("));
        Assert.assertTrue(text.contains("WORLD_SERVER_LOCAL_EFFECT_BROADCAST_BEHAVIOUR.broadcastNoteEffect("));
        Assert.assertTrue(text.contains("WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR.createEntityStatusPacket(entity, b0)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_NEARBY_PACKET_DISPATCH_BEHAVIOUR,"));
        Assert.assertTrue(text.contains("WORLD_SERVER_SAVE_LEVEL_BEHAVIOUR.flushDataManager(this.w)"));
        Assert.assertTrue(text.contains("WORLD_SERVER_TRACKER_DISPATCH_BEHAVIOUR.sendPacketToTrackedEntity(this.server, this.dimension, entity, packet38entitystatus)"));
        Assert.assertTrue(text.contains("WORLD_LIGHTNING_EVENT_BRIDGE_BEHAVIOUR.shouldCancelLightning(this, entity)"));
        Assert.assertFalse(text.contains("if (tileentity.x >= i && tileentity.y >= j && tileentity.z >= k && tileentity.x < l && tileentity.y < i1 && tileentity.z < j1)"));
        Assert.assertFalse(text.contains("if (flag != this.v())"));
        Assert.assertFalse(text.contains("new Packet70Bed(flag ? 2 : 1)"));
        Assert.assertFalse(text.contains("int i1 = WORLD_SERVER_BEHAVIOUR.maxSpawnAxisDistance(this.worldData.c(), this.worldData.e(), i, k);"));
        Assert.assertFalse(text.contains("new ChunkProviderServer(this, ichunkloader, gen)"));
        Assert.assertFalse(text.contains("gen = new CustomChunkGenerator(this, this.getSeed(), this.generator);"));
        Assert.assertFalse(text.contains("LightningStrikeEvent lightning = new LightningStrikeEvent(this.getWorld(), (org.bukkit.entity.LightningStrike) entity.getBukkitEntity());"));
        Assert.assertFalse(text.contains("new Packet71Weather(entity)"));
        Assert.assertFalse(text.contains("new Packet60Explosion(d0, d1, d2, f, explosion.blocks)"));
        Assert.assertFalse(text.contains("new Packet54PlayNoteBlock(i, j, k, l, i1)"));
        Assert.assertFalse(text.contains("new Packet38EntityStatus(entity.id, b0)"));
        Assert.assertFalse(text.contains("if (entity.passenger == null || !(entity.passenger instanceof EntityHuman))"));
        Assert.assertFalse(text.contains("if (((EntityPlayer) this.players.get(i)).world == this)"));
        Assert.assertFalse(text.contains("this.G.a(entity.id, entity);"));
        Assert.assertFalse(text.contains("this.G.d(entity.id);"));
        Assert.assertFalse(text.contains("return (Entity) this.G.a(i);"));
        Assert.assertFalse(text.contains("this.w.e();"));
        Assert.assertFalse(text.contains("if (!WORLD_SERVER_PACKET_BROADCAST_BEHAVIOUR.shouldBroadcastExplosion(explosion)) {"));
        Assert.assertFalse(text.contains("this.server.serverConfigurationManager.sendPacketNearby("));
        Assert.assertFalse(text.contains("this.server.getTracker(this.dimension).sendPacketToEntity(entity, packet38entitystatus);"));
    }
}
