package ru.ntwz.worldborder.module;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;

public class Effect {

    public static final List<String> BORDER_MESSAGES = List.of(
            "Вы вдруг почувствовали, как шевелится земля...",
            "Внезапно воздух заполнился странным ощущением...",
            "Кажется вы чувствуете себя свободнее..."
    );

    public static void borderChanged(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player.isSleeping()) {
                player.wakeUp();
            }
            StatusEffectInstance EFFECT_INSTANCE = new StatusEffectInstance(StatusEffects.DARKNESS, 4 * 20, 1, true, false, false);
            player.addStatusEffect(EFFECT_INSTANCE);
            player.playSoundToPlayer(SoundEvents.AMBIENT_CAVE.value(), SoundCategory.AMBIENT, 1, .2f);
            player.sendMessage(Text.of((String) Functions.getRandomFromList(BORDER_MESSAGES)), true);
        }
    }
}
