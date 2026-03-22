package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.CraftingManagerBehaviour;

import java.util.ArrayList;
import java.util.List;

public class CraftingManager {
    private static final CraftingManagerBehaviour CRAFTING_MANAGER_BEHAVIOUR = CraftingManagerBehaviour.getInstance();

    private static final CraftingManager a = new CraftingManager();
    private List b = new ArrayList();

    public static final CraftingManager getInstance() {
        return a;
    }

    private CraftingManager() {
        CRAFTING_MANAGER_BEHAVIOUR.bootstrapDefaultRecipes(this, this.b, new RecipeSorter(this));
    }

    public void registerShapedRecipe(ItemStack itemstack, Object... aobject) { // CraftBukkit - default -> public
        CRAFTING_MANAGER_BEHAVIOUR.registerShapedRecipe(this.b, itemstack, aobject);
    }

    public void registerShapelessRecipe(ItemStack itemstack, Object... aobject) { // CraftBukkit - default -> public
        CRAFTING_MANAGER_BEHAVIOUR.registerShapelessRecipe(this.b, itemstack, aobject);
    }

    public ItemStack craft(InventoryCrafting inventorycrafting) {
        return CRAFTING_MANAGER_BEHAVIOUR.craft(this.b, inventorycrafting);
    }

    public List b() {
        return CRAFTING_MANAGER_BEHAVIOUR.recipes(this.b);
    }
}
