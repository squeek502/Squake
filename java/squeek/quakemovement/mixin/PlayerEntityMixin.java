package squeek.quakemovement.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import squeek.quakemovement.ModConfig;
import squeek.quakemovement.QuakeClientPlayer;
import squeek.quakemovement.QuakeServerPlayer;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
	@Inject(at = @At("HEAD"), method = "travel(Lnet/minecraft/util/math/Vec3d;)V", cancellable = true)
	private void travel(Vec3d movementInput, CallbackInfo info)
	{
		if (!ModConfig.ENABLED)
			return;

		if (QuakeClientPlayer.travel((PlayerEntity) (Object) this, movementInput))
			info.cancel();
	}

	@Inject(at = @At("HEAD"), method = "tick()V")
	private void beforeUpdate(CallbackInfo info)
	{
		QuakeClientPlayer.beforeOnLivingUpdate((PlayerEntity) (Object) this);
	}

	@Inject(at = @At("TAIL"), method = "jump()V")
	private void afterJump(CallbackInfo info)
	{
		QuakeClientPlayer.afterJump((PlayerEntity) (Object) this);
	}

	@Inject(at = @At("HEAD"), method = "handleFallDamage")
	private void beforeFall(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> info)
	{
		QuakeServerPlayer.beforeFall((PlayerEntity) (Object) this, fallDistance, damageMultiplier);
	}

	@Inject(at = @At("TAIL"), method = "handleFallDamage")
	private void afterFall(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> info)
	{
		QuakeServerPlayer.afterFall((PlayerEntity) (Object) this, fallDistance, damageMultiplier);
	}
}
