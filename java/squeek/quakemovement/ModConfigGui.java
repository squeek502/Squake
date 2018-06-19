package squeek.quakemovement;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModConfigGui extends GuiConfig
{
	public ModConfigGui(GuiScreen parentScreen)
	{
		super(parentScreen, new ConfigElement(ModConfig.config.getCategory(ModConfig.CATEGORY_MOVEMENT)).getChildElements(), ModInfo.MODID, false, false, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString()));
	}
}
