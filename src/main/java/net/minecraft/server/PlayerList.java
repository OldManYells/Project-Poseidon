package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.PlayerListBehaviour;

public class PlayerList {
    private static final PlayerListBehaviour PLAYER_LIST_BEHAVIOUR = PlayerListBehaviour.getInstance();

    private transient PlayerListEntry[] a = new PlayerListEntry[16];
    private transient int b;
    private int c = 12;
    private final float d = 0.75F;
    private transient volatile int e;

    public PlayerList() {}

    private static int e(long i) {
        return PLAYER_LIST_BEHAVIOUR.hashLong(i);
    }

    private static int a(int i) {
        return PLAYER_LIST_BEHAVIOUR.hashInt(i);
    }

    private static int a(int i, int j) {
        return PLAYER_LIST_BEHAVIOUR.indexFor(i, j);
    }

    public Object a(long i) {
        return PLAYER_LIST_BEHAVIOUR.get(this.a, i);
    }

    public void a(long i, Object object) {
        PLAYER_LIST_BEHAVIOUR.updateExistingValues(this.a, i, object);
        PlayerListBehaviour.PutState state = PLAYER_LIST_BEHAVIOUR.put(this.a, this.b, this.c, this.e, this.d, i, object);
        this.a = (PlayerListEntry[]) state.table;
        this.b = state.size;
        this.c = state.threshold;
        this.e = state.modCount;
    }

    public Object b(long i) {
        PlayerListEntry playerlistentry = this.c(i);

        return playerlistentry == null ? null : playerlistentry.b;
    }

    final PlayerListEntry c(long i) {
        PlayerListBehaviour.RemoveState state = PLAYER_LIST_BEHAVIOUR.remove(this.a, this.b, this.e, i);
        this.a = (PlayerListEntry[]) state.table;
        this.b = state.size;
        this.e = state.modCount;
        return (PlayerListEntry) state.removedEntry;
    }

    static int d(long i) {
        return e(i);
    }
}
