package squeek.quakemovement;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
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

	public static void drawSpeedometer(MatrixStack matrixStack)
	{
		MinecraftClient mc = MinecraftClient.getInstance();
		PlayerEntity player = mc.player;
		double deltaX = player.getX() - player.prevX;
		double deltaZ = player.getZ() - player.prevZ;
		double speed = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		String speedString = String.format("%.02f", speed);
		mc.textRenderer.drawWithShadow(matrixStack, speedString, 10, mc.getWindow().getScaledHeight() - mc.textRenderer.fontHeight - 10, 0xFFDDDDDD);
	}
}