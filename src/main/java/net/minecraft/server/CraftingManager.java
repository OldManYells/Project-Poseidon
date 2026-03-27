package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.CraftingManagerBehaviour;

import java.util.ArrayList;
import java.util.List;

public class CraftingManager extends com.legacyminecraft.poseidon.inventory.CraftingManager {
    private static final CraftingManagerBehaviour CRAFTING_MANAGER_BEHAVIOUR = CraftingManagerBehaviour.getInstance();

    private static final CraftingManager a = new CraftingManager();
    private List b = new ArrayList();

    public static final CraftingManager getInstance() {
        return a;
    }

    private CraftingManager() {
        CRAFTING_MANAGER_BEHAVIOUR.bootstrapDefaultRecipes(this, this.b, new RecipeSorter(this));
    }

    public void registerShapedRecipe(com.legacyminecraft.poseidon.inventory.ItemStack itemstack, Object... aobject) {
        CRAFTING_MANAGER_BEHAVIOUR.registerShapedRecipe(this, this.b, itemstack, aobject);
    }

    public void registerShapedRecipe(ItemStack itemstack, Object... aobject) { // CraftBukkit - default -> public
        this.registerShapedRecipe(new com.legacyminecraft.poseidon.inventory.ItemStack(itemstack.id, itemstack.count, itemstack.damage), aobject);
    }

    public void registerShapelessRecipe(com.legacyminecraft.poseidon.inventory.ItemStack itemstack, Object... aobject) {
        CRAFTING_MANAGER_BEHAVIOUR.registerShapelessRecipe(this, this.b, itemstack, aobject);
    }

    public void registerShapelessRecipe(ItemStack itemstack, Object... aobject) { // CraftBukkit - default -> public
        this.registerShapelessRecipe(new com.legacyminecraft.poseidon.inventory.ItemStack(itemstack.id, itemstack.count, itemstack.damage), aobject);
    }

    public ItemStack craft(InventoryCrafting inventorycrafting) {
        return (ItemStack) CRAFTING_MANAGER_BEHAVIOUR.craft(this.b, inventorycrafting);
    }

    public List b() {
        return CRAFTING_MANAGER_BEHAVIOUR.recipes(this.b);
    }
}
