package tasks;

import java.awt.Graphics2D;
import java.util.ArrayList;

import abstractClasses.UnlockedFromGrid;
import main.Player;

public class GoTo extends Task {

	public GoTo(int row, int col, Player player, double timeCost) {
		this(row, col, player, timeCost, null);
	}

	public GoTo(int row, int col, Player player, double timeCost, Task prerequisiteFor) {
		super(row, col, player, timeCost, prerequisiteFor);
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
		return (isPrerequisite() ? getDependentTask().getPriorty() + 1 : 1);
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
