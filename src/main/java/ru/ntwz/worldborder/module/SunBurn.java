package ru.ntwz.worldborder.module;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.ntwz.worldborder.WorldBorder;

public class SunBurn {

    public static void addPlayerToMap(ServerPlayerEntity player, long timestamp) {
        WorldBorder.vampireLikePlayers.put(player.getUuid(), timestamp);
    }

    public static void removePlayerFromMap(ServerPlayerEntity player) {
        WorldBorder.vampireLikePlayers.remove(player.getUuid());
    }

    public void tick(MinecraftServer server) {
        WorldBorder.vampireLikePlayers.forEach((u, t) -> {
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(u);

            if (player == null) return;

            if (System.currentTimeMillis() - t >= 0) {
                removePlayerFromMap(player);
                return;
            }

            if (player.isTouchingWaterOrRain() || Functions.isPlayerUnderAnyBlockRayCast(player)) {
                if (!player.isOnFire()) return;
                player.extinguishWithSound();
                return;
            }

            player.setOnFire(true);
        });
    }

}
