package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.ItemStack;

public final class ShapedRecipeMatchingBehaviour {
    private static final ShapedRecipeMatchingBehaviour INSTANCE = new ShapedRecipeMatchingBehaviour();

    private ShapedRecipeMatchingBehaviour() {
    }

    public static ShapedRecipeMatchingBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean matches(int width, int height, ItemStack[] ingredients, InventoryCrafting inventorycrafting) {
        for (int offsetX = 0; offsetX <= 3 - width; ++offsetX) {
            for (int offsetY = 0; offsetY <= 3 - height; ++offsetY) {
                if (this.matchesAt(width, height, ingredients, inventorycrafting, offsetX, offsetY, true)) {
                    return true;
                }

                if (this.matchesAt(width, height, ingredients, inventorycrafting, offsetX, offsetY, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean matchesAt(int width, int height, ItemStack[] ingredients, InventoryCrafting inventorycrafting, int offsetX, int offsetY, boolean mirror) {
        for (int gridX = 0; gridX < 3; ++gridX) {
            for (int gridY = 0; gridY < 3; ++gridY) {
                int recipeX = gridX - offsetX;
                int recipeY = gridY - offsetY;
                ItemStack recipeItem = null;

                if (recipeX >= 0 && recipeY >= 0 && recipeX < width && recipeY < height) {
                    if (mirror) {
                        recipeItem = ingredients[width - recipeX - 1 + recipeY * width];
                    } else {
                        recipeItem = ingredients[recipeX + recipeY * width];
                    }
                }

                ItemStack craftingItem = inventorycrafting.b(gridX, gridY);

                if (craftingItem != null || recipeItem != null) {
                    if (craftingItem == null && recipeItem != null || craftingItem != null && recipeItem == null) {
                        return false;
                    }

                    if (recipeItem.id != craftingItem.id) {
                        return false;
                    }

                    if (recipeItem.getData() != -1 && recipeItem.getData() != craftingItem.getData()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ItemStack craftResult(ItemStack resultTemplate) {
        return new ItemStack(resultTemplate.id, resultTemplate.count, resultTemplate.getData());
    }

    public int ingredientCount(int width, int height) {
        return width * height;
    }
}
