package threads;

import java.util.ArrayList;
import java.util.HashMap;

import tasks.Task;
import util.Util;
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

	public void requestPath(UnlockedFromGrid objectNeedingPath, int targRow, int targCol) {

		removePathFor(objectNeedingPath);

		pathFindingRequests.add(new Object[] { objectNeedingPath, targRow, targCol });
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
				Object[] currentRequest = pathFindingRequests.remove(0);

				System.out.println("Pathfinder has request");

				int objectRow = ((UnlockedFromGrid) currentRequest[0]).getRow();
				int objectCol = ((UnlockedFromGrid) currentRequest[0]).getCol();

				int targetRow = (int) currentRequest[1];
				int targetCol = (int) currentRequest[2];

				int[][] mapMesh = mapMesh(targetRow, targetCol, objectRow, objectCol,
						(UnlockedFromGrid) currentRequest[0]);

				if (mapMesh != null) {

					ArrayList<int[]> pathFound = getPathFromMesh(objectRow, objectCol, mapMesh);

					pathsFound.put((UnlockedFromGrid) currentRequest[0], pathFound);

					System.out.println("Path found, saved");

					System.out.println("Path length: " + pathFound.size());
				} else {
					System.out.println("Path Mesh is null, request dumped");
				}
			}

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<int[]> getPathFromMesh(int startRow, int startCol, int[][] pathMesh) {

		ArrayList<int[]> pathFound = new ArrayList<int[]>();

		int curRow = startRow;
		int curCol = startCol;

		while (true) {

			pathFound.add(new int[] { curRow, curCol });

			int curCycle = pathMesh[curRow][curCol];

			System.out.println("Currently at cycle: " + curCycle);

			System.out.println("Cur Row: " + curRow + " Cur Col " + curCol);

			if (curCycle == 1) {
				return pathFound;
			}

			if (curRow + 1 < pathMesh.length) {
				if (pathMesh[curRow + 1][curCol] == curCycle - 1) {
					curRow = curRow + 1;
					System.out.println("Added Row: " + curRow + " Col " + curCol);
					continue;
				}
			}

			if (curRow - 1 >= 0) {
				if (pathMesh[curRow - 1][curCol] == curCycle - 1) {
					curRow = curRow - 1;
					System.out.println("Added Row: " + curRow + " Col " + curCol);
					continue;
				}
			}

			if (curCol + 1 < pathMesh[curRow].length) {
				if (pathMesh[curRow][curCol + 1] == curCycle - 1) {
					curCol = curCol + 1;
					System.out.println("Added Row: " + curRow + " Col " + curCol);
					continue;
				}
			}

			if (curCol - 1 >= 0) {
				if (pathMesh[curRow][curCol - 1] == curCycle - 1) {
					curCol = curCol - 1;
					System.out.println("Added Row: " + curRow + " Col " + curCol);
					continue;
				}
			}

			System.out.println("***Oh, no! No Path!***");

		}
	}

	private int[][] mapMesh(int targRow, int targCol, int objectRow, int objectCol, UnlockedFromGrid obj) {

		int[][] pathMesh = new int[map.numRows()][map.numCols()];

		pathMesh[targRow][targCol] = 1;

		ArrayList<int[]> lastNodes = new ArrayList<int[]>();

		lastNodes.add(new int[] { targRow, targCol });

		int cycle = 2;

		while (true) {

			ArrayList<int[]> newNodes = new ArrayList<int[]>();

			System.out.println("Building off " + lastNodes.size() + " nodes");

			for (int[] nodeLoc : lastNodes) {

				if (nodeLoc[0] == objectRow && nodeLoc[1] == objectCol) {
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

	private ArrayList<int[]> mapNearbyTiles(int row, int col, int cycle, int[][] pathMesh, UnlockedFromGrid obj) {

		System.out.println("Mapping Nearby Tiles for Row: " + row + " Col " + col);

		System.out.println("Mapping cycle: " + (cycle + 1));

		ArrayList<int[]> newTileLocs = new ArrayList<int[]>();

		if (row + 1 < pathMesh.length) {
			if (pathMesh[row + 1][col] > cycle + 1 || pathMesh[row + 1][col] == 0) {
				if (map.canPassThrough(obj, row + 1, col)) {
					pathMesh[row + 1][col] = cycle;
					newTileLocs.add(new int[] { row + 1, col });
				}
			}
		}

		if (row - 1 >= 0) {
			if (pathMesh[row - 1][col] > cycle + 1 || pathMesh[row - 1][col] == 0) {
				if (map.canPassThrough(obj, row - 1, col)) {
					pathMesh[row - 1][col] = cycle;
					newTileLocs.add(new int[] { row - 1, col });
				}
			}
		}

		if (col + 1 < pathMesh[row].length) {
			if (pathMesh[row][col + 1] > cycle + 1 || pathMesh[row][col + 1] == 0) {
				if (map.canPassThrough(obj, row, col + 1)) {
					pathMesh[row][col + 1] = cycle;
					newTileLocs.add(new int[] { row, col + 1 });
				}
			}
		}

		if (col - 1 >= 0) {
			if (pathMesh[row][col - 1] > cycle + 1 || pathMesh[row][col - 1] == 0) {
				if (map.canPassThrough(obj, row, col - 1)) {
					pathMesh[row][col - 1] = cycle;
					newTileLocs.add(new int[] { row, col - 1 });
				}
			}
		}

		return newTileLocs;

	}
}
