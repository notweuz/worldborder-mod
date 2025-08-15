package ru.ntwz.worldborder.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static BorderConfig config;

    public static void loadConfig() {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("world-border.json");

        try {
            if (!Files.exists(configFile)) {
                Files.createFile(configFile);

                config = new BorderConfig();
                config.soundPerTick = 15;
                config.borderSize = 1300.0;

                saveConfig();
            } else {
                config = GSON.fromJson(Files.newBufferedReader(configFile), BorderConfig.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConfig() {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("world-border.json");

        try (Writer writer = Files.newBufferedWriter(configFile)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BorderConfig getConfig() {
        return config;
    }
}
