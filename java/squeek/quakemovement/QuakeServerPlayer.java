package squeek.quakemovement;

import api.player.server.ServerPlayerAPI;
import api.player.server.ServerPlayerBase;

public class QuakeServerPlayer extends ServerPlayerBase
{

	private boolean wasVelocityChangedBeforeFall = false;

	public QuakeServerPlayer(ServerPlayerAPI playerapi)
	{
		super(playerapi);
	}

	@Override
	public void fall(float fallDistance)
	{
		wasVelocityChangedBeforeFall = this.playerAPI.getVelocityChangedField() || this.player.velocityChanged;

		if (ModConfig.INCREASED_FALL_DISTANCE != 0.0D)
		{
			fallDistance -= ModConfig.INCREASED_FALL_DISTANCE;
		}
		super.fall(fallDistance);

		this.playerAPI.setVelocityChangedField(wasVelocityChangedBeforeFall);
		this.player.velocityChanged = wasVelocityChangedBeforeFall;
	}
}
