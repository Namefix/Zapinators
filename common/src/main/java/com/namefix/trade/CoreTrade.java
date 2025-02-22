package com.namefix.trade;

import com.namefix.registry.ItemRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CoreTrade implements VillagerTrades.ItemListing {
	@Override
	public @Nullable MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
		int chance = randomSource.nextInt(0, 3);
		int price = randomSource.nextInt(chance==0?15:5,chance==0?25:15);

		return new MerchantOffer(new ItemCost(Items.EMERALD, price), Optional.empty(), new ItemStack(chance == 0 ? ItemRegistry.ORANGE_CORE : ItemRegistry.GRAY_CORE), 1, 1, 1.0f);
	}
}
