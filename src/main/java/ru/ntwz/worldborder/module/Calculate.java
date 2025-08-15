package ru.ntwz.worldborder.module;

import net.minecraft.util.math.Vec3d;

public class Calculate {
    public static double distance(Vec3d player) {
        return Math.sqrt(player.getX() * player.getX() + player.getZ() * player.getZ());
    }
}