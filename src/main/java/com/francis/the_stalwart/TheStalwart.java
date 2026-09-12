package com.francis.the_stalwart;

import com.francis.the_stalwart.config.StalwartConfig;
import com.francis.the_stalwart.core.registry.ModEntities;
import com.francis.the_stalwart.core.registry.ModItems;
import com.francis.the_stalwart.core.registry.ModSounds;
import com.francis.the_stalwart.core.registry.ModTabs;
import net.minecraft.world.entity.raid.Raid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod("thestalwart")
public class TheStalwart {
    public static final String MODID = "thestalwart";

    public TheStalwart() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.Register(bus);
        StalwartConfig.register();
        ModEntities.ENTITY_TYPES.register(bus);
        ModSounds.Register(bus);
        bus.addListener(ModTabs::AddCreative);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void setup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            List<? extends Integer> source = StalwartConfig.SWOLAGER_WAVE_COUNTS.get().size() == 8 ? StalwartConfig.SWOLAGER_WAVE_COUNTS.get() : StalwartConfig.SWOLAGER_WAVE_COUNTS.getDefault();

            Raid.RaiderType.create(ModEntities.SWOLAGER.get().getDescriptionId(), ModEntities.SWOLAGER.get(), source.stream().mapToInt(i -> i).toArray());
        });
    }
}