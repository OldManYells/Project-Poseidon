package net.minecraft.server;

import com.legacyminecraft.poseidon.item.CookieItemBehaviour;

public class ItemCookie extends ItemFood {
    private static final CookieItemBehaviour COOKIE_ITEM_BEHAVIOUR = CookieItemBehaviour.getInstance();

    public ItemCookie(int i, int j, boolean flag, int k) {
        super(i, j, flag);
        this.maxStackSize = COOKIE_ITEM_BEHAVIOUR.resolveMaxStackSize(k);
    }
}
