package people;

import java.awt.Color;
import java.awt.Graphics2D;

import throwables.IllegalLocationException;

public class Worker extends Person {

	public Worker(int xPos, int yPos, double speed, int player) throws IllegalLocationException {
		super(xPos, yPos, speed, player);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(Color.BLUE);

		g2d.fillOval(getTopLeftX(), getTopLeftY(), getSize(), getSize());
	}

	@Override
	protected void doesntDoTasks() {
		// TODO: wander logic
	}
}
