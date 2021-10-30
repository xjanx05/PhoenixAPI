package de.codingphoenix.phoenixapi.mc.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TPSManager {
    public static int TICK_COUNT = 0;
    public static long[] TICKS = new long[600];
    public static long LAST_TICK = 0L;

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int ticks) {
        if (TICK_COUNT < ticks) {
            return 20.0D;
        }
        int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }
    public static long getTPSRounded(){
        return Math.round(getTPS() * 100) / 100;
    }

    public static long getElapsed(int tickID) {
        if (TICK_COUNT - tickID >= TICKS.length) {
            return 20;
        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    public void start(Plugin plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();

                TICK_COUNT += 1;
            }
        }, 100, 1);
    }
}
