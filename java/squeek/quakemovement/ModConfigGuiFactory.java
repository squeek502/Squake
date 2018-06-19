package squeek.quakemovement;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModConfigGuiFactory extends DefaultGuiFactory
{
	public ModConfigGuiFactory()
	{
		super(ModInfo.MODID, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString()));
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new GuiConfig(parentScreen, new ConfigElement(ModConfig.config.getCategory(ModConfig.CATEGORY_MOVEMENT)).getChildElements(), modid, false, false, title);
	}
}
