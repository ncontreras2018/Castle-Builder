package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import tasks.Task;

public class Dirt extends LockedToGrid {

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

		g2d.fillRect(getCol() * getMap().getTileSize(), getRow() * getMap().getTileSize(), getMap().getTileSize(),
				getMap().getTileSize());

		g2d.setColor(Color.BLACK);

		g2d.drawRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	}

	@Override
	public void update() {
	}

	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 1;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return true;
	}
	
	@Override
	public boolean taskCanBePreformed(Task attemptedTask) {
		return false;
	}

	@Override
	public String getDisplayName() {
		return "Dirt";
	}

}
