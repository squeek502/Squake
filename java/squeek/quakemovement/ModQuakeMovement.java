package squeek.quakemovement;

import com.mojang.blaze3d.platform.GlStateManager;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

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
		mc.textRenderer.drawWithShadow(matrixStack, speedString, 10, mc.getWindow().getScaledHeight() - mc.textRenderer.fontHeight - 10, 0xFFDDDDDD);
		GlStateManager.popMatrix();
	}
}