package tasks;

import java.awt.Graphics2D;
import java.util.ArrayList;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import main.Player;
import objects.Farmland;
import objects.Ore;
import people.Farmer;
import people.Miner;
import people.Person;

public class Farming extends Task {

	public Farming(int row, int col, Player player) {
		super(row, col, player, 5);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
	}

	@Override
	protected ArrayList<Task> createPrerequisiteTasks() {
		return new ArrayList<Task>();
	}

	@Override
	public boolean shouldBeAdjacent() {
		return false;
	}

	@Override
	public int getPriorty() {
		return 1;
	}

	@Override
	public void update() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<Person> getTypeNeeded() {
		try {
			return (Class<Person>) Class.forName("people.Farmer");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return true;
	}

	@Override
	protected void preformFinish() {
		
		((Farmer) getAssignedPerson()).collectFood();
		((Farmland) getMap().getGrid()[getRow()][getCol()][1]).harvest();
	}
}
