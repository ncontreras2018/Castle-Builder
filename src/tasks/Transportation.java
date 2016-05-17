package tasks;

import java.awt.Graphics2D;
import java.util.ArrayList;

import abstractClasses.UnlockedFromGrid;
import main.Player;

public class Transportation extends Task {

	public Transportation(int row, int col, Player player, double timeCost, Task prerequisiteFor) {
		super(row, col, player, timeCost, prerequisiteFor);
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
	public void update() {
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
	}

	@Override
	public boolean shouldBeAdjacent() {
		return false;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return true;
	}

	@Override
	protected void preformFinish() {
	}
}
