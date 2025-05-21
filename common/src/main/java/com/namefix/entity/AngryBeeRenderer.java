package com.namefix.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class AngryBeeRenderer <T extends AngryBee, S extends AngryBeeRenderState> extends EntityRenderer<T,S> {
	protected AngryBeeRenderer(EntityRendererProvider.Context context) {
		super(context);
	}



	@Override
	@SuppressWarnings("unchecked")
	public @NotNull S createRenderState() {
		return (S) new AngryBeeRenderState();
	}
}
