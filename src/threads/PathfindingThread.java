package threads;

import java.util.ArrayList;
import java.util.HashMap;

import tasks.Task;
import util.Util;
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

	public void requestPath(UnlockedFromGrid objectNeedingPath, double targX,
			double targY) {

		removePathFor(objectNeedingPath);

		pathFindingRequests.add(new Object[] { objectNeedingPath,
				(int) Math.round(targX), (int) Math.round(targY) });
	}

	@Override
	public void run() {
		while (true) {

			System.out.println("Pathfinder tick");

			if (pathFindingRequests.size() > 0) {
				Object[] currentRequest = pathFindingRequests.remove(0);

				System.out.println("Pathfinder has request");

				pathMesh = new int[map.numCols() * map.getTileSize()][map
						.numRows() * map.getTileSize()];

				// ArrayList<int[]> pathFound = recursivelyGetPath(
				// (int) currentRequest[1], (int) currentRequest[2],
				// ((UnlockedFromGrid) currentRequest[0]).getApproxX(),
				// ((UnlockedFromGrid) currentRequest[0]).getApproxY(),
				// (int) Math.round(((UnlockedFromGrid) currentRequest[0])
				// .getSpeed()), 1);

				int cycle = 1;

				pathMesh[(int) currentRequest[1]][(int) currentRequest[2]] = 1;

				while (true) {
					if (mapNextMeshLayer(
							((UnlockedFromGrid) currentRequest[0]).getApproxX(),
							((UnlockedFromGrid) currentRequest[0]).getApproxY(),
							(int) Math
									.round(((UnlockedFromGrid) currentRequest[0])
											.getSpeed()), cycle)) {

						System.out.println("MeshMapper retuned true");

						break;
					}

					cycle++;
				}

				ArrayList<int[]> pathFound = getPathFromMesh(
						((UnlockedFromGrid) currentRequest[0]).getApproxX(),
						((UnlockedFromGrid) currentRequest[0]).getApproxY(),
						(int) Math.round(((UnlockedFromGrid) currentRequest[0])
								.getSpeed()));

				pathsFound.put((UnlockedFromGrid) currentRequest[0], pathFound);

				System.out.println("Path found, saved");

				System.out.println("Path length: " + pathFound.size());
			}

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<int[]> getPathFor(UnlockedFromGrid needsPath) {
		return pathsFound.get(needsPath);
	}

	public void removePathFor(UnlockedFromGrid removeFrom) {
		pathsFound.remove(removeFrom);
	}

	public int[] getFinalDestFor(UnlockedFromGrid pathfindingObject) {

		if (pathsFound.get(pathfindingObject) == null
				|| pathsFound.get(pathfindingObject).isEmpty()) {
			return null;
		} else {
			return pathsFound.get(pathfindingObject).get(
					pathsFound.get(pathfindingObject).size() - 1);
		}
	}

	private boolean mapNextMeshLayer(int objectX, int objectY, int distBetween,
			int cycle) {

		System.out.println("Mapping cycle: " + cycle);

		for (int y = 0; y < pathMesh.length; y++) {
			for (int x = 0; x < pathMesh[y].length; x++) {

				if (pathMesh[y][x] == cycle) {

					System.out.println("Found Node to Branch off of: X: " + x
							+ " Y: " + y);

					System.out.println("Target is X: " + objectX + " Y: "
							+ objectY);

					if (Util.getDistance(x, y, objectX, objectY) < distBetween) {
						pathMesh[y][x] = cycle + 1;
						System.out.println("Mapping has reached target");
						return true;
					}

					if (pathMesh[y + distBetween][x] > cycle + 1
							|| pathMesh[y + distBetween][x] == 0) {
						pathMesh[y + distBetween][x] = cycle + 1;
					}

					if (pathMesh[y - distBetween][x] > cycle + 1
							|| pathMesh[y - distBetween][x] == 0) {
						pathMesh[y - distBetween][x] = cycle + 1;
					}

					if (pathMesh[y][x + distBetween] > cycle + 1
							|| pathMesh[y][x + distBetween] == 0) {
						pathMesh[y][x + distBetween] = cycle + 1;
					}

					if (pathMesh[y][x - distBetween] > cycle + 1
							|| pathMesh[y][x - distBetween] == 0) {
						pathMesh[y][x - distBetween] = cycle + 1;
					}
				}
			}
		}
		return false;
	}

	private ArrayList<int[]> getPathFromMesh(int objectX, int objectY,
			int distBetween) {
		ArrayList<int[]> path = new ArrayList<int[]>();

		System.out.println("Obtaining path from mesh");

		int curX = objectX;
		int curY = objectY;

		while (pathMesh[curY][curX] != 1) {

			int curNodeCycle = pathMesh[curY][curX];

			System.out.println("At X: " + curX + " Y: " + curY + " Cycle: "
					+ curNodeCycle);

			path.add(new int[] { curX, curY });

			System.out.println("Adjacent Node: "
					+ pathMesh[curY + distBetween][curX]);

			if (pathMesh[curY + distBetween][curX] == curNodeCycle - 1) {
				curY = curY + distBetween;
				continue;
			}

			System.out.println("Adjacent Node: "
					+ pathMesh[curY - distBetween][curX]);

			if (pathMesh[curY - distBetween][curX] == curNodeCycle - 1) {
				curY = curY - distBetween;
				continue;
			}

			System.out.println("Adjacent Node: "
					+ pathMesh[curY][curX + distBetween]);

			if (pathMesh[curY][curX + distBetween] == curNodeCycle - 1) {
				curX = curX + distBetween;
				continue;
			}

			System.out.println("Adjacent Node: "
					+ pathMesh[curY][curX - distBetween]);

			if (pathMesh[curY][curX - distBetween] == curNodeCycle - 1) {
				curX = curX - distBetween;
				continue;
			}

			System.out.println("Uh, oh! No path!");
		}

		System.out.println("Reached node with cycle 1");

		path.add(new int[] { curX, curY });

		return path;
	}

	private ArrayList<int[]> recursivelyGetPath(int curX, int curY,
			int objectX, int objectY, int distBetween, int cycle) {

		System.out.println("Pathfinding, cycle: " + cycle);

		System.out.println("Testing point: " + curX + ", " + curY);

		System.out
				.println("Current Cycle At Location: " + pathMesh[curY][curX]);

		if (pathMesh[curY][curX] < cycle && pathMesh[curY][curX] != 0) {
			return null;
		}

		pathMesh[curY][curX] = cycle;

		if (Util.getDistance(curX, curY, objectX, objectY) < distBetween) {

			ArrayList<int[]> path = new ArrayList<int[]>();

			path.add(0, new int[] { curX, curY });

			return path;

		} else {
			ArrayList<ArrayList<int[]>> possiblePaths = new ArrayList<ArrayList<int[]>>();

			possiblePaths.add(recursivelyGetPath(curX + distBetween, curY,
					objectX, objectY, distBetween, cycle + 1));

			possiblePaths.add(recursivelyGetPath(curX - distBetween, curY,
					objectX, objectY, distBetween, cycle + 1));

			possiblePaths.add(recursivelyGetPath(curX, curY + distBetween,
					objectX, objectY, distBetween, cycle + 1));

			possiblePaths.add(recursivelyGetPath(curX, curY - distBetween,
					objectX, objectY, distBetween, cycle + 1));

			ArrayList<int[]> bestPath = null;
			int bestPathLength = Integer.MAX_VALUE;

			for (int i = 0; i < 4; i++) {
				if (possiblePaths.get(i) != null) {
					if (possiblePaths.get(i).size() < bestPathLength) {
						bestPath = possiblePaths.get(i);
						bestPathLength = bestPath.size();
					}
				}
			}

			if (bestPath == null) {
				return null;
			} else {

				bestPath.add(0, new int[] { curX, curY });

				return bestPath;
			}
		}
	}
}
