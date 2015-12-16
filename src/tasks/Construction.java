package tasks;

import interfaces.Drawable;

import java.awt.Graphics2D;

import objects.VisibleObject;

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
	public void update() {
		if (isDone()) {
			MAP.getGrid()[row][col][1] = toBuild;
			MAP.getGrid()[row][col][2] = null;
		}
	}
}
