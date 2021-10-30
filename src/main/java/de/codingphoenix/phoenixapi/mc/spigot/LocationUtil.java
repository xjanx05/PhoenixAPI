package de.codingphoenix.phoenixapi.mc.spigot;

import org.bukkit.Location;

import java.util.ArrayList;

public class LocationUtil {
    public static ArrayList<Location> generateLocationsHorizontal(Location middle, double radius, int amount) {
        double alpha = (2 * Math.PI) / amount;

        ArrayList<Location> locs = new ArrayList<>();

        for (int count = 0; count != amount; count++) {
            double beta = alpha * count;

            double x = radius * Math.cos(beta);
            double z = radius * Math.sin(beta);

            Location loc = new Location(middle.getWorld(), x + middle.getX(), middle.getY(), middle.getZ() + z);
            locs.add(loc);
        }

        return locs;
    }

    public static ArrayList<Location> generateLocationsVertical(Location middle, double radius, int amount, boolean stayX) {
        double alpha = (2 * Math.PI) / amount;
        ArrayList<Location> locs = new ArrayList<>();

        if (stayX) {
            for (int count = 0; count != amount; count++) {
                double beta = alpha * count;

                double z = radius * Math.cos(beta);
                double y = radius * Math.sin(beta);

                Location loc = new Location(middle.getWorld(), middle.getX(), middle.getY() + y, middle.getBlockZ() + z);
                locs.add(loc);
            }
        } else {
            for (int count = 0; count != amount; count++) {
                double beta = alpha * count;

                double x = radius * Math.cos(beta);
                double y = radius * Math.sin(beta);

                Location loc = new Location(middle.getWorld(), x + middle.getX(), middle.getY() + y, middle.getBlockZ());
                locs.add(loc);
            }
        }
        return locs;
    }
}
