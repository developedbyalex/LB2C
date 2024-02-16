package me.eeshe.lofiblockstocommand.models.config;

import me.eeshe.lofiblockstocommand.files.config.SoundConfig;
import me.eeshe.lofiblockstocommand.util.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Sound {
    SUCCESS("success", true, org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING, 0.6F, 1.6F),
    ERROR("error", true, org.bukkit.Sound.BLOCK_NOTE_BLOCK_BASS, 0.6F, 0.6F),
    COMMAND_BLOCK_CREATE("command-block-create", true, org.bukkit.Sound.BLOCK_BEACON_ACTIVATE, 0.6F, 1.2F),
    COMMAND_BLOCK_REMOVE("command-block-remove", true, org.bukkit.Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 0.6F, 1.2F);

    private static SoundConfig CONFIG;
    private final String path;
    private final boolean defaultEnabled;
    private final org.bukkit.Sound defaultSound;
    private final float defaultVolume;
    private final float defaultPitch;

    Sound(String path, boolean defaultEnabled, org.bukkit.Sound defaultSound, float defaultVolume, float defaultPitch) {
        this.path = path;
        this.defaultEnabled = defaultEnabled;
        this.defaultSound = defaultSound;
        this.defaultVolume = defaultVolume;
        this.defaultPitch = defaultPitch;
    }

    public static void setConfig(SoundConfig config) {
        Sound.CONFIG = config;
    }

    /**
     * Plays the sound to the passed CommandSender.
     *
     * @param sender CommandSender the sound will be played for.
     */
    public void play(CommandSender sender) {
        if (!(sender instanceof Player player)) return;

        ConfigSound configSound = fetchConfigSound();
        player.playSound(player, configSound.sound(), configSound.volume(), configSound.pitch());
    }

    /**
     * Plays teh sound in the passed location.
     *
     * @param location Location the sound will be played in.
     */
    public void play(Location location) {
        ConfigSound configSound = fetchConfigSound();
        if (location.getWorld() == null) return;

        location.getWorld().playSound(location, configSound.sound(), configSound.volume(), configSound.pitch());
    }

    /**
     * Fetches the ConfigSound
     *
     * @return ConfigSound.
     */
    private ConfigSound fetchConfigSound() {
        ConfigSound configParticle = ConfigUtil.fetchConfigSound(CONFIG.getConfig(), path);
        if (configParticle == null) return createDefaultConfigSound();

        return configParticle;
    }

    /**
     * Creates a ConfigSound instance with the default values of the Sound.
     *
     * @return ConfigSound instance with the default values of the Sound.
     */
    private ConfigSound createDefaultConfigSound() {
        return new ConfigSound(defaultSound, defaultEnabled, defaultVolume, defaultPitch);
    }

    public String getPath() {
        return path;
    }

    public boolean getDefaultEnabled() {
        return defaultEnabled;
    }

    public org.bukkit.Sound getDefaultSound() {
        return defaultSound;
    }

    public float getDefaultVolume() {
        return defaultVolume;
    }

    public float getDefaultPitch() {
        return defaultPitch;
    }
}
