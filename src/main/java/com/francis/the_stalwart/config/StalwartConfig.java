package com.francis.the_stalwart.config;

import com.francis.the_stalwart.TheStalwart;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

public class StalwartConfig
{
    public static ForgeConfigSpec.BooleanValue SWOLAGER_ENABLED;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> SWOLAGER_WAVE_COUNTS;

    public static void register()
    {
        ForgeConfigSpec.Builder config = new ForgeConfigSpec.Builder();

        config.push("Swolager Settings");

        SWOLAGER_ENABLED = config
                .comment("Determines if the Swolager will show up in Raids.")
                .define("swolagerRaids", true);

        SWOLAGER_WAVE_COUNTS = config
                .comment("Determines the number of Swolagers that will spawn in each wave. The first number is ignored! Wave 1 of a Raid is the 2nd element.")
                .defineList("swolagerWaveCounts", List.of(0, 0, 0, 0, 0, 1, 1, 1), o -> o instanceof Integer);

        config.pop();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config.build(), TheStalwart.MODID + ".toml");
    }
}