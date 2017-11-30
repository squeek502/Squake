package squeek.quakemovement;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ToggleKeyHandler
{
	private static final KeyBinding TOGGLE_KEY = new KeyBinding("squake.key.toggle", Keyboard.CHAR_NONE, ModInfo.MODID);

	static
	{
		ClientRegistry.registerKeyBinding(TOGGLE_KEY);
	}

	@SubscribeEvent
	public void onKeyEvent(InputEvent.KeyInputEvent event)
	{
		if (TOGGLE_KEY.isPressed())
		{
			ModConfig.setEnabled(!ModConfig.ENABLED);

			EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
			String feedback = ModConfig.ENABLED ? I18n.format("squake.key.toggle.enabled") : I18n.format("squake.key.toggle.disabled");
			player.addChatMessage(new ChatComponentText("[" + ModInfo.MODID + "] " + feedback));
		}
	}
}
