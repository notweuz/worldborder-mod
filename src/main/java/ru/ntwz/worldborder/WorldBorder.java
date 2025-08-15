package ru.ntwz.worldborder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ntwz.worldborder.config.ConfigManager;
import ru.ntwz.worldborder.module.Commands;
import ru.ntwz.worldborder.module.SunBurn;
import ru.ntwz.worldborder.module.WorldBorderTick;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldBorder implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("World Border");

    public static ConcurrentHashMap<UUID, Long> vampireLikePlayers = new ConcurrentHashMap<>();

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
        LOGGER.info("Initializing World Border");

        LOGGER.info("Loaded config");
        Commands.registerCommands(LOGGER);
        LOGGER.info("Registered commands");

        WorldBorderTick worldBorderTick = new WorldBorderTick();
        SunBurn sunBurn = new SunBurn();

        ServerTickEvents.END_SERVER_TICK.register(s -> {
            worldBorderTick.tick(s);
            sunBurn.tick(s);
        });
    }
}
