package com.namefix;

import com.namefix.client.ManaRenderer;
import com.namefix.client.ZapinatorsClient;
import com.namefix.config.ZapinatorsConfig;
import com.namefix.loot.ModLootTables;
import com.namefix.network.ZapinatorsNetwork;
import com.namefix.registry.*;
import com.namefix.server.ZapinatorsServer;
import com.namefix.trade.ModVillagerTrades;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        AttributeRegistry.register();

        ModVillagerTrades.register();
        ModLootTables.register();

        ZapinatorsNetwork.initialize();

        PlayerEvent.PLAYER_JOIN.register(ZapinatorsServer::sendInitialSync);
        PlayerEvent.PLAYER_RESPAWN.register(ZapinatorsServer::onPlayerRespawn);
        TickEvent.SERVER_LEVEL_POST.register(ZapinatorsServer::tick);

        LOGGER.info("--- Zapinators --- Mod --- initialized. ---");
    }

    public static void initClient() {
        EntityRegistry.registerRenderers();

        ClientGuiEvent.RENDER_HUD.register(ManaRenderer::render);
        ClientTickEvent.CLIENT_LEVEL_POST.register(ZapinatorsClient::tick);
    }
}
