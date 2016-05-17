package people;

import java.awt.Graphics2D;

import main.Player;
import tasks.Mining;
import tasks.Task;
import throwables.IllegalLocationException;

public class Miner extends Person {

	private static int ORE_HAUL = 5;

	private boolean hasOre;

	public Miner(int xPos, int yPos, Player player) {
		super(xPos, yPos, 1, player);
	}

	public Miner(Integer xPos, Integer yPos, Player player) {
		this(xPos.intValue(), yPos.intValue(), player);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(getPlayer().getColor());

		g2d.fillOval(getTopLeftX(), getTopLeftY(), getSize(), getSize());
	}
	
	//TODO: Fix this

	@Override
	protected void doesntDoTasks() {

		if (getNexus().isNearby(this)) {

			if (hasOre) {
				System.err.println("--- " + this + " ---");
				getPlayer().addOre(ORE_HAUL);
				hasOre = false;
			}
			
			if (!hasTask() && !Task.hasApplicableTaskFor(this)) {
				Mining.createTask(this);
			}
		} else {
			autoMoveTo(getNexus().getRow(), getNexus().getCol(), true);
		}
	}

	public void collectOre() {
		hasOre = true;
	}

	@Override
	public int getFoodCost() {
		return 15;
	}
}
