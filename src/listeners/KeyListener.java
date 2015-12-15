package listeners;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;

import main.GamePanel;
import main.Map;

public class KeyListener implements java.awt.event.KeyListener {

	private GamePanel gamePanel;
	
	private final int CAMERA_SPEED = 5;

	public KeyListener(GamePanel gp) {
		gamePanel = gp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {

		case (KeyEvent.VK_W):
			gamePanel.moveCamera(0, -CAMERA_SPEED);
			break;
		case (KeyEvent.VK_S):
			gamePanel.moveCamera(0, CAMERA_SPEED);
			break;
		case (KeyEvent.VK_A):
			gamePanel.moveCamera(-CAMERA_SPEED, 0);
			break;
		case (KeyEvent.VK_D):
			gamePanel.moveCamera(CAMERA_SPEED, 0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
