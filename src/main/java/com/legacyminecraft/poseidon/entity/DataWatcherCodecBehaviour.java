package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.item.ItemStack;
import com.legacyminecraft.poseidon.packet.Packet;
import com.legacyminecraft.poseidon.world.ChunkCoordinates;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class DataWatcherCodecBehaviour {
    private static final DataWatcherCodecBehaviour INSTANCE = new DataWatcherCodecBehaviour();

    private static final int TERMINATOR = 127;

    private DataWatcherCodecBehaviour() {
    }

    public static DataWatcherCodecBehaviour getInstance() {
        return INSTANCE;
    }

    public HashMap createSupportedTypeMap() {
        HashMap typeMap = new HashMap();
        typeMap.put(Byte.class, Integer.valueOf(0));
        typeMap.put(Short.class, Integer.valueOf(1));
        typeMap.put(Integer.class, Integer.valueOf(2));
        typeMap.put(Float.class, Integer.valueOf(3));
        typeMap.put(String.class, Integer.valueOf(4));
        typeMap.put(ItemStack.class, Integer.valueOf(5));
        typeMap.put(ChunkCoordinates.class, Integer.valueOf(6));
        return typeMap;
    }

    public boolean register(Map watchedValues, Map supportedTypes, int id, Object value, boolean wasEmpty) {
        Integer typeId = (Integer) supportedTypes.get(value.getClass());

        if (typeId == null) {
            throw new IllegalArgumentException("Unknown data type: " + value.getClass());
        } else if (id > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + id + "! (Max is " + 31 + ")");
        } else if (watchedValues.containsKey(Integer.valueOf(id))) {
            throw new IllegalArgumentException("Duplicate id value for " + id + "!");
        }

        WatchableObject watchableobject = new WatchableObject(typeId.intValue(), id, value);
        watchedValues.put(Integer.valueOf(id), watchableobject);
        return false;
    }

    public byte getByte(Map watchedValues, int id) {
        return ((Byte) ((WatchableObject) watchedValues.get(Integer.valueOf(id))).b()).byteValue();
    }

    public int getInt(Map watchedValues, int id) {
        return ((Integer) ((WatchableObject) watchedValues.get(Integer.valueOf(id))).b()).intValue();
    }

    public String getString(Map watchedValues, int id) {
        return (String) ((WatchableObject) watchedValues.get(Integer.valueOf(id))).b();
    }

    public boolean watch(Map watchedValues, int id, Object value, boolean changed) {
        WatchableObject watchableobject = (WatchableObject) watchedValues.get(Integer.valueOf(id));

        if (!value.equals(watchableobject.b())) {
            watchableobject.a(value);
            watchableobject.a(true);
            return true;
        }

        return changed;
    }

    public static final class DirtyCollectResult {
        public final ArrayList dirtyEntries;
        public final boolean changed;

        public DirtyCollectResult(ArrayList dirtyEntries, boolean changed) {
            this.dirtyEntries = dirtyEntries;
            this.changed = changed;
        }
    }

    public DirtyCollectResult collectDirty(Map watchedValues, boolean changed) {
        ArrayList arraylist = null;

        if (changed) {
            Iterator iterator = watchedValues.values().iterator();

            while (iterator.hasNext()) {
                WatchableObject watchableobject = (WatchableObject) iterator.next();

                if (watchableobject.d()) {
                    watchableobject.a(false);
                    if (arraylist == null) {
                        arraylist = new ArrayList();
                    }

                    arraylist.add(watchableobject);
                }
            }
        }

        return new DirtyCollectResult(arraylist, false);
    }

    public void writeList(List list, DataOutputStream output) throws IOException {
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                this.writeEntry(output, (WatchableObject) iterator.next());
            }
        }

        output.writeByte(TERMINATOR);
    }

    public void writeAll(Map watchedValues, DataOutputStream output) throws IOException {
        Iterator iterator = watchedValues.values().iterator();
        while (iterator.hasNext()) {
            this.writeEntry(output, (WatchableObject) iterator.next());
        }

        output.writeByte(TERMINATOR);
    }

    public List readList(DataInputStream input) throws IOException {
        ArrayList arraylist = null;

        for (byte typeByte = input.readByte(); typeByte != TERMINATOR; typeByte = input.readByte()) {
            if (arraylist == null) {
                arraylist = new ArrayList();
            }

            int type = (typeByte & 224) >> 5;
            int id = typeByte & 31;
            WatchableObject watchableobject = null;

            switch (type) {
            case 0:
                watchableobject = new WatchableObject(type, id, Byte.valueOf(input.readByte()));
                break;

            case 1:
                watchableobject = new WatchableObject(type, id, Short.valueOf(input.readShort()));
                break;

            case 2:
                watchableobject = new WatchableObject(type, id, Integer.valueOf(input.readInt()));
                break;

            case 3:
                watchableobject = new WatchableObject(type, id, Float.valueOf(input.readFloat()));
                break;

            case 4:
                watchableobject = new WatchableObject(type, id, Packet.a(input, 64));
                break;

            case 5:
                short itemId = input.readShort();
                byte count = input.readByte();
                short data = input.readShort();
                watchableobject = new WatchableObject(type, id, new ItemStack(itemId, count, data));
                break;

            case 6:
                int x = input.readInt();
                int y = input.readInt();
                int z = input.readInt();
                watchableobject = new WatchableObject(type, id, new ChunkCoordinates(x, y, z));
                break;
            }

            arraylist.add(watchableobject);
        }

        return arraylist;
    }

    private void writeEntry(DataOutputStream output, WatchableObject watchableobject) throws IOException {
        int header = (watchableobject.c() << 5 | watchableobject.a() & 31) & 255;

        output.writeByte(header);
        switch (watchableobject.c()) {
        case 0:
            output.writeByte(((Byte) watchableobject.b()).byteValue());
            break;

        case 1:
            output.writeShort(((Short) watchableobject.b()).shortValue());
            break;

        case 2:
            output.writeInt(((Integer) watchableobject.b()).intValue());
            break;

        case 3:
            output.writeFloat(((Float) watchableobject.b()).floatValue());
            break;

        case 4:
            Packet.a((String) watchableobject.b(), output);
            break;

        case 5:
            ItemStack itemstack = (ItemStack) watchableobject.b();
            output.writeShort(itemstack.getItem().id);
            output.writeByte(itemstack.count);
            output.writeShort(itemstack.getData());
            break;

        case 6:
            ChunkCoordinates chunkcoordinates = (ChunkCoordinates) watchableobject.b();
            output.writeInt(chunkcoordinates.x);
            output.writeInt(chunkcoordinates.y);
            output.writeInt(chunkcoordinates.z);
            break;
        }
    }
}
