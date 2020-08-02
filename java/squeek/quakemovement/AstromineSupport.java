package squeek.quakemovement;

import com.github.chainmailstudios.astromine.common.registry.GravityRegistry;

import net.minecraft.entity.player.PlayerEntity;

public class AstromineSupport {
    private AstromineSupport(){}

    public static double getGravity(PlayerEntity player) {
        return GravityRegistry.INSTANCE.get(player.world.getDimensionRegistryKey());
    }
}