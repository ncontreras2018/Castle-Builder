package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;

public class Dirt extends LockedToGrid {
	
	private final double COLOR_DIFFERENCE;

	public Dirt(int row, int col) {
		super(row, col);
		
		COLOR_DIFFERENCE = Math.random();
	}

	@Override
	public void draw(Graphics2D g2d) {
		
		if (COLOR_DIFFERENCE > .66) {
			g2d.setColor(Color.GREEN.darker().darker());
		} else if (COLOR_DIFFERENCE > .33) {
			g2d.setColor(Color.GREEN.darker());
		} else {
			g2d.setColor(Color.GREEN);
		}

		g2d.fillRect(location[0] * MAP.getTileSize(),
				location[1] * MAP.getTileSize(), MAP.getTileSize(),
				MAP.getTileSize());
		
		g2d.setColor(Color.BLACK);
		
		g2d.drawRect(location[0] * MAP.getTileSize(),
				location[1] * MAP.getTileSize(), MAP.getTileSize(),
				MAP.getTileSize());
	}

	@Override
	public void update() {
	}
}
