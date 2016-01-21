package main;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import objects.Dirt;
import objects.Nexus;
import objects.Ore;
import people.Worker;
import tasks.Construction;
import tasks.Task;
import abstractClasses.Existent;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import gameIO.GameIO;

public class Map implements Serializable {

	private int tileSize;

	private LockedToGrid[][][] grid;

	private ArrayList<UnlockedFromGrid> unlockedObjects;

	private Nexus nexus;

	public Map(int rows, int cols, int tileSize) {

		Existent.setMap(this);

		this.tileSize = tileSize;

		grid = new LockedToGrid[rows][cols][3];

		unlockedObjects = new ArrayList<UnlockedFromGrid>();

		fillWithDirt();

		Ore.distributeOre(.0025, 5);

		nexus = new Nexus(grid.length / 2, grid[grid.length / 2].length / 2);

		grid[grid.length / 2][grid[grid.length / 2].length / 2][1] = nexus;
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

	public boolean canPassThroughTile(int row, int col, UnlockedFromGrid obj) {

		for (LockedToGrid cur : grid[row][col]) {

			System.out.println("Checking: " + cur);

			if (cur != null && !cur.canMoveThrough(obj)) {
				return false;
			}
		}

		return true;
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

		if (row < grid.length && row > 0) {
			if (col < grid[row].length && col > 0) {
				return new int[] { row, col };
			}
		}
		return null;
	}

	public Nexus getNexus() {
		return nexus;
	}
}
