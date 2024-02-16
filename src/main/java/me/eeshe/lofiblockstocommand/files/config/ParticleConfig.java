package me.eeshe.lofiblockstocommand.files.config;

import me.eeshe.lofiblockstocommand.LofiBlocksToCommand;
import me.eeshe.lofiblockstocommand.models.config.Particle;
import org.bukkit.configuration.file.FileConfiguration;

public class ParticleConfig extends ConfigWrapper {

    public ParticleConfig(LofiBlocksToCommand plugin) {
        super(plugin, null, "particles.yml");
    }

    @Override
    public void writeDefaults() {
        FileConfiguration config = getConfig();
        for (Particle particle : Particle.values()) {
            String path = particle.getPath();
            config.addDefault(path + ".particle", particle.getDefaultParticle().name());
            config.addDefault(path + ".enable", particle.getDefaultEnabled());
            config.addDefault(path + ".amount", particle.getDefaultAmount());
        }
        config.options().copyDefaults(true);
        saveConfig();
    }
}
