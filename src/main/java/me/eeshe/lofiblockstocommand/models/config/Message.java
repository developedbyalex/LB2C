package me.eeshe.lofiblockstocommand.models.config;

import me.eeshe.lofiblockstocommand.files.config.MessageConfig;
import me.eeshe.lofiblockstocommand.util.Messager;
import org.bukkit.command.CommandSender;

import java.util.Map;

public enum Message {
    PREFIX("prefix", "&#8364E8&lL&#906DEB&lo&#9E75EE&lf&#AB7EF1&li&#B886F4&lB&#C68FF7&l2&#D397FA&lC "),
    UNKNOWN_COMMAND("unknown-command", "&cUnknown command. Run &l/lbtc &chelp to see the full list of commands."),
    PLAYER_COMMAND("player-command", "Not available for consoles."),
    NO_PERMISSION("no-permission", "&cYou don''t have permissions to run this command."),
    PLAYER_NOT_FOUND("player-not-found", "&cUnknown player &l%player%&c."),
    NON_NUMERIC_INPUT("non-numeric-input", "&cYou must enter a numeric value."),
    INVALID_NUMERIC_INPUT_ZERO("invalid-numeric-input-zero", "&cValue must be higher than 0."),

    HELP_COMMAND_INFO("help-command-info", "Displays this list."),
    HELP_COMMAND_USAGE("help-command-usage", "/lbtc help"),
    HELP_TEXT_HEADER("help-text-header", "&e&lCommands"),
    HELP_TEXT_COMMAND_FORMAT("help-text-command-format", "&b%usage% &e- &9%info%"),
    NO_AVAILABLE_COMMANDS("no-available-commands", "&cYou don't have access to any commands."),

    RELOAD_COMMAND_INFO("reload-command-info", "Reloads the plugin's configuration file."),
    RELOAD_COMMAND_USAGE("reload-command-usage", "/lbtc reload"),
    RELOAD_COMMAND_SUCCESS("reload-command-success", "&aConfiguration successfully reloaded."),

    CREATE_COMMAND_INFO("create-command-info", "Creates the specified command block with the block you are targeting."),
    CREATE_COMMAND_USAGE("create-command-usage", "/lbtc create <CommandBlockID>"),
    UNKNOWN_COMMAND_BLOCK("unknown-command-block", "&cUnknown command block &l%name%&c."),
    NO_TARGETED_BLOCK("no-targeted-block", "&cYou must be looking at a block."),
    ALREADY_COMMAND_BLOCK("already-command-block", "&cThis block is already a command block."),
    CREATE_COMMAND_SUCCESS("create-command-success", "&aSuccessfully created command block &l%name%&a.");

    private static MessageConfig CONFIG;
    private final String path;
    private final String defaultValue;

    Message(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    public static void setConfig(MessageConfig config) {
        Message.CONFIG = config;
    }

    /**
     * Sends the message to the passed CommandSender and plays the success sound.
     *
     * @param sender CommandSender the message will be sent to.
     */
    public void sendSuccess(CommandSender sender) {
        send(sender, Sound.SUCCESS);
    }

    /**
     * Sends the message to the passed CommandSender replacing the passed placeholders and plays the success sound.
     *
     * @param sender       CommandSender the message will be sent to.
     * @param placeholders Placeholders that will be replaced in the message.
     */
    public void sendSuccess(CommandSender sender, Map<String, String> placeholders) {
        send(sender, Sound.SUCCESS, placeholders);
    }

    /**
     * Sends the message to the passed CommandSender and plays the error sound.
     *
     * @param sender CommandSender the message will be sent to.
     */
    public void sendError(CommandSender sender) {
        send(sender, Sound.ERROR);
    }

    /**
     * Sends the message to the passed CommandSender replacing the passed placeholders and plays the error sound.
     *
     * @param sender       CommandSender the message will be sent to.
     * @param placeholders Placeholders that will be replaced in the message.
     */
    public void sendError(CommandSender sender, Map<String, String> placeholders) {
        send(sender, Sound.ERROR, placeholders);
    }

    /**
     * Sends the message to the passed CommandSender.
     *
     * @param sender CommandSender the message will be sent to.
     */
    public void send(CommandSender sender) {
        Messager.sendMessage(sender, getValue());
    }

    /**
     * Sends the message to the passed CommandSender replacing the passed placeholders.
     *
     * @param sender       CommandSender the message will be sent to.
     * @param placeholders Placeholders that will be replaced in the message.
     */
    public void send(CommandSender sender, Map<String, String> placeholders) {
        Messager.sendMessage(sender, getFormattedMessage(placeholders));
    }

    /**
     * Sends the message to the passed CommandSender and plays the passed Sound.
     *
     * @param sender CommandSender the message will be sent to.
     * @param sound  Sound that will be played.
     */
    public void send(CommandSender sender, Sound sound) {
        send(sender);
        sound.play(sender);
    }

    /**
     * Sends the message to the passed CommandSender replacing the passed placeholders and plays the passed Sound.
     *
     * @param sender       CommandSender the message will be sent to.
     * @param sound        Sound that will be sent.
     * @param placeholders Placeholders that will be replaced.
     */
    public void send(CommandSender sender, Sound sound, Map<String, String> placeholders) {
        Messager.sendMessage(sender, getFormattedMessage(placeholders));
        sound.play(sender);
    }

    /**
     * Returns the message formatted with the passed placeholders.
     *
     * @param placeholders Placeholders that will be formatted.
     * @return Message formatted with the passed placeholders.
     */
    private String getFormattedMessage(Map<String, String> placeholders) {
        String message = getValue();
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getValue() {
        return CONFIG.getConfig().getString(path, defaultValue);
    }
}
