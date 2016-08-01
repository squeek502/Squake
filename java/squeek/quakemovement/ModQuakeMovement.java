package squeek.quakemovement;

import api.player.client.ClientPlayerAPI;
import api.player.server.ServerPlayerAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, acceptedMinecraftVersions="[1.10,1.11)", dependencies = "required-after:PlayerAPI;after:Squeedometer")
public class ModQuakeMovement
{
	// The instance of your mod that Forge uses.
	@Instance(value = ModInfo.MODID)
	public static ModQuakeMovement instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModConfig.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		ServerPlayerAPI.register(ModInfo.MODID, QuakeServerPlayer.class);

		if (event.getSide() == Side.CLIENT)
			ClientPlayerAPI.register(ModInfo.MODID, QuakeClientPlayer.class);

		FMLInterModComms.sendRuntimeMessage(ModInfo.MODID, "VersionChecker", "addVersionCheck", "http://www.ryanliptak.com/minecraft/versionchecker/squeek502/Squake");
	}

	@SubscribeEvent
	public void onLivingFall(LivingFallEvent event)
	{
		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		if (ModConfig.INCREASED_FALL_DISTANCE != 0.0D)
		{
			event.setDistance((float) (event.getDistance() - ModConfig.INCREASED_FALL_DISTANCE));
		}
	}
}
