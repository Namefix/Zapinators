package com.namefix.registry;

import com.namefix.ZapinatorsMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ZapinatorsMod.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> SPACE_GUN_SHOOT = SOUNDS.register("space_gun_shoot", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "space_gun_shoot")));
    public static final RegistrySupplier<SoundEvent> LASER_RIFLE_SHOOT = SOUNDS.register("laser_rifle_shoot", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "laser_rifle_shoot")));
    public static final RegistrySupplier<SoundEvent> ZAPINATOR_SHOOT = SOUNDS.register("zapinator_shoot", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "zapinator_shoot")));
    public static final RegistrySupplier<SoundEvent> COLOR_ZAPINATOR_SHOOT = SOUNDS.register("color_zapinator_shoot", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "color_zapinator_shoot")));
    public static final RegistrySupplier<SoundEvent> MANA_FILL = SOUNDS.register("mana_fill", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "mana_fill")));
    public static final RegistrySupplier<SoundEvent> FALLEN_STAR_FALL = SOUNDS.register("fallen_star_fall", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "fallen_star_fall")));
    public static final RegistrySupplier<SoundEvent> FALLEN_STAR_HIT = SOUNDS.register("fallen_star_hit", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "fallen_star_hit")));
    public static final RegistrySupplier<SoundEvent> FALLEN_STAR_USE = SOUNDS.register("fallen_star_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "fallen_star_use")));
    public static final RegistrySupplier<SoundEvent> MANA_CRYSTAL_USE = SOUNDS.register("mana_crystal_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "mana_crystal_use")));

    public static void register() {
        SOUNDS.register();
    }
}
