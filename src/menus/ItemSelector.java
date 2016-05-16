package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import objects.Gate;
import objects.Wall;
import people.Miner;
import people.Worker;

public class ItemSelector extends MenuItem {

	private Class<?>[] placeableItems;

	private int selectedItem;

	ItemSelector(int menuLocation) {
		super(menuLocation);
		placeableItems = new Class<?>[] { Wall.class, Gate.class, Worker.class, Miner.class };
		selectedItem = 0;
	}

	@Override
	public void update() {
		getGamePanel().getPlayer().setItemToPlace(placeableItems[selectedItem]);
	}

	@Override
	public void draw(Graphics2D g2d, Dimension allocatedSize) {

		g2d.setColor(Color.GRAY);

		g2d.fillRect(0, 0, allocatedSize.width, allocatedSize.height);

		g2d.setColor(Color.GREEN);

		String text1 = "Selected Item:";

		String text2 = placeableItems[selectedItem].getSimpleName();

		FontMetrics metrics = g2d.getFontMetrics();

		int text1Width = metrics.stringWidth(text1);

		g2d.drawString(text1, (allocatedSize.width / 2) - (text1Width / 2), allocatedSize.height * 1 / 3);

		int text2Width = metrics.stringWidth(text2);

		g2d.drawString(text2, (allocatedSize.width / 2) - (text2Width / 2), allocatedSize.height * 2 / 3);
	}

	@Override
	public int getSizePriorty() {
		return 4;
	}

	@Override
	public void mouseClicked() {
		selectedItem++;
		selectedItem %= placeableItems.length;
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
