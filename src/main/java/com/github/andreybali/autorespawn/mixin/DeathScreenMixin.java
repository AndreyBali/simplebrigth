package com.github.andreybali.autorespawn.mixin;

import com.github.andreybali.autorespawn.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Shadow
    private int ticksSinceDeath;
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (ModConfig.INSTANCE.autoRespawn && ticksSinceDeath != 0 && ticksSinceDeath % 20 == 0) {
            MinecraftClient client = MinecraftClient.getInstance();
            if( !client.isInSingleplayer()) {
                client.player.requestRespawn();
            }
        }
    }
}
