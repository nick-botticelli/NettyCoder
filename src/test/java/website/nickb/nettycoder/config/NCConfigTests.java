package website.nickb.nettycoder.config;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NCConfigTests
{
    private NCConfigManager configManager, customConfigManager;

    /**
     * Passes if the required default configuration file resource exists and it can be written to disk
     */
    @Test
    @Order(1)
    public void Config_Loading(@TempDir Path tempPath) throws IOException
    {
        Path customTestConfigFilePath = tempPath.resolve("config-test-custom.yml");

        configManager = new NCConfigManager();
        assertNotNull(configManager.getConfig());
        assertNotNull(configManager.getConfigFile());

        customConfigManager = new NCConfigManager(customTestConfigFilePath.toAbsolutePath().toString());
        assertNotNull(configManager.getConfig());
        assertNotNull(configManager.getConfigFile());
    }

    /**
     * Passes if parsed content from configurations are correct
     */
    @Test
    public void Config_Content()
    {
        NCConfig config = configManager.getConfig();
        NCConfig config2 = customConfigManager.getConfig();

        assertNotNull(config);
        assertEquals(config.version, 0);
        assertEquals(config.port, 18600);

        assertNotNull(config2);
        assertEquals(config2.version, 0);
        assertEquals(config2.port, 18600);
    }

    /**
     * Cleanup after tests (e.g., delete leftover files)
     */
    @AfterAll
    public void cleanUp()
    {
        assertTrue(configManager.getConfigFile().delete());
    }
}
