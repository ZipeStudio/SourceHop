package me.zipestudio.sourcehop.mixin;

import me.zipestudio.sourcehop.SourceHop;
import me.zipestudio.sourcehop.config.SHConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class NoFallDamageMixin {

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {

        if (!SourceHop.getConfig().isEnableNoFallDamage()) {
            return;
        }

        cir.setReturnValue(false);
    }
}