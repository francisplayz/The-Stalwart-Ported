package com.francis.the_stalwart.core;

import com.francis.the_stalwart.core.registry.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = "stalwart"
)
public class ClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModEntities.RegisterLayers(event);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModEntities.ClientSetup();
    }
}
