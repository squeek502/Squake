package squeek.quakemovement;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "squake")
public class ModConfig implements ConfigData {

	private SpeedometerPosition speedometerPosition = SpeedometerPosition.BOTTOM_LEFT;

	private float trimpMultiplier = 1.4f;

	private float hardCapThreshold = 2f;

	private float softCapThreshold = 1.4f;

	private float softCapDegen = 0.65f;

	private boolean sharkingEnabled = true;

	private double sharkingSurfaceTension = 0.2d;

	private double sharkingWaterFriction = 0.99d;

	private double groundAccelerate = 10d;

	private double airAccelerate = 14d;

	private boolean uncappedBunnyhopEnabled = true;

	private boolean trimpEnabled = true;

	private double fallDistanceThresholdIncrease = 0.0d;

	private double maxAirAccelerationPerTick = 0.045d;

	private boolean enabled = true;

	public enum SpeedometerPosition {
		TOP_LEFT,
		TOP_RIGHT,
		BOTTOM_LEFT,
		BOTTOM_RIGHT,
		OFF
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		((ConfigManager)AutoConfig.getConfigHolder(ModConfig.class)).save();
	}

	public SpeedometerPosition getSpeedometerPosition() {
		return speedometerPosition;
	}

	public float getTrimpMultiplier() {
		return this.trimpMultiplier;
	}

	public float getHardCapThreshold() {
		return this.hardCapThreshold;
	}

	public float getSoftCapThreshold() {
		return this.softCapThreshold;
	}

	public float getSoftCapDegen() {
		return this.softCapDegen;
	}

	public boolean isSharkingEnabled() {
		return this.sharkingEnabled;
	}

	public double getSharkingSurfaceTension() {
		return this.sharkingSurfaceTension;
	}

	public double getSharkingWaterFriction() {
		return this.sharkingWaterFriction;
	}

	public double getGroundAccelerate() {
		return this.groundAccelerate;
	}

	public double getAirAccelerate() {
		return this.airAccelerate;
	}

	public boolean isUncappedBunnyhopEnabled() {
		return this.uncappedBunnyhopEnabled;
	}

	public boolean isTrimpEnabled() {
		return this.trimpEnabled;
	}

	public double getFallDistanceThresholdIncrease() {
		return this.fallDistanceThresholdIncrease;
	}

	public double getMaxAirAccelerationPerTick() {
		return this.maxAirAccelerationPerTick;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

}
