package com.francis.the_stalwart.core;

import com.francis.the_stalwart.TheStalwart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation swolager = reg("swolager");

    private static ModelLayerLocation reg(String path, String model) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(TheStalwart.MODID, path), model);
    }

    private static ModelLayerLocation reg(String path) {
        return reg(path, "main");
    }
}