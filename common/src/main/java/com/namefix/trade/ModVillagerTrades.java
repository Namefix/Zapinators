package com.namefix.trade;

import dev.architectury.registry.level.entity.trade.TradeRegistry;

public class ModVillagerTrades {
    public static void register() {
        TradeRegistry.registerTradeForWanderingTrader(true, new ZapinatorTrade());
        TradeRegistry.registerTradeForWanderingTrader(false, new CoreTrade());
    }
}
