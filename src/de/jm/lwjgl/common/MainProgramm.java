package de.jm.lwjgl.common;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;

public abstract class MainProgramm {

	protected static boolean running = true;
	protected static int fps;
	protected static long lastFrame;
	protected static long lastFPS;

	public void start() {
		setUpDisplay();
		initGL();
		initialize();
		mainLoop();
		close();
	}

	protected void setUpDisplay() {
		try {
			Display.setTitle(Constants.WINDOW_TITLE);
			Display.setDisplayMode(new DisplayMode(Config.windowWidth, Config.windowHeight));
			Display.setResizable(Config.resizable);
			Display.setVSyncEnabled(Config.vsyncEnabled);
			Display.create();

			if (!GLContext.getCapabilities().OpenGL33) {
				System.err.println("You must have at least OpenGL 3.3 to run this tutorial.\n");
				System.exit(-1);
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	protected void initGL() {

	}

	protected abstract void initialize();

	protected void mainLoop() {
		getDelta();
		lastFPS = getTime();
		while (running && !Display.isCloseRequested()) {
			update(getDelta());
			render();
			calcTimings();

			Display.sync(Config.maxFPS);
			if (Display.wasResized()) {
				reshape(Display.getWidth(), Display.getHeight());
			}
		}
	}

	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	protected abstract void update(int delta);

	protected abstract void render();

	protected void calcTimings() {
		if (getTime() - lastFPS > 1000) {
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	protected void reshape(int width, int height) {
		glViewport(0, 0, width, height);
	}

	protected void close() {
		Display.destroy();
	}
}
