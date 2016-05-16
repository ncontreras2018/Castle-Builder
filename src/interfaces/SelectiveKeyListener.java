package interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface SelectiveKeyListener extends KeyListener {
	
	abstract int[] getKeysToListen();

}
