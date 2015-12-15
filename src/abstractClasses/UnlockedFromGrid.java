package abstractClasses;


abstract public class UnlockedFromGrid extends Existent {
	
	protected double[] location;
	
	protected double movementSpeed;
	
	public UnlockedFromGrid(double xPos, double yPos, double speed) {
		location[0] = xPos;
		location[1] = yPos;
		movementSpeed = speed;
	}
	
	public double[] getLocation() {
		return location;
	}
	
	public void setLocation(double[] newLoc) {
		location = newLoc;
	}
	
	public double getSpeed() {
		return movementSpeed;
	}

}
