package objects;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

public class Wall extends LockedToGrid {

	public Wall(int row, int col) {
		super(row, col);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		if (isTransparent) {
			g2d.setColor(Color.LIGHT_GRAY);
		} else {
			g2d.setColor(Color.DARK_GRAY);
		}

		g2d.fillRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	}

	@Override
	public void update() {
	}

	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 10000;
	}
}
