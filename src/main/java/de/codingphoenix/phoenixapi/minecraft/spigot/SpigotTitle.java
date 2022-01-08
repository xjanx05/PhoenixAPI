package de.codingphoenix.phoenixapi.minecraft.spigot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SpigotTitle {
    private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();
    private final HashMap<UUID, Integer> timerIDS = new HashMap<>();
    private final HashMap<UUID, Integer> steps = new HashMap<>();
    private final HashMap<UUID, Boolean> reves = new HashMap<>();
    private Class<?> packetTitle;
    private Class<?> packetActions;
    private Class<?> nmsChatSerializer;
    private Class<?> chatBaseComponent;
    private String title = "";
    private ChatColor titleColor = ChatColor.WHITE;
    private String subtitle = "";
    private ChatColor subtitleColor = ChatColor.WHITE;
    private int fadeInTime = -1;
    private int stayTime = -1;
    private int fadeOutTime = -1;
    private boolean ticks = false;

    public SpigotTitle(String title) {
        this.title = title;
        loadClasses();
    }


    public SpigotTitle(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        loadClasses();
    }


    public SpigotTitle(SpigotTitle title) {
        this.title = title.title;
        this.subtitle = title.subtitle;
        this.titleColor = title.titleColor;
        this.subtitleColor = title.subtitleColor;
        this.fadeInTime = title.fadeInTime;
        this.fadeOutTime = title.fadeOutTime;
        this.stayTime = title.stayTime;
        this.ticks = title.ticks;
        loadClasses();
    }


    public SpigotTitle(String title, String subtitle, int fadeInTime, int stayTime,
                       int fadeOutTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;
        loadClasses();
    }

    private void loadClasses() {
        packetTitle = getNMSClass("PacketPlayOutTitle");
        packetActions = getNMSClass("EnumTitleAction");
        Bukkit.broadcastMessage(String.valueOf(packetActions));
        chatBaseComponent = getNMSClass("IChatBaseComponent");
        nmsChatSerializer = getNMSClass("ChatSerializer");
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitleColor(ChatColor color) {
        this.titleColor = color;
    }

    public void setSubtitleColor(ChatColor color) {
        this.subtitleColor = color;
    }

    public void setFadeInTime(int time) {
        this.fadeInTime = time;
    }

    public void setFadeOutTime(int time) {
        this.fadeOutTime = time;
    }

    public void setStayTime(int time) {
        this.stayTime = time;
    }
    /*
    public void send(Player player) {
        try {
            Bukkit.broadcastMessage("sebded");
            if (title == null) {
                title = " ";
            }
            if (subtitle == null) {
                subtitle = " ";
            }
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + title + "\"}");
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime * (ticks ? 1 : 20),
                    stayTime * (ticks ? 1 : 20),
                    fadeOutTime * (ticks ? 1 : 20));

            Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + subtitle + "\"}");
            Constructor<?> stitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object spacket = stitleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime * (ticks ? 1 : 20),
                    stayTime * (ticks ? 1 : 20),
                    fadeOutTime * (ticks ? 1 : 20));

            sendPacket(player, packet);
            sendPacket(player, spacket);
        } catch (Exception ex) {
        }
    }
     */

    public void setTimingsToTicks() {
        ticks = true;
    }

    public void setTimingsToSeconds() {
        ticks = false;
    }

    /*public void send(Player player) {
        if (packetTitle != null) {
            resetTitle(player);
            try {
                Object handle = getHandle(player);
                Object connection = getField(handle.getClass(),
                        "playerConnection").get(handle);
                Object[] actions = packetActions.getEnumConstants();
                Method sendPacket = getMethod(connection.getClass(),
                        "sendPacket");
                Object packet = packetTitle.getConstructor(packetActions,
                        chatBaseComponent, Integer.TYPE, Integer.TYPE,
                        Integer.TYPE).newInstance(actions[2], null,
                        fadeInTime * (ticks ? 1 : 20),
                        stayTime * (ticks ? 1 : 20),
                        fadeOutTime * (ticks ? 1 : 20));
                if (fadeInTime != -1 && fadeOutTime != -1 && stayTime != -1)
                    sendPacket.invoke(connection, packet);
                Object serialized = getMethod(nmsChatSerializer, "a",
                        String.class).invoke(
                        null,
                        "{text:\""
                                + ChatColor.translateAlternateColorCodes('&',
                                title) + "\",color:"
                                + titleColor.name().toLowerCase() + "}");
                packet = packetTitle.getConstructor(packetActions,
                        chatBaseComponent).newInstance(actions[0], serialized);
                sendPacket.invoke(connection, packet);
                if (subtitle != "") {
                    serialized = getMethod(nmsChatSerializer, "a", String.class)
                            .invoke(null,
                                    "{text:\""
                                            + ChatColor
                                            .translateAlternateColorCodes(
                                                    '&', subtitle)
                                            + "\",color:"
                                            + subtitleColor.name()
                                            .toLowerCase() + "}");
                    packet = packetTitle.getConstructor(packetActions,
                            chatBaseComponent).newInstance(actions[1],
                            serialized);
                    sendPacket.invoke(connection, packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     */
    public void send(Player p) {
        if (title == null || title.equals("")) {
            title = " ";
        }
        if (subtitle == null || subtitle.equals("")) {
            subtitle = " ";
        }
        p.sendTitle(title, subtitle);
    }

    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception ex) {
        }
    }

    public Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server"
                    + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException ex) {
        }
        return null;
    }

    public void broadcast() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p);
        }
    }

    public void startAnimation(JavaPlugin plugin, String[] animations, int speed, Player target, boolean backAndForth) {
        UUID uuid = target.getUniqueId();
        if (timerIDS.containsKey(uuid)) {
            stopAnimation(uuid);
        }
        setFadeInTime(0);
        setStayTime(speed + 20);

        int timerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (!target.isOnline()) {
                    stopAnimation(uuid);

                    return;
                }
                int step = -1;
                boolean neg = false;
                if (steps.containsKey(target.getUniqueId())) {
                    step = steps.get(target.getUniqueId());
                }
                if (reves.containsKey(target.getUniqueId())) {
                    neg = reves.get(target.getUniqueId());
                }
                if (step + 1 >= animations.length) {
                    if (backAndForth) {
                        neg = true;
                        step--;
                    } else {
                        step = 0;
                    }
                } else {
                    if (neg) {
                        if (step <= 0) {
                            neg = false;
                            step++;
                        } else {
                            step--;
                        }
                    } else {
                        step++;
                    }
                }
                //setTitle(animations[step]);
                setSubtitle(animations[step]);
                send(target);
                steps.remove(target.getUniqueId());
                reves.remove(target.getUniqueId());
                steps.put(target.getUniqueId(), step);
                reves.put(target.getUniqueId(), neg);
            }
        }, 0, speed);
        timerIDS.put(target.getUniqueId(), timerID);
    }

    public void stopAnimation(UUID uniqueId) {
        if (timerIDS.containsKey(uniqueId)) {
            Bukkit.getScheduler().cancelTask(timerIDS.remove(uniqueId));
        }
        steps.remove(uniqueId);
        reves.remove(uniqueId);
    }
}