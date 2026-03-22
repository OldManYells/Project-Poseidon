package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.progress.ConversionProgressBehaviour;

public class ConvertProgressUpdater implements IProgressUpdate {
    private static final ConversionProgressBehaviour CONVERSION_PROGRESS_BEHAVIOUR = ConversionProgressBehaviour.getInstance();

    private long b;

    final MinecraftServer a;

    public ConvertProgressUpdater(MinecraftServer minecraftserver) {
        this.a = minecraftserver;
        this.b = CONVERSION_PROGRESS_BEHAVIOUR.initializeTimestamp(System.currentTimeMillis());
    }

    public void a(String s) {}

    public void a(int i) {
        this.b = CONVERSION_PROGRESS_BEHAVIOUR.maybeLogProgress(this.b, System.currentTimeMillis(), i, MinecraftServer.log);
    }

    public void b(String s) {}
}
