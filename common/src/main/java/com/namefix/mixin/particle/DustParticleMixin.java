package com.namefix.mixin.particle;

import com.namefix.interfaces.EmissiveParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DustParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.DustParticleOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DustParticle.class)
public class DustParticleMixin implements EmissiveParticle {
	@Unique
	private boolean zapinators$isEmissive = false;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void zapinators$checkIfEmissive(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, DustParticleOptions dustParticleOptions, SpriteSet spriteSet, CallbackInfo ci) {
		if (Math.abs(dustParticleOptions.getScale() - 1.51337f) < 0.0001f || Math.abs(dustParticleOptions.getScale() - 1.069f) < 0.0001f) {
			this.zapinators$isEmissive = true;
		}
	}

	@Override
	@Unique
	public void setEmissive(boolean emissive) {
		this.zapinators$isEmissive = emissive;
	}

	@Override
	@Unique
	public boolean isEmissive() {
		return this.zapinators$isEmissive;
	}
}
