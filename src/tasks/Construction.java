package tasks;

import interfaces.Drawable;
import interfaces.Valuable;
import main.Player;

import java.awt.Graphics2D;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import people.Person;

public class Construction extends Task {

	private LockedToGrid toBuild;

	private boolean hasOre;

	public Construction(int row, int col, Player player, LockedToGrid toBuild) {
		super(row, col, player, 5);

		this.toBuild = toBuild;

		this.hasOre = false;
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		toBuild.draw(g2d, true);

		System.out.println("Drawing Construction @ " + getRow() + ", " + getCol());
	}

	@Override
	public int getPriorty() {
		return hasOre ? 3 : 0;
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
		if (!hasOre) {
			if (toBuild instanceof Valuable) {
				hasOre = getPlayer().reserveOre(((Valuable) toBuild).getOreValue());
			} else {
				hasOre = true;
			}
		}

		if (isDone()) {

			getMap().getGrid()[getRow()][getCol()][1] = toBuild;
			getMap().getGrid()[getRow()][getCol()][2] = null;

			if (toBuild instanceof Valuable) {
				getPlayer().unReserveOre(((Valuable) toBuild).getOreValue());
				getPlayer().removeOre(((Valuable) toBuild).getOreValue());
			}
			System.out.println("Finished Construction: " + toBuild);
		}
	}

	@Override
	public boolean isDone() {

		for (UnlockedFromGrid obj : getMap().getObjectsAt(getRow(), getCol())) {
			if (!toBuild.canMoveThrough(obj)) {
				return false;
			}
		}

		if (toBuild instanceof Valuable) {
			if (((Valuable) toBuild).getOreValue() > getPlayer().getAmountOfOre()) {
				return false;
			}
		}

		if (!super.isDone()) {
			return false;
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

	@Override
	public boolean shouldBeAdjacent() {
		return true;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return toBuild.canMoveThrough(obj);
	}
}
