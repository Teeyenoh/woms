package uk.co.quarklike.woms;

import static org.lwjgl.opengl.GL11.*;

import java.text.NumberFormat;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.ImageBuffer;

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

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
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
		glOrtho(-WINDOW_WIDTH / 2, WINDOW_WIDTH / 2, -WINDOW_HEIGHT / 2, WINDOW_HEIGHT / 2, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		canvas = new ImageBuffer(WINDOW_WIDTH, WINDOW_HEIGHT);

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
				if (i >= 0 && i < WINDOW_WIDTH && j >= 0 && j < WINDOW_HEIGHT)
					canvas.setRGBA(i - cameraX, j - cameraY, map.getTile(i - cameraX, j - cameraY), map.getTile(i - cameraX, j - cameraY), map.getTile(i - cameraX, j - cameraY), 255);
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
