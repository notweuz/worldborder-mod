package ru.ntwz.worldborder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ntwz.worldborder.config.ConfigManager;
import ru.ntwz.worldborder.module.Commands;
import ru.ntwz.worldborder.module.WorldBorderTick;

public class WorldBorder implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("World Border");

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
        LOGGER.info("Initializing World Border");

        LOGGER.info("Loaded config");
        Commands.registerCommands(LOGGER);
        LOGGER.info("Registered commands");

        WorldBorderTick worldBorderTick = new WorldBorderTick();

        ServerTickEvents.END_SERVER_TICK.register(worldBorderTick::tick);
    }
}
