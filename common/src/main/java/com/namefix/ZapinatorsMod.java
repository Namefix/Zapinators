package com.namefix;

import com.namefix.registry.*;
import com.namefix.trade.ModVillagerTrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZapinatorsMod {
    public static final String MOD_ID = "zapinators";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        EntityRegistry.register();
        SoundRegistry.register();
        TabRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();

        ModVillagerTrades.register();

        LOGGER.info("--- Zapinators --- Mod --- initialized. ---");
    }
}
