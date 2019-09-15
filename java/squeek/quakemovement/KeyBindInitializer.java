package squeek.quakemovement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBindInitializer implements ClientModInitializer
{
	public static FabricKeyBinding ENABLE;
	public static String CATEGORY = "fabric.mods." + ModInfo.MODID;

	@Override
	public void onInitializeClient()
	{
		KeyBindingRegistry.INSTANCE.addCategory(CATEGORY);
		ENABLE = registerKey("enable", GLFW.GLFW_KEY_UNKNOWN, CATEGORY);
	}

	public static FabricKeyBinding registerKey(String name, Integer code, String category)
	{
		FabricKeyBinding key = FabricKeyBinding.Builder.create(new Identifier(ModInfo.MODID, name), InputUtil.Type.KEYSYM, code, category).build();
		KeyBindingRegistry.INSTANCE.register(key);

		return key;
	}
}
