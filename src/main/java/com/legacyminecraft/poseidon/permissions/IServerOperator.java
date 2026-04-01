package com.legacyminecraft.poseidon.permissions;

/**
 * Represents an object that may become a server operator, such as a {@link org.bukkit.entity.Player}
 */
public interface IServerOperator {
    /**
     * Checks if this object is a server operator
     *
     * @return true if this is an operator, otherwise false
     */
    public boolean isOp();

    /**
     * Sets the operator status of this object
     *
     * @param value New operator value
     */
    public void setOp(boolean value);
}
