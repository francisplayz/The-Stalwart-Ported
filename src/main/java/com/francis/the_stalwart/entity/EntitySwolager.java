package com.francis.the_stalwart.entity;

import com.francis.the_stalwart.core.registry.ModSounds;
import com.francis.the_stalwart.entity.ai.FarLeapGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class EntitySwolager extends AbstractIllager {
    private int turn;

    public EntitySwolager(EntityType<? extends AbstractIllager> type, Level level) {
        super(type, level);
        this.xpReward = 50;
        this.setMaxUpStep(1.0F);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.BREACH, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WALKABLE_DOOR, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, (double) 400.0F).add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.ATTACK_DAMAGE, (double) 30.0F).add(Attributes.ATTACK_KNOCKBACK, (double) 3.5F).add(Attributes.KNOCKBACK_RESISTANCE, 0.8).add(Attributes.FOLLOW_RANGE, (double) 46.0F);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        if (fallDistance > 10.0F) {
            super.causeFallDamage(fallDistance - 10.0F, multiplier, source);
        }
        return super.causeFallDamage(fallDistance, multiplier, source);
    }

    public static void SpawnParticles(ServerLevel level, Vec3 center, ParticleOptions particle, double radius, int count, double y_offset) {
        for (int i = 0; i < count; ++i) {
            double angle = (Math.PI * 2D) / (double) count * (double) i;
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            double y = center.y + y_offset;
            level.sendParticles(particle, x, y, z, 1, (double) 0.0F, (double) 0.0F, (double) 0.0F, (double) 1.0F);
        }

    }

    protected boolean canRide(Entity p_219462_) {
        return false;
    }

    protected void blockedByShield(LivingEntity entity) {
        double x = entity.getX() - this.getX();
        double z = entity.getZ() - this.getZ();
        double modifier = Math.max(x * x + z * z, 0.002);
        entity.push(x / modifier * (double) 3.0F, (double) 0.3F, z / modifier * (double) 3.0F);
        entity.hurtMarked = true;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && entity instanceof LivingEntity) {
            if (this.turn == 1) {
                this.turn = 2;
            }

            if (this.turn != 1) {
                this.turn = 1;
            }

            LivingEntity livingentity = (LivingEntity) entity;
            double resistance = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            double x = entity.getX() - this.getX();
            double z = entity.getZ() - this.getZ();
            double modifier = Math.max(x * x + z * z, 0.07);
            entity.push(x / modifier * ((double) 3.0F - resistance * (double) 2.0F), (double) 0.5F, z / modifier * ((double) 3.0F - resistance * (double) 2.0F));
            entity.hurtMarked = true;
            this.playSound((SoundEvent) ModSounds.swolager_HIT.get(), 3.0F, 1.0F);
            if (this.level() instanceof ServerLevel) {
                SpawnParticles((ServerLevel) this.level(), livingentity.position(), new BlockParticleOption(ParticleTypes.BLOCK, this.level().getBlockState(livingentity.getOnPos())), (double) 3.0F, 34, 0.1);
            }
        }

        return flag;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.swolager_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.swolager_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.swolager_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.swolager_STEP.get(), 2.5F, 1.0F);
    }

    public void tick() {
        super.tick();
    }

    public void aiStep() {
        super.aiStep();
        if (this.isAggressive() && this.horizontalCollision && ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
            AABB axisalignedbb = this.getBoundingBox().inflate(0.3, (double) 0.0F, 0.3);

            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (!blockstate.is(BlockTags.WITHER_IMMUNE)) {
                    this.level().destroyBlock(blockpos, false, this);
                }
            }
        }

    }

    @Override
    public SoundEvent getCelebrateSound() {
        return null;
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.1, true));
        this.targetSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(3, new FarLeapGoal(this, 0.6F));
    }

    @Override
    public void applyRaidBuffs(int i, boolean b) {

    }

    public boolean canBeLeader() {
        return true;
    }

    protected AABB getAttackBoundingBox() {
        return this.getBoundingBox().inflate(0.15D, 0.0D, 0.15D);
    }
}