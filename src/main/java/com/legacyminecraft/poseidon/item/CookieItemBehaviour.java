package com.legacyminecraft.poseidon.item;

public final class CookieItemBehaviour {
    private static final CookieItemBehaviour INSTANCE = new CookieItemBehaviour();

    private CookieItemBehaviour() {
    }

    public static CookieItemBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveMaxStackSize(int configuredMaxStackSize) {
        return configuredMaxStackSize;
    }
}
