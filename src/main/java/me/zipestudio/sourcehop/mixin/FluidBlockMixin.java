package me.zipestudio.sourcehop.mixin;

import me.zipestudio.sourcehop.SourceHop;
import me.zipestudio.sourcehop.config.SHConfig;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.CollisionView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityShapeContext.class)
public abstract class FluidBlockMixin {

	@Shadow
	@Final
	@Nullable
	private Entity entity;

	@Unique
	private final VoxelShape cuboidShape = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

	@Inject(method = "getCollisionShape", at = @At("RETURN"), cancellable = true)
	private void injectGetCollisionShape(BlockState state, CollisionView world, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {

		SHConfig config = SourceHop.getConfig();
		if (!config.isJesusMode()) {
			return;
		}

		if (this.entity == null) {
			return;
		}

		if (this.entity.isTouchingWater() || this.entity.isInLava() || this.entity.isSneaking()) {
			return;
		}

		Block block = state.getBlock();
		if (block != Blocks.WATER && block != Blocks.LAVA) {
			return;
		}

		cir.setReturnValue(cuboidShape);
	}
}
