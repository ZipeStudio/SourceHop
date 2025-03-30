package me.zipestudio.sourcehop.mixin;

import me.zipestudio.sourcehop.client.SHClient;
import me.zipestudio.sourcehop.config.SourceHopConfig;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("java:S2160") // Mixin class
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    protected abstract float getJumpVelocity();

    @Shadow
    public float sidewaysSpeed;

    @Shadow
    public float forwardSpeed;

    @Shadow
    private int jumpingCooldown;

    @Shadow
    private float movementSpeed;

    @Shadow
    protected abstract Vec3d applyClimbingSpeed(Vec3d motion);

    @Shadow
    protected boolean jumping;

    @Shadow
    public abstract boolean isClimbing();

    @Shadow
    public abstract void updateLimbs(boolean flutter);

    @Shadow
    public abstract void remove(RemovalReason reason);

    //? if >=1.20.5 {
    @Shadow
    public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);
    //?} else {
    /*@Shadow public abstract boolean hasStatusEffect(StatusEffect par1);
    @Shadow public abstract StatusEffectInstance getStatusEffect(StatusEffect par1);
    *///?}

    @Unique
    private boolean wasOnGround;

    // Speedometer
    @Unique
    private boolean speedWasPrinted;

    @Unique
    private double prevSpeed;

    @Unique
    private double smoothedSpeed = 0.0;


    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travel(Vec3d movementInput, CallbackInfo ci) {

        SourceHopConfig config = SHClient.getConfig();
        if (!config.isEnableStrafing()) {
            return;
        }

        if (!this.isAlive()) {
            return;
        }

        if (this.getType() != EntityType.PLAYER) {
            return;
        }

        // Player
        LivingEntity self = (LivingEntity) this.getWorld().getEntityById(this.getId());
        if (self == null) {
            return;
        }

        if (isFlying((PlayerEntity) self)) {
            return;
        }

        if (!this.canMoveVoluntarily() && !this.isLogicalSideForUpdatingMovement()) {
            return;
        }

        if (this.isTouchingWater() || this.isInLava() || this.fallDistance > 0) {
            return;
        }

        if (config.isEnableManualJump() && this.getWorld().isClient && !isFlying((PlayerEntity) self)) {
            KeyBinding.setKeyPressed(InputUtil.fromTranslationKey("key.keyboard.space"), false);
        }


        this.sidewaysSpeed /= 0.98F;
        this.forwardSpeed /= 0.98F;
        double sI = movementInput.x / 0.98F;
        double fI = movementInput.z / 0.98F;

        this.jumpingCooldown = 0;

        BlockPos blockPos = this.getVelocityAffectingPos();
        float slipperiness = this.getWorld().getBlockState(blockPos).getBlock().getSlipperiness();
        float friction = 1 - (slipperiness * slipperiness);

        boolean fullGrounded = this.wasOnGround && this.isOnGround(); //Allows for no friction 1-frame upon landing.
        if (fullGrounded) {
            Vec3d velFin = this.getVelocity();
            Vec3d horFin = new Vec3d(velFin.x, 0.0F, velFin.z);
            float speed = (float) horFin.length();
            if (speed > 0.001F) {
                float drop = 0.0F;

                drop += (speed * config.getFriction() * friction);

                float newspeed = Math.max(speed - drop, 0.0F);
                newspeed /= speed;
                this.setVelocity(horFin.x * newspeed, velFin.y, horFin.z * newspeed);
            }
        }

        this.wasOnGround = this.isOnGround();

        if (sI != 0.0F || fI != 0.0F) {
            Vec3d moveDir = movementInputToVelocity(new Vec3d(sI, 0.0F, fI), 1.0F, this.getYaw());
            Vec3d accelVec = this.getVelocity();

            double projVel = new Vec3d(accelVec.x, 0.0F, accelVec.z).dotProduct(moveDir);
            double accelVel = this.isOnGround() ? config.getAccelerate() : config.getAirAccelerate();
            float maxVel = this.isOnGround() ? this.movementSpeed * config.getMaxSpeedMul() : config.getMaxAirSpeed();

            if (projVel + accelVel > maxVel) {
                accelVel = maxVel - projVel;
            }
            Vec3d accelDir = moveDir.multiply(Math.max(accelVel, 0.0F));

            this.setVelocity(accelVec.add(accelDir));
        }

        this.setVelocity(this.applyClimbingSpeed(this.getVelocity()));
        this.move(MovementType.SELF, this.getVelocity());

        //
        // Ladder Logic
        //
        Vec3d preVel = this.getVelocity();
        if ((this.horizontalCollision || this.jumping) && this.isClimbing()) {
            preVel = new Vec3d(preVel.x * 0.7D, 0.2D, preVel.z * 0.7D);
        }

        //
        // Apply Gravity (If not in Water)
        //
        double yVel = preVel.y;
        float gravity = config.getGravity();

        boolean dY;
        //? if >=1.20.5 {
        dY = preVel.y <= 0.0D && this.hasStatusEffect(StatusEffects.SLOW_FALLING);
        //?} else {
        /*dY = preVel.y <= 0.0D && this.hasStatusEffect(StatusEffects.SLOW_FALLING);
         *///?}

        if (dY) {
            gravity = 0.01F;
            this.fallDistance = 0.0F;
        }

        StatusEffectInstance levitation;
        //? if >=1.20.5 {
        levitation = this.getStatusEffect(StatusEffects.LEVITATION);
        //?} else {
        /*levitation = this.getStatusEffect(StatusEffects.LEVITATION);
         *///?}

        if (levitation != null) {
            yVel += (0.05D * (levitation.getAmplifier() + 1) - preVel.y) * 0.2D;
            this.fallDistance = 0.0F;
        } else if (this.getWorld().isClient && !this.getWorld().isChunkLoaded(this.getChunkPos().x, this.getChunkPos().z)) {
            yVel = 0.0D;
        } else if (!this.hasNoGravity()) {
            yVel -= gravity;
        }

        this.setVelocity(preVel.x, yVel, preVel.z);
        // Speedometer
        if (config.isEnableSpeedometer()) {
            double rawSpeed = Math.sqrt(preVel.x * preVel.x + preVel.z * preVel.z) * 19.5;

            if (this.isTouchingWater() || this.isInLava() || this.hasVehicle() || isFlying((PlayerEntity) self)) {
                smoothedSpeed = 0.0;
                prevSpeed = 0.0;
            } else {
                smoothedSpeed = smoothedSpeed * 0.8 + rawSpeed * 0.2;
            }

            if (smoothedSpeed > 0.1 && !fullGrounded && !this.hasVehicle()) {
                this.speedWasPrinted = true;

                String speedColor = (smoothedSpeed < prevSpeed) ? "§4↓" : "§2↑";

                Text speedText = Text.literal(String.format(speedColor + "%.02f §r", smoothedSpeed))
                        .append(Text.translatable("sourcehop.text.option.general.enableSpeedometer.desc.actionbar"));

                ((PlayerEntity) self).sendMessage(speedText, true);
            } else if (this.speedWasPrinted) {
                this.speedWasPrinted = false;
                ((PlayerEntity) self).sendMessage(Text.empty(), true);
            }

            this.prevSpeed = smoothedSpeed;
        }

        //
        // Update limbs.
        //
        this.updateLimbs(self instanceof Flutterer);

        //Override original method.
        ci.cancel();
    }


    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    void jump(CallbackInfo ci) {
        if (!this.isAlive()) {
            return;
        }

        SourceHopConfig config = SHClient.getConfig();
        if (this.getType() == EntityType.PLAYER) {
            if (config.isEnableManualJump() && this.getWorld().isClient) {
                KeyBinding.setKeyPressed(InputUtil.fromTranslationKey("key.keyboard.space"), false);
            }
            if (config.isEnableCrouchJump() && this.isSneaking()) {
                double cx = this.getVelocity().x;
                double cz = this.getVelocity().z;
                double crouchjump = this.getJumpVelocity() * config.getCrouchJumpPower();
                this.setVelocity(cx, crouchjump, cz);
            }
        }

        if (!config.isEnableStrafing()) {
            return;
        }

        Vec3d vecFin = this.getVelocity();
        double yVel = this.getJumpVelocity();
        StatusEffectInstance jumpBoost = this.getStatusEffect(StatusEffects.JUMP_BOOST);
        if (jumpBoost != null) {
            yVel += 0.1F * (jumpBoost.getAmplifier() + 1);
        }

        this.setVelocity(vecFin.x, yVel, vecFin.z);
        this.velocityDirty = true;

        ci.cancel();
    }

    @Unique
    private static boolean isFlying(PlayerEntity player) {
        return player != null && player.getAbilities().flying;
    }

}
