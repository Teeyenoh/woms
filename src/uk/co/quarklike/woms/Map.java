package uk.co.quarklike.woms;

import java.util.ArrayList;

public class Map {
	private int mapWidth, mapHeight;
	private int[][] height;
	private int[][] temperature;
	private int[][] rainfall;
	private ArrayList<Entity> entities;

	public Map(int width, int height) {
		this.mapWidth = width;
		this.mapHeight = height;
		this.height = FractalMap.generateMap(width, -1000, 100, 1000, 0.5f);
		this.temperature = FractalMap.generateMap(width, 260, 290, 330, 0.5f);
		this.rainfall = FractalMap.generateMap(width, 0, 100, 2000, 0.5f);
		this.entities = new ArrayList<Entity>();
	}

	public int getHeight(int x, int y) {
		if (isOutOfBounds(x, y))
			return 0;

		return this.height[x][y];
	}

	public void setHeight(int x, int y, int height) {
		if (isOutOfBounds(x, y))
			return;

		this.height[x][y] = height;
	}

	public int getTemperature(int x, int y) {
		if (isOutOfBounds(x, y))
			return 0;

		return this.temperature[x][y];
	}

	public void setTemperature(int x, int y, int temperature) {
		if (isOutOfBounds(x, y))
			return;

		this.temperature[x][y] = temperature;
	}

	public int getRainfall(int x, int y) {
		if (isOutOfBounds(x, y))
			return 0;

		return this.rainfall[x][y];
	}

	public void setRainfall(int x, int y, int rainfall) {
		if (isOutOfBounds(x, y))
			return;

		this.rainfall[x][y] = rainfall;
	}

	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || x >= mapWidth || y < 0 || y >= mapHeight;
	}

	public void addEntity(Entity e) {
		this.entities.add(e);
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public int getWidth() {
		return mapWidth;
	}

	public int getHeight() {
		return mapHeight;
	}
}
