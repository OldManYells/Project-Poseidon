package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ProgressUpdateContract;

public interface IProgressUpdate extends ProgressUpdateContract {

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
