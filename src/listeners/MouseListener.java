package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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
