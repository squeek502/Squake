package squeek.quakemovement;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import api.player.client.ClientPlayerAPI;
import api.player.client.ClientPlayerBase;
import cpw.mods.fml.common.Loader;

public class QuakeClientPlayer extends ClientPlayerBase
{
	public boolean didJumpThisTick = false;

	List<float[]> baseVelocities = new ArrayList<float[]>();

	private static Class<?> hudSpeedometer = null;
	private static Method setDidJumpThisTick = null;
	private static Method setIsJumping = null;
	static
	{
		try
		{
			if (Loader.isModLoaded("Squeedometer"))
			{
				hudSpeedometer = Class.forName("squeek.speedometer.HudSpeedometer");
				setDidJumpThisTick = hudSpeedometer.getDeclaredMethod("setDidJumpThisTick", new Class<?>[]{boolean.class});
				setIsJumping = hudSpeedometer.getDeclaredMethod("setIsJumping", new Class<?>[]{boolean.class});
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public QuakeClientPlayer(ClientPlayerAPI playerapi)
	{
		super(playerapi);
	}

	@Override
	public void moveEntityWithHeading(float sidemove, float forwardmove)
	{
		if (!ModConfig.ENABLED)
		{
			super.moveEntityWithHeading(sidemove, forwardmove);
			return;
		}

		double d0 = this.player.posX;
		double d1 = this.player.posY;
		double d2 = this.player.posZ;

		if (this.player.capabilities.isFlying && this.player.ridingEntity == null)
			super.moveEntityWithHeading(sidemove, forwardmove);
		else
			this.quake_moveEntityWithHeading(sidemove, forwardmove);

		this.player.addMovementStat(this.player.posX - d0, this.player.posY - d1, this.player.posZ - d2);
	}

	@Override
	public void beforeOnLivingUpdate()
	{
		this.didJumpThisTick = false;
		if (setDidJumpThisTick != null)
		{
			try
			{
				setDidJumpThisTick.invoke(null, false);
			}
			catch (Exception e)
			{
			}
		}

		if (!baseVelocities.isEmpty())
		{
			baseVelocities.clear();
		}

		super.beforeOnLivingUpdate();
	}

	@Override
	public void onLivingUpdate()
	{
		if (setIsJumping != null)
		{
			try
			{
				setIsJumping.invoke(null, this.playerAPI.getIsJumpingField());
			}
			catch (Exception e)
			{
			}
		}

		super.onLivingUpdate();
	}

	@Override
	public void moveFlying(float sidemove, float forwardmove, float wishspeed)
	{
		if (!ModConfig.ENABLED)
		{
			super.moveFlying(sidemove, forwardmove, wishspeed);
			return;
		}

		if ((this.player.capabilities.isFlying && this.player.ridingEntity == null) || this.player.isInWater() || this.player.handleLavaMovement() || this.player.isOnLadder())
		{
			super.moveFlying(sidemove, forwardmove, wishspeed);
			return;
		}

		wishspeed *= 2.15f;
		float[] wishdir = getMovementDirection(sidemove, forwardmove);
		float[] wishvel = new float[]{wishdir[0] * wishspeed, wishdir[1] * wishspeed};
		baseVelocities.add(wishvel);

	}

	@Override
	public void jump()
	{
		super.jump();

		if (!ModConfig.ENABLED)
			return;

		// undo this dumb thing
		if (this.player.isSprinting())
		{
			float f = this.player.rotationYaw * 0.017453292F;
			this.player.motionX += MathHelper.sin(f) * 0.2F;
			this.player.motionZ -= MathHelper.cos(f) * 0.2F;
		}

		quake_Jump();

		this.didJumpThisTick = true;
		if (setDidJumpThisTick != null)
		{
			try
			{
				setDidJumpThisTick.invoke(null, true);
			}
			catch (Exception e)
			{
			}
		}
	}

	/* =================================================
	 * START HELPERS
	 * =================================================
	 */

	public double getSpeed()
	{
		return MathHelper.sqrt_double(this.player.motionX * this.player.motionX + this.player.motionZ * this.player.motionZ);
	}

	public float getSurfaceFriction()
	{
		float f2 = 1.0F;

		if (this.player.onGround)
		{
			Block ground = this.player.worldObj.getBlock(MathHelper.floor_double(this.player.posX), MathHelper.floor_double(this.player.boundingBox.minY) - 1, MathHelper.floor_double(this.player.posZ));

			f2 = 1.0F - ground.slipperiness;
		}

		return f2;
	}

	public float getSlipperiness()
	{
		float f2 = 0.91F;
		if (this.player.onGround)
		{
			f2 = 0.54600006F;
			Block ground = this.player.worldObj.getBlock(MathHelper.floor_double(this.player.posX), MathHelper.floor_double(this.player.boundingBox.minY) - 1, MathHelper.floor_double(this.player.posZ));

			if (ground != null)
				f2 = ground.slipperiness * 0.91F;
		}
		return f2;
	}

	public float minecraft_getMoveSpeed()
	{
		float f2 = this.getSlipperiness();

		float f3 = 0.16277136F / (f2 * f2 * f2);

		return this.player.getAIMoveSpeed() * f3;
	}

	public float[] getMovementDirection(float sidemove, float forwardmove)
	{
		float f3 = sidemove * sidemove + forwardmove * forwardmove;
		float[] dir = {0.0F, 0.0F};

		if (f3 >= 1.0E-4F)
		{
			f3 = MathHelper.sqrt_float(f3);

			if (f3 < 1.0F)
			{
				f3 = 1.0F;
			}

			f3 = 1.0F / f3;
			sidemove *= f3;
			forwardmove *= f3;
			float f4 = MathHelper.sin(this.player.rotationYaw * (float) Math.PI / 180.0F);
			float f5 = MathHelper.cos(this.player.rotationYaw * (float) Math.PI / 180.0F);
			dir[0] = (sidemove * f5 - forwardmove * f4);
			dir[1] = (forwardmove * f5 + sidemove * f4);
		}

		return dir;
	}

	public float quake_getMoveSpeed()
	{
		float baseSpeed = this.player.getAIMoveSpeed();
		return !this.player.isSneaking() ? baseSpeed * 2.15F : baseSpeed * 1.11F;
	}

	public float quake_getMaxMoveSpeed()
	{
		float baseSpeed = this.player.getAIMoveSpeed();
		return baseSpeed * 2.15F;
	}

	private void spawnBunnyhopParticles(int numParticles)
	{
		// taken from sprint
		int j = MathHelper.floor_double(this.player.posX);
		int i = MathHelper.floor_double(this.player.posY - 0.20000000298023224D - this.player.yOffset);
		int k = MathHelper.floor_double(this.player.posZ);
		Block ground = this.player.worldObj.getBlock(j, i, k);

		if (ground != null && ground.getMaterial() != Material.air)
		{
			for (int iParticle = 0; iParticle < numParticles; iParticle++)
			{
				this.player.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(ground) + "_" + this.player.worldObj.getBlockMetadata(j, i, k), this.player.posX + (this.playerAPI.getRandField().nextFloat() - 0.5D) * this.player.width, this.player.boundingBox.minY + 0.1D, this.player.posZ + (this.playerAPI.getRandField().nextFloat() - 0.5D) * this.player.width, -this.player.motionX * 4.0D, 1.5D, -this.player.motionZ * 4.0D);
			}
		}
	}

	public boolean isJumping()
	{
		return this.playerAPI.getIsJumpingField();
	}

	/* =================================================
	 * END HELPERS
	 * =================================================
	 */

	/* =================================================
	 * START MINECRAFT PHYSICS
	 * =================================================
	 */

	private void minecraft_ApplyGravity()
	{
		if (this.player.worldObj.isRemote && (!this.player.worldObj.blockExists((int) this.player.posX, 0, (int) this.player.posZ) || !this.player.worldObj.getChunkFromBlockCoords((int) this.player.posX, (int) this.player.posZ).isChunkLoaded))
		{
			if (this.player.posY > 0.0D)
			{
				this.player.motionY = -0.1D;
			}
			else
			{
				this.player.motionY = 0.0D;
			}
		}
		else
		{
			// gravity
			this.player.motionY -= 0.08D;
		}

		// air resistance
		this.player.motionY *= 0.9800000190734863D;
	}

	private void minecraft_ApplyFriction(float momentumRetention)
	{
		this.player.motionX *= momentumRetention;
		this.player.motionZ *= momentumRetention;
	}

	private void minecraft_ApplyLadderPhysics()
	{
		if (this.player.isOnLadder())
		{
			float f5 = 0.15F;

			if (this.player.motionX < (-f5))
			{
				this.player.motionX = (-f5);
			}

			if (this.player.motionX > f5)
			{
				this.player.motionX = f5;
			}

			if (this.player.motionZ < (-f5))
			{
				this.player.motionZ = (-f5);
			}

			if (this.player.motionZ > f5)
			{
				this.player.motionZ = f5;
			}

			this.player.fallDistance = 0.0F;

			if (this.player.motionY < -0.15D)
			{
				this.player.motionY = -0.15D;
			}

			boolean flag = this.player.isSneaking();

			if (flag && this.player.motionY < 0.0D)
			{
				this.player.motionY = 0.0D;
			}
		}
	}

	private void minecraft_ClimbLadder()
	{
		if (this.player.isCollidedHorizontally && this.player.isOnLadder())
		{
			this.player.motionY = 0.2D;
		}
	}

	private void minecraft_SwingLimbsBasedOnMovement()
	{
		this.player.prevLimbSwingAmount = this.player.limbSwingAmount;
		double d0 = this.player.posX - this.player.prevPosX;
		double d1 = this.player.posZ - this.player.prevPosZ;
		float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

		if (f6 > 1.0F)
		{
			f6 = 1.0F;
		}

		this.player.limbSwingAmount += (f6 - this.player.limbSwingAmount) * 0.4F;
		this.player.limbSwing += this.player.limbSwingAmount;
	}

	private void minecraft_WaterMove(float sidemove, float forwardmove)
	{
		double d0 = this.player.posY;
		this.player.moveFlying(sidemove, forwardmove, 0.04F);
		this.player.moveEntity(this.player.motionX, this.player.motionY, this.player.motionZ);
		this.player.motionX *= 0.800000011920929D;
		this.player.motionY *= 0.800000011920929D;
		this.player.motionZ *= 0.800000011920929D;
		this.player.motionY -= 0.02D;

		if (this.player.isCollidedHorizontally && this.player.isOffsetPositionInLiquid(this.player.motionX, this.player.motionY + 0.6000000238418579D - this.player.posY + d0, this.player.motionZ))
		{
			this.player.motionY = 0.30000001192092896D;
		}
	}

	public void minecraft_moveEntityWithHeading(float sidemove, float forwardmove)
	{
		// take care of water and lava movement using default code
		if ((this.player.isInWater() && !this.player.capabilities.isFlying)
				|| (this.player.handleLavaMovement() && !this.player.capabilities.isFlying))
		{
			super.moveEntityWithHeading(sidemove, forwardmove);
		}
		else
		{
			// get friction
			float momentumRetention = this.getSlipperiness();

			// alter motionX/motionZ based on desired movement
			this.player.moveFlying(sidemove, forwardmove, this.minecraft_getMoveSpeed());

			// make adjustments for ladder interaction
			minecraft_ApplyLadderPhysics();

			// do the movement
			this.player.moveEntity(this.player.motionX, this.player.motionY, this.player.motionZ);

			// climb ladder here for some reason
			minecraft_ClimbLadder();

			// gravity + friction
			minecraft_ApplyGravity();
			minecraft_ApplyFriction(momentumRetention);

			// swing them arms
			minecraft_SwingLimbsBasedOnMovement();
		}
	}

	/* =================================================
	 * END MINECRAFT PHYSICS
	 * =================================================
	 */

	/* =================================================
	 * START QUAKE PHYSICS
	 * =================================================
	 */

	/**
	 * Moves the entity based on the specified heading.  Args: strafe, forward
	 */
	public void quake_moveEntityWithHeading(float sidemove, float forwardmove)
	{
		// take care of ladder movement using default code
		if (this.player.isOnLadder())
		{
			super.moveEntityWithHeading(sidemove, forwardmove);
			return;
		}
		// take care of lava movement using default code
		else if ((this.player.handleLavaMovement() && !this.player.capabilities.isFlying))
		{
			super.moveEntityWithHeading(sidemove, forwardmove);
			return;
		}
		else if (this.player.isInWater() && !this.player.capabilities.isFlying)
		{
			if (ModConfig.SHARKING_ENABLED)
				quake_WaterMove(sidemove, forwardmove);
			else
			{
				super.moveEntityWithHeading(sidemove, forwardmove);
				return;
			}
		}
		else
		{
			// get all relevant movement values
			float wishspeed = (sidemove != 0.0F || forwardmove != 0.0F) ? this.quake_getMoveSpeed() : 0.0F;
			float[] wishdir = this.getMovementDirection(sidemove, forwardmove);
			boolean onGroundForReal = this.player.onGround && !this.isJumping();
			float momentumRetention = this.getSlipperiness();

			// ground movement
			if (onGroundForReal)
			{
				// apply friction before acceleration so we can accelerate back up to maxspeed afterwards
				//quake_Friction(); // buggy because material-based friction uses a totally different format
				minecraft_ApplyFriction(momentumRetention);

				double sv_accelerate = ModConfig.ACCELERATE;

				if (wishspeed != 0.0F)
				{
					// alter based on the surface friction
					sv_accelerate *= this.minecraft_getMoveSpeed() * 2.15F / wishspeed;

					quake_Accelerate(wishspeed, wishdir[0], wishdir[1], sv_accelerate);
				}

				if (!baseVelocities.isEmpty())
				{
					float speedMod = wishspeed / quake_getMaxMoveSpeed();
					// add in base velocities
					for (float[] baseVel : baseVelocities)
					{
						this.player.motionX += baseVel[0] * speedMod;
						this.player.motionZ += baseVel[1] * speedMod;
					}
				}
			}
			// air movement
			else
			{
				double sv_airaccelerate = ModConfig.AIR_ACCELERATE;
				quake_AirAccelerate(wishspeed, wishdir[0], wishdir[1], sv_airaccelerate);

				if (ModConfig.SHARKING_ENABLED && ModConfig.SHARKING_SURFACE_TENSION > 0.0D && this.playerAPI.getIsJumpingField() && this.player.motionY < 0.0F)
				{
					AxisAlignedBB axisalignedbb = this.player.boundingBox.getOffsetBoundingBox(this.player.motionX, this.player.motionY, this.player.motionZ);
					boolean isFallingIntoWater = this.player.worldObj.isAnyLiquid(axisalignedbb);

					if (isFallingIntoWater)
						this.player.motionY *= ModConfig.SHARKING_SURFACE_TENSION;
				}
			}

			// apply velocity
			this.player.moveEntity(this.player.motionX, this.player.motionY, this.player.motionZ);

			// HL2 code applies half gravity before acceleration and half after acceleration, but this seems to work fine
			minecraft_ApplyGravity();
		}

		// swing them arms
		minecraft_SwingLimbsBasedOnMovement();
	}

	private void quake_Jump()
	{
		quake_ApplySoftCap(this.quake_getMaxMoveSpeed());

		boolean didTrimp = quake_DoTrimp();

		if (!didTrimp)
		{
			quake_ApplyHardCap(this.quake_getMaxMoveSpeed());
		}
	}

	private boolean quake_DoTrimp()
	{
		if (ModConfig.TRIMPING_ENABLED && this.player.isSneaking())
		{
			double curspeed = this.getSpeed();
			float movespeed = this.quake_getMaxMoveSpeed();
			if (curspeed > movespeed)
			{
				double speedbonus = curspeed / movespeed * 0.5F;
				if (speedbonus > 1.0F)
					speedbonus = 1.0F;

				this.player.motionY += speedbonus * curspeed * ModConfig.TRIMP_MULTIPLIER;

				if (ModConfig.TRIMP_MULTIPLIER > 0)
				{
					float mult = 1.0f / ModConfig.TRIMP_MULTIPLIER;
					this.player.motionX *= mult;
					this.player.motionZ *= mult;
				}

				spawnBunnyhopParticles(30);

				return true;
			}
		}

		return false;
	}

	private void quake_ApplyWaterFriction(double friction)
	{
		this.player.motionX *= friction;
		this.player.motionY *= friction;
		this.player.motionZ *= friction;

		/*
		float speed = (float)(this.player.getSpeed());
		float newspeed = 0.0F;
		if (speed != 0.0F)
		{
			newspeed = speed - 0.05F * speed * friction; //* player->m_surfaceFriction;

			float mult = newspeed/speed;
			this.player.motionX *= mult;
			this.player.motionY *= mult;
			this.player.motionZ *= mult;
		}

		return newspeed;
		*/

		/*
		// slow in water
		this.player.motionX *= 0.800000011920929D;
		this.player.motionY *= 0.800000011920929D;
		this.player.motionZ *= 0.800000011920929D;
		*/
	}

	@SuppressWarnings("unused")
	private void quake_WaterAccelerate(float wishspeed, float speed, double wishX, double wishZ, double accel)
	{
		float addspeed = wishspeed - speed;
		if (addspeed > 0)
		{
			float accelspeed = (float) (accel * wishspeed * 0.05F);
			if (accelspeed > addspeed)
			{
				accelspeed = addspeed;
			}

			this.player.motionX += accelspeed * wishX;
			this.player.motionZ += accelspeed * wishZ;
		}
	}

	private void quake_WaterMove(float sidemove, float forwardmove)
	{
		double lastPosY = this.player.posY;

		// get all relevant movement values
		float wishspeed = (sidemove != 0.0F || forwardmove != 0.0F) ? this.quake_getMaxMoveSpeed() : 0.0F;
		float[] wishdir = this.getMovementDirection(sidemove, forwardmove);
		boolean isSharking = this.isJumping() && this.player.isOffsetPositionInLiquid(0.0D, 1.0D, 0.0D);
		double curspeed = this.getSpeed();

		if (!isSharking || curspeed < 0.078F)
		{
			minecraft_WaterMove(sidemove, forwardmove);
		}
		else
		{
			if (curspeed > 0.09)
				quake_ApplyWaterFriction(ModConfig.SHARKING_WATER_FRICTION);

			if (curspeed > 0.098)
				quake_AirAccelerate(wishspeed, wishdir[0], wishdir[1], ModConfig.ACCELERATE);
			else
				quake_Accelerate(.0980F, wishdir[0], wishdir[1], ModConfig.ACCELERATE);

			this.player.moveEntity(this.player.motionX, this.player.motionY, this.player.motionZ);

			this.player.motionY = 0.0D;
		}

		// water jump
		if (this.player.isCollidedHorizontally && this.player.isOffsetPositionInLiquid(this.player.motionX, this.player.motionY + 0.6000000238418579D - this.player.posY + lastPosY, this.player.motionZ))
		{
			this.player.motionY = 0.30000001192092896D;
		}

		if (!baseVelocities.isEmpty())
		{
			float speedMod = wishspeed / quake_getMaxMoveSpeed();
			// add in base velocities
			for (float[] baseVel : baseVelocities)
			{
				this.player.motionX += baseVel[0] * speedMod;
				this.player.motionZ += baseVel[1] * speedMod;
			}
		}
	}

	private void quake_Accelerate(float wishspeed, double wishX, double wishZ, double accel)
	{
		double addspeed, accelspeed, currentspeed;

		// Determine veer amount
		// this is a dot product
		currentspeed = this.player.motionX * wishX + this.player.motionZ * wishZ;

		// See how much to add
		addspeed = wishspeed - currentspeed;

		// If not adding any, done.
		if (addspeed <= 0)
			return;

		// Determine acceleration speed after acceleration
		accelspeed = accel * wishspeed / getSlipperiness() * 0.05F;

		// Cap it
		if (accelspeed > addspeed)
			accelspeed = addspeed;

		// Adjust pmove vel.
		this.player.motionX += accelspeed * wishX;
		this.player.motionZ += accelspeed * wishZ;
	}

	private void quake_AirAccelerate(float wishspeed, double wishX, double wishZ, double accel)
	{
		double addspeed, accelspeed, currentspeed;

		float wishspd = wishspeed;
		float maxAirAcceleration = (float) ModConfig.MAX_AIR_ACCEL_PER_TICK;

		if (wishspd > maxAirAcceleration)
			wishspd = maxAirAcceleration;

		// Determine veer amount
		// this is a dot product
		currentspeed = this.player.motionX * wishX + this.player.motionZ * wishZ;

		// See how much to add
		addspeed = wishspd - currentspeed;

		// If not adding any, done.
		if (addspeed <= 0)
			return;

		// Determine acceleration speed after acceleration
		accelspeed = accel * wishspeed * 0.05F;

		// Cap it
		if (accelspeed > addspeed)
			accelspeed = addspeed;

		// Adjust pmove vel.
		this.player.motionX += accelspeed * wishX;
		this.player.motionZ += accelspeed * wishZ;
	}

	@SuppressWarnings("unused")
	private void quake_Friction()
	{
		double speed, newspeed, control;
		float friction;
		float drop;

		// Calculate speed
		speed = this.getSpeed();

		// If too slow, return
		if (speed <= 0.0F)
		{
			return;
		}

		drop = 0.0F;

		// convars
		float sv_friction = 1.0F;
		float sv_stopspeed = 0.005F;

		float surfaceFriction = this.getSurfaceFriction();
		friction = sv_friction * surfaceFriction;

		// Bleed off some speed, but if we have less than the bleed
		//  threshold, bleed the threshold amount.
		control = (speed < sv_stopspeed) ? sv_stopspeed : speed;

		// Add the amount to the drop amount.
		drop += control * friction * 0.05F;

		// scale the velocity
		newspeed = speed - drop;
		if (newspeed < 0.0F)
			newspeed = 0.0F;

		if (newspeed != speed)
		{
			// Determine proportion of old speed we are using.
			newspeed /= speed;
			// Adjust velocity according to proportion.
			this.player.motionX *= newspeed;
			this.player.motionZ *= newspeed;
		}
	}

	private void quake_ApplySoftCap(float movespeed)
	{
		float softCapPercent = ModConfig.SOFT_CAP;
		float softCapDegen = ModConfig.SOFT_CAP_DEGEN;

		if (ModConfig.UNCAPPED_BUNNYHOP_ENABLED)
		{
			softCapPercent = 1.0F;
			softCapDegen = 1.0F;
		}

		float speed = (float) (this.getSpeed());
		float softCap = movespeed * softCapPercent;

		// apply soft cap first; if soft -> hard is not done, then you can continually trigger only the hard cap and stay at the hard cap
		if (speed > softCap)
		{
			if (softCapDegen != 1.0F)
			{
				float applied_cap = (speed - softCap) * softCapDegen + softCap;
				float multi = applied_cap / speed;
				this.player.motionX *= multi;
				this.player.motionZ *= multi;
			}

			spawnBunnyhopParticles(10);
		}
	}

	private void quake_ApplyHardCap(float movespeed)
	{
		if (ModConfig.UNCAPPED_BUNNYHOP_ENABLED)
			return;

		float hardCapPercent = ModConfig.HARD_CAP;

		float speed = (float) (this.getSpeed());
		float hardCap = movespeed * hardCapPercent;

		if (speed > hardCap && hardCap != 0.0F)
		{
			float multi = hardCap / speed;
			this.player.motionX *= multi;
			this.player.motionZ *= multi;

			spawnBunnyhopParticles(30);
		}
	}

	@SuppressWarnings("unused")
	private void quake_OnLivingUpdate()
	{
		this.didJumpThisTick = false;
	}

	/* =================================================
	 * END QUAKE PHYSICS
	 * =================================================
	 */
}
