package tasks;

import interfaces.Drawable;

import java.awt.Graphics2D;
import java.io.ObjectInputStream.GetField;

import abstractClasses.UnlockedFromGrid;
import objects.VisibleObject;
import people.Person;

public class Construction extends Task implements Drawable {

	private VisibleObject toBuild;
	
	private boolean isPlaced;

	public Construction(int row, int col, int player, double timeCost,
			VisibleObject toBuild, boolean isPlaced) {
		super(row, col, player, timeCost);

		this.toBuild = toBuild;
		
		this.isPlaced = isPlaced;
	}
	
	public void place() {
		isPlaced = true;
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		toBuild.draw(g2d, true);
		
		System.out.println("Drawing Construction @ " + getRow() + ", " + getCol());
	}

	@Override
	public int getPriorty() {
		return isPlaced ? 3 : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class<Person> getTypeNeeded() {
		try {
			return (Class<Person>) Class.forName("people.Worker");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update() {
		if (isDone()) {
			MAP.getGrid()[row][col][1] = toBuild;
			MAP.getGrid()[row][col][2] = null;
		}
	}

	@Override
	public boolean canPassThrough(UnlockedFromGrid other) {
		return true;
	}
}
