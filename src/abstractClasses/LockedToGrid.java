package abstractClasses;


abstract public class LockedToGrid extends Existent {
	
	protected int[] location;
	
	public LockedToGrid(int row, int col) {
		
		location = new int[2];
		
		location[0] = row;
		location[1] = col;
	}
 
	public int[] getLocation() {
		return location;
	}
	
	public void setLocation(int[] newLoc) {
		location = newLoc;
	}
	
	public boolean canPassThrough(UnlockedFromGrid other) {
		return false;
	}
}
