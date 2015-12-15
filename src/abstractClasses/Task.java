package abstractClasses;

public abstract class Task {
	
	protected double[] startLocation;
	protected double[] endLocation;
	
	public Task(double startX, double startY, double endX, double endY) {
		startLocation = new double[] {startX, startY};
		endLocation = new double[] {endX, endY};
	}
	
	public abstract boolean getPriorty();
	
	public abstract double[] getStartLocation();
	
	public abstract double[] getEndLocation();

}
