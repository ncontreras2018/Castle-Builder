package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;

public class Dirt extends LockedToGrid {

	public Dirt(int row, int col) {
		super(row, col);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.GREEN.darker());

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
