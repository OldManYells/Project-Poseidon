package net.minecraft.server;

public class ConvertProgressUpdater implements IProgressUpdate {

    private long lastUpdateTime;

    final MinecraftServer a;

    public ConvertProgressUpdater(MinecraftServer minecraftserver) {
        this.a = minecraftserver;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void start(String message) {}

    public void setProgress(int progress) {
        if (System.currentTimeMillis() - this.lastUpdateTime >= 1000L) {
            this.lastUpdateTime = System.currentTimeMillis();
            MinecraftServer.log.info("Converting... " + progress + "%");
        }
    }

    public void finish(String message) {}

    @Deprecated
    public void a(String message) {
        this.start(message);
    }

    @Deprecated
    public void a(int progress) {
        this.setProgress(progress);
    }

    @Deprecated
    public void b(String message) {
        this.finish(message);
    }
}
