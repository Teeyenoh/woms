package uk.co.quarklike.woms;

public class Entity {
	private String name;
	private int size;

	private float x, y;
	private int direction;
	private int speed;

	public Entity(String name, int size) {
		this.name = name;
		this.size = size;
		direction = 45;
		speed = 1;
	}

	public void update() {
		x += Math.cos(Math.toRadians(direction)) * speed;
		y += Math.sin(Math.toRadians(direction)) * speed;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void printData() {
		System.out.println("Name:\t" + getName());
	}
}
