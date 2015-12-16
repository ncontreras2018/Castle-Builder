package main;

import java.awt.Dimension;
import java.util.ArrayList;

import objects.Dirt;
import tasks.Construction;
import tasks.Task;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

public class Map {

	private int tileSize;
	
	private LockedToGrid[][][] grid;
	
	private ArrayList<UnlockedFromGrid> unlockedObjects;

	public Map(int rows, int cols, int tileSize) {

		this.tileSize = tileSize;
		
		grid = new LockedToGrid[rows][cols][3];
		
		unlockedObjects = new ArrayList<UnlockedFromGrid>();
		
		fillWithDirt();
	}
	
	public void addUnlockedObject(UnlockedFromGrid object) {
		unlockedObjects.add(object);
	}

	public int numRows() {
		System.out.println("rows " + grid.length);
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
}
