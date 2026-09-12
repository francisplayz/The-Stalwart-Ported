package com.francis.the_stalwart.mixins;

import com.francis.the_stalwart.entity.EntitySwolager;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySwolager.class)
public class SwolagerMixin {
    @Inject(method = "aiStep", at = @At("TAIL"))
    private void thestalwart$setDifficultyDamage(CallbackInfo ci) {
        EntitySwolager swolager = (EntitySwolager) (Object) this;
        Difficulty difficulty = swolager.level().getDifficulty();

        double damage;

        switch (difficulty) {
            case EASY -> damage = 5.0D;
            case HARD -> damage = 9.0D;
            case NORMAL -> damage = 7.0D;
            default -> damage = 6.0D;
        }

        if (swolager.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            swolager.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        }
    }
}