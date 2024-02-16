package me.eeshe.lofiblockstocommand.models;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.managers.ActiveCommandBlockManager;
import me.eeshe.lofiblockstocommand.models.actions.Action;
import me.eeshe.lofiblockstocommand.models.config.Sound;
import me.eeshe.lofiblockstocommand.util.LogUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ActiveCommandBlock {
    private static final ActiveCommandBlockManager ACTIVE_COMMAND_BLOCK_MANAGER = LofiBlocksToCommand.getInstance().getActiveCommandBlockManager();

    private final UUID uuid;
    private final String commandBlockId;
    private final Location location;

    public ActiveCommandBlock(Block block, CommandBlock commandBlock) {
        this.uuid = UUID.randomUUID();
        this.commandBlockId = commandBlock.getId();
        this.location = block.getLocation();
    }

    public ActiveCommandBlock(UUID uuid, String commandBlockId, Location location) {
        this.uuid = uuid;
        this.commandBlockId = commandBlockId;
        this.location = location;
    }

    /**
     * Searches and returns the ActiveCommandBlock corresponding to the passed location.
     *
     * @param location Location that will be searched.
     * @return ActiveCommandBlock corresponding to the passed location.
     */
    public static ActiveCommandBlock fromLocation(Location location) {
        return ACTIVE_COMMAND_BLOCK_MANAGER.getActiveCommandBlocks().get(location);
    }

    /**
     * Loads and saves the data of the ActiveCommandBlock.
     */
    public void register() {
        load();
        saveData();
        Sound.COMMAND_BLOCK_CREATE.play(getCenteredLocation());
    }

    /**
     * Loads the ActiveCommandBlock to the ActiveCommandBlockManager class.
     */
    public void load() {
        ACTIVE_COMMAND_BLOCK_MANAGER.getActiveCommandBlocks().put(location, this);
    }

    /**
     * Unloads and deletes the data of the ActiveCommandBlock.
     */
    public void unregister() {
        unload();
        ACTIVE_COMMAND_BLOCK_MANAGER.delete(this);
        Sound.COMMAND_BLOCK_REMOVE.play(getCenteredLocation());
    }

    /**
     * Unloads the ActiveCommandBlock from the ActiveCommandBlockManager class.
     */
    public void unload() {
        ACTIVE_COMMAND_BLOCK_MANAGER.getActiveCommandBlocks().remove(location);
    }

    /**
     * Saves the data of the ActiveCommandBlock.
     */
    private void saveData() {
        ACTIVE_COMMAND_BLOCK_MANAGER.save(this);
    }

    /**
     * Runs the CommandBlock actions to the passed player.
     *
     * @param player Player the actions will be run for.
     */
    public void runActions(Player player) {
        CommandBlock commandBlock = getCommandBlock();
        if (commandBlock == null) return;

        for (Action action : commandBlock.getActions()) {
            action.run(this, player);
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCommandBlockId() {
        return commandBlockId;
    }

    public CommandBlock getCommandBlock() {
        CommandBlock commandBlock = CommandBlock.fromId(commandBlockId);
        if (commandBlock == null) {
            LogUtil.sendWarnLog("Unknown command block '" + commandBlockId + "'.");
        }
        return commandBlock;
    }

    public Location getLocation() {
        return location;
    }

    public Location getCenteredLocation() {
        return location.clone().add(0.5, 0, 0.5);
    }
}
