package me.eeshe.lofiblockstocommand.files.config;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.CommandBlock;
import me.eeshe.lofiblockstocommand.models.actions.*;
import me.eeshe.lofiblockstocommand.models.config.ConfigParticle;
import me.eeshe.lofiblockstocommand.models.config.ConfigSound;
import me.eeshe.lofiblockstocommand.util.LogUtil;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainConfig extends ConfigWrapper {

    public MainConfig(LofiBlocksToCommand plugin) {
        super(plugin, null, "config.yml");
    }

    @Override
    public void writeDefaults() {
        writeGeneralDefaults();
        writeCommandBlockDefaults();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    /**
     * Writes the default general configurations.
     */
    private void writeGeneralDefaults() {
        getConfig().addDefault("admin-shift-remove", true);
    }

    /**
     * Writes the default command block configurations.
     */
    private void writeCommandBlockDefaults() {
        writeCommandBlock(
                "kitmenu",
                List.of(
                        "[CONSOLE] give %player_name% diamond 1",
                        "[PLAYER] msg %player_name% this is a player command",
                        "[SOUND] ENTITY_PARROT_DEATH",
                        "[PARTICLE] CLOUD"
                ));
    }

    /**
     * Writes the passed CommandBlock to the configuration file.
     *
     * @param id            ID that will be written.
     * @param actionStrings Actions that will be written.
     */
    private void writeCommandBlock(String id, List<String> actionStrings) {
        FileConfiguration config = getConfig();
        String path = "command-blocks." + id;
        config.addDefault(path + ".actions", actionStrings);
    }

    /**
     * Returns the configurations of whether an admin can remove a command block with shift + Right-click
     *
     * @return Whether an admin can remove a command block with shift + Right-click
     */
    public boolean adminCanShiftRemove() {
        return getConfig().getBoolean("admin-shift-remove");
    }

    /**
     * Fetches the configured CommandBlocks.
     *
     * @return Configured CommandBlocks.
     */
    public List<CommandBlock> fetchCommandBlocks() {
        List<CommandBlock> commandBlocks = new ArrayList<>();
        ConfigurationSection blockSection = getConfig().getConfigurationSection("command-blocks");
        if (blockSection == null) return commandBlocks;
        for (String commandBlockId : blockSection.getKeys(false)) {
            CommandBlock commandBlock = fetchCommandBlock(commandBlockId);
            if (commandBlock == null) continue;

            commandBlocks.add(commandBlock);
        }
        return commandBlocks;
    }

    /**
     * Fetches the configured CommandBlock under the passed ID.
     *
     * @param commandBlockId CommandBlock that will be fetched.
     * @return CommandBlock under the passed ID.
     */
    private CommandBlock fetchCommandBlock(String commandBlockId) {
        ConfigurationSection commandBlockSection = getConfig().getConfigurationSection("command-blocks." + commandBlockId);
        if (commandBlockSection == null) return null;

        List<Action> actions = fetchActions(commandBlockSection.getStringList("actions"));
        return new CommandBlock(commandBlockId, actions);
    }

    /**
     * Fetches the actions corresponding to the passed list of action strings.
     *
     * @param actionStrings Action strings that will be fetched.
     * @return Actions corresponding to the passed list of action strings.
     */
    private List<Action> fetchActions(List<String> actionStrings) {
        List<Action> actions = new ArrayList<>();
        for (String actionString : actionStrings) {
            String[] splitAction = actionString.split(" ");
            if (splitAction.length == 0) continue;

            String actionType = actionString.split(" ")[0];
            String actionDetails = String.join(" ", Arrays.copyOfRange(splitAction, 1, splitAction.length));
            Action action = switch (actionType.toUpperCase(Locale.ROOT)) {
                case "[CONSOLE]":
                    yield new ConsoleCommandAction(actionDetails);
                case "[PLAYER]":
                    yield new PlayerCommandAction(actionDetails);
                case "[SOUND]":
                    Sound sound;
                    try {
                        sound = Sound.valueOf(actionDetails);
                    } catch (Exception e) {
                        LogUtil.sendWarnLog("Unknown sound '" + actionDetails + "'.");
                        yield null;
                    }
                    yield new SoundAction(new ConfigSound(sound, true, 1F, 1F));
                case "[PARTICLE]":
                    Particle particle;
                    try {
                        particle = Particle.valueOf(actionDetails);
                    } catch (Exception e) {
                        LogUtil.sendWarnLog("Unknown particle '" + actionDetails + "'.");
                        yield null;
                    }
                    yield new ParticleAction(new ConfigParticle(particle, true, 1));
                default:
                    yield null;
            };
            if (action == null) continue;

            actions.add(action);
        }
        return actions;
    }
}
