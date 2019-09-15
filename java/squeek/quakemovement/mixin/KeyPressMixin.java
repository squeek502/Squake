package squeek.quakemovement.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.quakemovement.KeyBindInitializer;
import squeek.quakemovement.ModConfig;
import squeek.quakemovement.ModInfo;

@Mixin(MinecraftClient.class)
public class KeyPressMixin
{
	@Inject(method = "handleInputEvents", at=@At("HEAD"))
	public void handleInputEvents(CallbackInfo info)
	{
		if (KeyBindInitializer.ENABLE.wasPressed())
		{
			ModConfig.ENABLED = !ModConfig.ENABLED;

			String feedback = ModConfig.ENABLED ? I18n.translate("squake.key.toggle.enabled") : I18n.translate("squake.key.toggle.disabled");
			MinecraftClient.getInstance().player.sendChatMessage("[" + ModInfo.MODID + "] " + feedback);
		}
	}
}
