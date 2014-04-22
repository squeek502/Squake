package squeek.quakemovement;

import api.player.client.ClientPlayerAPI;
import api.player.server.ServerPlayerAPI;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="squeek_QuakeMovement", name="Quake Movement", version="0.1.0", dependencies="required-after:PlayerAPI")
@NetworkMod(clientSideRequired=true)
public class ModQuakeMovement {

        // The instance of your mod that Forge uses.
        @Instance(value = "squeek_QuakeMovement")
        public static ModQuakeMovement instance;
       
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
        	ModConfig.init(event.getSuggestedConfigurationFile());
        }
       
        @EventHandler
        public void load(FMLInitializationEvent event) {
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
    		ServerPlayerAPI.register("squeek_QuakeMovement", QuakeServerPlayer.class);
    		
        	if (event.getSide() == Side.CLIENT)
            	ClientPlayerAPI.register("squeek_QuakeMovement", QuakeClientPlayer.class);
        }
}
