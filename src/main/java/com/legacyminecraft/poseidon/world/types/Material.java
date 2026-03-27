package com.legacyminecraft.poseidon.world.types;

/**
 * World-type local material alias.
 */
public final class Material {
    public static final Material AIR = new Material(com.legacyminecraft.poseidon.world.Material.AIR);
    public static final Material WATER = new Material(com.legacyminecraft.poseidon.world.Material.WATER);
    public static final Material STATIONARY_WATER = new Material(com.legacyminecraft.poseidon.world.Material.STATIONARY_WATER);
    public static final Material LAVA = new Material(com.legacyminecraft.poseidon.world.Material.LAVA);
    public static final Material STATIONARY_LAVA = new Material(com.legacyminecraft.poseidon.world.Material.STATIONARY_LAVA);
    public static final Material STONE = new Material(com.legacyminecraft.poseidon.world.Material.STONE);
    public static final Material SAND = new Material(com.legacyminecraft.poseidon.world.Material.SAND);
    public static final Material SHATTERABLE = new Material(com.legacyminecraft.poseidon.world.Material.SHATTERABLE);
    public static final Material WOOD = new Material(com.legacyminecraft.poseidon.world.Material.WOOD);
    public static final Material DIRT = new Material(com.legacyminecraft.poseidon.world.Material.DIRT);

    private final com.legacyminecraft.poseidon.world.Material delegate;

    private Material(com.legacyminecraft.poseidon.world.Material delegate) {
        this.delegate = delegate;
    }

    public boolean isBuildable() {
        return delegate.isBuildable();
    }

    public boolean isSolid() {
        return delegate.isSolid();
    }

    public com.legacyminecraft.poseidon.world.Material asWorldMaterial() {
        return delegate;
    }
}
