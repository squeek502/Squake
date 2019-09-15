package squeek.quakemovement.mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import squeek.quakemovement.ModQuakeMovement;

@Mixin(Block.class)
public abstract class TestBlockMixin
{
//	@Overwrite
//	public float getSlipperiness() {
//		return ModQuakeMovement.getFriction();
//	}
}
