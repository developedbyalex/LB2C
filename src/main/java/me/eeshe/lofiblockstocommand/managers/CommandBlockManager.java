package me.eeshe.lofiblockstocommand.managers;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.CommandBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandBlockManager extends DataManager {
    private final Map<String, CommandBlock> commandBlocks = new HashMap<>();

    public CommandBlockManager(LofiBlocksToCommand plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        commandBlocks.clear();
        for (CommandBlock commandBlock : getPlugin().getMainConfig().fetchCommandBlocks()) {
            commandBlocks.put(commandBlock.getId(), commandBlock);
        }
    }

    @Override
    public void unload() {
        commandBlocks.clear();
    }

    public void reload() {
        unload();
        load();
    }

    public Map<String, CommandBlock> getCommandBlocks() {
        return commandBlocks;
    }

    public List<String> getCommandBlockIds() {
        return new ArrayList<>(commandBlocks.keySet());
    }
}
