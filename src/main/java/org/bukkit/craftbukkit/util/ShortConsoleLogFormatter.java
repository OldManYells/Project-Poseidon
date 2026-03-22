package org.bukkit.craftbukkit.util;

import com.legacyminecraft.poseidon.compat.bukkit.ShortConsoleLogFormatBehaviour;
import net.minecraft.server.MinecraftServer;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ShortConsoleLogFormatter extends Formatter {
    private final SimpleDateFormat date;
    private final ShortConsoleLogFormatBehaviour shortConsoleLogFormatBehaviour = ShortConsoleLogFormatBehaviour.getInstance();

    public ShortConsoleLogFormatter(MinecraftServer server) {
        this.date = shortConsoleLogFormatBehaviour.resolveDateFormat(server.options);
    }

    @Override
    public String format(LogRecord record) {
        return shortConsoleLogFormatBehaviour.formatRecord(record, this.date);
    }

}
