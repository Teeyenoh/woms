package uk.co.quarklike.woms;

public class Biome {
	private static final int DEEP = 0; // below -1000
	private static final int SHALLOW = 1; // below -300
	private static final int VERY_SHALLOW = 2; // below 0
	private static final int LOW = 3; // below 1000
	private static final int HIGH = 4; // below 2000
	private static final int VERY_HIGH = 5;

	private static final int FREEZING = 0; // below 260 (-13 C)
	private static final int COLD = 1; // below 280 (7 C)
	private static final int TEMPERATE = 2; // below 290 (17 C)
	private static final int WARM = 3; // below 310 (37 C)
	private static final int HOT = 4; // below 320 (47 C)
	private static final int SCORCHING = 5;

	private static final int VERY_DRY = 0; // below 5
	private static final int DRY = 1; // below 100
	private static final int NORMAL = 2; // below 300
	private static final int WET = 3; // below 500
	private static final int VERY_WET = 4; // below 1000
	private static final int SOAKED = 5;

	private static Biome[][][] biomes = new Biome[6][6][6];

	private int r, g, b;
	private String name;

	public static final Biome nullBiome = new Biome("Null", 0, 0, 0, DEEP, VERY_HIGH, FREEZING, SCORCHING, VERY_DRY, SOAKED);
	public static final Biome ocean = new Biome("Ocean", 0, 4, 53, DEEP, DEEP, COLD, SCORCHING, VERY_DRY, SOAKED);
	public static final Biome sea = new Biome("Sea", 18, 28, 140, SHALLOW, SHALLOW, COLD, SCORCHING, VERY_DRY, SOAKED);
	public static final Biome coast = new Biome("Coast", 55, 67, 198, VERY_SHALLOW, VERY_SHALLOW, COLD, SCORCHING, VERY_DRY, SOAKED);
	public static final Biome icesheet = new Biome("Icesheet", 175, 189, 255, DEEP, VERY_SHALLOW, FREEZING, FREEZING, VERY_DRY, DRY);
	public static final Biome snowsheet = new Biome("Snowsheet", 226, 231, 255, DEEP, VERY_SHALLOW, FREEZING, FREEZING, NORMAL, SOAKED);
	public static final Biome grassland = new Biome("Grassland", 149, 168, 8, LOW, LOW, TEMPERATE, WARM, NORMAL, NORMAL);
	public static final Biome grassland_hills = new Biome("Grassland Hills", 131, 140, 65, HIGH, HIGH, TEMPERATE, WARM, NORMAL, NORMAL);
	public static final Biome forest_deciduous = new Biome("Deciduous Forest", 26, 66, 5, LOW, LOW, TEMPERATE, WARM, WET, VERY_WET);
	public static final Biome forest_deciduous_hills = new Biome("Deciduous Forest Hills", 60, 76, 51, HIGH, HIGH, TEMPERATE, WARM, WET, SOAKED);
	public static final Biome forest_coniferous = new Biome("Coniferous Forest", 26, 66, 5, LOW, LOW, COLD, COLD, WET, VERY_WET);
	public static final Biome forest_coniferous_hills = new Biome("Coniferous Forest Hills", 60, 76, 51, HIGH, HIGH, COLD, COLD, WET, SOAKED);
	public static final Biome rainforest = new Biome("Rainforest", 38, 119, 4, LOW, LOW, HOT, SCORCHING, VERY_WET, SOAKED);
	public static final Biome rainforest_hills = new Biome("Rainforest Hills", 72, 124, 49, HIGH, HIGH, HOT, SCORCHING, VERY_WET, SOAKED);
	public static final Biome mountains = new Biome("Mountains", 127, 127, 127, VERY_HIGH, VERY_HIGH, FREEZING, SCORCHING, VERY_DRY, SOAKED);
	public static final Biome savannah = new Biome("Savannah", 255, 0, 0, LOW, LOW, HOT, SCORCHING, NORMAL, WET);
	public static final Biome desert = new Biome("Desert", 255, 246, 142, LOW, LOW, HOT, SCORCHING, VERY_DRY, DRY);
	public static final Biome desert_hills = new Biome("Desert Hills", 196, 191, 137, HIGH, HIGH, HOT, SCORCHING, VERY_DRY, DRY);
	public static final Biome tundra = new Biome("Tundra", 200, 200, 255, LOW, LOW, FREEZING, FREEZING, VERY_DRY, DRY);
	public static final Biome tundra_hills = new Biome("Tundra Hills", 170, 170, 235, HIGH, HIGH, FREEZING, FREEZING, VERY_DRY, DRY);
	public static final Biome tundra_snowy = new Biome("Snowy Tundra", 200, 200, 200, LOW, LOW, FREEZING, FREEZING, NORMAL, SOAKED);
	public static final Biome tundra_snowy_hills = new Biome("Snowy Tundra Hills", 170, 170, 170, HIGH, HIGH, FREEZING, FREEZING, NORMAL, SOAKED);
	public static final Biome swamp = new Biome("Swamp", 5, 15, 0, LOW, LOW, COLD, WARM, SOAKED, SOAKED);

	public Biome(String name, int r, int g, int b, int minHeight, int maxHeight, int minTemp, int maxTemp, int minRain, int maxRain) {
		this.r = r;
		this.g = g;
		this.b = b;

		for (int h = minHeight; h <= maxHeight; h++) {
			for (int t = minTemp; t <= maxTemp; t++) {
				for (int rain = minRain; rain <= maxRain; rain++) {
					if (biomes[h][t][rain] != nullBiome) {
						System.out.println("When adding biome " + name + ", found position " + h + " " + t + " " + rain + " to be filled by " + biomes[h][t][rain].getName());
					}
					biomes[h][t][rain] = this;
				}
			}
		}
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param height
	 *            in meters
	 * @param temperature
	 *            in Kelvin
	 * @param rainfall
	 *            in millimetres
	 * @return
	 */
	public static Biome getBiome(int height, int temperature, int rainfall) {
		int heightC = height < -1000 ? DEEP : height < -50 ? SHALLOW : height < 0 ? VERY_SHALLOW : height < 1000 ? LOW : height < 2000 ? HIGH : VERY_HIGH;
		int tempC = temperature < 260 ? FREEZING : temperature < 280 ? COLD : temperature < 290 ? TEMPERATE : temperature < 310 ? WARM : temperature < 320 ? HOT : SCORCHING;
		int rainC = rainfall < 250 ? VERY_DRY : rainfall < 500 ? DRY : rainfall < 500 ? NORMAL : rainfall < 1000 ? WET : rainfall < 2000 ? VERY_WET : SOAKED;

		Biome type = biomes[heightC][tempC][rainC];

		return type == null ? nullBiome : type;
	}
}
