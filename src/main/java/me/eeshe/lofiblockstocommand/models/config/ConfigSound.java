package me.eeshe.lofiblockstocommand.models.config;

import org.bukkit.Location;

public record ConfigSound(org.bukkit.Sound sound, boolean enabled, float volume, float pitch) {

    public void play(Location location) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }
}
