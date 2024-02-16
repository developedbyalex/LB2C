package me.eeshe.lofiblockstocommand.managers;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;

public abstract class DataManager {
    private final LofiBlocksToCommand plugin;

    public DataManager(LofiBlocksToCommand plugin) {
        this.plugin = plugin;
    }

    /**
     * Functions that will be run when the plugin enables.
     */
    public void onEnable() {
        load();
    }

    /**
     * Loads the DataManager.
     */
    public abstract void load();

    /**
     * Functions that will be run when the plugin disables.
     */
    public void onDisable() {
        unload();
    }

    /**
     * Unloads the DataManager.
     */
    public abstract void unload();

    public LofiBlocksToCommand getPlugin() {
        return plugin;
    }
}
