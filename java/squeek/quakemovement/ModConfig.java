package squeek.quakemovement;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ModConfig
{
	private static final String CATEGORY_MOVEMENT = "movement";

	public static float TRIMP_MULTIPLIER;
	private static final String TRIMP_MULTIPLIER_NAME = "trimpMultiplier";
	private static final double TRIMP_MULTIPLIER_DEFAULT = 1.4D;

	public static float HARD_CAP;
	private static final String HARD_CAP_NAME = "hardCapThreshold";
	private static final double HARD_CAP_DEFAULT = 2.0D;

	public static float SOFT_CAP;
	private static final String SOFT_CAP_NAME = "softCapThreshold";
	private static final double SOFT_CAP_DEFAULT = 1.4D;

	public static float SOFT_CAP_DEGEN;
	private static final String SOFT_CAP_DEGEN_NAME = "softCapDegen";
	private static final double SOFT_CAP_DEGEN_DEFAULT = 0.65D; // 0.65

	public static boolean SHARKING_ENABLED;
	private static final String SHARKING_ENABLED_NAME = "sharkingEnabled";
	private static final boolean SHARKING_ENABLED_DEFAULT = true;

	public static double SHARKING_SURFACE_TENSION;
	private static final String SHARKING_SURFACE_TENSION_NAME = "sharkingSurfaceTension";
	private static final double SHARKING_SURFACE_TENSION_DEFAULT = 0.2D;

	public static double SHARKING_WATER_FRICTION;
	private static final String SHARKING_WATER_FRICTION_NAME = "sharkingWaterFriction";
	private static final double SHARKING_WATER_FRICTION_DEFAULT = 0.1D;

	public static double ACCELERATE;
	private static final String ACCELERATE_NAME = "groundAccelerate";
	private static final double ACCELERATE_DEFAULT = 10.0D;

	public static double AIR_ACCELERATE;
	private static final String AIR_ACCELERATE_NAME = "airAccelerate";
	private static final double AIR_ACCELERATE_DEFAULT = 14.0D;

	public static boolean UNCAPPED_BUNNYHOP_ENABLED;
	private static final String UNCAPPED_BUNNYHOP_ENABLED_NAME = "uncappedBunnyhopEnabled";
	private static final boolean UNCAPPED_BUNNYHOP_ENABLED_DEFAULT = true;

	public static boolean TRIMPING_ENABLED;
	private static final String TRIMPING_ENABLED_NAME = "trimpEnabled";
	private static final boolean TRIMPING_ENABLED_DEFAULT = true;

	public static double INCREASED_FALL_DISTANCE;
	private static final String INCREASED_FALL_DISTANCE_NAME = "fallDistanceThresholdIncrease";
	private static final double INCREASED_FALL_DISTANCE_DEFAULT = 0.0D;

	public static double MAX_AIR_ACCEL_PER_TICK;
	private static final String MAX_AIR_ACCEL_PER_TICK_NAME = "maxAirAccelerationPerTick";
	private static final double MAX_AIR_ACCEL_PER_TICK_DEFAULT = 0.045D;

	public static boolean ENABLED;
	private static Property ENABLED_PROPERTY;
	private static final String ENABLED_NAME = "enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private static Configuration config;

	public static void init(File file)
	{
		config = new Configuration(file);

		load();

		UNCAPPED_BUNNYHOP_ENABLED = config.get(CATEGORY_MOVEMENT, UNCAPPED_BUNNYHOP_ENABLED_NAME, UNCAPPED_BUNNYHOP_ENABLED_DEFAULT, "if enabled, the soft and hard caps will not be applied at all").getBoolean(UNCAPPED_BUNNYHOP_ENABLED_DEFAULT);
		AIR_ACCELERATE = config.get(CATEGORY_MOVEMENT, AIR_ACCELERATE_NAME, AIR_ACCELERATE_DEFAULT, "a higher value means you can turn more sharply in the air without losing speed").getDouble(AIR_ACCELERATE_DEFAULT);
		MAX_AIR_ACCEL_PER_TICK = config.get(CATEGORY_MOVEMENT, MAX_AIR_ACCEL_PER_TICK_NAME, MAX_AIR_ACCEL_PER_TICK_DEFAULT, "a higher value means faster air acceleration").getDouble(MAX_AIR_ACCEL_PER_TICK_DEFAULT);
		ACCELERATE = config.get(CATEGORY_MOVEMENT, ACCELERATE_NAME, ACCELERATE_DEFAULT, "a higher value means you accelerate faster on the ground").getDouble(ACCELERATE_DEFAULT);
		HARD_CAP = (float) (config.get(CATEGORY_MOVEMENT, HARD_CAP_NAME, HARD_CAP_DEFAULT, "see " + UNCAPPED_BUNNYHOP_ENABLED_NAME + "; if you ever jump while above the hard cap speed (moveSpeed*hardCapThreshold), your speed is set to the hard cap speed").getDouble(HARD_CAP_DEFAULT));
		SOFT_CAP = (float) (config.get(CATEGORY_MOVEMENT, SOFT_CAP_NAME, SOFT_CAP_DEFAULT, "see " + UNCAPPED_BUNNYHOP_ENABLED_NAME + " and " + SOFT_CAP_DEGEN_NAME + "; soft cap speed = (moveSpeed*softCapThreshold)").getDouble(SOFT_CAP_DEFAULT));
		SOFT_CAP_DEGEN = (float) (config.get(CATEGORY_MOVEMENT, SOFT_CAP_DEGEN_NAME, SOFT_CAP_DEGEN_DEFAULT, "the modifier used to calculate speed lost when jumping above the soft cap").getDouble(SOFT_CAP_DEGEN_DEFAULT));

		SHARKING_ENABLED = config.get(CATEGORY_MOVEMENT, SHARKING_ENABLED_NAME, SHARKING_ENABLED_DEFAULT, "if enabled, holding jump while swimming at the surface of water allows you to glide").getBoolean(SHARKING_ENABLED_DEFAULT);
		SHARKING_WATER_FRICTION = 1.0D - config.get(CATEGORY_MOVEMENT, SHARKING_WATER_FRICTION_NAME, SHARKING_WATER_FRICTION_DEFAULT, "amount of friction while sharking (between 0 and 1)").getDouble(SHARKING_WATER_FRICTION_DEFAULT) * 0.05D;
		SHARKING_SURFACE_TENSION = 1.0D - config.get(CATEGORY_MOVEMENT, SHARKING_SURFACE_TENSION_NAME, SHARKING_SURFACE_TENSION_DEFAULT, "amount of downward momentum you lose while entering water, a higher value means that you are able to shark after hitting the water from higher up").getDouble(SHARKING_SURFACE_TENSION_DEFAULT);

		TRIMPING_ENABLED = config.get(CATEGORY_MOVEMENT, TRIMPING_ENABLED_NAME, TRIMPING_ENABLED_DEFAULT, "if enabled, holding sneak while jumping will convert your horizontal speed into vertical speed").getBoolean(TRIMPING_ENABLED_DEFAULT);
		TRIMP_MULTIPLIER = (float) (config.get(CATEGORY_MOVEMENT, TRIMP_MULTIPLIER_NAME, TRIMP_MULTIPLIER_DEFAULT, "a lower value means less horizontal speed converted to vertical speed and vice versa").getDouble(TRIMP_MULTIPLIER_DEFAULT));

		INCREASED_FALL_DISTANCE = (float) (config.get(CATEGORY_MOVEMENT, INCREASED_FALL_DISTANCE_NAME, INCREASED_FALL_DISTANCE_DEFAULT, "increases the distance needed to fall in order to take fall damage; this is a server-side setting").getDouble(INCREASED_FALL_DISTANCE_DEFAULT));

		ENABLED_PROPERTY = config.get(CATEGORY_MOVEMENT, ENABLED_NAME, ENABLED_DEFAULT, "turns off/on the quake-style movement for the client (essentially the saved value of the ingame toggle keybind)");
		ENABLED = ENABLED_PROPERTY.getBoolean(ENABLED_DEFAULT);

		save();
	}

	public static void setEnabled(boolean enabled)
	{
		ModConfig.ENABLED = enabled;
		ENABLED_PROPERTY.set(enabled);
		save();
	}

	public static void save()
	{
		config.save();
	}

	public static void load()
	{
		config.load();
	}
}
