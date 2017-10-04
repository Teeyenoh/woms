package uk.co.quarklike.woms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Listener implements Runnable {
	private Thread thread;
	private boolean running;

	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (running) {
			String s = "";

			try {
				if (br.ready()) {
					s = br.readLine();
					readCommand(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void readCommand(String line) {
		String[] parts = line.split(" ");
		String command = parts[0];

		try {
			switch (command) {
			case "track":
				track(parts[1]);
				break;
			case "untrack":
				untrack();
				break;
			case "move":
				if (parts[1].equals("to")) {
					setCamera(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
				} else {
					setCamera(Main.instance.getCameraX() + Integer.parseInt(parts[1]), Main.instance.getCameraY() + Integer.parseInt(parts[2]));
				}
				break;
			case "info":
				printInfo(parts[1]);
				break;
			default:
				System.out.println("I don't understand that");
				break;
			}
		} catch (Exception e) {
			System.out.println("Failed to understand command: " + line);
		}
	}

	private void track(String name) {
		if (Main.instance.getEntity(name) == null) {
			System.out.println("No entity named: " + name);
			return;
		}
		Main.instance.setTracking(name);
	}

	private void untrack() {
		Main.instance.setTracking("");
	}

	private void setCamera(int x, int y) {
		Main.instance.setCamera(x, y);
	}

	private void printInfo(String name) {
		if (Main.instance.getEntity(name) == null) {
			System.out.println("No entity named: " + name);
			return;
		}
		Main.instance.getEntity(name).printData();
	}

	synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this, Main.TITLE + " Listener");
		thread.start();
	}

	synchronized void stop() {
		if (!running)
			return;

		running = false;
		thread.interrupt();
	}
}
