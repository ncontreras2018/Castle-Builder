package people;

import java.awt.Graphics2D;

import main.Player;

public class Farmer extends Person {

	private boolean hasFood;
	private static final int FOOD_HAUL = 5;

	public Farmer(Integer xPos, Integer yPos, Player player) {
		super(xPos, yPos, 1, player);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(getPlayer().getColor().darker().darker());

		g2d.fillOval(getTopLeftX(), getTopLeftY(), getSize(), getSize());
	}
	
	@Override
	protected boolean shouldDoTasks() {
		return super.shouldDoTasks() && !hasFood;
	}

	@Override
	protected void doesntDoTasks() {
		if (!getNexus().isNearby(this)) {
			autoMoveTo(getNexus().getRow(), getNexus().getCol(), true);

			if (getNexus().isNearby(this)) {
				getPlayer().addFood(FOOD_HAUL);
				hasFood = false;
			}
		}
	}

	@Override
	public int getFoodCost() {
		return 10;
	}

	public void collectFood() {
		hasFood = true;
	}
}
