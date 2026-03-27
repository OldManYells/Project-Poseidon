package net.minecraft.server;

import com.legacyminecraft.poseidon.compat.LegacyCompatGateway;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakByWorldEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.io.InputStream;
import java.io.OutputStream;

final class PoseidonLegacyCompatGateway implements LegacyCompatGateway {
    @Override
    public Object createMovingTileEntityPiston(int movedBlockId, int movedBlockData, int facing, boolean extending, boolean renderHead) {
        return new TileEntityPiston(movedBlockId, movedBlockData, facing, extending, renderHead);
    }

    @Override
    public boolean isTileEntityPiston(Object tileEntity) {
        return tileEntity instanceof TileEntityPiston;
    }

    @Override
    public Object blockById(int blockId) {
        if (blockId < 0 || blockId >= Block.byId.length) {
            return null;
        }
        return Block.byId[blockId];
    }

    @Override
    public int[] pistonOffsetX() {
        return PistonBlockTextures.b;
    }

    @Override
    public int[] pistonOffsetY() {
        return PistonBlockTextures.c;
    }

    @Override
    public int[] pistonOffsetZ() {
        return PistonBlockTextures.d;
    }

    @Override
    public Object createPlayerInteractEntityEvent(Object bukkitPlayer, Object bukkitEntity) {
        return new PlayerInteractEntityEvent((Player) bukkitPlayer, (Entity) bukkitEntity);
    }

    @Override
    public Object createPlayerAnimationEvent(Object bukkitPlayer) {
        return new PlayerAnimationEvent((Player) bukkitPlayer);
    }

    @Override
    public Object createPlayerToggleSneakEvent(Object bukkitPlayer, boolean sneaking) {
        return new PlayerToggleSneakEvent((Player) bukkitPlayer, sneaking);
    }

    @Override
    public Object createEntityTargetEvent(Object bukkitSource, Object bukkitTarget, String reasonName) {
        EntityTargetEvent.TargetReason reason = EntityTargetEvent.TargetReason.valueOf(reasonName);
        return new EntityTargetEvent((Entity) bukkitSource, (Entity) bukkitTarget, reason);
    }

    @Override
    public boolean isEventCancelled(Object event) {
        return event instanceof Cancellable && ((Cancellable) event).isCancelled();
    }

    @Override
    public Object getEntityTargetEventTarget(Object event) {
        return event instanceof EntityTargetEvent ? ((EntityTargetEvent) event).getTarget() : null;
    }

    @Override
    public Object createPaintingBreakByWorldEvent(Object bukkitPainting) {
        return new PaintingBreakByWorldEvent((Painting) bukkitPainting);
    }

    @Override
    public Object createPaintingBreakByEntityEvent(Object bukkitPainting, Object bukkitAttacker) {
        return new PaintingBreakByEntityEvent((Painting) bukkitPainting, (Entity) bukkitAttacker);
    }

    @Override
    public Object paintingItemSingleton() {
        return Item.PAINTING;
    }

    @Override
    public Object createItemStackFromItem(Object item) {
        return new ItemStack((Item) item);
    }

    @Override
    public Object createEntityItem(Object world, double x, double y, double z, Object itemStack) {
        return new EntityItem((World) world, x, y, z, (ItemStack) itemStack);
    }

    @Override
    public void addEntityToWorld(Object world, Object entity) {
        ((World) world).addEntity((net.minecraft.server.Entity) entity);
    }

    @Override
    public Object resolveArtByNameOrDefault(String motive) {
        for (EnumArt art : EnumArt.values()) {
            if (art.A != null && art.A.equals(motive)) {
                return art;
            }
        }
        return EnumArt.KEBAB;
    }

    @Override
    public Object createEntityTrackerEntry(Object entity, int trackingDistance, int updateInterval, boolean moving) {
        return new EntityTrackerEntry((net.minecraft.server.Entity) entity, trackingDistance, updateInterval, moving);
    }

    @Override
    public boolean isEntityKind(Object entity, String kind) {
        if ("PLAYER".equals(kind)) return entity instanceof EntityPlayer;
        if ("FISH".equals(kind)) return entity instanceof EntityFish;
        if ("ARROW".equals(kind)) return entity instanceof EntityArrow;
        if ("FIREBALL".equals(kind)) return entity instanceof EntityFireball;
        if ("SNOWBALL".equals(kind)) return entity instanceof EntitySnowball;
        if ("EGG".equals(kind)) return entity instanceof EntityEgg;
        if ("ITEM".equals(kind)) return entity instanceof EntityItem;
        if ("MINECART".equals(kind)) return entity instanceof EntityMinecart;
        if ("BOAT".equals(kind)) return entity instanceof EntityBoat;
        if ("SQUID".equals(kind)) return entity instanceof EntitySquid;
        if ("IANIMAL".equals(kind)) return entity instanceof IAnimal;
        if ("TNT_PRIMED".equals(kind)) return entity instanceof EntityTNTPrimed;
        if ("FALLING_SAND".equals(kind)) return entity instanceof EntityFallingSand;
        if ("PAINTING".equals(kind)) return entity instanceof EntityPainting;
        return false;
    }

    @Override
    public Object createPlayerListEntry(int hash, long key, Object value, Object next) {
        return new PlayerListEntry(hash, key, value, (PlayerListEntry) next);
    }

    @Override
    public Object createPacket130UpdateSign(int x, int y, int z, String[] lines) {
        return new Packet130UpdateSign(x, y, z, lines);
    }

    @Override
    public Object createPacket61(int i, int j, int k, int l, int i1) {
        return new Packet61(i, j, k, l, i1);
    }

    @Override
    public Object createPacket70Bed(int weatherPacketType) {
        return new Packet70Bed(weatherPacketType);
    }

    @Override
    public Object enumMovingObjectTypeTile() {
        return EnumMovingObjectType.TILE;
    }

    @Override
    public Object enumMovingObjectTypeEntity() {
        return EnumMovingObjectType.ENTITY;
    }

    @Override
    public Object createVec3(double x, double y, double z) {
        return Vec3D.create(x, y, z);
    }

    @Override
    public Object createChunkProviderServer(Object worldServer, Object chunkLoader, Object chunkProvider) {
        return new ChunkProviderServer((WorldServer) worldServer, (IChunkLoader) chunkLoader, (IChunkProvider) chunkProvider);
    }

    @Override
    public Object createChunkProviderByWorldProvider(Object world, Object worldProvider, long seed) {
        if (worldProvider instanceof WorldProviderHell) {
            return new ChunkProviderHell((World) world, seed);
        }
        if (worldProvider instanceof WorldProviderSky) {
            return new ChunkProviderSky((World) world, seed);
        }
        return new ChunkProviderGenerate((World) world, seed);
    }

    @Override
    public void writeCompressed(Object tag, OutputStream outputStream) {
        CompressedStreamTools.a((NBTTagCompound) tag, outputStream);
    }

    @Override
    public Object readCompressed(InputStream inputStream) {
        return CompressedStreamTools.a(inputStream);
    }
}
