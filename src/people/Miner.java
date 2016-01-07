package people;

import java.awt.Graphics2D;

import main.Player;
import tasks.Mining;
import throwables.IllegalLocationException;

public class Miner extends Person {

	public Miner(int xPos, int yPos, Player player) throws IllegalLocationException {
		super(xPos, yPos, 1, player);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(getPlayer().getColor().brighter());
		
		g2d.fillOval(getTopLeftX(), getTopLeftY(), getSize(), getSize());
	}

	@Override
	protected void doesntDoTasks() {
		
		autoMoveTo(getNexus().getRow(), getNexus().getRow(), true);
		
		if (getNexus().isNearby(this)) {
			Mining.createTask(this);
		}
	}
}
