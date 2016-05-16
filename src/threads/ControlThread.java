package threads;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import interfaces.SelectiveKeyListener;
import listeners.KeyListener;
import listeners.MouseListener;
import main.GamePanel;

public class ControlThread extends Thread implements Serializable, SelectiveKeyListener {

	private long nanoDelay;

	private GamePanel gamePanel;

	private final int CAMERA_SPEED = 15;
	private final double ZOOM_SPEED = .01;

	private final int NANOS_PER_MILLI = 1000000;

	public ControlThread(int refreshRate, GamePanel gp, KeyListener kl, MouseListener ml) {

		setRefreshRate(refreshRate);

		gamePanel = gp;

		kl.addSelectiveListener(this);

	}

	public void setRefreshRate(int frameRate) {
		nanoDelay = (1000 * NANOS_PER_MILLI) / frameRate;
	}

	@Override
	public void run() {
		while (true) {

			long startTime = System.nanoTime();

			gamePanel.getPlayer().update();

			delayThread(System.nanoTime() - startTime);
		}
	}

	private void delayThread(long timeTaken) {

		long timeLeftToWait = nanoDelay - timeTaken;

		if (timeLeftToWait <= 0) {
			return;
		}

		try {
			Thread.sleep(timeLeftToWait / NANOS_PER_MILLI, (int) (timeLeftToWait % NANOS_PER_MILLI));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int[] getKeysToListen() {
		return new int[] { KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_EQUALS,
				KeyEvent.VK_MINUS };
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			gamePanel.moveCamera(0, -CAMERA_SPEED);
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			gamePanel.moveCamera(0, CAMERA_SPEED);
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			gamePanel.moveCamera(-CAMERA_SPEED, 0);
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			gamePanel.moveCamera(CAMERA_SPEED, 0);
		}

		if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
			gamePanel.zoomCamera(ZOOM_SPEED);
		}

		if (e.getKeyCode() == KeyEvent.VK_MINUS) {
			gamePanel.zoomCamera(-ZOOM_SPEED);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
