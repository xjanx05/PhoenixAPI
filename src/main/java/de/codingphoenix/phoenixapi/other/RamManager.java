package de.codingphoenix.phoenixapi.other;

public class RamManager {
    public double getMaxRam() {
        return Runtime.getRuntime().maxMemory();
    }

    public double getFreeRam() {
        return Runtime.getRuntime().freeMemory();
    }

    public double getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public double getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }
}
