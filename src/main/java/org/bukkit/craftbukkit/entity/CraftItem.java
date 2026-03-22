package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.ItemEntityStackBridgeBehaviour;
import net.minecraft.server.EntityItem;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class CraftItem extends CraftEntity implements Item {
    private static final ItemEntityStackBridgeBehaviour ITEM_ENTITY_STACK_BRIDGE_BEHAVIOUR =
            ItemEntityStackBridgeBehaviour.getInstance();
    private EntityItem item;

    public CraftItem(CraftServer server, EntityItem entity) {
        super(server, entity);
        this.item = entity;
    }

    public ItemStack getItemStack() {
        return ITEM_ENTITY_STACK_BRIDGE_BEHAVIOUR.toBukkitItemStack(item);
    }

    public void setItemStack(ItemStack stack) {
        ITEM_ENTITY_STACK_BRIDGE_BEHAVIOUR.applyBukkitItemStack(item, stack);
    }

    @Override
    public String toString() {
        return "CraftItem";
    }

}
