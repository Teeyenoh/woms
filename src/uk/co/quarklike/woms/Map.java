package uk.co.quarklike.woms;

import java.util.ArrayList;

public class Map {
	private int width, height;
	private int[][] tiles;
	private ArrayList<Entity> entities;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = FractalMap.generateMap(width, 127, 0.5f);
		this.entities = new ArrayList<Entity>();
	}

	public int getTile(int x, int y) {
		if (isOutOfBounds(x, y))
			return 0;

		return this.tiles[x][y];
	}

	public void setTile(int x, int y, int tile) {
		if (isOutOfBounds(x, y))
			return;

		this.tiles[x][y] = tile;
	}

	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || x >= width || y < 0 || y >= width;
	}

	public void addEntity(Entity e) {
		this.entities.add(e);
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
