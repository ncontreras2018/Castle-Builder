package menus;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import interfaces.SelectiveKeyListener;
import main.GamePanel;

abstract public class MenuItem implements Serializable, SelectiveKeyListener {
	
	private static Menu menu;
	
	private static GamePanel gamePanel;
	
	private int menuLocation;
	
	MenuItem(int menuLocation) {
		this.menuLocation = menuLocation;
	}
	
	public static void setMenu(Menu m) {
		menu = m;
	}
	
	Menu getMenu() {
		return menu;
	}
	
	public static void setGamePanel(GamePanel gp) {
		gamePanel = gp;
	}
	
	GamePanel getGamePanel() {
		return gamePanel;
	}
	
	public int getMenuLocation() {
		return menuLocation;
	}
	
	abstract public void update();
	
	abstract public void draw(Graphics2D g2d, Dimension allocatedSize);
	
	abstract public int getSizePriorty();

	abstract public void mouseClicked();

}
