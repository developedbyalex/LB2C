package me.eeshe.lofiblockstocommand;

import me.eeshe.lofiblockstocommand.commands.*;
import me.eeshe.lofiblockstocommand.files.config.MainConfig;
import me.eeshe.lofiblockstocommand.files.config.MessageConfig;
import me.eeshe.lofiblockstocommand.files.config.ParticleConfig;
import me.eeshe.lofiblockstocommand.files.config.SoundConfig;
import me.eeshe.lofiblockstocommand.listeners.ActiveCommandBlockHandler;
import me.eeshe.lofiblockstocommand.managers.ActiveCommandBlockManager;
import me.eeshe.lofiblockstocommand.managers.CommandBlockManager;
import me.eeshe.lofiblockstocommand.managers.DataManager;
import me.eeshe.lofiblockstocommand.models.config.Message;
import me.eeshe.lofiblockstocommand.models.config.Particle;
import me.eeshe.lofiblockstocommand.models.config.Sound;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class LofiBlocksToCommand extends JavaPlugin {
    /**
     * HashMap that links all the subcommand Strings with their respective subcommand instance.
     */
    //           CommandName, SubcommandInstance
    private final Map<String, LofiCommand> subcommands = new LinkedHashMap<>();
    private final List<DataManager> dataManagers = new ArrayList<>();
    private MainConfig mainConfig;
    private CommandBlockManager commandBlockManager;
    private ActiveCommandBlockManager activeCommandBlockManager;

    /**
     * Creates and returns a static instance of the Plugin's main class.
     *
     * @return Instance of the main class of the plugin.
     */
    public static LofiBlocksToCommand getInstance() {
        return LofiBlocksToCommand.getPlugin(LofiBlocksToCommand.class);
    }

    @Override
    public void onEnable() {
        setupFiles();
        registerManagers();
        registerCommands();
        registerListeners();
        for (DataManager dataManager : dataManagers) {
            dataManager.load();
        }
    }

    /**
     * Creates and configures all the config files of the plugin.
     */
    public void setupFiles() {
        this.mainConfig = new MainConfig(this);
        mainConfig.writeDefaults();

        MessageConfig messageConfig = new MessageConfig(this);
        messageConfig.writeDefaults();
        Message.setConfig(messageConfig);

        SoundConfig soundConfig = new SoundConfig(this);
        soundConfig.writeDefaults();
        Sound.setConfig(soundConfig);

        ParticleConfig particleConfig = new ParticleConfig(this);
        particleConfig.writeDefaults();
        Particle.setConfig(particleConfig);
    }

    /**
     * Registers all the needed managers.
     */
    private void registerManagers() {
        this.commandBlockManager = new CommandBlockManager(this);
        this.activeCommandBlockManager = new ActiveCommandBlockManager(this);

        dataManagers.addAll(List.of(
                commandBlockManager,
                activeCommandBlockManager
        ));
    }

    /**
     * Registers all the commands, subcommands, CommandExecutors and TabCompleters regarding the plugin.
     */
    private void registerCommands() {
        List<LofiCommand> lofiCommands = Arrays.asList(
                new CommandCreate(),
                new CommandHelp(),
                new CommandReload(this)
        );
        for (LofiCommand lofiCommand : lofiCommands) {
            subcommands.put(lofiCommand.getName(), lofiCommand);
        }
        CommandExecutor commandExecutor = new CommandRunner(this);
        TabCompleter tabCompleter = new CommandCompleter(this);
        getCommand("lofiblockstocommand").setExecutor(commandExecutor);
        getCommand("lofiblockstocommand").setTabCompleter(tabCompleter);
    }

    /**
     * Registers all the event listeners the plugin might need.
     */
    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ActiveCommandBlockHandler(this), this);
    }

    @Override
    public void onDisable() {
        subcommands.clear();
        for (DataManager dataManager : dataManagers) {
            dataManager.unload();
        }
    }

    /**
     * Returns an instance of the subcommands HashMap.
     *
     * @return Instance of the subcommands HashMap.
     */
    public Map<String, LofiCommand> getSubcommands() {
        return subcommands;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public CommandBlockManager getCommandBlockManager() {
        return commandBlockManager;
    }

    public ActiveCommandBlockManager getActiveCommandBlockManager() {
        return activeCommandBlockManager;
    }
}
