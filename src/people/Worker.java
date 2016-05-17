package people;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Player;
import throwables.IllegalLocationException;

public class Worker extends Person {

	public Worker(int xPos, int yPos, Player player) {
		super(xPos, yPos, 1, player);
	}
	
	public Worker(Integer xPos, Integer yPos, Player player) {
		this(xPos.intValue(), yPos.intValue(), player);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(getPlayer().getColor().brighter().brighter());

		g2d.fillOval(getTopLeftX(), getTopLeftY(), getSize(), getSize());
	}

	@Override
	protected void doesntDoTasks() {

		if (!getNexus().isNearby(this)) {
			autoMoveTo(getNexus().getRow(), getNexus().getCol(), true);
		}
	}

	@Override
	public int getFoodCost() {
		return 15;
	}
}
