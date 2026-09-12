package com.francis.the_stalwart.core;

import com.francis.the_stalwart.TheStalwart;
import com.francis.the_stalwart.core.registry.ModEntities;
import com.francis.the_stalwart.entity.EntitySwolager;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheStalwart.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler
{
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.SWOLAGER.get(), EntitySwolager.createAttributes().build());
    }
}