package me.eeshe.lofiblockstocommand.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.ListIterator;

/**
 * Utility class that eases the work with framed GUIs.
 */
public class MenuUtil {

    /**
     * Sets a frame that covers all the borders of the passed inventory using the passed Material.
     *
     * @param inventory     Inventory where frames will be added to.
     * @param frameMaterial Material used to set the frame.
     */
    public static void setFrame(Inventory inventory, Material frameMaterial) {
        ItemStack filler = getFiller(frameMaterial);
        int inventorySize = inventory.getSize();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if ((slot > 9 && slot < 17) || (slot > 18 && slot < 26 && inventorySize > 27)
                    || (slot > 27 && slot < 35 && inventorySize > 36)
                    || (slot > 36 && slot < 44 && inventorySize > 45)) {
                continue;
            }
            inventory.setItem(slot, filler);
        }
    }

    /**
     * Fills the empty slots of the passed Inventory with the passed Material.
     *
     * @param inventory    Inventory that will be filled.
     * @param fillMaterial Material that will be used to fill the empty slots of the inventory.
     */
    public static void fillEmpty(Inventory inventory, Material fillMaterial) {
        ItemStack filler = getFiller(fillMaterial);
        while (inventory.firstEmpty() != -1) {
            inventory.setItem(inventory.firstEmpty(), filler);
        }
    }

    /**
     * Adds page items depending on the passed arguments.
     * If page > 1 add a previous page item.
     * If hasNextPage == true add a next page item.
     *
     * @param inventory   Inventory where page items will be added to.
     * @param page        Current page number the inventory is in.
     * @param hasNextPage True if the inventory is supposed to have a next page.
     */
    public static void setPageItems(Inventory inventory, int page, boolean hasNextPage) {
        if (page > 1) {
            inventory.setItem(inventory.getSize() - 8, getGUIItem(Material.PAPER, "&6Previous Page", null));
        }
        if (hasNextPage) {
            inventory.setItem(53, getGUIItem(Material.PAPER, "&6Next Page", null));
        }
    }

    /**
     * Adds a back item in the top left corner of the inventory to be able to go back to certain inventories.
     *
     * @param inventory Inventory the back button will be added to.
     */
    public static void addBackItem(Inventory inventory) {
        inventory.setItem(0, getGUIItem(Material.RED_STAINED_GLASS_PANE, "&c&lGo Back", null));
    }

    /**
     * Creates and returns an ItemStack to be used on a GUI with the passed material, name and lore.
     *
     * @param material Material that will be used to create the item.
     * @param name     Name the item will have.
     * @param lore     Lore the item will have.
     * @return ItemStack created with the passed parameters.
     */
    public static ItemStack getGUIItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(StringUtil.formatColor(name));
        }
        if (lore != null) {
            ListIterator<String> loreIterator = lore.listIterator();
            while (loreIterator.hasNext()) {
                loreIterator.set(StringUtil.formatColor(loreIterator.next()));
            }
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Checks if the passed item is a GUI filler based on its item name.
     *
     * @param item ItemStack that will be checked.
     * @return True if the item's name is ' '
     */
    public static boolean isFiller(ItemStack item) {
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;

        return item.getItemMeta().getDisplayName().equals(" ");
    }

    /**
     * Calculates and returns the size of a framed inventory depending on the amount of items that will be added to the
     * inventory.
     *
     * @param itemAmount Amount of items that will be added to the inventory.
     * @return Amount of slots the inventory must have to fit the passed amount of items + the inventory frame.
     */
    public static int getFramedInventorySize(int itemAmount) {
        // 18 stands for the top and bottom row of frames of the inventory
        // Since both left and right columns of the inventory will be filled only 7 items fit in each inventory row
        int inventorySize = 18 + 9 * (int) Math.ceil(itemAmount / 7.0);
        if (inventorySize > 54) return 54;

        return Math.max(27, inventorySize);
    }

    /**
     * Calculates the initial index of a list of items that will be added to a framed inventory based on the passed page.
     *
     * @param page Current page of the inventory where the items will be added.
     * @return Index where the list iteration should start.
     */
    public static int getInitialIndex(int page) {
        return page == 1 ? 0 : (page - 1) * 28;
    }

    /**
     * Creates and returns a filler item, which means it will have as display name ' '. It uses the passed Material.
     *
     * @param material Material used to create the ItemStack.
     * @return ItemStack representing the filler item.
     */
    private static ItemStack getFiller(Material material) {
        ItemStack filler = new ItemStack(material);
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);
        return filler;
    }
}
