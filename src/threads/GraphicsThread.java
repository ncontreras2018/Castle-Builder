package threads;

import main.GamePanel;

public class GraphicsThread extends Thread {

	private long nanoDelay;

	private boolean draw;

	private GamePanel gamePanel;

	private final int NANOS_PER_MILLI = 1000000;

	public GraphicsThread(int frameRate, GamePanel gamePanel) {

		setFrameRate(frameRate);

		this.gamePanel = gamePanel;
		draw = true;
	}

	public void setFrameRate(int frameRate) {
		nanoDelay = 1000000000 / frameRate;
	}

	@Override
	public void run() {
		while (true) {

			long startTime = System.nanoTime();

			if (draw) {
				gamePanel.repaint();
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
			Thread.sleep(timeLeftToWait / NANOS_PER_MILLI,
					(int) (timeLeftToWait % NANOS_PER_MILLI));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
