package com.github.andreybali.autorespawn;

import net.fabricmc.api.ClientModInitializer;

public class Main implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.init();
    }
}
