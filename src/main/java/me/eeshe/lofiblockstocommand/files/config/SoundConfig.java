package me.eeshe.lofiblockstocommand.files.config;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.config.Sound;
import org.bukkit.configuration.file.FileConfiguration;

public class SoundConfig extends ConfigWrapper {

    public SoundConfig(LofiBlocksToCommand plugin) {
        super(plugin, null, "sounds.yml");
    }

    @Override
    public void writeDefaults() {
        FileConfiguration config = getConfig();
        for (Sound sound : Sound.values()) {
            String path = sound.getPath();
            config.addDefault(path + ".sound", sound.getDefaultSound().name());
            config.addDefault(path + ".enable", sound.getDefaultEnabled());
            config.addDefault(path + ".volume", sound.getDefaultVolume());
            config.addDefault(path + ".pitch", sound.getDefaultPitch());
        }
        config.options().copyDefaults(true);
        saveConfig();
    }
}
