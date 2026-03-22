package net.minecraft.server;

import com.legacyminecraft.poseidon.block.RedstoneUpdateStateBehaviour;

class RedstoneUpdateInfo {
    private static final RedstoneUpdateStateBehaviour REDSTONE_UPDATE_STATE_BEHAVIOUR = RedstoneUpdateStateBehaviour.getInstance();

    int a;
    int b;
    int c;
    long d;

    public RedstoneUpdateInfo(int i, int j, int k, long l) {
        this.a = REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveX(i);
        this.b = REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveY(j);
        this.c = REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveZ(k);
        this.d = REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveScheduledTick(l);
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.b;
    }

    public int getZ() {
        return this.c;
    }

    public long getScheduledTick() {
        return this.d;
    }

}
