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

	private int[] mouseDownPoint;

	private boolean mousePressed;

	public MouseListener(GamePanel gp) {
		gamePanel = gp;

		mouseDownPoint = new int[2];
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!mousePressed) {
			mouseDownPoint = new int[] { e.getY(), e.getY() };
		}
		mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		System.out.println("MousePressed: " + mousePressed);
		
		gamePanel.getMap().removeTemporaryItems();

		int[] mouseStart = mouseDownPoint;
		int[] mouseEnd = new int[] {e.getX(), e.getY()};
		
		System.out.println("Mouse Loc: " + mouseEnd[0] + ", " + mouseEnd[1]);

		if (Util.sameRow(mouseStart[1], mouseEnd[1])) {

			int[] start = Util
					.getRowColAt(mouseStart[0], mouseStart[1]);
			int[] end = Util.getRowColAt(mouseStart[0], mouseStart[1]);

			int curCol = start[1];

			while (curCol <= end[1]) {
				
				System.out.println("Adding Temp Construction to : " + start[1] + ", " + curCol);
				
				gamePanel.getMap().getGrid()[start[0]][curCol][2] = new Construction(
						start[0], curCol, 1, 5, new Wall(start[0], curCol),
						false);
				
				curCol++;
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		gamePanel.zoomCamera(e.getPreciseWheelRotation() * ZOOM_SPEED);
	}
}
