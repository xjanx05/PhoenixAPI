package de.codingphoenix.phoenixapi.mc.spigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TabTitleManager {
    public static void setTablistToAll(String header, String footer) {
        for (Player current : Bukkit.getOnlinePlayers()) {
            setTablist(current, header, footer);
        }
    }

    public static void setTablist(Player p, String header, String footer) {
        if (header == null) {
            header = "";
        }
        if (footer == null) {
            footer = "";
        }
        try {
            Object tabHeader = BukkitUtils.getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
            Object tabFooter = BukkitUtils.getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
            Constructor<?> titleConstructor = BukkitUtils.getClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[0]);
            Object packet = titleConstructor.newInstance();
            try {
                Field aField = packet.getClass().getDeclaredField("a");
                aField.setAccessible(true);
                aField.set(packet, tabHeader);
                Field bField = packet.getClass().getDeclaredField("b");
                bField.setAccessible(true);
                bField.set(packet, tabFooter);
            } catch (Exception e) {
                Field aField2 = packet.getClass().getDeclaredField("header");
                aField2.setAccessible(true);
                aField2.set(packet, tabHeader);
                Field bField2 = packet.getClass().getDeclaredField("footer");
                bField2.setAccessible(true);
                bField2.set(packet, tabFooter);
            }
            try {
                Object handle = p.getClass().getMethod("getHandle", new Class[0]).invoke(p);
                Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
                playerConnection.getClass().getMethod("sendPacket", BukkitUtils.getClass("Packet")).invoke(playerConnection, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
