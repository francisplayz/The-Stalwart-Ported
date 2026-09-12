package com.francis.the_stalwart.core;

import com.francis.the_stalwart.entity.EntitySwolager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "stalwart"
)
public class ModEvents {
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Mob mob) {
            if (mob instanceof AbstractVillager villager) {
                villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, EntitySwolager.class, 8.0F, 0.6, 0.6));
            }
        }

    }
}
