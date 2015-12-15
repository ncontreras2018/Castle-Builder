package main;

import java.awt.Dimension;
import java.util.ArrayList;

import objects.Dirt;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

public class Map {

	private int tileSize;
	
	private LockedToGrid[][] grid;
	
	private ArrayList<UnlockedFromGrid> unlockedObjects;
	
	private ArrayList<Tasks> jobQueue;

	public Map(int rows, int cols, int tileSize) {

		this.tileSize = tileSize;
		
		grid = new LockedToGrid[rows][cols];
		
		unlockedObjects = new ArrayList<UnlockedFromGrid>();
		
		jobQueue = new ArrayList<Tasks>();
		
		fillWithDirt();
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
				grid[row][col] = new Dirt(row, col);
			}
		}
	}

	public int getTileSize() {
		return tileSize;
	}
	
	public LockedToGrid[][] getGrid() {
		return grid;
	}
	
	public ArrayList<UnlockedFromGrid> getUnlockedObjects() {
		return unlockedObjects;
	}

}
