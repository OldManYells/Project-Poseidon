package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.ConsoleInputLoopSystem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadCommandReader extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ThreadCommandReader.class.getName());
    final MinecraftServer server;
    private final ConsoleInputLoopSystem consoleInputLoopSystem = ConsoleInputLoopSystem.getInstance();
    private final ConsoleInputLoopSystem.LineReader consoleLineReader = new ConsoleInputLoopSystem.LineReader() {
        @Override
        public String readLine() throws java.io.IOException {
            try {
                if (org.bukkit.craftbukkit.Main.useJline) {
                    return ThreadCommandReader.this.server.reader.readLine(">", null);
                }
                return ThreadCommandReader.this.server.reader.readLine();
            } catch (Throwable throwable) {
                LOGGER.log(Level.SEVERE, "Console reader failed; stopping command input loop", throwable);
                return null;
            }
        }
    };
    private final ConsoleInputLoopSystem.RunningState runningState = new ConsoleInputLoopSystem.RunningState() {
        @Override
        public boolean shouldContinue() {
            return !ThreadCommandReader.this.server.isStopped && MinecraftServer.isRunning(ThreadCommandReader.this.server);
        }
    };
    private final ConsoleInputLoopSystem.CommandSink commandSink = new ConsoleInputLoopSystem.CommandSink() {
        @Override
        public void dispatch(String commandLine) {
            ThreadCommandReader.this.server.issueCommand(commandLine, ThreadCommandReader.this.server);
        }
    };

    public ThreadCommandReader(MinecraftServer minecraftserver) {
        this.server = minecraftserver;
        this.setName("Server Command Reader");
    }

    public void run() {
        consoleInputLoopSystem.runLoop(this.consoleLineReader, this.runningState, this.commandSink, LOGGER);
    }
}
