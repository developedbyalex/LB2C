package me.eeshe.lofiblockstocommand.files.config;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.config.Message;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageConfig extends ConfigWrapper {

    public MessageConfig(LofiBlocksToCommand plugin) {
        super(plugin, null, "messages.yml");
    }

    @Override
    public void writeDefaults() {
        FileConfiguration config = getConfig();
        for (Message message : Message.values()) {
            config.addDefault(message.getPath(), message.getDefaultValue());
        }
        config.options().copyDefaults(true);
        saveConfig();
    }
}
