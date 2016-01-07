package abstractClasses;

import java.util.ArrayList;

import interfaces.Drawable;

abstract public class LockedToGrid extends Existent implements Drawable {

	private int row, col;

	public LockedToGrid(int row, int col) {

		this.row = row;
		this.col = col;
	}

	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public abstract double movementPenalty(UnlockedFromGrid other);

	public boolean contains(double x, double y) {
		if (x > col * getMap().getTileSize() && x < (col * getMap().getTileSize()) + getMap().getTileSize()) {
			if (y > row * getMap().getTileSize() && y < (row * getMap().getTileSize()) + getMap().getTileSize()) {
				return true;
			}
		}
		return false;
	}

	public boolean isNearby(UnlockedFromGrid object) {

		for (int[] curPos : getNearbyLocs()) {
			if (object.getApproxX() == curPos[0] && object.getApproxY() == curPos[1]) {
				return true;
			}
		}
		return false;
	}

	protected ArrayList<int[]> getNearbyLocs() {
		ArrayList<int[]> locs = new ArrayList<int[]>();

		locs.add(new int[] { getApproxX() + getMap().getTileSize(), getApproxY() });
		locs.add(new int[] { getApproxX() - getMap().getTileSize(), getApproxY() });
		locs.add(new int[] { getApproxX(), getApproxY() + getMap().getTileSize() });
		locs.add(new int[] { getApproxX(), getApproxY() - getMap().getTileSize() });

		return locs;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public double getX() {
		return (col * getMap().getTileSize()) + (getMap().getTileSize() / 2);
	}

	public double getY() {
		return (row * getMap().getTileSize()) + (getMap().getTileSize() / 2);
	}

	public int getApproxX() {
		return (int) Math.round(getX());
	}

	public int getApproxY() {
		return (int) Math.round(getY());
	}

	public int getTopLeftX() {
		return col * getMap().getTileSize();
	}

	public int getTopLeftY() {
		return row * getMap().getTileSize();
	}
}
