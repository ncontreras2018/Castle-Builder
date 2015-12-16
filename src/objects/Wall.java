package objects;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;

public class Wall extends VisibleObject implements Drawable  {

	public Wall(int row, int col) {
		super(row, col);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		g2d.setColor(Color.GRAY);

		g2d.fillRect(col, row, MAP.getTileSize(),
				MAP.getTileSize());
	}

	@Override
	public void update() {
	}
}
