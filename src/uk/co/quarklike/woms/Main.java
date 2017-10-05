package uk.co.quarklike.woms;

import static org.lwjgl.opengl.GL11.*;

import java.text.NumberFormat;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.ImageBuffer;

import uk.co.quarklike.woms.Biome.BiomeType;

public class Main implements Runnable {
	public static final double FEMTO = Math.pow(10, -15);
	public static final double PICO = Math.pow(10, -12);
	public static final double NANO = Math.pow(10, -9);
	public static final double MICRO = Math.pow(10, -6);
	public static final double MILI = Math.pow(10, -3);
	public static final double KILO = Math.pow(10, 3);
	public static final double MEGA = Math.pow(10, 6);
	public static final double GIGA = Math.pow(10, 9);
	public static final double TERA = Math.pow(10, 12);
	public static final double PETA = Math.pow(10, 15);

	public static final int WINDOW_WIDTH = 512;
	public static final int WINDOW_HEIGHT = 512;
	public static final String TITLE = "The World on Michael's Skin";

	public static Main instance;

	public static NumberFormat nf;
	{
		nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}

	private Thread thread;
	private Listener listener;
	private boolean running;
	private Random rand;

	private ImageBuffer canvas;

	private Map map;
	private String tracking;
	private int cameraX, cameraY;

	@Override
	public void run() {
		init();

		while (running) {
			update();
		}

		deinit();
	}

	public void init() {
		rand = new Random();

		try {
			Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
			Display.setTitle(TITLE);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho((float) -WINDOW_WIDTH / 2, (float) WINDOW_WIDTH / 2, (float) -WINDOW_HEIGHT / 2, (float) WINDOW_HEIGHT / 2, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		canvas = new ImageBuffer(WINDOW_WIDTH, WINDOW_HEIGHT);

		Biome.initBiomes();

		map = new Map(513, 513);

		Entity e = new Entity("Test", 4);
		e.setPosition(256, 256);
		map.addEntity(e);
	}

	public Entity getEntity(String name) {
		for (Entity e : map.getEntities()) {
			if (e.getName().equals(name)) {
				return e;
			}
		}

		return null;
	}

	public void update() {
		glClear(GL_COLOR_BUFFER_BIT);

		for (int i = 0; i < WINDOW_WIDTH; i++) {
			for (int j = 0; j < WINDOW_HEIGHT; j++) {
				canvas.setRGBA(i, j, 0, 0, 0, 0);
			}
		}

		if (Display.isCloseRequested())
			stop();

		for (int i = cameraX; i < cameraX + map.getWidth(); i++) {
			for (int j = cameraY; j < cameraY + map.getHeight(); j++) {
				if (i >= 0 && i < WINDOW_WIDTH && j >= 0 && j < WINDOW_HEIGHT) {
					int height = map.getHeight(i - cameraX, j - cameraY);
					int temp = map.getTemperature(i - cameraX, j - cameraY);
					int rain = map.getRainfall(i - cameraX, j - cameraY);
					int r = 0;
					int g = 0;
					int b = 0;

					BiomeType biome = Biome.getBiome(height, temp, rain);

					switch (biome) {
					case OCEAN:
						r = 0;
						g = 4;
						b = 53;
						break;
					case SEA:
						r = 18;
						g = 28;
						b = 140;
						break;
					case COAST:
						r = 55;
						g = 67;
						b = 198;
						break;
					case SNOWSHEET:
						r = 226;
						g = 231;
						b = 255;
						break;
					case ICESHEET:
						r = 175;
						g = 189;
						b = 255;
						break;
					case GRASSLAND:
						r = 149;
						g = 168;
						b = 8;
						break;
					case GRASSLAND_HILLS:
						r = 131;
						g = 140;
						b = 65;
						break;
					case FOREST:
						r = 26;
						g = 66;
						b = 5;
						break;
					case FOREST_HILLS:
						r = 60;
						g = 76;
						b = 51;
						break;
					case RAINFOREST:
						r = 38;
						g = 119;
						b = 4;
						break;
					case RAINFOREST_HILLS:
						r = 72;
						g = 124;
						b = 49;
						break;
					case MOUNTAINS:
						r = g = b = 127;
						break;
					case DESERT:
						g = 255;
						r = 246;
						b = 142;
						break;
					case DESERT_HILLS:
						g = 196;
						r = 191;
						b = 137;
						break;
					case TUNDRA_HILLS:
						r = 170;
						g = 170;
						b = 235;
						break;
					case TUNDRA:
						r = 200;
						g = 200;
						b = 255;
						break;
					case SNOWY_TUNDRA:
						r = 200;
						g = 200;
						b = 200;
						break;
					case SNOWY_TUNDRA_HILLS:
						r = 170;
						g = 170;
						b = 170;
						break;
					case SWAMP:
						r = 5;
						g = 15;
						b = 0;
						break;
					default:
						// none
						break;
					}

					canvas.setRGBA(i - cameraX, j - cameraY, r, g, b, 255);
				}
			}
		}

		for (Entity e : map.getEntities()) {
			e.update();
			for (int i = 0; i < e.getSize(); i++) {
				for (int j = 0; j < e.getSize(); j++) {
					int x = e.getX() + i - cameraX;
					int y = e.getY() + j - cameraY;

					if (x >= 0 && x < WINDOW_WIDTH && y >= 0 && y < WINDOW_HEIGHT)
						canvas.setRGBA(x, y, 127, 31, 31, 255);
				}
			}
		}

		canvas.getImage().drawCentered(0, 0);

		Display.update();
		Display.sync(60);
	}

	public synchronized void setTracking(String name) {
		this.tracking = name;
	}

	public int getCameraX() {
		return this.cameraX;
	}

	public int getCameraY() {
		return this.cameraY;
	}

	public synchronized void setCamera(int x, int y) {
		this.cameraX = x;
		this.cameraY = y;
	}

	public void deinit() {
		try {
			canvas.getImage().destroy();
			Display.destroy();
		} catch (Exception e) {
			System.out.println("Error closing program");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public Random getRandom() {
		return rand;
	}

	private synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this, TITLE);
		thread.start();
		listener = new Listener();
		listener.start();
	}

	synchronized void stop() {
		if (!running)
			return;

		running = false;
		thread.interrupt();
		listener.stop();
	}

	public static final void main(String[] args) {
		instance = new Main();
		instance.start();
	}
}
