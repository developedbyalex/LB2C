package me.eeshe.lofiblockstocommand.models.actions;

import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import org.bukkit.entity.Player;

public abstract class Action {

    /**
     * Runs the action.
     *
     * @param activeCommandBlock CommandBlock that is running the action.
     * @param player             Player that clicked the CommandBlock.
     */
    public abstract void run(ActiveCommandBlock activeCommandBlock, Player player);
}
