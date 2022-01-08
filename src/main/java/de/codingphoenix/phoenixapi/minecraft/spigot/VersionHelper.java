package de.codingphoenix.phoenixapi.minecraft.spigot;

import org.bukkit.Material;

public class VersionHelper {
    public static Version getServerVersion() {
        if (Material.getMaterial("BUNDLE") != null) {
            return Version.UPPER17;
        }
        if (Material.getMaterial("DRIED_KELP") != null) {
            return Version.FROM13UPTO17;
        }
        return Version.BEFORE13;
    }

    public enum Version {
        BEFORE13, FROM13UPTO17, UPPER17
    }
}
