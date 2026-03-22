package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.DataWatcherCodecBehaviour;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class DataWatcher {
    private static final DataWatcherCodecBehaviour DATA_WATCHER_CODEC_BEHAVIOUR = DataWatcherCodecBehaviour.getInstance();

    private boolean d = true;
    private static final HashMap a = DATA_WATCHER_CODEC_BEHAVIOUR.createSupportedTypeMap();
    private final Map b = new HashMap();
    private boolean c;

    public DataWatcher() {}

    public void a(int i, Object object) {
        this.d = DATA_WATCHER_CODEC_BEHAVIOUR.register(this.b, a, i, object, this.d);
    }

    public byte a(int i) {
        return DATA_WATCHER_CODEC_BEHAVIOUR.getByte(this.b, i);
    }

    public int b(int i) {
        return DATA_WATCHER_CODEC_BEHAVIOUR.getInt(this.b, i);
    }

    public String c(int i) {
        return DATA_WATCHER_CODEC_BEHAVIOUR.getString(this.b, i);
    }

    public void watch(int i, Object object) {
        this.c = DATA_WATCHER_CODEC_BEHAVIOUR.watch(this.b, i, object, this.c);
    }

    public boolean a() {
        return this.c;
    }

    public static void a(List list, DataOutputStream dataoutputstream) throws IOException {
        DATA_WATCHER_CODEC_BEHAVIOUR.writeList(list, dataoutputstream);
    }

    public ArrayList b() {
        DataWatcherCodecBehaviour.DirtyCollectResult result = DATA_WATCHER_CODEC_BEHAVIOUR.collectDirty(this.b, this.c);
        this.c = result.changed;
        return result.dirtyEntries;
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        DATA_WATCHER_CODEC_BEHAVIOUR.writeAll(this.b, dataoutputstream);
    }

    public static List a(DataInputStream datainputstream) throws IOException {
        return DATA_WATCHER_CODEC_BEHAVIOUR.readList(datainputstream);
    }
    
    public boolean getD() {
        return this.d;
    }
}
