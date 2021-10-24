package de.xjanx05.xjavaapi.mc.spigot;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryBuilder {
    private int lines;
    private String name;
    private ItemStack fill;
    private HashMap<Integer, ItemStack> items = new HashMap<>();
    private Inventory inventory;


    public Inventory build() {
        if (name == null || lines == 0) {
            return null;
        }
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, lines * 9, name);
        }
        if (fill != null) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, fill);
            }
        }

        for (Integer integer : items.keySet()) {
            inventory.setItem(integer, items.get(integer));
        }
        return inventory;
    }

    public InventoryBuilder item(int slot, ItemStack item) {
        if (items.containsKey(slot)) {
            items.replace(slot, item);
        } else {
            items.put(slot, item);
        }
        return this;
    }

    public int getLines() {
        return lines;
    }

    public InventoryBuilder lines(int lines) {
        this.lines = lines;
        return this;
    }

    public String getName() {
        return name;
    }

    public InventoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemStack getFill() {
        return fill;
    }

    public InventoryBuilder fill(ItemStack fill) {
        this.fill = fill;
        return this;
    }
}
