package squeek.quakemovement.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.quakemovement.ModQuakeMovement;

@Mixin(InGameHud.class)
public class TestSpeedometerMixin
{
	@Inject(at = @At("HEAD"), method = "renderStatusEffectOverlay")
	private void renderStatusEffectOverlay(MatrixStack matrixStack, CallbackInfo info)
	{
		ModQuakeMovement.drawSpeedometer(matrixStack);
	}
}

