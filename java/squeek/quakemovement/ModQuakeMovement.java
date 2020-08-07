package squeek.quakemovement;

import com.mojang.blaze3d.platform.GlStateManager;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import squeek.quakemovement.ModConfig.SpeedometerPosition;

public class ModQuakeMovement implements ModInitializer {
	public static final ModConfig CONFIG;

	static {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
	}

	@Override
	public void onInitialize() {
		//Cause this class to be loaded so the config loads on startup
	}

	public static void drawSpeedometer(MatrixStack matrixStack)
	{
		GlStateManager.pushMatrix();
		MinecraftClient mc = MinecraftClient.getInstance();
		PlayerEntity player = mc.player;
		double deltaX = player.getX() - player.prevX;
		double deltaZ = player.getZ() - player.prevZ;
		double speed = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		String speedString = String.format("%.02f", speed);
		int x;
		int y;
		if (CONFIG.getSpeedometerPosition() == SpeedometerPosition.BOTTOM_LEFT || CONFIG.getSpeedometerPosition() == SpeedometerPosition.TOP_LEFT) {
			x = 10;
		} else {
			x = mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(speedString) - 10;
		}
		if (CONFIG.getSpeedometerPosition() == SpeedometerPosition.TOP_RIGHT || CONFIG.getSpeedometerPosition() == SpeedometerPosition.TOP_LEFT) {
			y = 10;
		} else {
			y = mc.getWindow().getScaledHeight() - mc.textRenderer.fontHeight - 10;
		}
		mc.textRenderer.drawWithShadow(matrixStack, speedString, x, y, 0xFFDDDDDD);
		GlStateManager.popMatrix();
	}
}