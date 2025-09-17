package com.namefix.mixin;

import com.namefix.registry.ItemRegistry;
import com.namefix.utils.Utils;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
	@Shadow public abstract ItemStack getItem();

	@Shadow private int age;

	@Inject(method = "tick", at = @At("HEAD"))
	private void zapinators$onTick(CallbackInfo ci) {
		ItemEntity ent = ((ItemEntity)(Object)this);
		if(this.getItem().getItem().equals(ItemRegistry.FALLEN_STAR.get())) {
			ent.level().addParticle(new DustParticleOptions(15656731, 1.069f), ent.getX(), ent.getY()+0.2, ent.getZ(), 0.0D, 0.0D, 0.0D);

			if(Utils.entityFallenDespawnStarCheck(ent))
				ent.discard();
		}
	}
}
