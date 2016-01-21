package main;

import people.Person;
import people.Worker;
import listeners.KeyListener;
import listeners.MouseListener;

import java.awt.Color;
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

		// System.setOut(new PrintStream(new OutputStream() {
		//
		// @Override
		// public void write(int b) throws IOException {
		// // TODO Auto-generated method stub
		//
		// }
		// }));

		new GamePanel();
	}

}
