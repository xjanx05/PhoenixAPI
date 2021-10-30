package de.codingphoenix.phoenixapi.mc.languageAPI;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class LanguageAPI {
    private final JavaPlugin plugin;
    private HashMap<UUID, Language> playerLanguages = new HashMap<>();
    private ArrayList<LanguageFile> languageFiles = new ArrayList<>();

    public LanguageAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        try {
            createDirIfNotExists();
        } catch (IOException | IllegalStateException ex) {
            plugin.getLogger().log(Level.WARNING, "The Language-files could not be created!");
        }
    }

    public LanguageAPI(JavaPlugin plugin, String defaultLanguage) {
        this.plugin = plugin;
        try {
            createDirIfNotExists(defaultLanguage);
        } catch (IOException | IllegalStateException ex) {
            plugin.getLogger().log(Level.WARNING, "The Language-files could not be created!");
        }
    }

    public void createDirIfNotExists(String defaultLanguage) throws IOException {
        File folder = new File(plugin.getDataFolder().getPath() + "/language");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        for (Language language : Language.values()) {
            languageFiles.add(new LanguageFile(language, plugin, defaultLanguage));
        }
    }

    public void createDirIfNotExists() throws IOException {
        File folder = new File(plugin.getDataFolder().getPath() + "/language");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        for (Language language : Language.values()) {
            languageFiles.add(new LanguageFile(language, plugin));
        }
    }

    public void loadPlayerLanguage(UUID uuid) {
        if (playerLanguages.containsKey(uuid)) {
            playerLanguages.replace(uuid, Language.ENGLISH);
        } else {
            playerLanguages.put(uuid, Language.ENGLISH);
        }
    }

    public String getMsgFromPlayer(UUID uuid, String msg) {
        Language language = null;
        if (playerLanguages.containsKey(uuid)) {
            language = playerLanguages.get(uuid);
        } else {
            language = Language.ENGLISH;
            loadPlayerLanguage(uuid);
        }

        LanguageFile file = getFileByLanguage(language);
        return file.getMsgByKey(msg);
    }

    public String getNoPermissionMessage(UUID uuid) {
        return getMsgFromPlayer(uuid, "noPermission");
    }

    public String getWrongUsageMessage(UUID uuid, String usage) {
        return getMsgFromPlayer(uuid, "wrongUsage").replaceAll("%usage%", usage);
    }

    public LanguageFile getFileByLanguage(Language languages) {
        for (LanguageFile languageFile : languageFiles) {
            if (languageFile.getLanguage().equals(languages)) {
                return languageFile;
            }
        }
        return null;
    }
}
