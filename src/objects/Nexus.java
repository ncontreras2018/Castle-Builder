package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

public class Nexus extends LockedToGrid {
	
	private int health;

	public Nexus(int row, int col) {
		super(row, col);
		
		setNexus(this);
		
		health = 100;
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(Color.BLACK);

		g2d.fillRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	}

	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 10000;
	}

	@Override
	public void update() {
	}

}
