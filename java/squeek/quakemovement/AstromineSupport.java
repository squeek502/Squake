package squeek.quakemovement;

import com.github.chainmailstudios.astromine.common.entity.GravityEntity;

import net.minecraft.entity.player.PlayerEntity;

public class AstromineSupport {
    private AstromineSupport(){}

    public static double getGravity(PlayerEntity player) {
        return ((GravityEntity)player).getGravity(player.getEntityWorld());
    }
}