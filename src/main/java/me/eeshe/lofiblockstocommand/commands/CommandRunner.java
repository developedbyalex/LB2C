package me.eeshe.lofiblockstocommand.commands;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.config.Message;
import me.eeshe.lofiblockstocommand.models.config.Sound;
import me.eeshe.lofiblockstocommand.util.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class controls everything regarding command execution in the plugin. It checks the command and arguments
 * every time the player runs a command registered by the plugin.
 */
public class CommandRunner implements CommandExecutor {
    private final LofiBlocksToCommand plugin;

    public CommandRunner(LofiBlocksToCommand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("")) return false;
        // If the player only issued the name of the plugin/command send the help text
        if (args.length < 1) {
            Messager.sendHelpMessage(sender);
            return true;
        }
        String inputCommand = args[0].toLowerCase();
        if (!plugin.getSubcommands().containsKey(inputCommand)) {
            // Player inputted an unknown subcommand
            Message.UNKNOWN_COMMAND.sendError(sender);
            return true;
        }
        LofiCommand subcommand = plugin.getSubcommands().get(inputCommand);
        if (subcommand.isPlayerCommand() && !(sender instanceof Player)) {
            Message.PLAYER_COMMAND.sendError(sender);
            return true;
        }
        if (args.length < subcommand.getArgumentLength()) {
            Messager.sendMessage(sender, "&cUsage: &l" + subcommand.getUsageMessage());
            Sound.ERROR.play(sender);
            return true;
        }
        if (!subcommand.hasPermission(sender)) {
            Messager.sendNoPermissionMessage(sender);
            return true;
        }
        subcommand.execute(sender, args);
        return true;
    }
}
