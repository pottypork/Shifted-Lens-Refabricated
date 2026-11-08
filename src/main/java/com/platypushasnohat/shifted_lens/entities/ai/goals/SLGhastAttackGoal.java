package com.platypushasnohat.shifted_lens.entities.ai.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SLGhastAttackGoal extends Goal {

    private static final double MOUTH_HEIGHT = 0.875D;
    private static final double SPAWN_DISTANCE = 4.0D;

    private final Ghast ghast;
    public int chargeTime;
    private int navigationCooldown;

    public SLGhastAttackGoal(Ghast ghast) {
        this.ghast = ghast;
    }

    @Override
    public boolean canUse() {
        return this.ghast.getTarget() != null;
    }

    @Override
    public void start() {
        this.chargeTime = 0;
        this.navigationCooldown = 0;
    }

    @Override
    public void stop() {
        this.ghast.setCharging(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.ghast.getTarget();
        if (target != null) {
            if (target.distanceToSqr(this.ghast) < 4096.0D && this.ghast.hasLineOfSight(target)) {
                this.ghast.getLookControl().setLookAt(target, 10.0F, this.ghast.getMaxHeadXRot());
                Level level = this.ghast.level();

                if (this.navigationCooldown > 0) {
                    this.ghast.getNavigation().stop();
                }

                this.chargeTime++;
                if (chargeTime == 1) {
                    this.navigationCooldown = 20 + this.ghast.getRandom().nextInt(20);
                }

                if (this.chargeTime == 32 && !this.ghast.isSilent()) {
                    level.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
                }

                if (this.chargeTime == 40) {
                    Vec3 view = this.ghast.getViewVector(1.0F);
                    float pitch = this.ghast.getXRot() * ((float) Math.PI / 180.0F);
                    float yaw = this.ghast.getYHeadRot() * ((float) Math.PI / 180.0F);
                    Vec3 up = new Vec3(-Mth.sin(yaw) * Mth.sin(pitch), Mth.cos(pitch), Mth.cos(yaw) * Mth.sin(pitch));
                    Vec3 spawn = this.ghast.position().add(up.scale(MOUTH_HEIGHT)).add(view.scale(SPAWN_DISTANCE));
                    Vec3 direction = new Vec3(target.getX() - spawn.x, target.getY(0.5D) - spawn.y, target.getZ() - spawn.z);
                    if (!this.ghast.isSilent()) {
                        level.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
                    }
                    LargeFireball largefireball = new LargeFireball(level, this.ghast, direction.normalize(), this.ghast.getExplosionPower());
                    largefireball.setPos(spawn.x, spawn.y, spawn.z);
                    level.addFreshEntity(largefireball);
                    this.chargeTime = -20;
                }
            } else if (this.chargeTime > 0) {
                this.chargeTime--;
            }

            if (navigationCooldown > 0) this.navigationCooldown--;

            this.ghast.setCharging(this.chargeTime > 10);
        }
    }
}