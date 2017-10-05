package uk.co.quarklike.woms;

public class Biome {
	public enum BiomeType {
		NULL, //
		COAST, // -30m to 0m, any temp, any rainfall
		OCEAN, //
		SEA, //
		ICESHEET, //
		SNOWSHEET, //
		GRASSLAND, // 0m to 600m, 285K to 313K, 550mm to 850mm
		MOUNTAINS, //
		DESERT, //
		FOREST, // 0m to 600m, 285K to 313K, 850mm to 1500mm
		RAINFOREST
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
		if (height <= -30) {
			if (temperature <= 273) {
				if (rainfall < 200) {
					return BiomeType.SNOWSHEET;
				} else {
					return BiomeType.ICESHEET;
				}
			} else if (temperature <= 283) {
				return BiomeType.OCEAN;
			} else {
				return BiomeType.SEA;
			}
		} else if (height <= 0) {
			return BiomeType.COAST;
		} else if (height <= 600) {
			//System.out.println(rainfall);
			if (temperature <= 285) {
				return BiomeType.NULL;
			} else if (temperature <= 310) {
				if (rainfall <= 550) {
					return BiomeType.NULL;
				} else if (rainfall <= 850) {
					return BiomeType.GRASSLAND;
				} else if (rainfall <= 1500) {
					return BiomeType.FOREST;
				} else {
					return BiomeType.RAINFOREST;
				}
			} else {
				if (rainfall < 10) {
					return BiomeType.DESERT;
				}
			}
		} else {
			return BiomeType.MOUNTAINS;
		}

		return BiomeType.NULL;
	}
}
