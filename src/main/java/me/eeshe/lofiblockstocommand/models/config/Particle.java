package me.eeshe.lofiblockstocommand.models.config;

import me.eeshe.lofiblockstocommand.files.config.ParticleConfig;
import me.eeshe.lofiblockstocommand.util.ConfigUtil;
import org.bukkit.Location;

public enum Particle {
    ;

    private static ParticleConfig CONFIG;
    private final String path;
    private final org.bukkit.Particle defaultParticle;
    private final boolean defaultEnabled;
    private final int defaultAmount;

    Particle(String path, org.bukkit.Particle defaultParticle, boolean defaultEnabled, int defaultAmount) {
        this.path = path;
        this.defaultParticle = defaultParticle;
        this.defaultEnabled = defaultEnabled;
        this.defaultAmount = defaultAmount;
    }

    public static void setConfig(ParticleConfig config) {
        Particle.CONFIG = config;
    }

    /**
     * Spawns the particle at the passed location.
     *
     * @param location Location the particle will be spawned in.
     */
    public void spawn(Location location) {
        fetchConfigParticle().spawn(location);
    }

    /**
     * Fetches the ConfigParticle.
     *
     * @return ConfigParticle.
     */
    private ConfigParticle fetchConfigParticle() {
        ConfigParticle configParticle = ConfigUtil.fetchConfigParticle(CONFIG.getConfig(), path);
        if (configParticle == null) return createDefaultConfigParticle();

        return configParticle;
    }

    /**
     * Creates a ConfigParticle instance with the default values of the Particle.
     *
     * @return ConfigParticle instance with the default values of the Particle.
     */
    private ConfigParticle createDefaultConfigParticle() {
        return new ConfigParticle(defaultParticle, defaultEnabled, defaultAmount);
    }

    public String getPath() {
        return path;
    }

    public org.bukkit.Particle getDefaultParticle() {
        return defaultParticle;
    }

    public boolean getDefaultEnabled() {
        return defaultEnabled;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }
}

