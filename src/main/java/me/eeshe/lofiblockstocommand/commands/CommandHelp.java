package me.eeshe.lofiblockstocommand.commands;

import me.eeshe.lofiblockstocommand.models.config.Message;
import me.eeshe.lofiblockstocommand.util.Messager;
import org.bukkit.command.CommandSender;

/**
 * / help command. Displays the help text for the plugin.
 */
public class CommandHelp extends LofiCommand {

    public CommandHelp() {
        setName("help");
        setInfoMessage(Message.HELP_COMMAND_INFO.getValue());
        setPermission("lofiblockstocommand.help");
        setArgumentLength(1);
        setUsageMessage(Message.HELP_COMMAND_USAGE.getValue());
        setUniversalCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Messager.sendHelpMessage(sender);
    }
}
