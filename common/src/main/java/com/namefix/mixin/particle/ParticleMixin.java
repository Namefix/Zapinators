package com.namefix.mixin.particle;

import com.namefix.interfaces.EmissiveParticle;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Particle.class)
public class ParticleMixin {
	// I know this ain't good, but the game left me with no other choice >:) might be fixed in future
	@Inject(method = "getLightColor", at = @At("HEAD"), cancellable = true)
	private void zapinators$makeEmissive(float partialTicks, CallbackInfoReturnable<Integer> cir) {
		if(((Object)this) instanceof EmissiveParticle emissive && emissive.isEmissive()) {
			cir.setReturnValue(0xF000F0);
		}
	}
}
