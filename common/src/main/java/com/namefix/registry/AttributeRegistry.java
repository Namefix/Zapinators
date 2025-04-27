package com.namefix.registry;

import com.namefix.ZapinatorsMod;
import dev.architectury.platform.Platform;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;

public class AttributeRegistry {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ZapinatorsMod.MOD_ID, Registries.ATTRIBUTE);

	public static final RegistrySupplier<Attribute> MANA_REGENERATION = ATTRIBUTES.register("mana_regeneration", () -> new RangedAttribute("attribute.name.mana_regeneration", 1.0, 0.0, 10.0).setSyncable(true));
	public static final RegistrySupplier<Attribute> MAX_MANA = ATTRIBUTES.register("max_mana", () -> new RangedAttribute("attribute.name.max_mana", 20.0, 0.0, 200.0).setSyncable(true));

	public static void register() {
		ATTRIBUTES.register();

		if(Platform.isFabric()) {
			EntityAttributeRegistry.register(
					() -> EntityType.PLAYER,
					() -> Player.createAttributes().add(getHolder(MANA_REGENERATION)).add(getHolder(MAX_MANA))
			);
		}
	}

	public static Holder<Attribute> getHolder(RegistrySupplier<Attribute> registry) {
		return registry.getRegistrar().getHolder(registry.getId());
	}
}
