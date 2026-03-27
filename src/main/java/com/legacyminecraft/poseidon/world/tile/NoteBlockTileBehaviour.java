package com.legacyminecraft.poseidon.world.tile;

import java.lang.reflect.Method;

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

    public byte readNote(Object tag) {
        byte noteValue = ((Number) invoke(tag, "c", new Class<?>[]{String.class}, "note")).byteValue();
        if (noteValue < MIN_NOTE) {
            return MIN_NOTE;
        }
        if (noteValue > MAX_NOTE) {
            return MAX_NOTE;
        }
        return noteValue;
    }

    public void writeNote(Object tag, byte noteValue) {
        invoke(tag, "a", new Class<?>[]{String.class, byte.class}, "note", noteValue);
    }

    public byte incrementNote(byte noteValue) {
        return (byte) ((noteValue + 1) % NOTE_COUNT);
    }

    public boolean canPlay(Object world, int x, int y, int z) {
        return typeId(world, x, y + 1, z) == 0;
    }

    public byte resolveInstrument(Object world, int x, int y, int z) {
        int below = typeId(world, x, y - 1, z);
        if (below == 1) { // stone
            return 1;
        }
        if (below == 12) { // sand
            return 2;
        }
        if (below == 20) { // glass
            return 3;
        }
        if (below == 5) { // planks
            return 4;
        }
        return 0;
    }

    private static int typeId(Object world, int x, int y, int z) {
        return ((Number) invoke(world, "getTypeId", new Class<?>[]{int.class, int.class, int.class}, x, y, z)).intValue();
    }

    private static Object invoke(Object target, String name, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = target.getClass().getMethod(name, parameterTypes);
            return method.invoke(target, args);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to invoke " + name + " on " + target.getClass().getName(), exception);
        }
    }
}
