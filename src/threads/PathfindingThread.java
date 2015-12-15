package threads;

import java.util.ArrayList;
import java.util.HashMap;

import abstractClasses.UnlockedFromGrid;
import main.Map;

public class PathfindingThread extends Thread {
	private Map map;

	private int[][] pathMesh;

	private ArrayList<Object[]> pathFindingRequests;
	
	private HashMap<UnlockedFromGrid, ArrayList<int[]>> pathsFound;

	public PathfindingThread(Map map) {
		this.map = map;
		pathFindingRequests = new ArrayList<Object[]>();
		pathsFound = new HashMap<UnlockedFromGrid, ArrayList<int[]>>();
	}

	public void requestPath(UnlockedFromGrid objectNeedingPath, int targX,
			int targY) {
		pathFindingRequests
				.add(new Object[] { objectNeedingPath, targX, targY });
	}

	@Override
	public void run() {
		while (true) {
			if (pathFindingRequests.size() > 0) {
				Object[] currentRequest = pathFindingRequests.remove(0);
				
				ArrayList<int[]> pathFound = getPath((UnlockedFromGrid) currentRequest[0], new int[] {(int) currentRequest[1], (int) currentRequest[2]});
				
				pathsFound.put((UnlockedFromGrid) currentRequest[0], pathFound);
			}
		}
	}
	
	public ArrayList<int[]> getPathFor(UnlockedFromGrid needsPath) {
		return pathsFound.remove(needsPath);
	}

	public ArrayList<int[]> getPath(UnlockedFromGrid needsPath,
			int[] targLocation) {
		pathMesh = new int[map.getGrid().length][map.getGrid()[0].length];

		int destRow = targLocation[1];
		int destCol = targLocation[0];

		int objectRow = (int) Math.round(needsPath.getLocation()[1]);
		int objectCol = (int) Math.round(needsPath.getLocation()[1]);

		double objectSpeed = needsPath.getSpeed();

		pathMesh[destRow][destCol] = 1;

		int cycle = 2;

		while (true) {
			if (nextCycle(cycle, objectRow, objectCol, objectSpeed)) {
				break;
			}
			cycle++;
		}

		return getPathFromMesh(destRow, destCol, objectRow, objectCol,
				objectSpeed);

	}

	private ArrayList<int[]> getPathFromMesh(int destRow, int destCol,
			int objectRow, int objectCol, double distBetweenNodes) {

		ArrayList<int[]> path = new ArrayList<int[]>();

		int integerDist = (int) Math.round(distBetweenNodes);

		int curRow = destRow;
		int curCol = destCol;

		while (pathMesh[curRow][curCol] != 1) {

			int bestRow = curRow + integerDist;
			int bestCol = curCol;
			int bestCycle = pathMesh[bestRow][bestCol];

			if (pathMesh[curRow - integerDist][curCol] < bestCycle) {
				bestRow = curRow - integerDist;
				bestCol = curCol;
				bestCycle = pathMesh[bestRow][bestCol];
			}

			if (pathMesh[curRow][curCol + integerDist] < bestCycle) {
				bestRow = curRow;
				bestCol = curCol + integerDist;
				bestCycle = pathMesh[bestRow][bestCol];
			}

			if (pathMesh[curRow][curCol - integerDist] < bestCycle) {
				bestRow = curRow;
				bestCol = curCol - integerDist;
				bestCycle = pathMesh[bestRow][bestCol];
			}

			path.add(new int[] { bestCol, bestRow });

		}
		return path;
	}

	private boolean nextCycle(int cycleNum, int objectRow, int objectCol,
			double distBetweenNodes) {
		for (int row = 0; row < pathMesh.length; row++) {
			for (int col = 0; col < pathMesh[row].length; col++) {

				if (pathMesh[row][col] == cycleNum - 1) {

					if (Math.abs(objectCol - col) < distBetweenNodes
							|| Math.abs(objectRow - row) < distBetweenNodes) {

						return true;

					}

					int integerDist = (int) Math.round(distBetweenNodes);

					pathMesh[row + integerDist][col] = Math.min(cycleNum,
							pathMesh[row + integerDist][col]);
					pathMesh[row - integerDist][col] = Math.min(cycleNum,
							pathMesh[row - integerDist][col]);
					pathMesh[row][col + integerDist] = Math.min(cycleNum,
							pathMesh[row][col + integerDist]);
					pathMesh[row][col - integerDist] = Math.min(cycleNum,
							pathMesh[row][col - integerDist]);
				}
			}
		}

		return false;

	}
}
