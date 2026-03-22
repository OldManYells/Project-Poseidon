package org.bukkit.command.defaults;

import com.legacyminecraft.poseidon.PoseidonPlugin;
import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.runtime.StopCommandExecutionSystem;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;

public class StopCommand extends VanillaCommand {
    private final String msgKickShutdown;
    private final StopCommandExecutionSystem stopCommandExecutionSystem = StopCommandExecutionSystem.getInstance();
    
    public StopCommand() {
        super("stop");
        this.description = "Stops the server";
        this.usageMessage = "/stop";
        this.setPermission("bukkit.command.stop");
        this.msgKickShutdown = PoseidonConfig.getInstance().getConfigString("message.kick.shutdown");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        stopCommandExecutionSystem.executeStopCommand(new StopCommandExecutionSystem.ShutdownActions() {
            @Override
            public void broadcast(String message) {
                Command.broadcastCommandMessage(sender, message);
            }

            @Override
            public void setShuttingDown(boolean shuttingDown) {
                ((CraftServer) Bukkit.getServer()).setShuttingdown(shuttingDown);
            }

            @Override
            public void saveAndKickPlayers(String kickMessage) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.saveData();
                    player.kickPlayer(kickMessage);
                }
            }

            @Override
            public void saveWorlds() {
                for (World world : Bukkit.getWorlds()) {
                    world.save();
                }
            }

            @Override
            public void scheduleFinalStop(Runnable task, long delayTicks) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(PoseidonPlugin.getInstance(), task, delayTicks);
            }

            @Override
            public void shutdownNow() {
                Bukkit.shutdown();
            }
        }, this.msgKickShutdown);

        return true;
    }

    @Override
    public boolean matches(String input) {
        return input.startsWith("stop");
    }
}
