package me.eeshe.lofiblockstocommand.commands;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.config.Message;
import org.bukkit.command.CommandSender;

/**
 * / reload command. Reloads the plugin's configurations.
 */
public class CommandReload extends LofiCommand {
    private final LofiBlocksToCommand plugin;

    public CommandReload(LofiBlocksToCommand plugin) {
        this.plugin = plugin;

        setName("reload");
        setInfoMessage(Message.RELOAD_COMMAND_INFO.getValue());
        setPermission("lofiblockstocommand.reload");
        setArgumentLength(1);
        setUsageMessage(Message.RELOAD_COMMAND_USAGE.getValue());
        setUniversalCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.setupFiles();
        plugin.getCommandBlockManager().reload();
        Message.RELOAD_COMMAND_SUCCESS.sendSuccess(sender);
    }
}
