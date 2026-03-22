package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.EnumArt;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakByWorldEvent;

public final class PaintingEntityBehaviour {
    private static final PaintingEntityBehaviour INSTANCE = new PaintingEntityBehaviour();
    private static final int STABILITY_CHECK_INTERVAL = 100;

    private PaintingEntityBehaviour() {
    }

    public static PaintingEntityBehaviour getInstance() {
        return INSTANCE;
    }

    public int tickAndMaybeResetCounter(int currentCounter) {
        int updatedCounter = currentCounter + 1;
        if (updatedCounter == STABILITY_CHECK_INTERVAL) {
            return 0;
        }
        return updatedCounter;
    }

    public boolean shouldRunStabilityCheck(int previousCounter, int updatedCounter, boolean worldStatic) {
        return previousCounter + 1 == STABILITY_CHECK_INTERVAL && !worldStatic;
    }

    public boolean breakFromWorldIfUnstable(EntityPainting painting) {
        PaintingBreakByWorldEvent event = new PaintingBreakByWorldEvent((org.bukkit.entity.Painting) painting.getBukkitEntity());
        painting.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        dropPaintingItem(painting);
        return true;
    }

    public boolean breakFromEntity(EntityPainting painting, Entity attacker) {
        PaintingBreakByEntityEvent event = new PaintingBreakByEntityEvent((org.bukkit.entity.Painting) painting.getBukkitEntity(), attacker == null ? null : attacker.getBukkitEntity());
        painting.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        dropPaintingItem(painting);
        return true;
    }

    public boolean shouldBreakFromMotion(boolean worldStatic, double deltaX, double deltaY, double deltaZ) {
        return !worldStatic && deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 0.0D;
    }

    public void dropPaintingItem(EntityPainting painting) {
        painting.die();
        painting.world.addEntity(new EntityItem(painting.world, painting.locX, painting.locY, painting.locZ, new ItemStack(Item.PAINTING)));
    }

    public void writeNbt(NBTTagCompound nbt, int direction, EnumArt art, int tileX, int tileY, int tileZ) {
        nbt.a("Dir", (byte) direction);
        nbt.setString("Motive", art.A);
        nbt.a("TileX", tileX);
        nbt.a("TileY", tileY);
        nbt.a("TileZ", tileZ);
    }

    public LoadedState readNbt(NBTTagCompound nbt) {
        int direction = nbt.c("Dir");
        int tileX = nbt.e("TileX");
        int tileY = nbt.e("TileY");
        int tileZ = nbt.e("TileZ");
        String motive = nbt.getString("Motive");
        EnumArt art = resolveArt(motive);
        return new LoadedState(direction, tileX, tileY, tileZ, art);
    }

    private EnumArt resolveArt(String motive) {
        EnumArt[] values = EnumArt.values();
        for (int idx = 0; idx < values.length; ++idx) {
            EnumArt art = values[idx];
            if (art.A.equals(motive)) {
                return art;
            }
        }
        return EnumArt.KEBAB;
    }

    public static final class LoadedState {
        public final int direction;
        public final int tileX;
        public final int tileY;
        public final int tileZ;
        public final EnumArt art;

        public LoadedState(int direction, int tileX, int tileY, int tileZ, EnumArt art) {
            this.direction = direction;
            this.tileX = tileX;
            this.tileY = tileY;
            this.tileZ = tileZ;
            this.art = art;
        }
    }
}
