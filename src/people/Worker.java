package people;

import java.awt.Color;
import java.awt.Graphics2D;

public class Worker extends Person {

	public Worker(int xPos, int yPos, double speed, int player) {
		super(xPos, yPos, speed, player);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(Color.BLUE);
		
		g2d.fillOval(getTopLeftX(), getTopLeftY(), size, size);
	}

	@Override
	protected void doesntDoTasks() {
		//TODO: wander logic
	}
}
