package com.francis.the_stalwart.core.registry;
import com.francis.the_stalwart.TheStalwart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> reg;
    public static final RegistryObject<SoundEvent> swolager_STEP;
    public static final RegistryObject<SoundEvent> swolager_AMBIENT;
    public static final RegistryObject<SoundEvent> swolager_HURT;
    public static final RegistryObject<SoundEvent> swolager_DEATH;
    public static final RegistryObject<SoundEvent> swolager_HIT;

    public static void Register(IEventBus bus) {
        reg.register(bus);
    }

    private static RegistryObject<SoundEvent> reg(String name) {
        return reg.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(TheStalwart.MODID, name)));
    }

    public static RegistryObject<SoundEvent> entity_sound(String name) {
        return reg("entity.thestalwart." + name);
    }

    static {
        reg = DeferredRegister.create(Registries.SOUND_EVENT, "thestalwart");
        swolager_STEP = entity_sound("swolager.step");
        swolager_AMBIENT = entity_sound("swolager.ambient");
        swolager_HURT = entity_sound("swolager.hurt");
        swolager_DEATH = entity_sound("swolager.death");
        swolager_HIT = entity_sound("swolager.hit");
    }
}