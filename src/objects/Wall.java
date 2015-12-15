package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;

public class Wall extends LockedToGrid {

	public Wall(int row, int col) {
		super(row, col);
	}

	@Override
	public void draw(Graphics2D g2d) {

		g2d.setColor(Color.GRAY);

		g2d.fillRect(location[0], location[1], MAP.getTileSize(),
				MAP.getTileSize());
	}

	@Override
	public void update() {
	}
}
