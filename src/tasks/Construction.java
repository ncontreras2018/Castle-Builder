package tasks;

import interfaces.Drawable;

import java.awt.Graphics2D;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import abstractClasses.UnlockedFromGrid;
import objects.VisibleObject;
import people.Person;

public class Construction extends Task {

	private VisibleObject toBuild;

	private boolean isPlaced;

	public Construction(int row, int col, int player, VisibleObject toBuild, boolean isPlaced) {
		super(row, col, player, 5);

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

			getMap().getGrid()[getRow()][getCol()][1] = toBuild;
			getMap().getGrid()[getRow()][getCol()][2] = null;
			System.out.println("Finished Construction: " + toBuild);
		}
	}

	@Override
	public boolean isDone() {

		if (!super.isDone()) {
			return false;
		}

		for (UnlockedFromGrid obj : getMap().getObjectsAt(getRow(), getCol())) {
			if (!toBuild.canPassThrough(obj)) {
				return false;
			}
		}

		return true;

	}

	@Override
	protected ArrayList<Task> createPrerequisiteTasks() {
		ArrayList<Task> prereqs = new ArrayList<Task>();

		if (getMap().getGrid()[getRow()][getCol()][1] != null) {
			prereqs.add(new Demolition(getRow(), getCol(), getPlayer(), this));
		}
		return prereqs;
	}
}
