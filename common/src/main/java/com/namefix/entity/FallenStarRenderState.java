package com.namefix.entity;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class FallenStarRenderState extends EntityRenderState {
	public final ItemStackRenderState item = new ItemStackRenderState();
	public float entityXRot;
	public float entityYRot;

	public FallenStarRenderState() {

	}
}
