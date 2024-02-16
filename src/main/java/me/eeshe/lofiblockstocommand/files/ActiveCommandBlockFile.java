package me.eeshe.lofiblockstocommand.files;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ActiveCommandBlockFile {
    private File dataFile;
    private FileConfiguration dataYML;

    public ActiveCommandBlockFile(LofiBlocksToCommand plugin, ActiveCommandBlock activeCommandBlock) {
        createDataFile(plugin, activeCommandBlock.getUuid());
    }

    public ActiveCommandBlockFile(File dataFile) {
        this.dataFile = dataFile;
        this.dataYML = YamlConfiguration.loadConfiguration(dataFile);
    }

    private void createDataFile(LofiBlocksToCommand plugin, UUID uuid) {
        this.dataFile = new File(plugin.getDataFolder() + "/active_command_blocks/" + uuid.toString() + ".yml");
        if (!dataFile.exists()) {
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.dataYML = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getData() {
        return dataYML;
    }

    public void saveData() {
        try {
            dataYML.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadData() {
        dataYML.setDefaults(YamlConfiguration.loadConfiguration(dataFile));
    }

    public void deleteData() {
        dataFile.delete();
    }
}
