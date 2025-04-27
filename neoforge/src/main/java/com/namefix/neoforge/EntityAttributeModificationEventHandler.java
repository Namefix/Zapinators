package com.namefix.neoforge;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import static com.namefix.registry.AttributeRegistry.*;

public class EntityAttributeModificationEventHandler {
	@SubscribeEvent
	public void onEntityAttributeModification(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, getHolder(MANA_REGENERATION));
		event.add(EntityType.PLAYER, getHolder(MAX_MANA));
	}
}
