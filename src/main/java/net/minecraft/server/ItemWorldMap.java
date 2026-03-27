package net.minecraft.server;

public class ItemWorldMap extends ItemWorldMapBase {

    protected ItemWorldMap(int i) {
        super(i);
        this.c(1);
    }

    public WorldMap a(ItemStack itemstack, World world) {
        WorldMap worldmap = (WorldMap) world.a(WorldMap.class, "map_" + itemstack.getData());
        if (worldmap == null) {
            itemstack.b(world.b("map"));
            String key = "map_" + itemstack.getData();
            worldmap = new WorldMap(key);
            worldmap.b = world.worldData.c();
            worldmap.c = world.worldData.e();
            worldmap.e = 3;
            worldmap.map = (byte) world.worldProvider.dimension;
            worldmap.a();
            world.a(key, worldmap);
        }
        return worldmap;
    }

    public void a(World world, Entity entity, WorldMap worldmap) {
        // no-op; full map rendering behavior remains in legacy map tracker updates
    }

    public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        if (!world.isStatic) {
            WorldMap worldmap = this.a(itemstack, world);
            if (entity instanceof EntityHuman) {
                worldmap.a((EntityHuman) entity, itemstack);
            }
        }
    }

    public void c(ItemStack itemstack, World world, EntityHuman entityhuman) {
        itemstack.b(world.b("map"));
        String key = "map_" + itemstack.getData();
        WorldMap worldmap = new WorldMap(key);
        world.a(key, worldmap);
        worldmap.b = MathHelper.floor(entityhuman.locX);
        worldmap.c = MathHelper.floor(entityhuman.locZ);
        worldmap.e = 3;
        worldmap.map = (byte) ((WorldServer) world).dimension;
        worldmap.a();
    }

    public Packet b(ItemStack itemstack, World world, EntityHuman entityhuman) {
        byte[] bytes = this.a(itemstack, world).a(itemstack, world, entityhuman);
        return bytes == null ? null : new Packet131((short) Item.MAP.id, (short) itemstack.getData(), bytes);
    }
}
