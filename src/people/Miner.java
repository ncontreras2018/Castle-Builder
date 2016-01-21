package people;

import java.awt.Graphics2D;

import main.Player;
import tasks.Mining;
import throwables.IllegalLocationException;

public class Miner extends Person {

	private static int ORE_HAUL = 5;

	private boolean hasOre;

	public Miner(int xPos, int yPos, Player player) {
		super(xPos, yPos, 1, player);
		
		Mining.createTask(this);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(getPlayer().getColor().brighter());

		g2d.fillOval(getTopLeftX(), getTopLeftY(), getSize(), getSize());
	}

	@Override
	protected void doesntDoTasks() {

		System.out.println(this + " not doing tasks");

		if (hasOre) {
			
			System.out.println("Miner has ore");
			
			autoMoveTo(getNexus().getRow(), getNexus().getCol(), true);

			if (getNexus().isNearby(this)) {
				getPlayer().addOre(ORE_HAUL);
				hasOre = false;

				Mining.createTask(this);
			}
		}
	}

	public void collectOre() {
		hasOre = true;
	}
}
