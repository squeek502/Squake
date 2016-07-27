package squeek.quakemovement;

import api.player.server.ServerPlayerAPI;
import api.player.server.ServerPlayerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class QuakeServerPlayer extends ServerPlayerBase
{

	private boolean wasVelocityChangedBeforeFall = false;

	public QuakeServerPlayer(ServerPlayerAPI playerapi)
	{
		super(playerapi);
	}

	@Override
	public void beforeFall(float fallDistance, float damageMultiplier)
	{
		wasVelocityChangedBeforeFall = this.playerAPI.getVelocityChangedField() || this.player.velocityChanged;
	}

	@Override
	public void afterFall(float fallDistance, float damageMultiplier)
	{
		this.playerAPI.setVelocityChangedField(wasVelocityChangedBeforeFall);
		this.player.velocityChanged = wasVelocityChangedBeforeFall;
	}
}
