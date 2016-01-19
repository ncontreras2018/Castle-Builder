package threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import tasks.Task;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import main.Map;

public class PathfindingThread extends Thread {
	private Map map;

	private ArrayList<Object[]> pathFindingRequests;

	private HashMap<UnlockedFromGrid, ArrayList<int[]>> pathsFound;

	public PathfindingThread(Map map) {
		this.map = map;
		pathFindingRequests = new ArrayList<Object[]>();
		pathsFound = new HashMap<UnlockedFromGrid, ArrayList<int[]>>();
	}

	public void requestPath(UnlockedFromGrid objectNeedingPath, int targRow, int targCol, boolean adjacent) {

		Object[] request = new Object[] { objectNeedingPath, targRow, targCol, adjacent };

		removePathFor(objectNeedingPath);

		pathFindingRequests.add(request);

		System.out.println("Pathfinding request placed for: " + objectNeedingPath + " going to Row: " + targRow
				+ " Col: " + targCol + " Adjacent: " + adjacent);
	}

	public boolean hasRequestFor(UnlockedFromGrid objectNeedingPath) {
		for (Object[] oldRequest : pathFindingRequests) {
			if (oldRequest[0].equals(objectNeedingPath)) {
				return true;
			}
		}
		return false;
	}

	public void removePathFor(UnlockedFromGrid objectNeedingPath) {
		pathsFound.remove(objectNeedingPath);
	}

	public ArrayList<int[]> getPathFor(UnlockedFromGrid objectNeedingPath) {
		return pathsFound.get(objectNeedingPath);
	}

