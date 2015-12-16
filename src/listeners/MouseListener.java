package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import objects.Wall;
import tasks.Construction;
import util.Util;
import main.GamePanel;

public class MouseListener implements java.awt.event.MouseListener,
		MouseMotionListener, MouseWheelListener {

	private GamePanel gamePanel;

	private final double ZOOM_SPEED = -.1;

	public MouseListener(GamePanel gp) {
		gamePanel = gp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int[] adjustedPos = gamePanel.adjustPointForCamera(e.getX(), e.getY());

		System.out.println("X: " + adjustedPos[0] + " Y: " + adjustedPos[1]);

		int[] rowCol = Util.getRowColAt(adjustedPos[0], adjustedPos[1]);

		System.out.println("Row: " + rowCol[0] + " Col: " + rowCol[1]);

		gamePanel.getMap().getGrid()[rowCol[0]][rowCol[1]][2] = new Construction(
				rowCol[0], rowCol[1], 1, 5, new Wall(rowCol[0], rowCol[1]),
				true);
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

		System.out.println("Unadjusted X: " + e.getX() + " Y: " + e.getY());

		int[] adjustedPos = gamePanel.adjustPointForCamera(e.getX(), e.getY());

		System.out.println("Adjusted X: " + adjustedPos[0] + " Y: " + adjustedPos[1]);

		int[] rowCol = Util.getRowColAt(adjustedPos[0], adjustedPos[1]);

		System.out.println("Row: " + rowCol[0] + " Col: " + rowCol[1]);

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		gamePanel.zoomCamera(e.getPreciseWheelRotation() * ZOOM_SPEED);
	}
}
