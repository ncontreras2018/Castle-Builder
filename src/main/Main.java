package main;

import people.Person;
import people.Worker;
import listeners.KeyListener;
import listeners.MouseListener;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import abstractClasses.Existent;
import threads.ControlThread;
import threads.GameThread;
import threads.GraphicsThread;
import threads.PathfindingThread;

public class Main {

	public static void main(String[] args) {
		
		System.setOut(new PrintStream(new OutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				// TODO Auto-generated method stub
				
			}
		}));
		
		int mapRows = 121, mapCols = 81;
		
		int windowWidth = 1000, windowHeight = 600;
		
		int tileSize = 30;
		
		Map map = new Map(mapRows, mapCols, tileSize);
		
		GamePanel panel = new GamePanel(windowWidth, windowHeight, map);
		
		KeyListener kl = new KeyListener();
		
		panel.getFrame().addKeyListener(kl);
		
		MouseListener ml = new MouseListener(panel, panel.getPlayer());
		
		panel.addMouseListener(ml);
		
		panel.addMouseMotionListener(ml);
		
		panel.addMouseWheelListener(ml);
		
		GameThread gameThread = new GameThread(60, panel);
		
		GraphicsThread graphicsThread = new GraphicsThread(60, panel);
		
		ControlThread controlThread = new ControlThread(30, panel, kl, ml);
		
		PathfindingThread pathfindingThread = new PathfindingThread(map);
		
		Person.setPathfinder(pathfindingThread);
		
		gameThread.start();
		
		graphicsThread.start();
		
		controlThread.start();
		
		pathfindingThread.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
