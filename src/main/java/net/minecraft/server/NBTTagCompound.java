package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTTagCompound extends NBTBase {

    private Map tagMap = new HashMap();

    public NBTTagCompound() {}

    protected void writeTagContents(DataOutput output) throws IOException {
        Iterator iterator = this.tagMap.values().iterator();

        while (iterator.hasNext()) {
            NBTBase tag = (NBTBase) iterator.next();
            NBTBase.writeNamedTag(tag, output);
        }

        output.writeByte(0);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.tagMap.clear();

        NBTBase tag;
        while ((tag = NBTBase.readNamedTag(input)).getTypeId() != 0) {
            this.tagMap.put(tag.getName(), tag);
        }
    }

    public byte getTypeId() {
        return (byte) 10;
    }

    public Collection getTags() {
        return this.tagMap.values();
    }

    public void setTag(String key, NBTBase tag) {
        this.tagMap.put(key, tag.setName(key));
    }

    public void setByte(String key, byte value) {
        this.tagMap.put(key, (new NBTTagByte(value)).setName(key));
    }

    public void setShort(String key, short value) {
        this.tagMap.put(key, (new NBTTagShort(value)).setName(key));
    }

    public void setInt(String key, int value) {
        this.tagMap.put(key, (new NBTTagInt(value)).setName(key));
    }

    public void setLong(String key, long value) {
        this.tagMap.put(key, (new NBTTagLong(value)).setName(key));
    }

    public void setFloat(String key, float value) {
        this.tagMap.put(key, (new NBTTagFloat(value)).setName(key));
    }

    public void setDouble(String key, double value) {
        this.tagMap.put(key, (new NBTTagDouble(value)).setName(key));
    }

    public void setString(String key, String value) {
        this.tagMap.put(key, (new NBTTagString(value)).setName(key));
    }

    public void setByteArray(String key, byte[] value) {
        this.tagMap.put(key, (new NBTTagByteArray(value)).setName(key));
    }

    public void setCompound(String key, NBTTagCompound value) {
        this.tagMap.put(key, value.setName(key));
    }

    public void setBoolean(String key, boolean value) {
        this.setByte(key, (byte) (value ? 1 : 0));
    }

    public boolean hasKey(String key) {
        return this.tagMap.containsKey(key);
    }

    public byte getByte(String key) {
        return !this.tagMap.containsKey(key) ? 0 : ((NBTTagByte) this.tagMap.get(key)).a;
    }

    public short getShort(String key) {
        return !this.tagMap.containsKey(key) ? 0 : ((NBTTagShort) this.tagMap.get(key)).a;
    }

    public int getInt(String key) {
        return !this.tagMap.containsKey(key) ? 0 : ((NBTTagInt) this.tagMap.get(key)).a;
    }

    public long getLong(String key) {
        return !this.tagMap.containsKey(key) ? 0L : ((NBTTagLong) this.tagMap.get(key)).a;
    }

    public float getFloat(String key) {
        return !this.tagMap.containsKey(key) ? 0.0F : ((NBTTagFloat) this.tagMap.get(key)).a;
    }

    public double getDouble(String key) {
        return !this.tagMap.containsKey(key) ? 0.0D : ((NBTTagDouble) this.tagMap.get(key)).a;
    }

    public String getString(String key) {
        return !this.tagMap.containsKey(key) ? "" : ((NBTTagString) this.tagMap.get(key)).a;
    }

    public byte[] getByteArray(String key) {
        return !this.tagMap.containsKey(key) ? new byte[0] : ((NBTTagByteArray) this.tagMap.get(key)).a;
    }

    public NBTTagCompound getCompound(String key) {
        return !this.tagMap.containsKey(key) ? new NBTTagCompound() : (NBTTagCompound) this.tagMap.get(key);
    }

    public NBTTagList getList(String key) {
        return !this.tagMap.containsKey(key) ? new NBTTagList() : (NBTTagList) this.tagMap.get(key);
    }

    public boolean getBoolean(String key) {
        return this.getByte(key) != 0;
    }

    public String toString() {
        return "" + this.tagMap.size() + " entries";
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    @Deprecated
    public Collection c() {
        return this.getTags();
    }

    @Deprecated
    public void a(String key, NBTBase tag) {
        this.setTag(key, tag);
    }

    @Deprecated
    public void a(String key, byte value) {
        this.setByte(key, value);
    }

    @Deprecated
    public void a(String key, short value) {
        this.setShort(key, value);
    }

    @Deprecated
    public void a(String key, int value) {
        this.setInt(key, value);
    }

    @Deprecated
    public void a(String key, float value) {
        this.setFloat(key, value);
    }

    @Deprecated
    public void a(String key, double value) {
        this.setDouble(key, value);
    }

    @Deprecated
    public void a(String key, byte[] value) {
        this.setByteArray(key, value);
    }

    @Deprecated
    public void a(String key, NBTTagCompound value) {
        this.setCompound(key, value);
    }

    @Deprecated
    public void a(String key, boolean value) {
        this.setBoolean(key, value);
    }

    @Deprecated
    public byte c(String key) {
        return this.getByte(key);
    }

    @Deprecated
    public short d(String key) {
        return this.getShort(key);
    }

    @Deprecated
    public int e(String key) {
        return this.getInt(key);
    }

    @Deprecated
    public float g(String key) {
        return this.getFloat(key);
    }

    @Deprecated
    public double h(String key) {
        return this.getDouble(key);
    }

    @Deprecated
    public byte[] j(String key) {
        return this.getByteArray(key);
    }

    @Deprecated
    public NBTTagCompound k(String key) {
        return this.getCompound(key);
    }

    @Deprecated
    public NBTTagList l(String key) {
        return this.getList(key);
    }

    @Deprecated
    public boolean m(String key) {
        return this.getBoolean(key);
    }
}
