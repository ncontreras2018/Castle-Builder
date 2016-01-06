package tasks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Demolition extends Task {

	public Demolition(int row, int col, int player, Task prerequisiteFor) {
		super(row, col, player, 2, prerequisiteFor);
	}
	
	public Demolition(int row, int col, int player) {
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
	public void update() {
		if (isDone()) {
			getMap().getGrid()[getRow()][getCol()][1] = null;
			getMap().getGrid()[getRow()][getCol()][2] = null;
		}
	}
}