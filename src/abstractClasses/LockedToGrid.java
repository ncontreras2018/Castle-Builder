package abstractClasses;

abstract public class LockedToGrid extends Existent {

	private int row, col;

	public LockedToGrid(int row, int col) {

		this.row = row;
		this.col = col;
	}

	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public abstract boolean canPassThrough(UnlockedFromGrid other);

	public boolean contains(double x, double y) {
		if (x > col * getMap().getTileSize() && x < (col * getMap().getTileSize()) + getMap().getTileSize()) {
			if (y > row * getMap().getTileSize() && y < (row * getMap().getTileSize()) + getMap().getTileSize()) {
				return true;
			}
		}
		return false;
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
