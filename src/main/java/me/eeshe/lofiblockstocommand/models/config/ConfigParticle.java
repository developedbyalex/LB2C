package me.eeshe.lofiblockstocommand.models.config;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public record ConfigParticle(Particle particle, boolean enabled, int amount) {

    /**
     * Spawns the ConfigParticle at the passed location.
     *
     * @param location Location the particle will be spawned in.
     */
    public void spawn(Location location) {
        if (!enabled) return;
        World world = location.getWorld();
        if (world == null) return;

        location.getWorld().spawnParticle(particle, location, amount);
    }
}
