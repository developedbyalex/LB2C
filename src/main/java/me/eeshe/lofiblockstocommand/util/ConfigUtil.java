package me.eeshe.lofiblockstocommand.util;

import me.eeshe.lofiblockstocommand.models.config.ConfigParticle;
import me.eeshe.lofiblockstocommand.models.config.ConfigSound;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.ListIterator;

public class ConfigUtil {

    /**
     * Fetches the configured ConfigSound.
     *
     * @return Configured ConfigSound.
     */
    public static ConfigSound fetchConfigSound(FileConfiguration config, String path) {
        ConfigurationSection soundSection = config.getConfigurationSection(path);
        if (soundSection == null) return null;

        String soundName = soundSection.getString("sound");
        org.bukkit.Sound sound;
        try {
            sound = org.bukkit.Sound.valueOf(soundName);
        } catch (Exception e) {
            LogUtil.sendWarnLog("Unknown sound '" + soundName + "' configured for '" + path + "'.");
            return null;
        }
        boolean enabled = config.getBoolean(path + ".enable");
        float volume = (float) config.getDouble(path + ".volume");
        float pitch = (float) config.getDouble(path + ".pitch");

        return new ConfigSound(sound, enabled, volume, pitch);
    }

    /**
     * Fetches the configured ConfigParticle.
     *
     * @param config Configuration file.
     * @param path   Path of the particle.
     * @return Configured ConfigParticle.
     */
    public static ConfigParticle fetchConfigParticle(FileConfiguration config, String path) {
        ConfigurationSection particleSection = config.getConfigurationSection(path);
        if (particleSection == null) return null;

        String particleName = particleSection.getString("particle");
        org.bukkit.Particle particle;
        try {
            particle = org.bukkit.Particle.valueOf(particleName);
        } catch (Exception e) {
            LogUtil.sendWarnLog("Unknown particle '" + particleName + "' configured for '" + path + "'.");
            return null;
        }
        boolean enabled = particleSection.getBoolean("enable");
        int amount = particleSection.getInt("amount");

        return new ConfigParticle(particle, enabled, amount);
    }

    /**
     * Fetches the configured material in the passed path within the passed config file.
     *
     * @param config Configuration file the material will be fetched from.
     * @param path   Configuration path the material will be fetched from.
     * @return Configured material in the passed path within the passed config file.
     */
    public static Material fetchMaterial(FileConfiguration config, String path) {
        String generatedItemName = config.getString(path, "");
        Material generatedItem = Material.matchMaterial(generatedItemName);
        if (generatedItem == null) {
            LogUtil.sendWarnLog("Unknown item '" + generatedItemName + "' configured in '" + path + "'.");
            return null;
        }
        return generatedItem;
    }

    /**
     * Returns the configured ItemStack found in the passed path.
     *
     * @param config Config file the item will be fetched from.
     * @param path   Configuration path where item will be searched.
     * @return Configured ItemStack found in the passed path.
     */
    public static ItemStack fetchConfigItemStack(FileConfiguration config, String path) {
        ConfigurationSection itemSection = config.getConfigurationSection(path);
        if (itemSection == null) {
            LogUtil.sendWarnLog("Missing configurations for '" + path + "'.");
            return null;
        }
        // Fetch item data
        String materialName = itemSection.getString("material");
        if (materialName == null) {
            LogUtil.sendWarnLog("Item name not provided for item settings '" + path + "'.");
            return null;
        }
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            LogUtil.sendWarnLog("Unknown item '" + materialName + "' found in item settings '" + path + "'.");
            return null;
        }
        int amount = itemSection.getInt("amount", 1);
        String name = itemSection.getString("name");
        List<String> lore = itemSection.getStringList("lore");

        // Create ItemStack
        ItemStack item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        if (name != null) {
            meta.setDisplayName(StringUtil.formatColor(name));
        }
        if (!lore.isEmpty()) {
            ListIterator<String> loreIterator = lore.listIterator();
            while (loreIterator.hasNext()) {
                loreIterator.set(StringUtil.formatColor(loreIterator.next()));
            }
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }
}
