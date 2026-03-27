package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat player-input packet scaffold.
 */
public class Packet27 extends Packet {
    private float primaryX;
    private float primaryY;
    private float secondaryX;
    private float secondaryY;
    private boolean primaryFlag;
    private boolean secondaryFlag;

    public Packet27() {
    }

    public Packet27(
            float primaryX,
            float primaryY,
            float secondaryX,
            float secondaryY,
            boolean primaryFlag,
            boolean secondaryFlag
    ) {
        this.primaryX = primaryX;
        this.primaryY = primaryY;
        this.secondaryX = secondaryX;
        this.secondaryY = secondaryY;
        this.primaryFlag = primaryFlag;
        this.secondaryFlag = secondaryFlag;
    }

    public float c() {
        return primaryX;
    }

    public float d() {
        return secondaryX;
    }

    public float e() {
        return primaryY;
    }

    public float f() {
        return secondaryY;
    }

    public boolean g() {
        return primaryFlag;
    }

    public boolean h() {
        return secondaryFlag;
    }
}

