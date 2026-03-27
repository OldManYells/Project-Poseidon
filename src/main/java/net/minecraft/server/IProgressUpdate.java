package net.minecraft.server;

public interface IProgressUpdate extends com.legacyminecraft.poseidon.world.IProgressUpdate {

    void a(String s);

    void b(String s);

    void a(int i);

    default void setPrimaryMessage(String message) {
        this.a(message);
    }

    default void setSecondaryMessage(String message) {
        this.b(message);
    }

    default void setProgress(int progressPercent) {
        this.a(progressPercent);
    }
}
