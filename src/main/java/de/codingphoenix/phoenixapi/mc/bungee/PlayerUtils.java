package de.codingphoenix.phoenixapi.mc.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerUtils {
    public static void sendPlayerToServer(Player player, String server, Plugin plugin) {
        if (!plugin.getServer().getMessenger().isOutgoingChannelRegistered(plugin, "BungeeCord")) {
            plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server.toLowerCase());
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

}
