package ru.ntwz.worldborder.module;

import net.minecraft.util.math.Vec3d;

public class Calculate {
    public static float distance(Vec3d playerPos, Vec3d spawnPos) {
        double deltaX = playerPos.x - spawnPos.x;
        double deltaZ = playerPos.z - spawnPos.z;
        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        return (float) horizontalDistance;
    }
}