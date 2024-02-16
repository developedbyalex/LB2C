package me.eeshe.lofiblockstocommand.listeners;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ActiveCommandBlockHandler implements Listener {
    private final LofiBlocksToCommand plugin;

    public ActiveCommandBlockHandler(LofiBlocksToCommand plugin) {
        this.plugin = plugin;
    }

    /**
     * Listens when a player right clicks a command block and handles it.
     *
     * @param event PlayerInteractEvent.
     */
    public void onCommandBlockClick(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;
        ActiveCommandBlock activeCommandBlock = ActiveCommandBlock.fromLocation(clickedBlock.getLocation());
        if (activeCommandBlock == null) return;

        Player player = event.getPlayer();
        if (player.isSneaking() && plugin.getMainConfig().adminCanShiftRemove() && player.hasPermission("lofiblockstocommand.admin")) {
            activeCommandBlock.unregister();
            return;
        }
        activeCommandBlock.runActions(player);
    }
}
