package net.minecraft.server;

import org.bukkit.craftbukkit.world.World;

import java.util.HashMap;
import java.util.Map;

public class TileEntity {

    private static Map nameToClassMap = new HashMap();
    private static Map classToNameMap = new HashMap();
    public World world;
    public int x;
    public int y;
    public int z;
    protected boolean invalid;

    public TileEntity() {}

    private static void addMapping(Class tileEntityClass, String id) {
        if (classToNameMap.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate id: " + id);
        }

        nameToClassMap.put(id, tileEntityClass);
        classToNameMap.put(tileEntityClass, id);
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.x = tag.getInt("x");
        this.y = tag.getInt("y");
        this.z = tag.getInt("z");
    }

    public void writeToNBT(NBTTagCompound tag) {
        String id = (String) classToNameMap.get(this.getClass());

        if (id == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }

        tag.setString("id", id);
        tag.setInt("x", this.x);
        tag.setInt("y", this.y);
        tag.setInt("z", this.z);
    }

    public void updateEntity() {}

    public static TileEntity createAndLoadEntity(NBTTagCompound tag) {
        TileEntity tileEntity = null;

        try {
            Class tileEntityClass = (Class) nameToClassMap.get(tag.getString("id"));
            if (tileEntityClass != null) {
                tileEntity = (TileEntity) tileEntityClass.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (tileEntity != null) {
            tileEntity.readFromNBT(tag);
        } else {
            System.out.println("Skipping TileEntity with id " + tag.getString("id"));
        }

        return tileEntity;
    }

    public int getBlockMetadata() {
        return this.world.getData(this.x, this.y, this.z);
    }

    public void update() {
        if (this.world != null) {
            this.world.b(this.x, this.y, this.z, this);
        }
    }

    public Packet getUpdatePacket() {
        return null;
    }

    public boolean isInvalid() {
        return this.invalid;
    }

    public void invalidate() {
        this.invalid = true;
    }

    public void validate() {
        this.invalid = false;
    }

    @Deprecated
    private static void a(Class tileEntityClass, String id) {
        addMapping(tileEntityClass, id);
    }

    @Deprecated
    public void a(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Deprecated
    public void b(NBTTagCompound tag) {
        this.writeToNBT(tag);
    }

    @Deprecated
    public void g_() {
        this.updateEntity();
    }

    @Deprecated
    public static TileEntity c(NBTTagCompound tag) {
        return createAndLoadEntity(tag);
    }

    @Deprecated
    public int e() {
        return this.getBlockMetadata();
    }

    @Deprecated
    public Packet f() {
        return this.getUpdatePacket();
    }

    @Deprecated
    public boolean g() {
        return this.isInvalid();
    }

    @Deprecated
    public void h() {
        this.invalidate();
    }

    @Deprecated
    public void j() {
        this.validate();
    }

    static {
        addMapping(TileEntityFurnace.class, "Furnace");
        addMapping(TileEntityChest.class, "Chest");
        addMapping(TileEntityRecordPlayer.class, "RecordPlayer");
        addMapping(TileEntityDispenser.class, "Trap");
        addMapping(TileEntitySign.class, "Sign");
        addMapping(TileEntityMobSpawner.class, "MobSpawner");
        addMapping(TileEntityNote.class, "Music");
        addMapping(TileEntityPiston.class, "Piston");
    }
}
