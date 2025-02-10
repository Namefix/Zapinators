package com.namefix.registry;

import com.namefix.ZapinatorsMod;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(ZapinatorsMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> ZAPINATORS_TAB = TABS.register("zapinators", () ->
            CreativeTabRegistry.create(
                Component.literal("Zapinators"),
                () -> new ItemStack(ItemRegistry.ZAPINATOR.get())
            )
    );

    public static void register() {

        TABS.register();
    }
}
