package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.block.Block;
import com.legacyminecraft.poseidon.block.MaterialMapColor;
import com.legacyminecraft.poseidon.entity.EntityHuman;
import com.legacyminecraft.poseidon.packet.Packet;
import com.legacyminecraft.poseidon.packet.Packet131;
import com.legacyminecraft.poseidon.world.Chunk;
import com.legacyminecraft.poseidon.world.Entity;
import com.legacyminecraft.poseidon.world.MathHelper;
import com.legacyminecraft.poseidon.world.World;
import com.legacyminecraft.poseidon.world.WorldMap;
import com.legacyminecraft.poseidon.world.WorldMapBase;
import com.legacyminecraft.poseidon.world.WorldServer;

public final class WorldMapItemBehaviour {
    private static final WorldMapItemBehaviour INSTANCE = new WorldMapItemBehaviour();

    private WorldMapItemBehaviour() {
    }

    public static WorldMapItemBehaviour getInstance() {
        return INSTANCE;
    }

    public WorldMap resolveMap(ItemStack itemstack, World world) {
        WorldMap worldmap = (WorldMap) world.a(WorldMap.class, "map_" + itemstack.getData());
        if (worldmap != null) {
            return worldmap;
        }
        itemstack.b(world.b("map"));
        String key = "map_" + itemstack.getData();
        worldmap = new WorldMap(key);
        worldmap.e = 3;
        worldmap.map = (byte) world.worldProvider.dimension;
        worldmap.a();
        world.a(key, (WorldMapBase) worldmap);
        return worldmap;
    }

    public void updateMapData(World world, Entity entity, WorldMap worldmap) {
        // Deferred heavy map rasterization path during migration.
    }

    public void onUpdate(ItemStack itemstack, World world, Entity entity, boolean flag) {
        if (!world.isStatic) {
            WorldMap worldmap = this.resolveMap(itemstack, world);
            if (flag) {
                this.updateMapData(world, entity, worldmap);
            }
        }
    }

    public void onCrafted(ItemStack itemstack, World world, EntityHuman entityhuman) {
        itemstack.b(world.b("map"));
        String s = "map_" + itemstack.getData();
        WorldMap worldmap = new WorldMap(s);

        world.a(s, (WorldMapBase) worldmap);
        worldmap.b = MathHelper.floor(entityhuman.locX);
        worldmap.c = MathHelper.floor(entityhuman.locZ);
        worldmap.e = 3;
        worldmap.map = (byte) ((WorldServer) world).dimension;
        worldmap.a();
    }

    public Packet createUpdatePacket(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return null;
    }
}
