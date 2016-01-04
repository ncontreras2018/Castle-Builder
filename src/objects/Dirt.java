package objects;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

public class Dirt extends LockedToGrid implements Drawable {

	private final double COLOR_DIFFERENCE;

	public Dirt(int row, int col) {
		super(row, col);

		COLOR_DIFFERENCE = Math.random();
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		if (COLOR_DIFFERENCE > .66) {
			g2d.setColor(Color.GREEN.darker().darker());
		} else if (COLOR_DIFFERENCE > .33) {
			g2d.setColor(Color.GREEN.darker());
		} else {
			g2d.setColor(Color.GREEN);
		}

		g2d.fillRect(col * MAP.getTileSize(), row * MAP.getTileSize(), MAP.getTileSize(), MAP.getTileSize());

		g2d.setColor(Color.BLACK);

		g2d.drawRect(getTopLeftX(), getTopLeftY(), MAP.getTileSize(), MAP.getTileSize());
	}

	@Override
	public void update() {
	}

	@Override
	public boolean canPassThrough(UnlockedFromGrid other) {
		return true;
	}
}
