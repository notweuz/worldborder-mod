package ru.ntwz.worldborder.module;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import ru.ntwz.worldborder.config.ConfigManager;

import javax.swing.text.html.parser.Entity;
import java.util.Objects;

public class Commands {
    public static void registerCommands(Logger logger) {
        CommandRegistrationCallback.EVENT.register(((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            commandDispatcher.register(CommandManager.literal("border")
                    .requires(src -> Objects.requireNonNull(src.getPlayer()).hasPermissionLevel(2))
                    .then(CommandManager.argument("size", DoubleArgumentType.doubleArg())
                            .executes(ctx -> {
                                ConfigManager.getConfig().borderSize = DoubleArgumentType.getDouble(ctx, "size");
                                ConfigManager.saveConfig();
                                ctx.getSource().sendFeedback(() -> Text.literal(
                                        "Border size set to " + ConfigManager.getConfig().borderSize), true);
                                Effect.borderChanged(ctx.getSource().getServer());
                                return 0;
                            })));
            commandDispatcher.register(CommandManager.literal("sun-burn")
                    .requires(src -> Objects.requireNonNull(src.getPlayer()).hasPermissionLevel(2))
                    .then(CommandManager.argument("player", EntityArgumentType.entity())
                            .then(CommandManager.argument("seconds", IntegerArgumentType.integer())
                                    .executes(ctx -> {
                                        ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "player");
                                        int seconds = IntegerArgumentType.getInteger(ctx, "seconds");
                                        ctx.getSource().sendFeedback(() -> Text.literal("Set player " + player.getName() + " to vampire like for " + seconds + " seconds"), true);
                                        long timestamp = System.currentTimeMillis() + (seconds * 1000L);
                                        SunBurn.addPlayerToMap(player, timestamp);
                                        return 0;
                                    }))));
        }));
        logger.info("Commands registered!");
    }
}
