package me.eeshe.lofiblockstocommand.commands;

import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import me.eeshe.lofiblockstocommand.models.CommandBlock;
import me.eeshe.lofiblockstocommand.models.config.Message;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandCreate extends LofiCommand {

    public CommandCreate() {
        setName("create");
        setInfoMessage(Message.CREATE_COMMAND_INFO.getValue());
        setPermission("lofiblockstocommand.create");
        setUsageMessage(Message.CREATE_COMMAND_USAGE.getValue());
        setArgumentLength(2);
        setPlayerCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String commandBlockId = args[1];
        CommandBlock commandBlock = CommandBlock.fromId(commandBlockId);
        if (commandBlock == null) {
            Message.UNKNOWN_COMMAND_BLOCK.sendError(player, Map.of("%name%", commandBlockId));
            return;
        }
        Block targetBlock = player.getTargetBlock(null, 4);
        if (targetBlock.getType().isAir()) {
            Message.NO_TARGETED_BLOCK.sendError(player);
            return;
        }
        if (ActiveCommandBlock.fromLocation(targetBlock.getLocation()) != null) {
            Message.ALREADY_COMMAND_BLOCK.sendError(player);
            return;
        }
        new ActiveCommandBlock(targetBlock, commandBlock).register();
        Message.CREATE_COMMAND_SUCCESS.sendSuccess(player, Map.of("%name%", commandBlockId));
    }
}
