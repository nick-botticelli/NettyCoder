package website.nickb.nettycoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import website.nickb.nettycoder.config.NCConfig;
import website.nickb.nettycoder.config.NCConfigManager;
import website.nickb.nettycoder.server.NCServer;

import java.io.IOException;

public class NettyCoder
{
    public static void main(@NotNull String[] args)
    {
        Logger logger = LogManager.getLogger("NettyCoder");
        NCConfigManager configManager;
        NCConfig config;

        logger.info("Loading configuration file...");

        try
        {
            configManager = new NCConfigManager();
            config = configManager.getConfig();
        }
        catch (IOException exc)
        {
            logger.error("There was an exception when loading the configuration!");
            exc.printStackTrace();
            return;
        }

        if (config == null)
        {
            logger.error("There was an error loading the configuration (config instance is null)!");
            return;
        }

        logger.info("Starting up NettyCoder server on port {}...", config.port);

        NCServer server = new NCServer(logger, config.port);
    }
}
