package abstractClasses;

abstract public class LockedToGrid extends Existent {

	protected int row, col;

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
		if (x > col * MAP.getTileSize()
				&& x < (col * MAP.getTileSize()) + MAP.getTileSize()) {
			if (y > row * MAP.getTileSize()
					&& y < (row * MAP.getTileSize()) + MAP.getTileSize()) {
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
		return (col * MAP.getTileSize()) + (MAP.getTileSize() / 2);
	}
	
	public double getY() {
		return (row * MAP.getTileSize()) + (MAP.getTileSize() / 2);
	}
	
	public int getTopLeftX() {
		return col * MAP.getTileSize();
	}
	
	public int getTopLeftY() {
		return row * MAP.getTileSize();
	}
}
