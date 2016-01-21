package listeners;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;

import main.GamePanel;
import main.Map;

public class KeyListener implements java.awt.event.KeyListener, Serializable {
	
	private boolean[] keysPressed;

	public KeyListener() {
		keysPressed = new boolean[9999];
	}
	
	public boolean keyPressed(int keyCode) {
		return keysPressed[keyCode];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysPressed[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysPressed[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
