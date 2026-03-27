package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local painting scaffold.
 */
public class EntityPainting extends Entity {
    public EnumArt art = EnumArt.KEBAB;
    public int direction;
    public int x;
    public int y;
    public int z;

    public EntityPainting() {
    }

    public EntityPainting(World world, int x, int y, int z, int direction) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
    }

    public boolean h() {
        return true;
    }
}
