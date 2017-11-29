package squeek.quakemovement;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
			player.addChatMessage(new TextComponentString("[" + ModInfo.MODID + "] " + feedback));
		}
	}
}
