package squeek.quakemovement.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import squeek.quakemovement.QuakeClientPlayer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements QuakeClientPlayer.IsJumpingGetter
{
	@Shadow
	boolean jumping;

	@Override
	public boolean isJumping()
	{
		return this.jumping;
	}
}
