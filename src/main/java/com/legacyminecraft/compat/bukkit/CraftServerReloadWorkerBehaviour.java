package com.legacyminecraft.compat.bukkit;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical behaviour for CraftServer reload worker-drain wrapper glue.
 */
public final class CraftServerReloadWorkerBehaviour {
    private static final CraftServerReloadWorkerBehaviour INSTANCE =
            new CraftServerReloadWorkerBehaviour();

    private CraftServerReloadWorkerBehaviour() {
    }

    public static CraftServerReloadWorkerBehaviour getInstance() {
        return INSTANCE;
    }

    public void drainAndWarn(BukkitScheduler scheduler, Logger logger) {
        int pollCount = 0;

        // Wait for at most 2.5 seconds for plugins to close their threads
        while (pollCount < 50 && scheduler.getActiveWorkers().size() > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
            pollCount++;
        }

        List<BukkitWorker> overdueWorkers = scheduler.getActiveWorkers();
        for (BukkitWorker worker : overdueWorkers) {
            Plugin plugin = worker.getOwner();
            String author = "<NoAuthorGiven>";
            if (plugin.getDescription().getAuthors().size() > 0) {
                author = plugin.getDescription().getAuthors().get(0);
            }
            logger.log(Level.SEVERE, String.format(
                    "Nag author: '%s' of '%s' about the following: %s",
                    author,
                    plugin.getDescription().getName(),
                    "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin"
            ));
        }
    }
}
