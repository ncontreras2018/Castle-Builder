package listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.Serializable;

import abstractClasses.LockedToGrid;
import objects.Wall;
import tasks.Construction;
import tasks.Demolition;
import tasks.Task;
import main.GamePanel;
import main.Player;

public class MouseListener
		implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener, Serializable {

	private GamePanel gamePanel;

	private final double ZOOM_SPEED = -.1;

	private Player humanPlayer;

	private int mouseX, mouseY;

	public MouseListener(GamePanel gp, Player humanPlayer) {
		gamePanel = gp;
		this.humanPlayer = humanPlayer;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getY() <= gamePanel.getMenu().getMenuSize().height) {
			gamePanel.getMenu().mouseClicked(e);

		} else {
			gamePanel.getPlayer().mouseClicked(e);

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		gamePanel.zoomCamera(e.getPreciseWheelRotation() * ZOOM_SPEED);
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int[] getAdjustedLocation() {
		return gamePanel.adjustPointForCamera(mouseX, mouseY);
	}
}
