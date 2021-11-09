package website.nickb.nettycoder.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * Configuration manager for NettyCoder
 */
public class NCConfigManager
{
    public static final String CONFIG_RESOURCE_PATH = "config.yml";
    public static final String DEFAULT_CONFIG_PATH = CONFIG_RESOURCE_PATH;

    private final File configFile;
    private NCConfig config;

    /**
     * Initializes the configuration manager for NettyCoder with custom configuration file path.
     *
     * @throws IOException If an I/O error occurred. {@link FileNotFoundException} is thrown if the configuration file
     * is attempted to be loaded when it does not exist or cannot be accessed. {@link FileAlreadyExistsException} is
     * thrown if the configuration file path already exists as a directory.
     */
    public NCConfigManager() throws IOException
    {
        this(DEFAULT_CONFIG_PATH);
    }

    /**
     * Initializes the configuration manager for NettyCoder with custom configuration file path.
     *
     * @throws IOException If an I/O error occurred. {@link FileNotFoundException} is thrown if the configuration file
     * is attempted to be loaded when it does not exist or cannot be accessed. {@link FileAlreadyExistsException} is
     * thrown if the configuration file path already exists as a directory.
     */
    public NCConfigManager(@NotNull String configPath) throws IOException
    {
        this.configFile = new File(configPath);

        if (configFile.exists())
        {
            if (configFile.isFile())
            {
                if (!loadConfigFile())
                    throw new IOException("Configuration could not be read from file (invalid YAML?)!");
            }
            else
            {
                throw new FileAlreadyExistsException("Configuration file at \"" + configFile.getAbsolutePath()
                        + "\" already exists as a directory!");
            }
        }
        else
        {
            generateConfigFile();
        }
    }

    private boolean loadConfigFile() throws FileNotFoundException
    {
        Yaml yaml = new Yaml(new Constructor(NCConfig.class));
        InputStream configInputStream = new FileInputStream(configFile);
        config = yaml.load(configInputStream);

        return config != null;
    }

    /**
     * Generates the configuration file on the filesystem.
     *
     * @throws IOException If there was an I/O exception when creating the configuration file
     */
    private void generateConfigFile() throws IOException
    {
        InputStream src = getClass().getClassLoader().getResourceAsStream(CONFIG_RESOURCE_PATH);
        Files.copy(Objects.requireNonNull(src), configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Gets the configuration file.
     *
     * @return The configuration {@link File} instance
     */
    @NotNull
    public File getConfigFile()
    {
        return configFile;
    }

    /**
     * Gets the configuration.
     *
     * @return {@link NCConfig} instance containing values from config file if not null, otherwise, configuration wasn't
     * sucessfully loaded
     */
    @Nullable
    public NCConfig getConfig()
    {
        return config;
    }
}
