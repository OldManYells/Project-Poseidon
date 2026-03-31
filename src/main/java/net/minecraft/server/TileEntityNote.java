package net.minecraft.server;

import org.bukkit.craftbukkit.world.World;

public class TileEntityNote extends TileEntity {

    public byte note = 0;
    public boolean b = false; // powered

    public TileEntityNote() {}

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setByte("note", this.note);
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.note = tag.getByte("note");
        if (this.note < 0) {
            this.note = 0;
        }

        if (this.note > 24) {
            this.note = 24;
        }
    }

    public void incrementNote() {
        this.note = (byte) ((this.note + 1) % 25);
        this.update();
    }

    public void play(World world, int x, int y, int z) {
        if (world.getMaterial(x, y + 1, z) == Material.AIR) {
            Material materialBelow = world.getMaterial(x, y - 1, z);
            byte instrument = 0;

            if (materialBelow == Material.STONE) {
                instrument = 1;
            }

            if (materialBelow == Material.SAND) {
                instrument = 2;
            }

            if (materialBelow == Material.SHATTERABLE) {
                instrument = 3;
            }

            if (materialBelow == Material.WOOD) {
                instrument = 4;
            }

            world.playNote(x, y, z, instrument, this.note);
        }
    }

    @Deprecated
    public void b(NBTTagCompound tag) {
        this.writeToNBT(tag);
    }

    @Deprecated
    public void a(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Deprecated
    public void a() {
        this.incrementNote();
    }
}
