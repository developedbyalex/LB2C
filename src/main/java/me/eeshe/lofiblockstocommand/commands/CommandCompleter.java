package me.eeshe.lofiblockstocommand.commands;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class handles the tab completion system, adding commands to the tab completer only if the player has
 * the required permissions.
 */
public class CommandCompleter implements TabCompleter {
    private final LofiBlocksToCommand plugin;

    public CommandCompleter(LofiBlocksToCommand plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        Collection<LofiCommand> subcommands = plugin.getSubcommands().values();
        // Add all the subcommands the player has access to the tab completion
        for (LofiCommand subcommand : subcommands) {
            if (!subcommand.hasPermission(sender)) continue;

            arguments.add(subcommand.getName());
        }
        LofiCommand subcommand = plugin.getSubcommands().get(args[0]);
        if (args.length > 1 && (subcommand == null || !sender.hasPermission(subcommand.getPermission()))) {
            // If the player already wrote a command it doesn't have access to or an invalid command then return an
            // empty tab completion
            arguments.clear();
            return arguments;
        }
        if (args.length < 2) {
            return getCompletion(arguments, args);
        } else if (args.length < 3) {
            if (subcommand.getName().equals("create")) {
                return getCompletion(plugin.getCommandBlockManager().getCommandBlockIds(), args);
            }
        }
        arguments.clear();
        return arguments;
    }

    /**
     * Searches for matches between the passed arguments and the passed String[] based on the specified index.
     *
     * @param arguments Possible arguments the player can type into the tab completion.
     * @param args      Arguments the player is currently using in the command completion.
     * @return ArrayList with all the matching completions.
     */
    private List<String> getCompletion(List<String> arguments, String[] args) {
        List<String> results = new ArrayList<>();
        for (String argument : arguments) {
            if (!argument.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) continue;

            results.add(argument);
        }
        return results;
    }
}
