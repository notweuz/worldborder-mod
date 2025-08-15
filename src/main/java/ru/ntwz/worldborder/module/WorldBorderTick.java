package ru.ntwz.worldborder.module;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import ru.ntwz.worldborder.config.BorderConfig;

public class WorldBorderTick {
    private int TICK_COUNTER = 0;

    private final Vec3d SPAWN_POS = new Vec3d(0, 0, 0);

    public void tick(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            if (world.getRegistryKey() != ServerWorld.OVERWORLD) continue;

            for (PlayerEntity player : world.getPlayers()) {
                TICK_COUNTER++;

                Vec3d playerPos = player.getPos();
                double deltaX = playerPos.x - SPAWN_POS.x;
                double deltaZ = playerPos.z - SPAWN_POS.z;
                double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
                float distanceFromBorder = (float) horizontalDistance;

                if (distanceFromBorder > BorderConfig.BORDER_SIZE) {
                    if (TICK_COUNTER >= BorderConfig.SOUND_PER_TICKS) {
                        TICK_COUNTER = 0;
                        player.playSoundToPlayer(SoundEvents.BLOCK_TRIAL_SPAWNER_AMBIENT_OMINOUS, SoundCategory.AMBIENT, distanceFromBorder / 28, .2f);
                    }

                    StatusEffectInstance EFFECT_INSTANCE = new StatusEffectInstance(StatusEffects.DARKNESS, 3 * 20, 1, true, false, false);
                    
                    player.addStatusEffect(EFFECT_INSTANCE);
                    double distanceBeyondBorder = distanceFromBorder - BorderConfig.BORDER_SIZE;
                    float damage = (float) (distanceBeyondBorder * 0.1);
                    player.damage(world, world.getDamageSources().outsideBorder(), damage);
                }
            }
        }
    }
}