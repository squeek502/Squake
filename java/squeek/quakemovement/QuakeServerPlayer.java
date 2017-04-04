package squeek.quakemovement;

import net.minecraft.entity.player.EntityPlayer;

public class QuakeServerPlayer
{
	// we can get away with a single static variable here instead of a <Player, boolean> map
	// because we only care about the state before it has any possibility of changing
	private static boolean wasVelocityChangedBeforeFall = false;

	public static void beforeFall(EntityPlayer player, float fallDistance, float damageMultiplier)
	{
		if (player.world.isRemote)
			return;

		wasVelocityChangedBeforeFall = player.velocityChanged;
	}

	public static void afterFall(EntityPlayer player, float fallDistance, float damageMultiplier)
	{
		if (player.world.isRemote)
			return;

		player.velocityChanged = wasVelocityChangedBeforeFall;
	}
}
