package com.namefix;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.loot.ModLootTables;
import com.namefix.registry.*;
import com.namefix.trade.ModVillagerTrades;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.parser.Entity;

public final class ZapinatorsMod {
    public static final String MOD_ID = "zapinators";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);

    public static void init() {
        CONFIGURATOR.register(ZapinatorsConfig.class);

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
