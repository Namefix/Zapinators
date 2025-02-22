package com.namefix;

import com.namefix.loot.ModLootTables;
import com.namefix.registry.*;
import com.namefix.trade.ModVillagerTrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.parser.Entity;

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
        ModLootTables.register();

        LOGGER.info("--- Zapinators --- Mod --- initialized. ---");
    }

    public static void initClient() {
        EntityRegistry.registerRenderers();
    }
}
