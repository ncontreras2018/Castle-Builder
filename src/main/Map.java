package main;

import java.awt.Dimension;
import java.util.ArrayList;

import objects.Dirt;
import objects.Nexus;
import objects.Ore;
import people.Worker;
import tasks.Construction;
import tasks.Task;
import abstractClasses.Existent;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

public class Map {

	private int tileSize;

	private LockedToGrid[][][] grid;

	private ArrayList<UnlockedFromGrid> unlockedObjects;

	public Map(int rows, int cols, int tileSize) {

		Existent.setMap(this);

		this.tileSize = tileSize;

		grid = new LockedToGrid[rows][cols][3];
		
		grid[grid.length / 2][grid[grid.length / 2].length / 2][1] = new Nexus(grid.length / 2,
				grid[grid.length / 2].length / 2);

		unlockedObjects = new ArrayList<UnlockedFromGrid>();

		fillWithDirt();

		Ore.distributeOre(.0025, 5);
	}

	public void addUnlockedObject(UnlockedFromGrid object) {
		unlockedObjects.add(object);
	}

	public int numRows() {
		return grid.length;
	}

	public int numCols() {
		return grid[0].length;
	}

	private void fillWithDirt() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				grid[row][col][0] = new Dirt(row, col);
			}
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public LockedToGrid[][][] getGrid() {
		return grid;
	}

	public ArrayList<UnlockedFromGrid> getUnlockedObjects() {
		return unlockedObjects;
	}

	public void removeTemporaryItems() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				for (int layer = 0; layer < grid[row][col].length; layer++) {

					LockedToGrid obj = grid[row][col][layer];

					if (obj != null) {
						if (obj instanceof Construction) {
							if (((Construction) obj).getPriorty() == 0) {
								grid[row][col][layer] = null;
							}
						}
					}
				}
			}
		}
	}

	public void validateTemporaryItems() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				for (int layer = 0; layer < grid[row][col].length; layer++) {

					LockedToGrid obj = grid[row][col][layer];

					if (obj != null) {
						if (obj instanceof Construction) {
							if (((Construction) obj).getPriorty() == 0) {
								((Construction) grid[row][col][layer]).place();
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<UnlockedFromGrid> getObjectsAt(int row, int col) {
		ArrayList<UnlockedFromGrid> objects = new ArrayList<UnlockedFromGrid>();

		for (UnlockedFromGrid cur : unlockedObjects) {
			if (cur.getRow() == row && cur.getCol() == col) {
				objects.add(cur);
			}
		}
		return objects;
	}

	public int[] convertXYtoRowCol(double x, double y) {
		int row = (int) y / tileSize;
		int col = (int) x / tileSize;

		return new int[] { row, col };
	}
}
