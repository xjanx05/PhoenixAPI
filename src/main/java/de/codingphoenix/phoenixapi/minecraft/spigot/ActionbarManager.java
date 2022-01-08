package de.codingphoenix.phoenixapi.minecraft.spigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class ActionbarManager {
    public static void sendActionbar(Player p, String s) {
        try {
            Object chatComponentText =
                    getClass("ChatComponentText").getConstructor(String.class).newInstance(s);
            Object chatMessageType = getClass("ChatMessageType").getField("GAME_INFO").get(null);
            Object packetPlayOutChat =
                    getClass("PacketPlayOutChat")
                            .getConstructor(getClass("IChatBaseComponent"), getClass("ChatMessageType"))
                            .newInstance(chatComponentText, chatMessageType);
            Object getHandle = p.getClass().getMethod("getHandle", new Class[0]).invoke(p);
            Object playerConnection = getHandle.getClass().getField("playerConnection").get(getHandle);
            playerConnection
                    .getClass()
                    .getMethod("sendPacket", getClass("Packet"))
                    .invoke(playerConnection, packetPlayOutChat);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | NoSuchFieldException
                | ClassNotFoundException ex2) {
            try {
                Object icbc =
                        getClass("IChatBaseComponent$ChatSerializer")
                                .getMethod("a", String.class)
                                .invoke(null, "{'text': '" + s + "'}");
                Object ppoc2 =
                        getClass("PacketPlayOutChat")
                                .getConstructor(getClass("IChatBaseComponent"), Byte.TYPE)
                                .newInstance(icbc, (byte) 2);
                Object nmsp2 = p.getClass().getMethod("getHandle", new Class[0]).invoke(p);
                Object pcon2 = nmsp2.getClass().getField("playerConnection").get(nmsp2);
                pcon2.getClass().getMethod("sendPacket", getClass("Packet")).invoke(pcon2, ppoc2);
            } catch (InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException
                    | NoSuchMethodException
                    | NoSuchFieldException
                    | ClassNotFoundException ex3) {
            }
        }
    }

    private static Class<?> getClass(final String name) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return Class.forName("net.minecraft.server." + version + "." + name);
    }

}
