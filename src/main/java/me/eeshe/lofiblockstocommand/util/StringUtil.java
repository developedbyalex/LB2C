package me.eeshe.lofiblockstocommand.util;

import me.eeshe.lofiblockstocommand.models.config.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This utility class helps to format or parse Strings to other Strings or numeric values.
 */
public class StringUtil {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");

    /**
     * Translates all the color codes on the passed message and returns it back.
     *
     * @param text Text whose color codes will be translated.
     * @return String with all the translated color codes.
     */
    public static String formatColor(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace(color, String.valueOf(net.md_5.bungee.api.ChatColor.of(color)));
            matcher = HEX_PATTERN.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Formats the passed Enum name, so it is a capitalized text.
     * Ex. WITHER_SKELETON_SPAWN_EGG -> Wither Skeleton Spawn Egg
     *
     * @param enumeration Enum that will be formatted.
     * @return Formatted Enum's name.
     */
    public static String formatEnum(Enum<?> enumeration) {
        return formatEnum(enumeration.name());
    }

    /**
     * Formats the passed Enum name, so it is a capitalized text.
     * Ex. WITHER_SKELETON_SPAWN_EGG -> Wither Skeleton Spawn Egg
     *
     * @param enumName Enum name that will be formatted.
     * @return Formatted Enum's name.
     */
    public static String formatEnum(String enumName) {
        String[] split = enumName.split("_");
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                String word = split[i];
                if (word.equalsIgnoreCase("of") || word.equalsIgnoreCase("the")) {
                    // Don't capitalize 'of' nor 'the'
                    split[i] = word.toLowerCase();
                    continue;
                }
                split[i] = capitalize(word);
            }
            return String.join(" ", split);
        } else
            return capitalize(enumName);
    }

    /**
     * Capitalizes and returns the passed text.
     *
     * @param text Text that will be capitalized.
     * @return Capitalized text.
     */
    public static String capitalize(String text) {
        // Extracting the first letter, capitalizing it and joining the rest of the String.
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    /**
     * Converts time in milliseconds to a formatted string in the format "dd:hh:mm:ss".
     *
     * @param millis time in milliseconds
     * @return formatted time string in the format "dd:hh:mm:ss"
     */
    public static String formatMillis(long millis) {
        return formatSeconds(TimeUnit.MILLISECONDS.toSeconds(millis));
    }

    /**
     * Converts time in ticks to a formatted string in the format "dd:hh:mm:ss".
     *
     * @param ticks time in ticks
     * @return formatted time string in the format "dd:hh:mm:ss"
     */
    public static String formatTicks(long ticks) {
        return formatSeconds(ticks / 20);
    }

    /**
     * Converts time in seconds to a formatted string in the format "dd:hh:mm:ss".
     *
     * @param seconds time in seconds
     * @return formatted time string in the format "dd:hh:mm:ss"
     */
    public static String formatSeconds(long seconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
        long hours = TimeUnit.SECONDS.toHours(seconds) % 24;
        long days = TimeUnit.SECONDS.toDays(seconds);

        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }

    /**
     * Attempts to parse the passed String to an Integer value. If it fails it sends a message to the passed CommandSender.
     *
     * @param commandSender CommandSender that will receive error messages if needed.
     * @param string        String that will be parsed into an Integer value.
     * @return Parsed integer value.
     */
    public static Integer parseInteger(CommandSender commandSender, String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            Message.NON_NUMERIC_INPUT.sendError(commandSender);
            return null;
        }
    }

    /**
     * Attempts to parse the passed String to a Double value. If it fails it sends a message to the passed CommandSender.
     *
     * @param commandSender CommandSender that will receive error messages if needed.
     * @param string        String that will be parsed into a Double value.
     * @return Parsed double value.
     */
    public static Double parseDouble(CommandSender commandSender, String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            Message.NON_NUMERIC_INPUT.sendError(commandSender);
            return null;
        }
    }
}
