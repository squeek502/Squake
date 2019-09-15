package squeek.quakemovement.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.quakemovement.ModConfig;
import squeek.quakemovement.QuakeClientPlayer;

@Mixin(Entity.class)
public abstract class EntityMixin
{
	@Inject(at = @At("HEAD"), method = "updateVelocity(FLnet/minecraft/util/math/Vec3d;)V", cancellable = true)
	private void updateVelocity(float movementSpeed, Vec3d movementInput, CallbackInfo info)
	{
		if (!ModConfig.ENABLED)
			return;

		if (QuakeClientPlayer.updateVelocity((Entity) (Object) this, movementInput, movementSpeed))
			info.cancel();
	}
}
