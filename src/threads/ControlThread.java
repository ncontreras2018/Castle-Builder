package threads;

import java.awt.event.KeyEvent;

import listeners.KeyListener;
import listeners.MouseListener;
import main.GamePanel;

public class ControlThread extends Thread {

	private long nanoDelay;

	private GamePanel gamePanel;

	private KeyListener keyListener;
	private MouseListener mouseListener;

	private final int CAMERA_SPEED = 15;
	private final double ZOOM_SPEED = .01;

	private final int NANOS_PER_MILLI = 1000000;

	public ControlThread(int refreshRate, GamePanel gp, KeyListener kl,
			MouseListener ml) {

		setRefreshRate(refreshRate);

		gamePanel = gp;

		keyListener = kl;
		mouseListener = ml;

	}

	public void setRefreshRate(int frameRate) {
		nanoDelay = (1000 * NANOS_PER_MILLI) / frameRate;
	}

	@Override
	public void run() {
		while (true) {

			long startTime = System.nanoTime();

			checkCameraControls();
			
			gamePanel.getPlayer().update();

			delayThread(System.nanoTime() - startTime);
		}
	}

	private void checkCameraControls() {
		if (keyListener.keyPressed(KeyEvent.VK_W)) {
			gamePanel.moveCamera(0, -CAMERA_SPEED);
		}

		if (keyListener.keyPressed(KeyEvent.VK_S)) {
			gamePanel.moveCamera(0, CAMERA_SPEED);
		}

		if (keyListener.keyPressed(KeyEvent.VK_A)) {
			gamePanel.moveCamera(-CAMERA_SPEED, 0);
		}

		if (keyListener.keyPressed(KeyEvent.VK_D)) {
			gamePanel.moveCamera(CAMERA_SPEED, 0);
		}

		if (keyListener.keyPressed(KeyEvent.VK_EQUALS)) {
			gamePanel.zoomCamera(ZOOM_SPEED);
		}

		if (keyListener.keyPressed(KeyEvent.VK_MINUS)) {
			gamePanel.zoomCamera(-ZOOM_SPEED);
		}
	}

	private void delayThread(long timeTaken) {

		long timeLeftToWait = nanoDelay - timeTaken;

		if (timeLeftToWait <= 0) {
			return;
		}

		try {
			Thread.sleep(timeLeftToWait / NANOS_PER_MILLI,
					(int) (timeLeftToWait % NANOS_PER_MILLI));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
