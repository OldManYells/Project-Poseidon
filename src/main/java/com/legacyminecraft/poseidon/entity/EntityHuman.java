package com.legacyminecraft.poseidon.entity;

/**
 * Canonical human-entity scaffold.
 */
public class EntityHuman extends Entity {
    public String name = "Player";
    public final Inventory inventory = new Inventory();
    public int dimension;

    public void a(Object statistic, int amount) {
    }

    public void a(boolean keepInventory, boolean updateSleepState, boolean resetTimer) {
    }

    public float f(Entity entity) {
        return (float) Math.sqrt(this.g(entity));
    }

    public static class Inventory {
        public int itemInHandIndex;
        private ItemStack inHand;

        public boolean c(ItemStack itemStack) {
            return true;
        }

        public ItemStack getItemInHand() {
            return inHand;
        }

        public void setItem(int index, ItemStack itemStack) {
            if (index == itemInHandIndex) {
                inHand = itemStack;
            }
        }
    }
}
