package com.legacyminecraft.poseidon.util;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import java.util.UUID;

public class UUIDResult {
    private final UUID uuid;
    private final ReturnType returnType;
    private Exception exception;

    public UUIDResult(UUID uuid, ReturnType returnType) {
        this.uuid = uuid;
        this.returnType = returnType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }


    public enum ReturnType {
        ONLINE,
        OFFLINE,
        API_OFFLINE
    }
}
