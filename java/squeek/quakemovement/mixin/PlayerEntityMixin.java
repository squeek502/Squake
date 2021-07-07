package squeek.quakemovement.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import squeek.quakemovement.ModQuakeMovement;
import squeek.quakemovement.QuakeClientPlayer;
import squeek.quakemovement.QuakeClientPlayer.IsJumpingGetter;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IsJumpingGetter {
	private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "travel(Lnet/minecraft/util/math/Vec3d;)V", cancellable = true)
	private void travel(Vec3d movementInput, CallbackInfo info)
	{
		if (!ModQuakeMovement.CONFIG.isEnabled())
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

	@Override
	public void updateVelocity(float speed, Vec3d movementInput) {
		if (!ModQuakeMovement.CONFIG.isEnabled() || !world.isClient) {
			super.updateVelocity(speed, movementInput);
			return;
		}

		if (QuakeClientPlayer.updateVelocity(this, movementInput, speed)) {
			return;
		}
		super.updateVelocity(speed, movementInput);
	}

	@Override
	public boolean isJumping()
	{
		return this.jumping;
	}

	//Fixes Fall Damage Negating Speed See: https://github.com/CoolMineman/Squake/commit/61c4622bca4209d60c7e7d61a59e5a5933ae844a#commitcomment-41109936
	boolean velocityHack = false;

	@Inject(at = @At("HEAD"), method = "handleFallDamage")
	private void preHandleFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> info) {
		velocityHack = velocityModified;
	}
	
	@Inject(at = @At("RETURN"), method = "handleFallDamage")
	private void postHandleFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> info) {
		if (!world.isClient) velocityModified = velocityHack;
	}
}
