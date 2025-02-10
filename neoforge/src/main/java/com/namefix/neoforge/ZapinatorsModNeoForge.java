package com.namefix.neoforge;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

import com.namefix.ZapinatorsMod;

@Mod(ZapinatorsMod.MOD_ID)
public final class ZapinatorsModNeoForge {
    public ZapinatorsModNeoForge() {
        // Run our common setup.
        ZapinatorsMod.init();

        if(Platform.getEnv() == Dist.CLIENT) ZapinatorsMod.initClient();
    }
}
