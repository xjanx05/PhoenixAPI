package de.codingphoenix.phoenixapi.minecraft;

import java.util.UUID;

public class MinecraftUtils {
    public static String getSkullLink(UUID uuid, int pixel) {
        return "https://cravatar.eu/helmavatar/" + uuid + "/" + pixel;
    }
}
