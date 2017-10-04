package uk.co.quarklike.woms;

public class FractalMap {
	public static int[][] generateMap(int size, int base, float roughness) {
		int width = size;
		int height = size;

		int[][] heights = new int[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				heights[i][j] = base;
			}
		}

		int max = 255;
		int range = max / 2;

		for (int i = 1; i < 12; i++) {
			System.out.println("Diamond");
			heights = diamond(heights, size, i, range);
			System.out.println("Square");
			heights = square(heights, size, i, range);
			range *= Math.pow(2, -roughness);
		}

		return heights;
	}

	private static int[][] diamond(int[][] values, int size, int pass, int range) {
		int[][] out = values;

		int d = (size - 1) / pass;
		System.out.println("Pass " + pass + ", d = " + d);

		for (int i = 0; i <= size; i += d) {
			for (int j = 0; j <= size; j += d) {
				int tl = getValue(out, i, j);
				int tr = getValue(out, i + d, j);
				int br = getValue(out, i + d, j + d);
				int bl = getValue(out, i, j + d);

				int average = (tl + tr + br + bl) / 4;
				int random = Main.instance.getRandom().nextInt(2 * range) - range;
				int value = average + random;

				int midX = i + (d / 2);
				int midY = j + (d / 2);

				setValue(out, midX, midY, value);
			}
		}

		return out;
	}

	private static int[][] square(int[][] values, int size, int pass, int range) {
		int[][] out = values;

		int d = (size - 1) / pass;

		for (int i = 0; i <= size; i += d) {
			for (int j = 0; j <= size; j += d) {
				int l = getValue(out, i, j);
				int t = getValue(out, i + (d / 2), j + (d / 2));
				int r = getValue(out, i + d, j);
				int b = getValue(out, i + (d / 2), j - (d / 2));

				int average = (l + t + r + b) / 4;
				int random = Main.instance.getRandom().nextInt(2 * range) - range;
				int value = average + random;

				int midX = i + (d / 2);
				int midY = j;

				setValue(out, midX, midY, value);
			}
		}

		for (int i = d / 2; i <= size; i += d) {
			for (int j = d / 2; j <= size; j += d) {
				int l = getValue(out, i, j);
				int t = getValue(out, i + (d / 2), j + (d / 2));
				int r = getValue(out, i + d, j);
				int b = getValue(out, i + (d / 2), j - (d / 2));

				int average = (l + t + r + b) / 4;
				int random = Main.instance.getRandom().nextInt(2 * range) - range;
				int value = average + random;

				int midX = i + (d / 2);
				int midY = j;

				setValue(out, midX, midY, value);
			}
		}

		return out;
	}

	private static int getValue(int[][] values, int x, int y) {
		int width = values.length - 1;
		int height = values[0].length - 1;

		while (x < 0) {
			x += width;
		}

		while (x >= width) {
			x -= width;
		}

		while (y < 0) {
			y += height;
		}

		while (y >= height) {
			y -= height;
		}

		return values[x][y];
	}

	private static void setValue(int[][] values, int x, int y, int value) {
		int width = values.length - 1;
		int height = values[0].length - 1;

		while (x < 0) {
			x += width;
		}

		while (x >= width) {
			x -= width;
		}

		while (y < 0) {
			y += height;
		}

		while (y >= height) {
			y -= height;
		}

		values[x][y] = value;

		if (x == 0) {
			values[width][y] = value;
		}

		if (y == 0) {
			values[x][height] = value;
		}
	}
}
