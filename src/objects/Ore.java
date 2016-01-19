package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;
import tasks.Task;
import abstractClasses.UnlockedFromGrid;
import tasks.Mining;

public class Ore extends LockedToGrid {

	public Ore(int row, int col) {
		super(row, col);
	}

	public static void distributeOre(double frequency, double depositSize) {

		LockedToGrid[][][] grid = getMap().getGrid();

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {

				if (Math.random() < frequency) {
					createOrePatch(row, col, depositSize);
				}
			}
		}
	}
	
	@Override
	public boolean taskCanBePreformed(Task attemptedTask) {
		
		if (attemptedTask instanceof Mining) {
			return true;
		} else {
			return false;
		}
	}

	private static void createOrePatch(int row, int col, double depositSize) {
		
		if (depositSize <= 0) {
			return;
		}

		LockedToGrid[] curTile = getMap().getGrid()[row][col];
		
		System.out.println("Creating Ore At Row: " + row + " Col: " + col);

		curTile[1] = new Ore(row, col);

		double random = Math.random();

		double decrease = Math.random();

		if (random < .25) {
			if (row + 1 < getMap().numRows()) {
				createOrePatch(row + 1, col, depositSize - decrease);
			}
		} else if (random < .5) {
			if (row - 1 >= 0) {
				createOrePatch(row - 1, col, depositSize - decrease);
			}
		} else if (random < .75) {
			if (col + 1 < getMap().numCols()) {
				createOrePatch(row, col + 1, depositSize - decrease);
			}
		} else {
			if (col - 1 >= 0) {
				createOrePatch(row, col - 1, depositSize - decrease);
			}
		}
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(Color.YELLOW);

		g2d.fillOval(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	}

	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 5;
	}

	@Override
	public void update() {
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return true;
	}
}
