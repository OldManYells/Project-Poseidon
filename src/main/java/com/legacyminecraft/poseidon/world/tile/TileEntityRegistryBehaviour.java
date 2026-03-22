package com.legacyminecraft.poseidon.world.tile;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

import java.util.Map;

public final class TileEntityRegistryBehaviour {
    private static final TileEntityRegistryBehaviour INSTANCE = new TileEntityRegistryBehaviour();

    private TileEntityRegistryBehaviour() {
    }

    public static TileEntityRegistryBehaviour getInstance() {
        return INSTANCE;
    }

    public void register(Map idToType, Map typeToId, Class type, String id) {
        if (typeToId.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate id: " + id);
        }

        idToType.put(id, type);
        typeToId.put(type, id);
    }

    public void readCoordinates(TileEntity tileEntity, NBTTagCompound tag) {
        tileEntity.x = tag.e("x");
        tileEntity.y = tag.e("y");
        tileEntity.z = tag.e("z");
    }

    public void writeBaseData(TileEntity tileEntity, Map typeToId, NBTTagCompound tag) {
        String id = (String) typeToId.get(tileEntity.getClass());

        if (id == null) {
            throw new RuntimeException(tileEntity.getClass() + " is missing a mapping! This is a bug!");
        }

        tag.setString("id", id);
        tag.a("x", tileEntity.x);
        tag.a("y", tileEntity.y);
        tag.a("z", tileEntity.z);
    }

    public TileEntity createFromTag(Map idToType, NBTTagCompound tag) {
        TileEntity tileEntity = null;

        try {
            Class type = (Class) idToType.get(tag.getString("id"));

            if (type != null) {
                tileEntity = (TileEntity) type.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (tileEntity != null) {
            tileEntity.a(tag);
        } else {
            System.out.println("Skipping TileEntity with id " + tag.getString("id"));
        }

        return tileEntity;
    }

    public int getBlockData(World world, int x, int y, int z) {
        return world.getData(x, y, z);
    }

    public void notifyUpdated(World world, int x, int y, int z, TileEntity tileEntity) {
        if (world != null) {
            world.b(x, y, z, tileEntity);
        }
    }

    public boolean isInvalid(boolean invalidFlag) {
        return invalidFlag;
    }

    public boolean markInvalid() {
        return true;
    }

    public boolean markValid() {
        return false;
    }
}
