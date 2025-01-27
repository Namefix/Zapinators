package com.namefix;

import com.namefix.registry.EntityRegistry;
import com.namefix.registry.ItemRegistry;
import com.namefix.registry.SoundRegistry;
import com.namefix.registry.TabRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZapinatorsMod {
    public static final String MOD_ID = "zapinators";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        EntityRegistry.register();
        SoundRegistry.register();
        TabRegistry.register();
        ItemRegistry.register();

        LOGGER.info("--- Zapinator --- Mod --- initialized. ---");
    }
}
