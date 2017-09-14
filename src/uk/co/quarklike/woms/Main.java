package uk.co.quarklike.woms;

import static org.lwjgl.opengl.GL11.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.geom.Vector2f;

public class Main implements Runnable {
	public static final String TITLE = "The World on Michael's Skin";

	public static Main instance;

	public static NumberFormat nf;
	{
		nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(3);
		nf.setMaximumFractionDigits(3);
	}

	private Thread thread;
	private boolean running;
	private Random rand;

	private float scale = 1.0f;
	private float scrollSpeed = 2.0f;
	private ArrayList<Particle> particles;

	@Override
	public void run() {
		init();

		while (running) {
			update();
		}

		deinit();
	}

	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle(TITLE);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-400, 400, -300, 300, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		rand = new Random();

		particles = new ArrayList<Particle>();

		Particle p1 = addParticle(10, 10, -100, 0);
		Particle p2 = addParticle(10, 10, 100, 0);

		// for (int i = 0; i < 100; i++) {
		// float randX = rand.nextInt(799) - 400 + rand.nextFloat();
		// float randY = rand.nextInt(599) - 300 + rand.nextFloat();
		// float randRadius = rand.nextInt(99) + rand.nextFloat();
		// addParticle(10, randRadius, randX, randY);
		// }

		p1.setVelocity(new Vector2f(1, 0));
		p2.setVelocity(new Vector2f(0, 0));
	}

	public Particle addParticle(float mass, float radius, float initialX, float initialY) {
		if (!isOpen(initialX, initialY, radius))
			return null;

		Particle p = new Particle(mass, radius);
		p.setPosition(new Vector2f(initialX, initialY));

		particles.add(p);
		return p;
	}

	public boolean isOpen(float x, float y, float radius) {
		Vector2f position = new Vector2f(x, y);

		for (Particle p : particles) {
			if (p.getPosition().distance(position) < radius + p.getRadius()) {
				return false;
			}
		}

		return true;
	}

	public void update() {
		glClear(GL_COLOR_BUFFER_BIT);

		if (Display.isCloseRequested())
			stop();

		float dWheel = Mouse.getDWheel();
		if (dWheel < 0) {
			scale /= scrollSpeed;
		} else if (dWheel > 0) {
			scale *= scrollSpeed;
		}

		for (Particle p : particles) {
			p.update(particles);
			drawCircle(p.getX() * scale, p.getY() * scale, p.getRadius() * scale);
		}

		Display.update();
		Display.sync(60);
	}

	public void drawCircle(float x, float y, float radius) {
		glTranslatef(x, y, 0);

		glBegin(GL_LINE_LOOP);

		int sides = 36;
		int resolution = 360 / sides;

		for (int i = 0; i < 360; i += resolution) {
			float angle = (float) (i * (Math.PI / 180));
			glVertex2f((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
		}

		glEnd();

		glTranslatef(-x, -y, 0);
	}

	public void deinit() {

	}

	private synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this, TITLE);
		thread.start();
	}

	synchronized void stop() {
		if (!running)
			return;

		running = false;
		thread.interrupt();
	}

	public static final void main(String[] args) {
		instance = new Main();
		instance.start();
	}
}
