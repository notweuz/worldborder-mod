package ru.ntwz.worldborder.module;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import ru.ntwz.worldborder.config.ConfigManager;

public class WorldBorderTick {
    private int TICK_COUNTER = 0;

    private final Vec3d SPAWN_POS = new Vec3d(0, 0, 0);

    public void tick(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            if (world.getRegistryKey() != ServerWorld.OVERWORLD) continue;

            for (PlayerEntity player : world.getPlayers()) {
                TICK_COUNTER++;

                Vec3d playerPos = player.getPos();
                float distanceFromBorder = Calculate.distance(playerPos, SPAWN_POS);

                if (distanceFromBorder > ConfigManager.getConfig().borderSize) {
                    if (TICK_COUNTER >= ConfigManager.getConfig().soundPerTick) {
                        TICK_COUNTER = 0;
                        player.playSoundToPlayer(SoundEvents.BLOCK_TRIAL_SPAWNER_AMBIENT_OMINOUS, SoundCategory.AMBIENT, distanceFromBorder / 28, .2f);
                    }

                    StatusEffectInstance EFFECT_INSTANCE = new StatusEffectInstance(StatusEffects.DARKNESS, 3 * 20, 1, true, false, false);
                    
                    player.addStatusEffect(EFFECT_INSTANCE);
                    double distanceBeyondBorder = distanceFromBorder - ConfigManager.getConfig().borderSize;
                    float damage = (float) (distanceBeyondBorder * 0.1);
                    player.damage(world, world.getDamageSources().outsideBorder(), damage);
                }
            }
        }
    }
}