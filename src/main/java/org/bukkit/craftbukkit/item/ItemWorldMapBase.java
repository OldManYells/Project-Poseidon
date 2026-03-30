package org.bukkit.craftbukkit.item;

import net.minecraft.server.Packet;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.craftbukkit.entity.EntityHuman;

public class ItemWorldMapBase extends Item {

    protected ItemWorldMapBase(int i) {
        super(i);
    }

    public boolean b() {
        return true;
    }

    public Packet b(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return null;
    }
}
