package com.legacyminecraft.compat.bukkit;

/**
 * Bukkit creature type scaffold.
 */
public enum CreatureType {
    PIG("Pig"),
    COW("Cow"),
    SHEEP("Sheep"),
    CHICKEN("Chicken"),
    CREEPER("Creeper"),
    GHAST("Ghast"),
    GIANT("Giant"),
    WOLF("Wolf"),
    PIG_ZOMBIE("PigZombie"),
    SKELETON("Skeleton"),
    SLIME("Slime"),
    SPIDER("Spider"),
    SQUID("Squid"),
    ZOMBIE("Zombie"),
    MONSTER("Monster");

    private final String name;

    CreatureType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
