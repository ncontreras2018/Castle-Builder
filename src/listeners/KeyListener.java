package listeners;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.SelectiveKeyListener;
import main.GamePanel;
import main.Map;

public class KeyListener implements java.awt.event.KeyListener, Serializable {

	private ArrayList<SelectiveKeyListener> keyListeners;

	private ArrayList<KeyEvent> pressedKeys;

	public KeyListener() {
		keyListeners = new ArrayList<SelectiveKeyListener>();
		pressedKeys = new ArrayList<KeyEvent>();

		new Timer(true).scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				for (int i = 0; i < keyListeners.size(); i++) {

					SelectiveKeyListener cur = keyListeners.get(i);

					for (int key1 : cur.getKeysToListen()) {
						for (KeyEvent key2 : pressedKeys) {
							if (key1 == key2.getKeyCode()) {
								cur.keyPressed(key2);
							}
						}
					}
				}
			}
		}, 0, 20);
	}

	public void addSelectiveListener(SelectiveKeyListener s) {
		keyListeners.add(s);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		for (int i = 0; i < pressedKeys.size(); i++) {

			KeyEvent cur = pressedKeys.get(i);

			if (cur.getKeyCode() == e.getKeyCode()) {
				pressedKeys.remove(cur);
				pressedKeys.add(e);
				return;
			}
		}

		for (SelectiveKeyListener cur : keyListeners) {
			for (int curKey : cur.getKeysToListen()) {
				if (curKey == e.getKeyCode()) {
					cur.keyTyped(e);
				}
			}
		}
		pressedKeys.add(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {

		for (SelectiveKeyListener cur : keyListeners) {
			for (int curKey : cur.getKeysToListen()) {
				if (curKey == e.getKeyCode()) {
					cur.keyReleased(e);
				}
			}
		}

		for (int i = 0; i < pressedKeys.size(); i++) {

			KeyEvent cur = pressedKeys.get(i);

			if (cur.getKeyCode() == e.getKeyCode()) {
				pressedKeys.remove(cur);
				return;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
