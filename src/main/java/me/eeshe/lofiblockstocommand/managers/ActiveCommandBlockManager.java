package me.eeshe.lofiblockstocommand.managers;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.files.ActiveCommandBlockFile;
import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class ActiveCommandBlockManager extends DataManager {
    private final Map<Location, ActiveCommandBlock> activeCommandBlocks = new HashMap<>();

    public ActiveCommandBlockManager(LofiBlocksToCommand plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        File activeCommandBlockDirectory = new File(getPlugin().getDataFolder() + "/active_command_blocks");
        if (!activeCommandBlockDirectory.exists()) return;
        File[] activeCommandDataFiles = activeCommandBlockDirectory.listFiles();
        if (activeCommandDataFiles == null) return;
        for (File activeCommandDataFile : activeCommandDataFiles) {
            ActiveCommandBlock activeCommandBlock = fetch(activeCommandDataFile);
            if (activeCommandBlock == null) continue;


        }
    }

    /**
     * Fetches the ActiveCommandBlock stored in the passed data file.
     *
     * @param dataFile Data file the ActiveCommandBlock will be fetched from.
     * @return ActiveCommandBlock stored in the passed data file.
     */
    private ActiveCommandBlock fetch(File dataFile) {
        ActiveCommandBlockFile activeCommandBlockFile = new ActiveCommandBlockFile(dataFile);
        FileConfiguration commandBlockData = activeCommandBlockFile.getData();
        if (commandBlockData.getKeys(true).isEmpty()) {
            return null;
        }
        UUID uuid = UUID.fromString(dataFile.getName().replace(".yml", ""));
        String commandBlockId = commandBlockData.getString("command-block");
        Location location = commandBlockData.getLocation("location");

        return new ActiveCommandBlock(uuid, commandBlockId, location);
    }

    /**
     * Saves the passed ActiveCommandBlock to its corresponding .yml file.
     *
     * @param activeCommandBlock ActiveCommandBlock that will be saved.
     */
    public void save(ActiveCommandBlock activeCommandBlock) {
        ActiveCommandBlockFile activeCommandBlockFile = new ActiveCommandBlockFile(getPlugin(), activeCommandBlock);
        FileConfiguration commandBlockData = activeCommandBlockFile.getData();

        commandBlockData.set("command-block", activeCommandBlock.getCommandBlockId());
        commandBlockData.set("location", activeCommandBlock.getLocation());

        activeCommandBlockFile.saveData();
    }

    @Override
    public void unload() {
        Iterator<ActiveCommandBlock> activeCommandBlockIterator = activeCommandBlocks.values().iterator();
        while (activeCommandBlockIterator.hasNext()) {
            ActiveCommandBlock activeCommandBlock = activeCommandBlockIterator.next();
            activeCommandBlockIterator.remove();
            activeCommandBlock.unload();
        }
    }

    /**
     * Deletes the passed ActiveCommandBlock.
     *
     * @param activeCommandBlock ActiveCommandBlock that will be deleted.
     */
    public void delete(ActiveCommandBlock activeCommandBlock) {
        new ActiveCommandBlockFile(getPlugin(), activeCommandBlock).deleteData();
    }

    public Map<Location, ActiveCommandBlock> getActiveCommandBlocks() {
        return activeCommandBlocks;
    }
}
