package squeek.quakemovement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBindInitializer implements ClientModInitializer {
	public static final String CATEGORY = "fabric.mods." + ModInfo.MODID;
	public static final KeyBinding ENABLE = new KeyBinding(CATEGORY + "." + "enable", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY);

	@Override
	public void onInitializeClient() {
		KeyBindingHelper.registerKeyBinding(ENABLE);
	}
}
