package squeek.quakemovement;

import net.minecraft.entity.player.PlayerEntity;

public class QuakeServerPlayer
{
	// we can get away with a single static variable here instead of a <Player, boolean> map
	// because we only care about the state before it has any possibility of changing
	private static boolean wasVelocityChangedBeforeFall = false;

	public static void beforeFall(PlayerEntity player, float fallDistance, float damageMultiplier)
	{
		if (player.world.isClient)
			return;

		wasVelocityChangedBeforeFall = player.velocityModified;
	}

	public static void afterFall(PlayerEntity player, float fallDistance, float damageMultiplier)
	{
		if (player.world.isClient)
			return;

		player.velocityModified = wasVelocityChangedBeforeFall;
	}
}
