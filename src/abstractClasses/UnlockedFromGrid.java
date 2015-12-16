package abstractClasses;

import interfaces.Drawable;
import util.Util;

abstract public class UnlockedFromGrid extends Existent implements Drawable {

	protected double xPos, yPos;

	protected double movementSpeed;

	public UnlockedFromGrid(double xPos, double yPos, double speed) {
		setLocation(xPos, yPos);
		movementSpeed = speed;
	}

	public void setLocation(double x, double y) {

		for (LockedToGrid layer : Util.getGridObjectsAt(x, y)) {

			if (layer != null) {

				if (!layer.canPassThrough(this)) {
					if (layer.contains(x, y)) {
						return;
					}
				}
			}
		}

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

}
