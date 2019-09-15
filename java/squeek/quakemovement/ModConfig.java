package squeek.quakemovement;

public class ModConfig
{
	public static final String CATEGORY_MOVEMENT = "movement";

	private static final double TRIMP_MULTIPLIER_DEFAULT = 1.4D;
	public static float TRIMP_MULTIPLIER = (float)TRIMP_MULTIPLIER_DEFAULT;
	private static final String TRIMP_MULTIPLIER_NAME = "trimpMultiplier";

	private static final double HARD_CAP_DEFAULT = 2.0D;
	public static float HARD_CAP = (float)HARD_CAP_DEFAULT;
	private static final String HARD_CAP_NAME = "hardCapThreshold";

	private static final double SOFT_CAP_DEFAULT = 1.4D;
	public static float SOFT_CAP = (float)SOFT_CAP_DEFAULT;
	private static final String SOFT_CAP_NAME = "softCapThreshold";

	public static float SOFT_CAP_DEGEN;
	private static final String SOFT_CAP_DEGEN_NAME = "softCapDegen";
	private static final double SOFT_CAP_DEGEN_DEFAULT = 0.65D; // 0.65

	private static final boolean SHARKING_ENABLED_DEFAULT = true;
	public static boolean SHARKING_ENABLED = SHARKING_ENABLED_DEFAULT;
	private static final String SHARKING_ENABLED_NAME = "sharkingEnabled";

	private static final double SHARKING_SURFACE_TENSION_DEFAULT = 0.2D;
	public static double SHARKING_SURFACE_TENSION = SHARKING_SURFACE_TENSION_DEFAULT;
	private static final String SHARKING_SURFACE_TENSION_NAME = "sharkingSurfaceTension";

	private static final double SHARKING_WATER_FRICTION_DEFAULT = 0.1D;
	public static double SHARKING_WATER_FRICTION = SHARKING_WATER_FRICTION_DEFAULT;
	private static final String SHARKING_WATER_FRICTION_NAME = "sharkingWaterFriction";

	private static final double ACCELERATE_DEFAULT = 10.0D;
	public static double ACCELERATE = ACCELERATE_DEFAULT;
	private static final String ACCELERATE_NAME = "groundAccelerate";

	private static final double AIR_ACCELERATE_DEFAULT = 14.0D;
	public static double AIR_ACCELERATE = AIR_ACCELERATE_DEFAULT;
	private static final String AIR_ACCELERATE_NAME = "airAccelerate";

	private static final boolean UNCAPPED_BUNNYHOP_ENABLED_DEFAULT = true;
	public static boolean UNCAPPED_BUNNYHOP_ENABLED = UNCAPPED_BUNNYHOP_ENABLED_DEFAULT;
	private static final String UNCAPPED_BUNNYHOP_ENABLED_NAME = "uncappedBunnyhopEnabled";

	private static final boolean TRIMPING_ENABLED_DEFAULT = true;
	public static boolean TRIMPING_ENABLED = TRIMPING_ENABLED_DEFAULT;
	private static final String TRIMPING_ENABLED_NAME = "trimpEnabled";

	private static final double INCREASED_FALL_DISTANCE_DEFAULT = 0.0D;
	public static double INCREASED_FALL_DISTANCE = INCREASED_FALL_DISTANCE_DEFAULT;
	private static final String INCREASED_FALL_DISTANCE_NAME = "fallDistanceThresholdIncrease";

	private static final double MAX_AIR_ACCEL_PER_TICK_DEFAULT = 0.045D;
	public static double MAX_AIR_ACCEL_PER_TICK = MAX_AIR_ACCEL_PER_TICK_DEFAULT;
	private static final String MAX_AIR_ACCEL_PER_TICK_NAME = "maxAirAccelerationPerTick";

	private static final boolean ENABLED_DEFAULT = true;
	public static boolean ENABLED = ENABLED_DEFAULT;
}
