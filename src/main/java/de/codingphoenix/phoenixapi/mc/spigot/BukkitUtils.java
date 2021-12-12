package de.codingphoenix.phoenixapi.mc.spigot;

import org.bukkit.Bukkit;

public class BukkitUtils {
    public static Class<?> getClass(final String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
