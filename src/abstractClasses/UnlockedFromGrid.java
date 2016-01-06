package abstractClasses;

import java.awt.Point;

import interfaces.Drawable;
import throwables.IllegalLocationException;
import util.Util;

abstract public class UnlockedFromGrid extends Existent implements Drawable {

	private double xPos, yPos;

	private double movementSpeed;

	private int size;

	public UnlockedFromGrid(int xPos, int yPos, double speed, int size) throws IllegalLocationException {

		if (!setLocation(xPos, yPos)) {
			throw new IllegalLocationException("Attempting To Create " + this + " At An Already Occupied Location");
		}

		movementSpeed = speed;
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	private boolean somethingInWayOfMe(double x, double y) {

		System.out.println("Checking for object at X: " + x + " Y: " + y);

		for (LockedToGrid layer : Util.getGridObjectsAt(x, y)) {

			System.out.println("Checking layer with: " + layer);

			if (layer != null) {

				if (!layer.canPassThrough(this)) {

					System.out.println("^Cannot pass through above layer^");

					return true;
				}
			}
		}

		return false;

	}

	public boolean setLocation(double x, double y) {

		System.out.println("Setting Location With Check To X: " + x + " Y: " + y);

		if (somethingInWayOfMe(x, y)) {
			return false;
		}

		xPos = x;
		yPos = y;

		return true;
	}

	public boolean setLocation(int[] location) {
		return setLocation(location[0], location[1]);
	}

	public double[] getLocation() {
		return new double[] { xPos, yPos };
	}

	public double getSpeed() {
		return movementSpeed;
	}

	public double getX() {
		return xPos;
	}

	public double getY() {
		return yPos;
	}

	public int getApproxX() {
		return (int) Math.round(xPos);
	}

	public int getApproxY() {
		return (int) Math.round(yPos);
	}

	public int getTopLeftX() {
		return getApproxX() - (size / 2);
	}

	public int getTopLeftY() {
		return getApproxY() - (size / 2);
	}

	public Point getTopLeft() {
		return new Point(getTopLeftX(), getTopLeftY());
	}

	public int getRow() {
		return getTopLeftY() / getMap().getTileSize();
	}

	public int getCol() {
		return getTopLeftX() / getMap().getTileSize();
	}

}
