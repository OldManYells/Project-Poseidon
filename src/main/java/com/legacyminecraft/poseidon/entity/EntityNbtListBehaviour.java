package com.legacyminecraft.poseidon.entity;


/**
 * Canonical behaviour for building entity NBT numeric list tags.
 */
public final class EntityNbtListBehaviour {
    private static final EntityNbtListBehaviour INSTANCE = new EntityNbtListBehaviour();

    private EntityNbtListBehaviour() {
    }

    public static EntityNbtListBehaviour getInstance() {
        return INSTANCE;
    }

    public NBTTagList buildDoubleList(double... values) {
        NBTTagList listTag = new NBTTagList();
        for (double value : values) {
            listTag.a((NBTBase) (new NBTTagDouble(value)));
        }

        return listTag;
    }

    public NBTTagList buildFloatList(float... values) {
        NBTTagList listTag = new NBTTagList();
        for (float value : values) {
            listTag.a((NBTBase) (new NBTTagFloat(value)));
        }

        return listTag;
    }
}
