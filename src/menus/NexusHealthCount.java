package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class NexusHealthCount extends MenuItem {

	NexusHealthCount(int menuLocation) {
		super(menuLocation);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g2d, Dimension allocatedSize) {
		
		int nexusHealth = getGamePanel().getMap().getNexus().getHealth();
		
		int centerX = allocatedSize.width/2;
		int centerY = allocatedSize.height/2;
		
		g2d.setColor(Color.GRAY);
		
		g2d.fillRect(0, 0, allocatedSize.width, allocatedSize.height);
		
		g2d.setColor(Color.GREEN);
		
		g2d.drawString("Nexus Health: " + nexusHealth, centerX, centerY);
	}

	@Override
	public int getSizePriorty() {
		return 5;
	}

	@Override
	public void mouseClicked() {
	}

	@Override
	public int[] getKeysToListen() {
		return new int[0];
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
