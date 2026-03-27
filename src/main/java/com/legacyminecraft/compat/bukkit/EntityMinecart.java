package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat minecart entity scaffold.
 */
public class EntityMinecart extends Entity implements Minecart {
    public int type;
    public int damage;
    public double maxSpeed = 0.4D;
    public boolean slowWhenEmpty = true;
    public double flyingX;
    public double flyingY;
    public double flyingZ;
    public double derailedX;
    public double derailedY;
    public double derailedZ;

    public EntityMinecart() {
    }

    public EntityMinecart(WorldServer world, double x, double y, double z, int type) {
        this.world = world;
        this.type = type;
        this.setLocation(x, y, z, 0.0F, 0.0F);
    }
}
