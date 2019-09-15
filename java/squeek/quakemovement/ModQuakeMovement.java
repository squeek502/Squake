package squeek.quakemovement;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ModQuakeMovement implements ModInitializer
{
	@Override
	public void onInitialize()
	{
	}

	public static float getFriction()
	{
		return 0.6f;
	}

	public static void drawSpeedometer()
	{
		GlStateManager.pushMatrix();
		MinecraftClient mc = MinecraftClient.getInstance();
		PlayerEntity player = mc.player;
		double deltaX = player.x - player.prevX;
		double deltaZ = player.z - player.prevZ;
		double speed = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		String speedString = String.format("%.02f", speed);
		mc.textRenderer.drawWithShadow(speedString, 10, mc.window.getScaledHeight() - mc.textRenderer.fontHeight - 10, 0xFFDDDDDD);
		GlStateManager.popMatrix();
	}
}