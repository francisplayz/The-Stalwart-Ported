package com.francis.the_stalwart.core.registry;

import com.francis.the_stalwart.TheStalwart;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> reg = DeferredRegister.create(ForgeRegistries.ITEMS, "thestalwart");
    public static final RegistryObject<Item> swolager_spawn_egg =
            reg("swolager_spawn_egg",
                    () -> ModEntities.SWOLAGER.get(),
                    7633028, 6373677);

    private static RegistryObject<Item> reg(String name,
                                                         Supplier<? extends EntityType<? extends Mob>> entityType,
                                                         int primaryColor, int secondaryColor) {
        return reg.register(name,
                () -> new ForgeSpawnEggItem(entityType, primaryColor, secondaryColor,
                        new Item.Properties())); // no .tab() here
    }

    public static void Register(IEventBus bus) {
        reg.register(bus);
    }
}