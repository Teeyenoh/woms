package uk.co.quarklike.woms;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class Particle {
	private float mass;
	private float radius;
	private Vector2f position;
	private Vector2f velocity;
	private Vector2f acceleration;

	private ArrayList<Particle> collidedWith;

	public Particle(float mass, float radius) {
		this.mass = mass;
		this.radius = radius;
		position = new Vector2f(0, 0);
		velocity = new Vector2f(0, 0);
		acceleration = new Vector2f(0, 0);

		collidedWith = new ArrayList<Particle>();
	}

	public void update(ArrayList<Particle> particles) {
		collidedWith.clear();

		for (Particle p : particles) {
			if (!p.equals(this) && !p.collidedWith.contains(this))
				if (p.isColliding(this)) {
					float mass1 = this.getMass();
					float mass2 = p.getMass();
					float v1x = this.getVelocity().getX();
					float v1y = this.getVelocity().getY();
					float v2x = p.getVelocity().getX();
					float v2y = p.getVelocity().getY();

					Vector2f normal1 = (new Vector2f(p.getX() - getX(), p.getY() - getY())).getPerpendicular().getNormal();
					Vector2f normal2 = (new Vector2f(getX() - p.getX(), getY() - p.getY())).getPerpendicular().getNormal();
					Vector2f i1 = this.getVelocity().getNormal();
					Vector2f i2 = p.getVelocity().getNormal();
					Vector2f alphaVec = normal1.scale(2 * normal1.dot(i1)).sub(i1);
					Vector2f betaVec = normal2.scale(2 * normal2.dot(i2)).sub(i2);
					double alpha = Math.toRadians(alphaVec.getTheta());
					double beta = Math.toRadians(betaVec.getTheta());

					float a = mass1 * v1x;
					float b = mass2 * v2x;
					float c = mass1 * (float) Math.cos(alpha);
					float d = mass2 * (float) Math.cos(beta);
					float e = mass1 * v1y;
					float f = mass2 * v2y;
					float g = mass1 * (float) Math.sin(alpha);
					float h = mass2 * (float) Math.sin(beta);

					System.out.println("A: " + a + " B: " + b + " C: " + c + " D: " + d);
					System.out.println("E: " + e + " F: " + f + " G: " + g + " H: " + h);

					System.out.println("Alpha: " + Math.toDegrees(alpha) + " Beta: " + Math.toDegrees(beta));

					float v1f = ((d * e) + (d * f) - (h * a) - (h * b)) / ((d * g) - (h * c));
					float v2f = (a + b - (c * v1f)) / d;

					System.out.println("V1f: " + v1f + " V2f: " + v2f);

					this.setVelocity(alphaVec.scale(v1f));
					p.setVelocity(betaVec.scale(v2f));

					this.collidedWith.add(p);
				}
		}

		velocity.add(acceleration);
		position.add(velocity);

		for (Particle p : particles) {
			if (!p.equals(this))
				if (this.isColliding(p)) {
					float distance = p.getPosition().sub(this.getPosition()).length();
					float radii = this.getRadius() + p.getRadius();
					float left = radii - distance;

					Vector2f back = p.getPosition().sub(this.getPosition()).getNormal().scale(left);
					this.setPosition(getPosition().add(back));
				}
		}
	}

	public void applyForce(Vector2f force) {
		float magnitude = force.length() / getMass();
		Vector2f direction = force.normalise();
		Vector2f acc = direction.scale(magnitude);

		acceleration.add(acc);
	}

	public boolean isColliding(Particle partB) {
		return partB.getPosition().distance(position) < radius + partB.getRadius();
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Vector2f getPosition() {
		return position;
	}

	public float getX() {
		return getPosition().getX();
	}

	public float getY() {
		return getPosition().getY();
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public Vector2f getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2f acceleration) {
		this.acceleration = acceleration;
	}

	private float compInDir(Vector2f vec, Vector2f dir) {
		return vec.dot(dir) / dir.length();
	}

	public void printInfo() {
		System.out.println("Printing particle info: \n" + "Mass: " + Main.nf.format(getMass()) + "kg");
	}

	public float solveQuadratic(float a, float b, float c) {
		return ((float) (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a) + (float) (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a)) / 2;
	}
}
