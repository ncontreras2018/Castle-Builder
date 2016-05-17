package tasks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import abstractClasses.UnlockedFromGrid;
import interfaces.Valuable;
import main.Player;
import people.Person;

public class Demolition extends Task {

	public Demolition(int row, int col, Player player, Task prerequisiteFor) {
		super(row, col, player, 2, prerequisiteFor);
		System.out.println("Constructed Demo Task At Row: " + getRow() + " Col: " + getCol());
	}

	public Demolition(int row, int col, Player player) {
		this(row, col, player, null);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		g2d.setColor(Color.RED);

		g2d.fillRect(this.getTopLeftX(), this.getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	}

	@Override
	protected ArrayList<Task> createPrerequisiteTasks() {
		return new ArrayList<Task>();
	}

	@Override
	public int getPriorty() {
		return isPrerequisite() ? getDependentTask().getPriorty() + 1 : 2;
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
	}

	@Override
	public boolean shouldBeAdjacent() {
		return true;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return true;
	}

	@Override
	protected void preformFinish() {
		if (getMap().getGrid()[getRow()][getCol()][1] instanceof Valuable) {
			getPlayer().addOre((int) (((Valuable) getMap().getGrid()[getRow()][getCol()][1]).getOreValue()
					* ((Valuable) getMap().getGrid()[getRow()][getCol()][1]).getReturnPercentage()));
		}
		
		getMap().getGrid()[getRow()][getCol()][1] = null;
		getMap().getGrid()[getRow()][getCol()][2] = null;
	}
}
