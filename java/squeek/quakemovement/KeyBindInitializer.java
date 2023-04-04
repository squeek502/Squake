package squeek.quakemovement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBindInitializer implements ClientModInitializer
{
	public static KeyBinding ENABLE;
	public static String CATEGORY = "fabric.mods." + ModInfo.MODID;

	@Override
	public void onInitializeClient()
	{
		ENABLE = registerKey("enable", GLFW.GLFW_KEY_UNKNOWN, CATEGORY);
	}

	public static KeyBinding registerKey(String name, Integer code, String category)
	{
		KeyBinding key = KeyBindingHelper.registerKeyBinding(new KeyBinding(new Identifier(ModInfo.MODID, name).toTranslationKey(), InputUtil.Type.KEYSYM, code, category));

		return key;
	}
}
