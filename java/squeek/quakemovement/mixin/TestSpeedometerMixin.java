package squeek.quakemovement.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.quakemovement.ModQuakeMovement;

@Mixin(InGameHud.class)
public class TestSpeedometerMixin
{
	@Inject(at = @At("HEAD"), method = "renderStatusEffectOverlay()V")
	private void renderStatusEffectOverlay(CallbackInfo info)
	{
		ModQuakeMovement.drawSpeedometer();
	}
}

