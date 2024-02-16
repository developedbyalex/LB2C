package me.eeshe.lofiblockstocommand.models.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerCommandAction extends Action {
    private final String command;

    public PlayerCommandAction(String command) {
        this.command = command;
    }

    @Override
    public void run(ActiveCommandBlock activeCommandBlock, Player player) {
        String command = this.command;
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            command = PlaceholderAPI.setPlaceholders(player, command);
        }
        player.performCommand(command);
    }
}
