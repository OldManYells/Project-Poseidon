package com.legacyminecraft.poseidon.world.tile;

import net.minecraft.server.Material;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public final class NoteBlockTileBehaviour {
    private static final NoteBlockTileBehaviour INSTANCE = new NoteBlockTileBehaviour();
    private static final int MIN_NOTE = 0;
    private static final int MAX_NOTE = 24;
    private static final int NOTE_COUNT = 25;

    private NoteBlockTileBehaviour() {
    }

    public static NoteBlockTileBehaviour getInstance() {
        return INSTANCE;
    }

    public byte readNote(NBTTagCompound tag) {
        byte noteValue = tag.c("note");
        if (noteValue < MIN_NOTE) {
            return MIN_NOTE;
        }
        if (noteValue > MAX_NOTE) {
            return MAX_NOTE;
        }
        return noteValue;
    }

    public void writeNote(NBTTagCompound tag, byte noteValue) {
        tag.a("note", noteValue);
    }

    public byte incrementNote(byte noteValue) {
        return (byte) ((noteValue + 1) % NOTE_COUNT);
    }

    public boolean canPlay(World world, int x, int y, int z) {
        return world.getMaterial(x, y + 1, z) == Material.AIR;
    }

    public byte resolveInstrument(World world, int x, int y, int z) {
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

        return instrument;
    }
}
