package com.namefix.fabric.client;

import com.namefix.ZapinatorsMod;
import net.fabricmc.api.ClientModInitializer;

public final class ZapinatorsModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ZapinatorsMod.initClient();
    }
}
