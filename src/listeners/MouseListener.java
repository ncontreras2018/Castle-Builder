package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import abstractClasses.LockedToGrid;
import objects.Wall;
import tasks.Construction;
import tasks.Demolition;
import tasks.Task;
import main.GamePanel;
import main.Player;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener {

	private GamePanel gamePanel;

	private final double ZOOM_SPEED = -.1;

	private Player humanPlayer;

	public MouseListener(GamePanel gp, Player humanPlayer) {
		gamePanel = gp;
		this.humanPlayer = humanPlayer;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		System.out.println("Button Pressed: " + e.getButton());

		int[] adjustedPos = gamePanel.adjustPointForCamera(e.getX(), e.getY());

		int[] rowCol = gamePanel.getMap().convertXYtoRowCol(adjustedPos[0], adjustedPos[1]);

		if (rowCol != null) {

			if (e.getButton() == MouseEvent.BUTTON1) {

				Task newTask = new Construction(rowCol[0], rowCol[1], gamePanel.getPlayer(),
						new Wall(rowCol[0], rowCol[1]), true);

				gamePanel.getMap().getGrid()[rowCol[0]][rowCol[1]][2] = newTask;

				Task.addTask(newTask);
			} else if (e.getButton() == MouseEvent.BUTTON3) {

				LockedToGrid[] tileClicked = gamePanel.getMap().getGrid()[rowCol[0]][rowCol[1]];

				if (tileClicked[2] != null) {
					((Task) tileClicked[2]).remove();
				}

				System.out.println("Create Demo? Object at = " + tileClicked[1]);

				if (tileClicked[1] != null) {

					System.out.println("Creating Demo Task");

					Task newTask = new Demolition(rowCol[0], rowCol[1], gamePanel.getPlayer(), null);

					tileClicked[2] = newTask;

					Task.addTask(newTask);
				}
			}
		} else {
			System.out.println("Click was off map");
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
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		gamePanel.zoomCamera(e.getPreciseWheelRotation() * ZOOM_SPEED);
	}
}
