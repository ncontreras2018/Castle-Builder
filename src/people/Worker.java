package people;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Player;
import throwables.IllegalLocationException;

public class Worker extends Person {

	public Worker(int xPos, int yPos, Player player) throws IllegalLocationException {
		super(xPos, yPos, 1, player);
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
}
