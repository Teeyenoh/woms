package uk.co.quarklike.woms;

public class Biome {
	public enum BiomeType {
		NULL, //
		OCEAN, //
		SEA, //
		COAST, // -30m to 0m, any temp, any rainfall
		ICESHEET, //
		SNOWSHEET, //
		TUNDRA, //
		SNOWY_TUNDRA, //
		TUNDRA_HILLS, //
		SNOWY_TUNDRA_HILLS, //
		TAIGA, // 0m to 600m, 285K to 313K, 850mm to 1500mm
		TAIGA_HILLS, // 0m to 600m, 285K to 313K, 850mm to 1500mm
		WASTELAND, //
		BARREN_HILLS, //
		GRASSLAND, // 0m to 600m, 285K to 313K, 550mm to 850mm
		GRASSLAND_HILLS, // 0m to 600m, 285K to 313K, 550mm to 850mm
		FOREST, // 0m to 600m, 285K to 313K, 850mm to 1500mm
		FOREST_HILLS, // 0m to 600m, 285K to 313K, 850mm to 1500mm
		SWAMP, //
		DESERT, //
		DESERT_HILLS, //
		RAINFOREST, //
		RAINFOREST_HILLS, //
		MOUNTAINS, //
	}

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

	private static BiomeType[][][] biomes;

	public static void initBiomes() {
		biomes = new BiomeType[6][6][6];

		for (int h = DEEP; h <= VERY_SHALLOW; h++) {
			for (int t = FREEZING; t <= FREEZING; t++) {
				for (int r = VERY_DRY; r <= NORMAL; r++) {
					biomes[h][t][r] = BiomeType.ICESHEET;
				}

				for (int r = WET; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.SNOWSHEET;
				}
			}
		}

		for (int h = DEEP; h <= DEEP; h++) {
			for (int t = COLD; t <= SCORCHING; t++) {
				for (int r = VERY_DRY; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.OCEAN;
				}
			}
		}

		for (int h = SHALLOW; h <= SHALLOW; h++) {
			for (int t = COLD; t <= SCORCHING; t++) {
				for (int r = VERY_DRY; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.SEA;
				}
			}
		}

		for (int h = VERY_SHALLOW; h <= VERY_SHALLOW; h++) {
			for (int t = COLD; t <= SCORCHING; t++) {
				for (int r = VERY_DRY; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.COAST;
				}
			}
		}

		for (int h = LOW; h <= LOW; h++) {
			for (int t = FREEZING; t <= COLD; t++) {
				for (int r = VERY_DRY; r <= DRY; r++) {
					biomes[h][t][r] = BiomeType.TUNDRA;
				}

				for (int r = NORMAL; r <= NORMAL; r++) {
					biomes[h][t][r] = BiomeType.SNOWY_TUNDRA;
				}

				for (int r = WET; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.TAIGA;
				}
			}

			for (int t = TEMPERATE; t <= WARM; t++) {
				for (int r = VERY_DRY; r <= VERY_DRY; r++) {
					biomes[h][t][r] = BiomeType.WASTELAND;
				}

				for (int r = DRY; r <= NORMAL; r++) {
					biomes[h][t][r] = BiomeType.GRASSLAND;
				}

				for (int r = WET; r <= WET; r++) {
					biomes[h][t][r] = BiomeType.FOREST;
				}

				for (int r = VERY_WET; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.SWAMP;
				}
			}

			for (int t = HOT; t <= SCORCHING; t++) {
				for (int r = VERY_DRY; r <= NORMAL; r++) {
					biomes[h][t][r] = BiomeType.DESERT;
				}

				for (int r = WET; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.RAINFOREST;
				}
			}
		}

		for (int h = HIGH; h <= HIGH; h++) {
			for (int t = FREEZING; t <= COLD; t++) {
				for (int r = VERY_DRY; r <= DRY; r++) {
					biomes[h][t][r] = BiomeType.TUNDRA_HILLS;
				}

				for (int r = NORMAL; r <= NORMAL; r++) {
					biomes[h][t][r] = BiomeType.SNOWY_TUNDRA_HILLS;
				}

				for (int r = WET; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.TAIGA_HILLS;
				}
			}

			for (int t = TEMPERATE; t <= WARM; t++) {
				for (int r = VERY_DRY; r <= VERY_DRY; r++) {
					biomes[h][t][r] = BiomeType.BARREN_HILLS;
				}

				for (int r = DRY; r <= NORMAL; r++) {
					biomes[h][t][r] = BiomeType.GRASSLAND_HILLS;
				}

				for (int r = WET; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.FOREST_HILLS;
				}
			}

			for (int t = HOT; t <= SCORCHING; t++) {
				for (int r = VERY_DRY; r <= NORMAL; r++) {
					biomes[h][t][r] = BiomeType.DESERT_HILLS;
				}

				for (int r = WET; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.RAINFOREST_HILLS;
				}
			}
		}

		for (int h = VERY_HIGH; h <= VERY_HIGH; h++) {
			for (int t = FREEZING; t <= SCORCHING; t++) {
				for (int r = VERY_DRY; r <= SOAKED; r++) {
					biomes[h][t][r] = BiomeType.MOUNTAINS;
				}
			}
		}
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
	public static BiomeType getBiome(int height, int temperature, int rainfall) {
		int heightC = height < -1000 ? DEEP : height < -50 ? SHALLOW : height < 0 ? VERY_SHALLOW : height < 1000 ? LOW : height < 2000 ? HIGH : VERY_HIGH;
		int tempC = temperature < 260 ? FREEZING : temperature < 280 ? COLD : temperature < 290 ? TEMPERATE : temperature < 310 ? WARM : temperature < 320 ? HOT : SCORCHING;
		int rainC = rainfall < 250 ? VERY_DRY : rainfall < 500 ? DRY : rainfall < 500 ? NORMAL : rainfall < 1000 ? WET : rainfall < 2000 ? VERY_WET : SOAKED;

		BiomeType type = biomes[heightC][tempC][rainC];

		return type == null ? BiomeType.NULL : type;
	}
}
