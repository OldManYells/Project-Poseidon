package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperDescriptionBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.EntityHandleMutationBehaviour;
import com.legacyminecraft.compat.bukkit.StorageMinecartInventoryBridgeBehaviour;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityMinecart;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.inventory.Inventory;

public class CraftStorageMinecart extends CraftMinecart implements StorageMinecart {
    private static final StorageMinecartInventoryBridgeBehaviour STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR =
            StorageMinecartInventoryBridgeBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final EntityHandleMutationBehaviour ENTITY_HANDLE_MUTATION_BEHAVIOUR =
            EntityHandleMutationBehaviour.getInstance();
    private static final EntityWrapperDescriptionBehaviour ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR =
            EntityWrapperDescriptionBehaviour.getInstance();
    private CraftInventory inventory;

    public CraftStorageMinecart(CraftServer server, EntityMinecart entity) {
        super(server, entity);
        inventory = STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(entity);
    }

    public Inventory getInventory() {
        return STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR.toInventory(inventory);
    }

    @Override
    public void setHandle(final Entity entity) {
        final EntityMinecart minecartHandle = ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityMinecart.class);
        ENTITY_HANDLE_MUTATION_BEHAVIOUR.applyHandle(
                minecartHandle,
                new EntityHandleMutationBehaviour.HandleMutationCallbacks() {
                    @Override
                    public void setSuperHandle(Object updatedHandle) {
                        CraftStorageMinecart.super.setHandle((Entity) updatedHandle);
                    }

                    @Override
                    public void assignHandleField(Object updatedHandle) {
                    }

                    @Override
                    public void afterHandleAssignment(Object updatedHandle) {
                        inventory = STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(
                                (EntityMinecart) updatedHandle
                        );
                    }
                }
        );
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR.craftStorageMinecartToString(inventory);
    }
}
