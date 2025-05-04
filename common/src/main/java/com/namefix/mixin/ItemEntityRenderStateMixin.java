package com.namefix.mixin;

import com.namefix.interfaces.ItemEntityRenderStateExtension;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemEntityRenderState.class)
public class ItemEntityRenderStateMixin implements ItemEntityRenderStateExtension {
	@Unique
	Item item;

	@Override
	public Item zapinators$getItem() {
		return item;
	}

	@Override
	public void zapinators$setItem(Item item) {
		this.item = item;
	}
}
