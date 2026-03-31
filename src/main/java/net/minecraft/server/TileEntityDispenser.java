package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.item.ItemStack;

import java.util.Random;

public class TileEntityDispenser extends TileEntity implements IInventory {

    private ItemStack[] items = new ItemStack[9];
    private Random random = new Random();

    public ItemStack[] getContents() {
        return this.items;
    }

    public TileEntityDispenser() {}

    public int getSize() {
        return 9;
    }

    public ItemStack getItem(int index) {
        return this.items[index];
    }

    public ItemStack splitStack(int index, int amount) {
        if (this.items[index] != null) {
            ItemStack itemStack;

            if (this.items[index].count <= amount) {
                itemStack = this.items[index];
                this.items[index] = null;
                this.update();
                return itemStack;
            } else {
                itemStack = this.items[index].splitStack(amount);
                if (this.items[index].count == 0) {
                    this.items[index] = null;
                }

                this.update();
                return itemStack;
            }
        }

        return null;
    }

    public int findDispenseSlot() {
        int selectedIndex = -1;
        int selectionWeight = 1;

        for (int slotIndex = 0; slotIndex < this.items.length; ++slotIndex) {
            if (this.items[slotIndex] != null && this.random.nextInt(selectionWeight++) == 0) {
                if (this.items[slotIndex].count == 0) {
                    continue;
                }

                selectedIndex = slotIndex;
            }
        }

        return selectedIndex;
    }

    public ItemStack splitRandomSlot() {
        int dispenseSlot = this.findDispenseSlot();
        return dispenseSlot >= 0 ? this.splitStack(dispenseSlot, 1) : null;
    }

    public void setItem(int index, ItemStack itemStack) {
        this.items[index] = itemStack;
        if (itemStack != null && itemStack.count > this.getMaxStackSize()) {
            itemStack.count = this.getMaxStackSize();
        }

        this.update();
    }

    public String getName() {
        return "Trap";
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NBTTagList itemsTag = tag.getList("Items");
        this.items = new ItemStack[this.getSize()];

        for (int index = 0; index < itemsTag.size(); ++index) {
            NBTTagCompound itemTag = (NBTTagCompound) itemsTag.get(index);
            int slot = itemTag.getByte("Slot") & 255;

            if (slot >= 0 && slot < this.items.length) {
                this.items[slot] = new ItemStack(itemTag);
            }
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        NBTTagList itemsTag = new NBTTagList();

        for (int index = 0; index < this.items.length; ++index) {
            if (this.items[index] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) index);
                this.items[index].writeToNBT(itemTag);
                itemsTag.add(itemTag);
            }
        }

        tag.setTag("Items", itemsTag);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean a_(EntityHuman player) {
        return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : player.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }

    @Deprecated
    public ItemStack b() {
        return this.splitRandomSlot();
    }

    @Deprecated
    public void a(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Deprecated
    public void b(NBTTagCompound tag) {
        this.writeToNBT(tag);
    }
}
