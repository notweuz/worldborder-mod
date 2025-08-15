package ru.ntwz.worldborder.module;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class Functions {
    public static Object getRandomFromList(List<?> list) {
        return list.get((int) (Math.random() * list.size()));
    }

    public static boolean isPlayerUnderAnyBlockRayCast(ServerPlayerEntity player) {
        World world = player.getWorld();

        Vec3d headPos = player.getPos().add(0, player.getStandingEyeHeight(), 0);

        int topY = world.getHeight();
        Vec3d endPos = headPos.add(0, topY * 2, 0);

        BlockHitResult hit = world.raycast(new RaycastContext(
                headPos,
                endPos,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                player
        ));

        return hit.getType() == HitResult.Type.BLOCK;
    }
}
