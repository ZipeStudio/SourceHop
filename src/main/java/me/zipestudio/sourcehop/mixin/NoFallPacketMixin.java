package me.zipestudio.sourcehop.mixin;

import me.zipestudio.sourcehop.SourceHop;
import me.zipestudio.sourcehop.client.SourceHopClient;
import me.zipestudio.sourcehop.config.SourceHopConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class NoFallPacketMixin {

    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void onSendPacket(CallbackInfo ci) {

        SourceHopConfig config = SourceHopClient.getConfig();
        if (!config.isEnableStrafing() || !config.isEnableNoFallDamage()) {
            return;
        }

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || player.isCreative()) return;

        ClientPlayNetworkHandler networkHandler = player.networkHandler;
        if (networkHandler == null) return;

        Vec3d velocity = player.getVelocity();
        if (velocity.y <= -3) {
            player.setVelocity(velocity.x, -2.999, velocity.z);
        }

        networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, player.horizontalCollision));
    }
}