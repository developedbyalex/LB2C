package me.eeshe.lofiblockstocommand.util;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.commands.LofiCommand;
import me.eeshe.lofiblockstocommand.models.config.Message;
import me.eeshe.lofiblockstocommand.models.config.Sound;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * This utility class easses the sending of messages to players while adding SFX feedback to them.
 */
public class Messager {

    /**
     * Sends the passed message to the passed CommandSender, formatting all color codes in the process.
     *
     * @param messageReceiver CommandSender that will receive the message.
     * @param message         Message that will be sent to the passed command sender.
     */
    public static void sendMessage(CommandSender messageReceiver, String message) {
        messageReceiver.sendMessage(StringUtil.formatColor(Message.PREFIX.getValue() + message));
    }

    /**
     * Sends a message with the help text, which contains all the subcommands the player has access to.
     *
     * @param messageReceiver CommandSender that will receiver the message.
     */
    public static void sendHelpMessage(CommandSender messageReceiver) {
        List<LofiCommand> displayCommands = new ArrayList<>();
        for (LofiCommand lofiCommand : LofiBlocksToCommand.getInstance().getSubcommands().values()) {
            if (!lofiCommand.hasPermission(messageReceiver)) continue;

            displayCommands.add(lofiCommand);
        }
        final StringBuilder finalMessage = new StringBuilder(Message.HELP_TEXT_HEADER.getValue() + "\n");
        final String commandFormat = Message.HELP_TEXT_COMMAND_FORMAT.getValue();
        final Iterator<LofiCommand> subcommandIterator = displayCommands.iterator();
        while (subcommandIterator.hasNext()) {
            LofiCommand subcommand = subcommandIterator.next();
            String commandText = commandFormat.replace("%usage%", subcommand.getUsageMessage());
            commandText = commandText.replace("%info%", subcommand.getInfoMessage());
            finalMessage.append(commandText);
            if (subcommandIterator.hasNext()) {
                finalMessage.append("\n");
            }
        }
        Messager.sendMessage(messageReceiver, finalMessage.toString());
        Sound.SUCCESS.play(messageReceiver);
    }

    /**
     * Sends an error message to the passed CommandSender indicating that it does not have the required permissions for
     * some action.
     *
     * @param messageReceiver CommandSender that will receive the message.
     */
    public static void sendNoPermissionMessage(CommandSender messageReceiver) {
        Message.NO_PERMISSION.sendError(messageReceiver);
    }

    /**
     * Sends an error message to the passed CommandSender indicating that the passed player name couldn't be found.
     *
     * @param messageReceiver CommandSender that will receive the message.
     * @param playerName      Name of the player that couldn't be found.
     */
    public static void sendPlayerNotFoundMessage(CommandSender messageReceiver, String playerName) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%player%", playerName);
        Message.PLAYER_NOT_FOUND.sendError(messageReceiver, placeholders);
    }

    /**
     * Sends the passed message to the passed Player as an Action Bar message. It also formats all color codes in the process.
     *
     * @param messageReceiver Player that will receive the action bar message.
     * @param message         Message that will be sent to the player.
     */
    public static void sendActionBarMessage(Player messageReceiver, String message) {
        TextComponent barMessage = new TextComponent(StringUtil.formatColor(message));
        messageReceiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, barMessage);
    }

    /**
     * Sends the passed title to the passed Player with the specified fade in, duration and fade out.
     *
     * @param messageReceiver Player that will receive the title.
     * @param title           Title that will be sent to the player.
     * @param subtitle        Subtitle that will be sent to the player.
     * @param fadeIn          Fade in time of the title in seconds.
     * @param duration        Duration of the title in seconds.
     * @param fadeOut         Fade out time of the title in seconds.
     */
    public static void sendTitleMessage(Player messageReceiver, String title, String subtitle, double fadeIn,
                                        double duration, double fadeOut) {
        if (title == null) return;

        title = StringUtil.formatColor(title);
        if (subtitle != null) {
            subtitle = StringUtil.formatColor(subtitle);
        }
        messageReceiver.sendTitle(title, subtitle, (int) fadeIn * 20, (int) duration * 20, (int) fadeOut * 20);
    }

    /**
     * Sends the passed message to all the players in the server.
     *
     * @param message Message that will be sent.
     */
    public static void sendGlobalMessage(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            sendMessage(online, message);
        }
    }

    /**
     * Sends the passed message to all the players in the server in the form of an action bar message.
     *
     * @param message Message that will be sent.
     */
    public static void sendGlobalActionbarMessage(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            sendActionBarMessage(online, message);
        }
    }

    /**
     * Sends the passed title to all the players in the server.
     *
     * @param title    Title that will be sent to the player.
     * @param subtitle Subtitle that will be sent to the player.
     * @param fadeIn   Fade in time of the title in seconds.
     * @param duration Duration of the title in seconds.
     * @param fadeOut  Fade out time of the title in seconds.
     */
    public static void sendGlobalTitle(String title, String subtitle, double fadeIn, double duration, double fadeOut) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            sendTitleMessage(online, title, subtitle, fadeIn, duration, fadeOut);
        }
    }
}
