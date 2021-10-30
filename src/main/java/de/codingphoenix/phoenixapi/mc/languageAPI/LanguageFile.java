package de.codingphoenix.phoenixapi.mc.languageAPI;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

public class LanguageFile {
    private final Language language;
    private final File languageFile;
    private final FileConfiguration configuration;
    private final JavaPlugin plugin;

    private final HashMap<String, String> msgs = new HashMap<>();

    public LanguageFile(Language language, JavaPlugin plugin) throws IOException {
        this.language = language;
        this.plugin = plugin;
        languageFile = new File(plugin.getDataFolder().getPath() + "/language/" + language.getLanguageName() + ".yml");
        if (!languageFile.exists()) {
            languageFile.createNewFile();
            configuration = YamlConfiguration.loadConfiguration(languageFile);
            setDefault("noPermission");
            setDefault("wrongUsage");
        } else {
            configuration = YamlConfiguration.loadConfiguration(languageFile);
        }
        load();
    }

    public LanguageFile(Language language, JavaPlugin plugin, String defaultLanguage) throws IOException {
        this.language = language;
        this.plugin = plugin;
        languageFile = new File(plugin.getDataFolder().getPath() + "/language/" + language.getLanguageName() + ".yml");
        if (!languageFile.exists()) {
            languageFile.createNewFile();
            try (InputStream in = plugin.getResource(defaultLanguage)) {
                OutputStream out = new FileOutputStream(languageFile);
                ByteStreams.copy(in, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
            configuration = YamlConfiguration.loadConfiguration(languageFile);
        } else {
            configuration = YamlConfiguration.loadConfiguration(languageFile);
        }
        load();

    }

    public void load() {
        ConfigurationSection section = configuration.getConfigurationSection("translate.");
        if (section == null) {
            plugin.getLogger().log(Level.WARNING, "Language '" + language.getLanguageName() + "' has no content!");
            return;
        }
        Set<String> configMessages = section.getKeys(false);
        if (configMessages.isEmpty()) {
            plugin.getLogger().log(Level.WARNING, "Language '" + language.getLanguageName() + "' has no content!");
            return;
        }
        for (String msgkey : configMessages) {
            Bukkit.broadcastMessage(String.valueOf(configuration.getString(msgkey)));
            msgs.put(msgkey.toLowerCase(), configuration.getString("translate." + msgkey));
        }
    }

    public boolean setDefault(String key) {
        configuration.set("translate." + key, key);
        try {
            configuration.save(languageFile);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getMsgByKey(String key) {
        if (msgs.containsKey(key.toLowerCase())) {
            return ChatColor.translateAlternateColorCodes('$', msgs.get(key.toLowerCase()));
        }
        setDefault(key);
        return key;
    }

    public HashMap<String, String> getMsgs() {
        return msgs;
    }

    public File getLanguageFile() {
        return languageFile;
    }

    public Language getLanguage() {
        return language;
    }
}
