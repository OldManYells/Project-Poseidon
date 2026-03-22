package net.minecraft.server;

import com.legacyminecraft.poseidon.item.CoalItemBehaviour;

public class ItemCoal extends Item {
    private static final CoalItemBehaviour COAL_ITEM_BEHAVIOUR = CoalItemBehaviour.getInstance();

    public ItemCoal(int i) {
        super(i);
        this.a(COAL_ITEM_BEHAVIOUR.hasSubtypes());
        this.d(COAL_ITEM_BEHAVIOUR.defaultDataValue());
    }
}
