
package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.HumanInventoryBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.HumanPermissionBridgeBehaviour;
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
    private CraftInventoryPlayer inventory;
    protected final PermissibleBase perm = new PermissibleBase(this);
    private boolean op;

    public CraftHumanEntity(final CraftServer server, final EntityHuman entity) {
        super(server, entity);
        this.inventory = HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(entity);
    }

    public String getName() {
        return getHandle().name;
    }

    @Override
    public EntityHuman getHandle() {
        return (EntityHuman) entity;
    }

    public void setHandle(final EntityHuman entity) {
        super.setHandle((EntityHuman) entity);
        this.entity = entity;
        this.inventory = HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(entity);
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public ItemStack getItemInHand() {
        return getInventory().getItemInHand();
    }

    public void setItemInHand(ItemStack item) {
        getInventory().setItemInHand(item);
    }

    @Override
    public String toString() {
        return "CraftHumanEntity{" + "id=" + getEntityId() + "name=" + getName() + '}';
    }

    public boolean isSleeping() {
        return getHandle().sleeping;
    }

    public int getSleepTicks() {
        return getHandle().sleepTicks;
    }

    public boolean isOp() {
        return op;
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
