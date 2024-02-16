package me.eeshe.lofiblockstocommand.models;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.managers.CommandBlockManager;
import me.eeshe.lofiblockstocommand.models.actions.Action;

import java.util.List;

public class CommandBlock {
    private static final CommandBlockManager COMMAND_BLOCK_MANAGER = LofiBlocksToCommand.getInstance().getCommandBlockManager();

    private final String id;
    private final List<Action> actions;

    public CommandBlock(String id, List<Action> actions) {
        this.id = id;
        this.actions = actions;
    }

    /**
     * Searches and returns the CommandBlock corresponding to the passed ID.
     *
     * @param id ID that will be searched.
     * @return CommandBlock corresponding to the passed ID.
     */
    public static CommandBlock fromId(String id) {
        return COMMAND_BLOCK_MANAGER.getCommandBlocks().get(id);
    }

    public String getId() {
        return id;
    }

    public List<Action> getActions() {
        return actions;
    }
}
