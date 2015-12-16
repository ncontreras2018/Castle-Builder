package objects;

import interfaces.Drawable;
import abstractClasses.LockedToGrid;

abstract public class VisibleObject extends LockedToGrid implements Drawable {

	public VisibleObject(int row, int col) {
		super(row, col);
		}
}
