package com.legacyminecraft.poseidon.permissions;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import org.bukkit.permissions.PermissionAttachment;

/**
 * Represents a class which is to be notified when a {@link PermissionAttachment} is removed from a {@link org.bukkit.permissions.Permissible}
 */
public interface IPermissionRemovedExecutor {
    /**
     * Called when a {@link PermissionAttachment} is removed from a {@link org.bukkit.permissions.Permissible}
     *
     * @param attachment Attachment which was removed
     */
    public void attachmentRemoved(PermissionAttachment attachment);
}
