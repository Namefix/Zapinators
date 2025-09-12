package com.namefix.loot;

import com.namefix.registry.ItemRegistry;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class ModLootTables {
	public static void register() {
		LootEvent.MODIFY_LOOT_TABLE.register(ModLootTables::modifyLootTable);
	}

	private static void modifyLootTable(ResourceKey<LootTable> key, LootEvent.LootTableModificationContext context, boolean builtin) {
		if(builtin) {
			if(key.location().toString().equals("minecraft:entities/wither")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.25f))
						.add(LootItem.lootTableItem(ItemRegistry.LASER_RIFLE.get()));
				context.addPool(builder);
			}

			// zapinator reset smithing template
			if(key.location().toString().equals("minecraft:chests/desert_pyramid")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.30f))
						.add(LootItem.lootTableItem(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/trial_chambers/reward_unique")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.25f))
						.add(LootItem.lootTableItem(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/bastion_treasure")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.5f))
						.add(LootItem.lootTableItem(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()));
				context.addPool(builder);
			}

			// red core
			if(key.location().toString().equals("minecraft:chests/ruined_portal")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.2f))
						.add(LootItem.lootTableItem(ItemRegistry.RED_CORE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/nether_bridge")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.35f))
						.add(LootItem.lootTableItem(ItemRegistry.RED_CORE.get()));
				context.addPool(builder);
			}

			// green core
			if(key.location().toString().equals("minecraft:chests/simple_dungeon")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.3f))
						.add(LootItem.lootTableItem(ItemRegistry.GREEN_CORE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/abandoned_mineshaft")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.3f))
						.add(LootItem.lootTableItem(ItemRegistry.GREEN_CORE.get()));
				context.addPool(builder);
			}

			// blue core
			if(key.location().toString().equals("minecraft:chests/igloo_chest")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.4f))
						.add(LootItem.lootTableItem(ItemRegistry.BLUE_CORE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/village/village_taiga_house")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.4f))
						.add(LootItem.lootTableItem(ItemRegistry.BLUE_CORE.get()));
				context.addPool(builder);
			}

			// purple core
			if(key.location().toString().equals("minecraft:chests/village/village_weaponsmith")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.3f))
						.add(LootItem.lootTableItem(ItemRegistry.PURPLE_CORE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/stronghold_library")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.3f))
						.add(LootItem.lootTableItem(ItemRegistry.PURPLE_CORE.get()));
				context.addPool(builder);
			}

			// rune
			if(key.location().toString().equals("minecraft:chests/bastion_treasure")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.5f))
						.add(LootItem.lootTableItem(ItemRegistry.RUNE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/trial_chambers/reward_ominous_unique")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.5f))
						.add(LootItem.lootTableItem(ItemRegistry.RUNE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/trial_chambers/reward_unique")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.2f))
						.add(LootItem.lootTableItem(ItemRegistry.RUNE.get()));
				context.addPool(builder);
			}
			if(key.location().toString().equals("minecraft:chests/ruined_portal")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.1f))
						.add(LootItem.lootTableItem(ItemRegistry.RUNE.get()));
				context.addPool(builder);
			}

			// Wand of Sparking
			if(key.location().toString().equals("minecraft:chests/spawn_bonus_chest")) {
				LootPool.Builder builder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(1.0f))
						.add(LootItem.lootTableItem(ItemRegistry.WAND_OF_SPARKING.get()));
				context.addPool(builder);
			}
		}
	}
}

