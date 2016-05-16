package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import main.GamePanel;
import threads.GameThread;

public class PauseButton extends MenuItem {

	private GamePanel gamePanel;

	public PauseButton(int menuLocation, GamePanel gamePanel) {
		super(menuLocation);
		this.gamePanel = gamePanel;
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
		gamePanel.setPaused(true);

		String[] menuChoices = new String[] { "Resume Game", "Save Game", "Load Game", "Exit Game" };

		boolean exitLoop = false;

		while (!exitLoop) {

			int userInput = JOptionPane.showOptionDialog(null, "Game Paused", "Main Menu", JOptionPane.PLAIN_MESSAGE,
					JOptionPane.DEFAULT_OPTION, null, menuChoices, menuChoices[0]);

			switch (userInput) {

			case 1:

				boolean saveResult = getGamePanel().saveGame();

				if (saveResult) {
					exitLoop = true;
				}

				break;

			case 2:

				boolean loadResult = getGamePanel().loadGame();

				if (loadResult) {
					exitLoop = true;
				}

				break;

			case 3:
				getGamePanel().closeGame();
				exitLoop = true;
				break;

			default:
				gamePanel.setPaused(false);
				exitLoop = true;
				break;
			}
		}
	}

	@Override
	public int[] getKeysToListen() {
		return new int[] { KeyEvent.VK_ESCAPE, KeyEvent.VK_SPACE };
	}

	@Override
	public void keyTyped(KeyEvent e) {
		mouseClicked();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
