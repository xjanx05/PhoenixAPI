package de.codingphoenix.phoenixapi.mc.spigot;

import org.bukkit.Material;

public class VersionHelper {
    public enum Version {
        BEFORE13, FROM13UPTO17, UPPER17
    }

    public static Version getServerVersion() {
        if (Material.getMaterial("BUNDLE") != null) {
            return Version.UPPER17;
        }
        if (Material.getMaterial("DRIED_KELP") != null) {
            return Version.FROM13UPTO17;
        }
        return Version.BEFORE13;
    }
}
