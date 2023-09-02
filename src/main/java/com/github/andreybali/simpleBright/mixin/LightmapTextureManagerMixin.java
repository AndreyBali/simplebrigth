package com.github.andreybali.simpleBright.mixin;

import com.github.andreybali.simpleBright.ModConfig;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;setColor(III)V"), index = 2)
    private int onSetColor(int color) {
        return makeColorBrighter(color, ModConfig.INSTANCE.brightLevel);
    }

    @Unique
    public int makeColorBrighter(int color, float factor) {
        if(factor == 1) return color;
        // Extract the alpha (transparency) value
        int alpha = (color >> 24) & 0xFF;

        // Extract red, green, and blue values
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        // Increase intensity
        red = clamp((int)(red * factor));
        green = clamp((int)(green * factor));
        blue = clamp((int)(blue * factor));

        // Reconstruct the color
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
    @Unique
    private int clamp(int val) {
        return Math.max(0, Math.min(255, val));
    }
}
