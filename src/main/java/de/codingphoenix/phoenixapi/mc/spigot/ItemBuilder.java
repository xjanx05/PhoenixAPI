package de.codingphoenix.phoenixapi.mc.spigot;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;


public class ItemBuilder {
    private int amount = 1;
    private String displayName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private String playerName;
    private ItemStack pre;
    private Material material;
    private Color armorColor;
    private Set<ItemFlag> flags = new HashSet<>();

    private boolean unbreakable;

    public ItemBuilder() {
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    /*
    Only for 1.16
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag... item) {
        flags.addAll(Arrays.asList(item));
        return this;
    }

    public ItemStack build() {
        ItemStack stack = this.pre != null ? this.pre : new ItemStack(this.material);
        ItemMeta stackMeta = stack.getItemMeta();
        if (this.displayName != null && stack.getType() != Material.AIR) {
            stackMeta.setDisplayName(this.displayName);
        }
        try {
            stackMeta.setUnbreakable(unbreakable);
        } catch (NoSuchMethodError e) {
        }
        if (!flags.isEmpty()) {
            for (ItemFlag flag : flags) {
                stackMeta.addItemFlags(flag);
            }
        }
        if (this.enchantments != null) {
            Iterator var3 = this.enchantments.keySet().iterator();

            while (var3.hasNext()) {
                Enchantment ent = (Enchantment) var3.next();
                stackMeta.addEnchant(ent, (Integer) this.enchantments.get(ent), true);
            }
        }

        if (this.lore != null) {
            stackMeta.setLore(this.lore);
        }

        stack.setItemMeta(stackMeta);
        stack.setAmount(this.amount);
        return stack;
    }

    public ItemBuilder setArmorColor(Color armorColor) {
        this.armorColor = armorColor;
        return this;
    }

    public ItemStack buildLeatherArmor() {
        ItemStack stack = this.pre != null ? this.pre : new ItemStack(this.material);
        if (stack.getItemMeta() instanceof LeatherArmorMeta) {
            LeatherArmorMeta stackMeta = (LeatherArmorMeta) stack.getItemMeta();
            try {
                stackMeta.setUnbreakable(unbreakable);
            } catch (Exception e) {
            }
            if (this.displayName != null && stack.getType() != Material.AIR) {
                stackMeta.setDisplayName(this.displayName);
            }
            if (!flags.isEmpty()) {
                for (ItemFlag flag : flags) {
                    stackMeta.addItemFlags(flag);
                }
            }
            if (armorColor != null) {
                stackMeta.setColor(armorColor);
            }
            if (this.enchantments != null) {
                Iterator var3 = this.enchantments.keySet().iterator();

                while (var3.hasNext()) {
                    Enchantment ent = (Enchantment) var3.next();
                    stackMeta.addEnchant(ent, (Integer) this.enchantments.get(ent), true);
                }
            }

            if (this.lore != null) {
                stackMeta.setLore(this.lore);
            }

            stack.setItemMeta(stackMeta);
            stack.setAmount(this.amount);
            return stack;
        } else {
            return null;
        }

    }

    public ItemStack buildSkull() {
        ItemStack stack = Materials.PLAYER_HEAD.parseItem();
        SkullMeta skullMeta = (SkullMeta) stack.getItemMeta();
        skullMeta.setDisplayName(this.displayName != null ? this.displayName : this.playerName);
        skullMeta.setOwner(this.playerName != null ? this.playerName : this.displayName);
        if (this.lore != null) {
            skullMeta.setLore(this.lore);
        }
        if (!flags.isEmpty()) {
            for (ItemFlag flag : flags) {
                skullMeta.addItemFlags(flag);
            }
        }
        stack.setItemMeta(skullMeta);
        stack.setAmount(this.amount);
        return stack;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int amplifier) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap();
        }

        this.enchantments.put(enchantment, amplifier);
        return this;
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder displayname(String displayname) {
        this.displayName = displayname;
        return this;
    }

    public ItemBuilder itemstack(ItemStack stack) {
        this.pre = stack.clone();
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder lore(String lore) {
        this.lore = Collections.singletonList(lore);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemBuilder player(Player player) {
        this.playerName = player.getName();
        return this;
    }

    public ItemBuilder playername(String playername) {
        this.playerName = playername;
        return this;
    }
}
