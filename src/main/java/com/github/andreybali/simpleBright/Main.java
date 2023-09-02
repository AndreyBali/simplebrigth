package com.github.andreybali.simpleBright;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Main implements ClientModInitializer {
    private static KeyBinding configGuiBind;
    @Override
    public void onInitializeClient() {
        ModConfig.init();

        configGuiBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.simpleBright.configGui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category.simpleBright.simpleBright"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configGuiBind.wasPressed()) {
                MinecraftClient.getInstance().setScreen(AutoConfig.getConfigScreen(ModConfig.class, MinecraftClient.getInstance().currentScreen).get());
            }
        });
    }
}