	@Override
	public void run() {
		while (true) {

			System.out.println("Pathfinder tick");

			if (pathFindingRequests.size() > 0) {
				Object[] currentRequest = pathFindingRequests.get(0);

				System.out.println("Pathfinder has request");

				int objectRow = ((UnlockedFromGrid) currentRequest[0]).getRow();
				int objectCol = ((UnlockedFromGrid) currentRequest[0]).getCol();

				int targetRow = (int) currentRequest[1];
				int targetCol = (int) currentRequest[2];

				System.out.println("Object: " + (UnlockedFromGrid) currentRequest[0] + " will be pathed to Row: "
						+ targetRow + " Col: " + targetCol + " isAdjacent: " + (boolean) currentRequest[3]);

				double[][] mapMesh = mapMesh(targetRow, targetCol, objectRow, objectCol,
						(UnlockedFromGrid) currentRequest[0], (boolean) currentRequest[3]);

				if (mapMesh != null) {

					ArrayList<int[]> pathFound = getPathFromMesh(objectRow, objectCol, mapMesh,
							(boolean) currentRequest[3]);

					pathsFound.put((UnlockedFromGrid) currentRequest[0], pathFound);

					System.out.println("Path found, saved");

					System.out.println("Path length: " + pathFound.size());
				} else {
					System.out.println("Path Mesh is null, request dumped");
				}

				pathFindingRequests.remove(0);
			}

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<int[]> getPathFromMesh(int startRow, int startCol, double[][] pathMesh, boolean adjacent) {

		ArrayList<int[]> pathFound = new ArrayList<int[]>();

		int curRow = startRow;
		int curCol = startCol;

		while (true) {

			pathFound.add(new int[] { curRow, curCol });

			double curCycle = pathMesh[curRow][curCol];

			System.out.println("Currently at cycle: " + curCycle);

			System.out.println("Cur Row: " + curRow + "   Cur Col: " + curCol);

			int bestRow = curRow;
			int bestCol = curCol;

			double bestScore = pathMesh[bestRow][bestCol];

			if (curRow + 1 >= 0) {
				if (pathMesh[curRow + 1][curCol] < bestScore && pathMesh[curRow + 1][curCol] != 0) {
					bestRow = curRow + 1;
					bestCol = curCol;
				}
			}
			
			bestScore = pathMesh[bestRow][bestCol];

			if (curRow - 1 >= 0) {
				if (pathMesh[curRow - 1][curCol] < bestScore && pathMesh[curRow - 1][curCol] != 0) {
					bestRow = curRow - 1;
					bestCol = curCol;
				}
			}

			bestScore = pathMesh[bestRow][bestCol];

			if (curCol + 1 < pathMesh[curRow].length) {
				if (pathMesh[curRow][curCol + 1] < bestScore && pathMesh[curRow][curCol + 1] != 0) {
					bestRow = curRow;
					bestCol = curCol + 1;
				}
			}

			bestScore = pathMesh[bestRow][bestCol];

			if (curCol - 1 >= 0) {
				if (pathMesh[curRow][curCol - 1] < bestScore && pathMesh[curRow][curCol - 1] != 0) {
					bestRow = curRow;
					bestCol = curCol - 1;
				}
			}
			
			bestScore = pathMesh[bestRow][bestCol];

			System.out.println("Adding Row: " + bestRow + " Col: " + bestCol + " Index: " + bestScore);

			if (bestScore >= curCycle) {

				System.out.println("Current Tile is best Tile, returning path");

				return pathFound;
			}

			bestScore = pathMesh[bestRow][bestCol];

			curRow = bestRow;
			curCol = bestCol;
		}
	}

	private double[][] mapMesh(int targRow, int targCol, int objectRow, int objectCol, UnlockedFromGrid obj,
			boolean adjacent) {

		double[][] pathMesh = new double[map.numRows()][map.numCols()];

		pathMesh[targRow][targCol] = 1;

		ArrayList<int[]> lastNodes = new ArrayList<int[]>();

		lastNodes.add(new int[] { targRow, targCol });

		int cycle = 2;

		while (true) {

			ArrayList<int[]> newNodes = new ArrayList<int[]>();

			System.out.println("Building off " + lastNodes.size() + " nodes");

			for (int[] nodeLoc : lastNodes) {

				if (nodeLoc[0] == objectRow && nodeLoc[1] == objectCol) {

					pathMesh[targRow][targCol] = (adjacent ? Double.MAX_VALUE : pathMesh[targRow][targCol]);

					System.out.println("mapping complete. targRow: " + targRow + " targCol: " + targCol + " Value: "
							+ pathMesh[targRow][targCol]);

					return pathMesh;
				}

				newNodes.addAll(mapNearbyTiles(nodeLoc[0], nodeLoc[1], cycle, pathMesh, obj));
			}

			lastNodes.clear();

			lastNodes.addAll(newNodes);

			System.out.println("Cycle " + cycle + " finished");

			System.out.println("Created " + lastNodes.size() + " nodes");

			if (lastNodes.isEmpty()) {
				return null;
			}
			cycle++;
		}
	}

	private ArrayList<int[]> mapNearbyTiles(int row, int col, double cycle, double[][] pathMesh, UnlockedFromGrid obj) {

		System.out.println("Mapping Nearby Tiles for Row: " + row + " Col " + col);

		System.out.println("This tile has a index of: " + cycle);

		ArrayList<int[]> newTileLocs = new ArrayList<int[]>();

		if (row + 1 < pathMesh.length) {
			if (map.canPassThroughTile(row + 1, col, obj)) {
				if (pathMesh[row + 1][col] > getPenalty(obj, cycle) || pathMesh[row + 1][col] == 0) {
					pathMesh[row + 1][col] = getPenalty(obj, cycle);
					newTileLocs.add(new int[] { row + 1, col });
				}
			}
		}

		if (row - 1 >= 0) {
			if (map.canPassThroughTile(row - 1, col, obj)) {
				if (pathMesh[row - 1][col] > getPenalty(obj, cycle) || pathMesh[row - 1][col] == 0) {
					pathMesh[row - 1][col] = getPenalty(obj, cycle);
					newTileLocs.add(new int[] { row - 1, col });
				}
			}
		}

		if (col + 1 < pathMesh[row].length) {
			if (map.canPassThroughTile(row, col + 1, obj)) {
				if (pathMesh[row][col + 1] > getPenalty(obj, cycle) || pathMesh[row][col + 1] == 0) {
					pathMesh[row][col + 1] = getPenalty(obj, cycle);
					newTileLocs.add(new int[] { row, col + 1 });
				}
			}
		}

		if (col - 1 >= 0) {
			if (map.canPassThroughTile(row, col - 1, obj)) {
				if (pathMesh[row][col - 1] > getPenalty(obj, cycle) || pathMesh[row][col - 1] == 0) {
					pathMesh[row][col - 1] = getPenalty(obj, cycle);
					newTileLocs.add(new int[] { row, col - 1 });
				}
			}
		}

		return newTileLocs;

	}

	private double getPenalty(UnlockedFromGrid object, double cycle) {

		double total = 1;

		for (LockedToGrid cur : map.getGrid()[object.getRow()][object.getCol()]) {
			if (cur != null) {
				total *= cur.movementPenalty(object);
			}
		}
		return cycle + total;
	}
}
