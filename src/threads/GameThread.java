package threads;

import java.io.Serializable;

import main.GamePanel;

public class GameThread extends Thread implements Serializable {

	private long nanoDelay;

	private GamePanel gamePanel;

	private boolean paused;

	private final int NANOS_PER_MILLI = 1000000;

	public GameThread(int refreshRate, GamePanel gamePanel) {

		setRefreshRate(refreshRate);

		this.gamePanel = gamePanel;

		this.setPriority(Thread.MAX_PRIORITY);
	}

	public void setRefreshRate(int frameRate) {
		nanoDelay = (1000 * NANOS_PER_MILLI) / frameRate;
	}

	@Override
	public void run() {
		while (true) {

			long startTime = System.nanoTime();

			if (!paused) {

				gamePanel.update();
			}

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

	public void setPaused(boolean isPaused) {
		this.paused = isPaused;
	}

	public void togglePaused() {
		paused = !paused;
	}
}
