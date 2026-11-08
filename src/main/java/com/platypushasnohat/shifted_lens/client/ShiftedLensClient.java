package com.platypushasnohat.shifted_lens.client;

import com.platypushasnohat.shifted_lens.ShiftedLens;
import com.platypushasnohat.shifted_lens.ShiftedLensConfig;
import com.platypushasnohat.shifted_lens.events.ClientEvents;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.neoforged.fml.config.ModConfig;

@Environment(EnvType.CLIENT)
public class ShiftedLensClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ForgeConfigRegistry.INSTANCE.register(ShiftedLens.MOD_ID, ModConfig.Type.CLIENT, ShiftedLensConfig.CLIENT_CONFIG, "shifted-lens-client.toml");

        ClientEvents.init();
    }
}
