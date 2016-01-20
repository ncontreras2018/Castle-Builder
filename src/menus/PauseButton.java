package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import threads.GameThread;

public class PauseButton extends MenuItem {

	private GameThread gameThread;

	public PauseButton(int menuLocation, GameThread gameThread) {
		super(menuLocation);
		this.gameThread = gameThread;
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g2d, Dimension allocatedSize) {

		int centerX = allocatedSize.width / 2;
		int centerY = allocatedSize.height / 2;

		g2d.setColor(Color.GRAY);

		g2d.fillRect(0, 0, allocatedSize.width, allocatedSize.height);

		g2d.setColor(Color.GREEN);

		g2d.drawString("PAUSE", centerX, centerY);
	}

	@Override
	public int getSizePriorty() {
		return 1;
	}

	@Override
	public void mouseClicked() {
		gameThread.setPaused(true);

		String[] menuChoices = new String[] { "Resume Game", "Save Game", "Exit Game" };

		int userInput = JOptionPane.showOptionDialog(null, "Game Paused", "Main Menu", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.DEFAULT_OPTION, null, menuChoices, menuChoices[0]);

		switch (userInput) {

		case JOptionPane.NO_OPTION:
			getGamePanel().getMap().saveGame();
			break;

		case JOptionPane.CANCEL_OPTION:
			getGamePanel().closeGame();
			break;

		default:
			gameThread.setPaused(false);
			break;
		}

	}
}
