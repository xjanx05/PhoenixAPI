package de.xjanx05.xjavaapi.mc.spigot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class DebugMode implements CommandExecutor {
    private HashSet<UUID> debugMode = new HashSet<>();


    public boolean isInDebugMode(UUID uuid) {
        return debugMode.contains(uuid);
    }

    public void setDebugMode(UUID uuid) {
        if (!debugMode.contains(uuid)) {
            debugMode.add(uuid);
        }
    }

    public void unsetDebug(UUID uuid) {
        debugMode.remove(uuid);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (!sender.hasPermission("system.debugmode")) {
            return false;
        }
        UUID uuid = ((Player) sender).getUniqueId();
        if (debugMode.contains(uuid)) {
            debugMode.remove(uuid);
            sender.sendMessage("disabled");
        } else {
            debugMode.add(uuid);
            sender.sendMessage("enabled");
        }
        return false;
    }
}
