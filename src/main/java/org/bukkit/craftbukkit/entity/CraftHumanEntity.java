
package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.HumanInventoryBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.HumanPermissionBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.HumanStateBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.EntityHandleMutationBehaviour;
import net.minecraft.server.EntityHuman;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;

//import org.bukkit.GameMode;

public class CraftHumanEntity extends CraftLivingEntity implements HumanEntity {
    private static final HumanInventoryBridgeBehaviour HUMAN_INVENTORY_BRIDGE_BEHAVIOUR =
            HumanInventoryBridgeBehaviour.getInstance();
    private static final HumanPermissionBridgeBehaviour HUMAN_PERMISSION_BRIDGE_BEHAVIOUR =
            HumanPermissionBridgeBehaviour.getInstance();
    private static final HumanStateBridgeBehaviour HUMAN_STATE_BRIDGE_BEHAVIOUR =
            HumanStateBridgeBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final EntityHandleMutationBehaviour ENTITY_HANDLE_MUTATION_BEHAVIOUR =
            EntityHandleMutationBehaviour.getInstance();
    private CraftInventoryPlayer inventory;
    protected final PermissibleBase perm = new PermissibleBase(this);
    private boolean op;

    public CraftHumanEntity(final CraftServer server, final EntityHuman entity) {
        super(server, entity);
        this.inventory = HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(entity);
    }

    public String getName() {
        return HUMAN_STATE_BRIDGE_BEHAVIOUR.getName(getHandle());
    }

    @Override
    public EntityHuman getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityHuman.class);
    }

    public void setHandle(final EntityHuman entity) {
        ENTITY_HANDLE_MUTATION_BEHAVIOUR.applyHandle(entity, new EntityHandleMutationBehaviour.HandleMutationCallbacks() {
            @Override
            public void setSuperHandle(Object updatedHandle) {
                CraftHumanEntity.super.setHandle((EntityHuman) updatedHandle);
            }

            @Override
            public void assignHandleField(Object updatedHandle) {
                CraftHumanEntity.this.entity = (EntityHuman) updatedHandle;
            }

            @Override
            public void afterHandleAssignment(Object updatedHandle) {
                CraftHumanEntity.this.inventory = HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(
                        (EntityHuman) updatedHandle
                );
            }
        });
    }

    public PlayerInventory getInventory() {
        return HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.toInventory(inventory);
    }

    public ItemStack getItemInHand() {
        return getInventory().getItemInHand();
    }

    public void setItemInHand(ItemStack item) {
        getInventory().setItemInHand(item);
    }

    @Override
    public String toString() {
        return HUMAN_STATE_BRIDGE_BEHAVIOUR.toString(getEntityId(), getName());
    }

    public boolean isSleeping() {
        return HUMAN_STATE_BRIDGE_BEHAVIOUR.isSleeping(getHandle());
    }

    public int getSleepTicks() {
        return HUMAN_STATE_BRIDGE_BEHAVIOUR.getSleepTicks(getHandle());
    }

    public boolean isOp() {
        return HUMAN_STATE_BRIDGE_BEHAVIOUR.isOperator(op);
    }

    public boolean isPermissionSet(String name) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.isPermissionSet(perm, name);
    }

    public boolean isPermissionSet(Permission perm) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.isPermissionSet(this.perm, perm);
    }

    public boolean hasPermission(String name) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.hasPermission(perm, name);
    }

    public boolean hasPermission(Permission perm) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.hasPermission(this.perm, perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.addAttachment(perm, plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.addAttachment(perm, plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.addAttachment(perm, plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.addAttachment(perm, plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.removeAttachment(perm, attachment);
    }

    public void recalculatePermissions() {
        HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.recalculatePermissions(perm);
    }

    public void setOp(boolean value) {
        this.op = HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.applyOperatorState(perm, value);
    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.getEffectivePermissions(perm);
    }

//    public GameMode getGameMode() {
//        return GameMode.SURVIVAL;
//    }
//
//    public void setGameMode(GameMode mode) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
}
