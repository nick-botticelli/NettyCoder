package website.nickb.nettycoder.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NCConfigTests
{
    /**
     * Passes if the required default configuration file resource exists
     */
    @Test
    public void Config_Loading(@TempDir Path tempPath) throws IOException
    {
        //Path defaultTestConfigFilePath = tempPath.resolve(NCConfigManager.DEFAULT_CONFIG_PATH);
        Path customTestConfigFilePath = tempPath.resolve("config-test-custom.yml");

        NCConfigManager configManager = new NCConfigManager();
        assertNotNull(configManager.getConfig());
        assertNotNull(configManager.getConfigFile());

        configManager = new NCConfigManager(customTestConfigFilePath.toAbsolutePath().toString());
        assertNotNull(configManager.getConfig());
        assertNotNull(configManager.getConfigFile());
    }
}
