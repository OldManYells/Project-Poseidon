package net.minecraft.server;

import org.bukkit.craftbukkit.item.ItemStack;
import org.bukkit.craftbukkit.server.ChunkCoordinates;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataWatcher {

    private boolean blank = true;
    private static final HashMap classToIdMap = new HashMap();
    private final Map watchedObjects = new HashMap();
    private boolean objectChanged;

    public DataWatcher() {}

    public void addObject(int dataValueId, Object value) {
        Integer objectType = (Integer) classToIdMap.get(value.getClass());

        if (objectType == null) {
            throw new IllegalArgumentException("Unknown data type: " + value.getClass());
        } else if (dataValueId > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + dataValueId + "! (Max is 31)");
        } else if (this.watchedObjects.containsKey(Integer.valueOf(dataValueId))) {
            throw new IllegalArgumentException("Duplicate id value for " + dataValueId + "!");
        } else {
            WatchableObject watchableObject = new WatchableObject(objectType.intValue(), dataValueId, value);
            this.watchedObjects.put(Integer.valueOf(dataValueId), watchableObject);
            this.blank = false;
        }
    }

    public byte getByte(int dataValueId) {
        return ((Byte) ((WatchableObject) this.watchedObjects.get(Integer.valueOf(dataValueId))).getValue()).byteValue();
    }

    public int getInt(int dataValueId) {
        return ((Integer) ((WatchableObject) this.watchedObjects.get(Integer.valueOf(dataValueId))).getValue()).intValue();
    }

    public String getString(int dataValueId) {
        return (String) ((WatchableObject) this.watchedObjects.get(Integer.valueOf(dataValueId))).getValue();
    }

    public void watch(int dataValueId, Object value) {
        WatchableObject watchableObject = (WatchableObject) this.watchedObjects.get(Integer.valueOf(dataValueId));

        if (!value.equals(watchableObject.getValue())) {
            watchableObject.setValue(value);
            watchableObject.setWatched(true);
            this.objectChanged = true;
        }
    }

    public boolean hasChanged() {
        return this.objectChanged;
    }

    public ArrayList getChangedObjects() {
        ArrayList changedObjects = null;

        if (this.objectChanged) {
            Iterator iterator = this.watchedObjects.values().iterator();

            while (iterator.hasNext()) {
                WatchableObject watchableObject = (WatchableObject) iterator.next();

                if (watchableObject.isWatched()) {
                    watchableObject.setWatched(false);
                    if (changedObjects == null) {
                        changedObjects = new ArrayList();
                    }

                    changedObjects.add(watchableObject);
                }
            }
        }

        this.objectChanged = false;
        return changedObjects;
    }

    public void writeAll(DataOutputStream output) throws IOException {
        Iterator iterator = this.watchedObjects.values().iterator();

        while (iterator.hasNext()) {
            WatchableObject watchableObject = (WatchableObject) iterator.next();
            writeWatchableObject(output, watchableObject);
        }

        output.writeByte(127);
    }

    public boolean isBlank() {
        return this.blank;
    }

    public static void writeWatchableObjects(List watchableObjects, DataOutputStream output) throws IOException {
        if (watchableObjects != null) {
            Iterator iterator = watchableObjects.iterator();

            while (iterator.hasNext()) {
                WatchableObject watchableObject = (WatchableObject) iterator.next();
                writeWatchableObject(output, watchableObject);
            }
        }

        output.writeByte(127);
    }

    private static void writeWatchableObject(DataOutputStream output, WatchableObject watchableObject) throws IOException {
        int typeAndId = (watchableObject.getObjectType() << 5 | watchableObject.getDataValueId() & 31) & 255;

        output.writeByte(typeAndId);
        switch (watchableObject.getObjectType()) {
        case 0:
            output.writeByte(((Byte) watchableObject.getValue()).byteValue());
            break;
        case 1:
            output.writeShort(((Short) watchableObject.getValue()).shortValue());
            break;
        case 2:
            output.writeInt(((Integer) watchableObject.getValue()).intValue());
            break;
        case 3:
            output.writeFloat(((Float) watchableObject.getValue()).floatValue());
            break;
        case 4:
            Packet.a((String) watchableObject.getValue(), output);
            break;
        case 5:
            ItemStack itemStack = (ItemStack) watchableObject.getValue();
            output.writeShort(itemStack.getItem().id);
            output.writeByte(itemStack.count);
            output.writeShort(itemStack.getData());
            break;
        case 6:
            ChunkCoordinates chunkCoordinates = (ChunkCoordinates) watchableObject.getValue();
            output.writeInt(chunkCoordinates.x);
            output.writeInt(chunkCoordinates.y);
            output.writeInt(chunkCoordinates.z);
        }
    }

    public static List readWatchableObjects(DataInputStream input) throws IOException {
        ArrayList watchableObjects = null;

        for (byte marker = input.readByte(); marker != 127; marker = input.readByte()) {
            if (watchableObjects == null) {
                watchableObjects = new ArrayList();
            }

            int objectType = (marker & 224) >> 5;
            int dataValueId = marker & 31;
            WatchableObject watchableObject = null;

            switch (objectType) {
            case 0:
                watchableObject = new WatchableObject(objectType, dataValueId, Byte.valueOf(input.readByte()));
                break;
            case 1:
                watchableObject = new WatchableObject(objectType, dataValueId, Short.valueOf(input.readShort()));
                break;
            case 2:
                watchableObject = new WatchableObject(objectType, dataValueId, Integer.valueOf(input.readInt()));
                break;
            case 3:
                watchableObject = new WatchableObject(objectType, dataValueId, Float.valueOf(input.readFloat()));
                break;
            case 4:
                watchableObject = new WatchableObject(objectType, dataValueId, Packet.a(input, 64));
                break;
            case 5:
                short itemId = input.readShort();
                byte itemCount = input.readByte();
                short itemData = input.readShort();
                watchableObject = new WatchableObject(objectType, dataValueId, new ItemStack(itemId, itemCount, itemData));
                break;
            case 6:
                int x = input.readInt();
                int y = input.readInt();
                int z = input.readInt();
                watchableObject = new WatchableObject(objectType, dataValueId, new ChunkCoordinates(x, y, z));
            }

            watchableObjects.add(watchableObject);
        }

        return watchableObjects;
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    @Deprecated
    public void a(int dataValueId, Object value) {
        this.addObject(dataValueId, value);
    }

    @Deprecated
    public byte a(int dataValueId) {
        return this.getByte(dataValueId);
    }

    @Deprecated
    public int b(int dataValueId) {
        return this.getInt(dataValueId);
    }

    @Deprecated
    public String c(int dataValueId) {
        return this.getString(dataValueId);
    }

    @Deprecated
    public boolean a() {
        return this.hasChanged();
    }

    @Deprecated
    public static void a(List watchableObjects, DataOutputStream output) throws IOException {
        writeWatchableObjects(watchableObjects, output);
    }

    @Deprecated
    public ArrayList b() {
        return this.getChangedObjects();
    }

    @Deprecated
    public void a(DataOutputStream output) throws IOException {
        this.writeAll(output);
    }

    @Deprecated
    private static void a(DataOutputStream output, WatchableObject watchableObject) throws IOException {
        writeWatchableObject(output, watchableObject);
    }

    @Deprecated
    public static List a(DataInputStream input) throws IOException {
        return readWatchableObjects(input);
    }

    @Deprecated
    public boolean getD() {
        return this.isBlank();
    }

    static {
        classToIdMap.put(Byte.class, Integer.valueOf(0));
        classToIdMap.put(Short.class, Integer.valueOf(1));
        classToIdMap.put(Integer.class, Integer.valueOf(2));
        classToIdMap.put(Float.class, Integer.valueOf(3));
        classToIdMap.put(String.class, Integer.valueOf(4));
        classToIdMap.put(ItemStack.class, Integer.valueOf(5));
        classToIdMap.put(ChunkCoordinates.class, Integer.valueOf(6));
    }
}
