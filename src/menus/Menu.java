package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import main.GamePanel;
import main.Player;

public class Menu {

	private GamePanel gamePanel;

	private Dimension menuSize;

	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

	private final int HORZONTAL_SPACING = 5;
	private final int VERTICAL_SPACING = 5;

	public Menu(GamePanel gamePanel) {

		this.gamePanel = gamePanel;

		updateMenuSize();

		addMenuItems();
	}

	private void updateMenuSize() {
		menuSize = new Dimension(gamePanel.getWidth(), gamePanel.getHeight() / 8);
	}

	private void addMenuItems() {

		MenuItem.setMenu(this);
		MenuItem.setGamePanel(gamePanel);

		menuItems.add(new OreCount(0));

		menuItems.add(new NexusHealthCount(1));
	}

	public void display(Graphics2D g2d) {

		updateMenuSize();

		g2d.setColor(Color.LIGHT_GRAY);

		g2d.fillRect(0, 0, menuSize.width, menuSize.height);

		for (int i = 0; i < menuItems.size(); i++) {

			MenuItem cur = menuItems.get(i);

			AffineTransform before = g2d.getTransform();

			Rectangle allocatedArea = getAllocatedAreaFor(cur);

			g2d.translate(allocatedArea.x, allocatedArea.y);

			cur.draw(g2d, allocatedArea.getSize());

			g2d.setTransform(before);
		}
	}

	private int getTotalPriorty() {
		int totalPriorty = 0;

		for (MenuItem item : menuItems) {
			totalPriorty += item.getSizePriorty();
		}

		return totalPriorty;
	}

	private int getUseableWidth() {
		return menuSize.width - (menuItems.size() + 1) * HORZONTAL_SPACING;
	}

	public Rectangle getAllocatedAreaFor(MenuItem menuItem) {

		double effectivePriorty = (double) menuItem.getSizePriorty() / getTotalPriorty();

		System.out.println("Effective Priorty: " + effectivePriorty);

		int widthAllocated = (int) Math.round(menuSize.width * effectivePriorty);

		int startX = HORZONTAL_SPACING;

		if (menuItem.getMenuLocation() > 0) {
			Rectangle prevArea = getAllocatedAreaFor(menuItems.get(menuItem.getMenuLocation() - 1));

			startX = prevArea.x + prevArea.width + HORZONTAL_SPACING;
		}

		Rectangle area = new Rectangle(startX, 0, widthAllocated, menuSize.height);

		if (menuItem.getMenuLocation() < menuItems.size() - 1) {
			area.width -= HORZONTAL_SPACING * 2;
		}

		area.y += VERTICAL_SPACING;

		area.height -= VERTICAL_SPACING * 2;

		System.out.println("Area for: " + menuItem + " is X: " + area.x + " Y: " + area.y + " Width: " + area.width
				+ " Height: " + area.height);

		return area;
	}

	public Dimension getMenuSize() {
		return menuSize;
	}

	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < menuItems.size(); i++) {
			if (getAllocatedAreaFor(menuItems.get(i)).contains(e.getX(), e.getY())) {
				menuItems.get(i).mouseClicked();
				break;
			}
		}
	}

	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem.getMenuLocation(), menuItem);
	}
}
