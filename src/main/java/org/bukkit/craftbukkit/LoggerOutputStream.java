
package org.bukkit.craftbukkit;

import com.legacyminecraft.compat.bukkit.LoggerOutputFlushBehaviour;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerOutputStream extends ByteArrayOutputStream {
    private final String separator = System.getProperty("line.separator");
    private final Logger logger;
    private final Level level;
    private final LoggerOutputFlushBehaviour loggerOutputFlushBehaviour = LoggerOutputFlushBehaviour.getInstance();

    public LoggerOutputStream(Logger logger, Level level) {
        super();
        this.logger = logger;
        this.level = level;
    }

    @Override
    public void flush() throws IOException {
        loggerOutputFlushBehaviour.flush(this, this.separator, this.logger, this.level);
    }
}
