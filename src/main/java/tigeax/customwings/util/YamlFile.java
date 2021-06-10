package tigeax.customwings.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import tigeax.customwings.CustomWings;


/**
 * Extention of YamlConfiguration to make it easier to work with YAML files.
 */
public abstract class YamlFile extends YamlConfiguration {


    protected final CustomWings plugin;
    protected final File file;

    public YamlFile(CustomWings plugin, String filename) {

        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), filename);
        
        this.updateFile();
    }

    public YamlFile(CustomWings plugin, File file) {

        this.plugin = plugin;
        this.file = file;
        
        this.updateFile();
    }

    public void update() {
        updateFile();
        loadDataFromFile();
    }

    public void save() {
        try {
            save(file);
            System.out.print("saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Reload/update the file, for when it was externally edited.
    * Check if it exists, and if not create it by calling createIfNotExists().
    */
    protected void updateFile() {

        plugin.getLogger().info("Loading " + file.getName() + "...");

        createIfNotExists();

        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    /**
    * Check if the file exists, if not create it by calling createFile().
    */
    private void createIfNotExists() {
        if (!file.exists()) {
            createFile();
        }
    }

    /**
    * Create the specified file, overwriting it if it already exists.
    */
    private void createFile() {
        plugin.getLogger().info(file.getName() + " file does not exists. Creating it...");
        file.getParentFile().mkdirs();
        plugin.saveResource(file.getName(), false);
    }

    protected abstract void loadDataFromFile();

}