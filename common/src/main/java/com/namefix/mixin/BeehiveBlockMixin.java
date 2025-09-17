package com.namefix.mixin;

import com.namefix.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.Block.popResource;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin {
	@Inject(method = "dropHoneycomb", at = @At("HEAD"))
	private static void zapinators$dropBeeCore(Level level, BlockPos blockPos, CallbackInfo ci) {
		if(level.random.nextFloat() > 0.5f) popResource(level, blockPos, new ItemStack(ItemRegistry.BEE_CORE.get()));
	}
}
