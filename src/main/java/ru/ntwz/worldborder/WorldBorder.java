package ru.ntwz.worldborder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ntwz.worldborder.module.WorldBorderTick;

public class WorldBorder implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("World Border");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing World Border");

        WorldBorderTick worldBorderTick = new WorldBorderTick();

        ServerTickEvents.END_SERVER_TICK.register(worldBorderTick::tick);
    }
}
