package com.francis.the_stalwart.entity.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class FarLeapGoal extends Goal {
    private final Mob mob;
    private LivingEntity target;
    private final float yd;

    public FarLeapGoal(Mob mob, float yd) {
        this.mob = mob;
        this.yd = yd;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    public boolean canUse() {
        this.target = this.mob.getTarget();
        if (this.target == null) {
            return false;
        } else {
            double d0 = this.mob.distanceToSqr(this.target);
            if (!(d0 < (double)100.0F) && !(d0 > (double)840.0F)) {
                return !this.mob.onGround() ? false : this.mob.getRandom().nextInt(reducedTickDelay(5)) == 0;
            } else {
                return false;
            }
        }
    }

    public boolean canContinueToUse() {
        return !this.mob.onGround();
    }

    public void start() {
        Vec3 vec3 = this.mob.getDeltaMovement();
        Vec3 vec31 = new Vec3(this.target.getX() - this.mob.getX(), (double)0.0F, this.target.getZ() - this.mob.getZ());
        if (vec31.lengthSqr() > 1.0E-7) {
            vec31 = vec31.normalize().scale(0.4).add(vec3.scale(0.2));
        }

        this.mob.setDeltaMovement(vec31.x * (double)6.0F, (double)this.yd, vec31.z * (double)6.0F);
    }
}