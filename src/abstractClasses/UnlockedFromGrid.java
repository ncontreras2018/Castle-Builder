package abstractClasses;

import java.awt.Point;

import interfaces.Drawable;
import throwables.IllegalLocationException;

abstract public class UnlockedFromGrid extends Existent implements Drawable {

	private double xPos, yPos;

	private double movementSpeed;

	private int size;

	private final boolean ALLOW_ESCAPEMENT = true;

	public UnlockedFromGrid(int xPos, int yPos, double speed, int size) {

		setLocation(xPos, yPos);

		movementSpeed = speed;
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setLocation(double x, double y) {

		System.out.println("Setting Location To X: " + x + " Y: " + y);

		xPos = x;
		yPos = y;
	}

	public void setLocation(int[] location) {
		setLocation(location[0], location[1]);
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
