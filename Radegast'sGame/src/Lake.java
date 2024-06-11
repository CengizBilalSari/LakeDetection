/**
 * This lake class is mainly created to determine lakes and  form their features.
 * The main aim of the project is to determine lakes' locations and their volumes,
 * so  Lake class has  2D volumes array,  array list for locations of lakes and array list for locations of adjacent volumes.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 01.05.2023
 */

import java.util.ArrayList;

public class Lake {
    /* This class has 3 methods. First of them just create volume array of the lake.
    The other two methods are doing the most critical job in this project. They are basically created with breadth first search algorithm.
    Firstly,the code  in main finds the lakes in one layer and with the last method,it finds lakes with all layers.
    It returns their volumes' matrix which is the crucial array list for the remaining part.
     */
    public ArrayList<ArrayList<int[]>> locationsOfLake;

    public static int[][] volumes;

    public static ArrayList<ArrayList<int[]>> locationsOfAdjacentVolumes;

    Lake() {
    }
    public static void creatingVolumesArray(ArrayList<Lake> lakeArrays) {
        // just to create volume array
        for (Lake lake : lakeArrays) {
            for (ArrayList<int[]> partsOfLake : lake.locationsOfLake) {
                for (int[] cell : partsOfLake) {
                    Lake.volumes[cell[0]][cell[1]] += 1;
                }
            }
        }
    }
    /*This method is used to find lakes in just one layer. Matrix layer has just 0 and 1 because it is the one layer of the all 3D object.
   The method utilizes breadth first search algorithm.
   Directions are used to check neighbour cells. If neighbour cell is 0 too, it is added to queue and the method controls its neighbours too.
   If the location is visited, it is marked in visited boolean 2D array.
   When it finds the all locations of one lake, it adds it to lakes array list, but there is a significant condition for that.
   If one of the location is in the boundary of the matrix, it cannot be lake, so it does not add it to lakes array list.
   At the end of the method , it returns to lakes array list.
   */
    public ArrayList<ArrayList<int[]>> findAndStoreLakes(ArrayList<ArrayList<Integer>> matrixLayer) {
        int row = matrixLayer.size();
        int col = matrixLayer.get(0).size();
        boolean[][] visited = new boolean[row][col];
        ArrayList<ArrayList<int[]>> lakes = new ArrayList<>();
        int[][] directions = {{-1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 0}, {1, 1}, {1, 0}, {0, 1}}; // diagonals,up-down,left-right
        // Iteration through each location in the matrix
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // If the cell is zero and has not been visited, it's part of a new lake
                if (matrixLayer.get(i).get(j) == 0 && !visited[i][j]) {
                    ArrayList<int[]> lake = new ArrayList<>();
                    ArrayList<int[]> queue = new ArrayList<>();
                    queue.add(new int[]{i, j}); // add the indexes of the cell into queue
                    visited[i][j] = true;
                    // search the lake using Breadth First Search Algorithm
                    int initial = 0;
                    while (initial < queue.size()) {
                        int[] currentLocation = queue.get(initial);
                        lake.add(currentLocation);
                        int currentRow = currentLocation[0];
                        int currentColumn = currentLocation[1];
                        // check neighbour locations
                        for (int[] direction : directions) {
                            if (currentRow + direction[0] >= 0 && currentRow + direction[0] < row && currentColumn + direction[1] >= 0 && currentColumn + direction[1] < col) {
                                int neighborRow = currentRow + direction[0];
                                int neighborColumn = currentColumn + direction[1];
                                // check the conditions of the adjacent locations
                                if (matrixLayer.get(neighborRow).get(neighborColumn) == 0 && !visited[neighborRow][neighborColumn] && neighborRow >= 0 && neighborRow < row && neighborColumn >= 0 && neighborColumn < col) {
                                    queue.add(new int[]{neighborRow, neighborColumn});
                                    visited[neighborRow][neighborColumn] = true;
                                }
                            }
                        }
                        initial += 1;
                    }
                    // control the condition whether there is boundary cell in the lake, if there is, it means that there is no lake.
                    boolean isThereExit = false;
                    for (int[] ints : lake) {
                        if (ints[0] == 0 || ints[0] == row - 1 || ints[1] == 0 || ints[1] == col - 1) {
                            isThereExit = true;
                            break;
                        }
                    }
                    if (!isThereExit)
                        lakes.add(lake);
                }
            }
        }
        return lakes;
    }

    /* This method has same logic as "find and store lakes" method, but this time it checks volumes matrix of the lakes. It means that it is not just one layer of lake. It is lake of 3D shape.
    If the volume is zero, it means that there is not lake in this location. If the cell is not zero, it should check its neighbour cells
    and other things is same as bfs. Also, this time it does not check boundaries, because the volumes is already created with the conditions of lakes.
    At the end of the method, it returns to array list of lakes' locations.
     */
    public static ArrayList<ArrayList<int[]>> findAdjacentVolumes(int[][] volumes) {
        int row = volumes.length;
        int col = volumes[0].length;
        boolean[][] visited = new boolean[row][col];
        ArrayList<ArrayList<int[]>> lakes = new ArrayList<>();
        int[][] directions = {{-1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 0}, {1, 1}, {1, 0}, {0, 1}}; // diagonals,up-down,left-right

        // Iteration through each location in the matrix
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // If the cell is not zero and has not been visited, it's part of a lake.
                if (volumes[i][j] != 0 && !visited[i][j]) {
                    ArrayList<int[]> lake = new ArrayList<>();
                    ArrayList<int[]> queue = new ArrayList<>();
                    queue.add(new int[]{i, j}); // add the indexes of the cell into queue
                    visited[i][j] = true;
                    // traverse the lake using Breadth First Search Algorithm
                    int initial = 0;
                    while (initial < queue.size()) {
                        int[] currentLocation = queue.get(initial);
                        lake.add(currentLocation);
                        int currentRow = currentLocation[0];
                        int currentColumn = currentLocation[1];
                        // Check neighbour locations
                        for (int[] direction : directions) {
                            if (currentRow + direction[0] >= 0 && currentRow + direction[0] < row && currentColumn + direction[1] >= 0 && currentColumn + direction[1] < col) {
                                int neighborRow = currentRow + direction[0];
                                int neighborColumn = currentColumn + direction[1];
                                // check the conditions of the adjacent locations
                                if (volumes[neighborRow][neighborColumn] != 0 && !visited[neighborRow][neighborColumn] && neighborRow >= 0 && neighborRow < row && neighborColumn >= 0 && neighborColumn < col) {
                                    queue.add(new int[]{neighborRow, neighborColumn});
                                    visited[neighborRow][neighborColumn] = true;
                                }
                            }
                        }
                        initial += 1;
                    }
                    lakes.add(lake);
                }
            }
        }
        return lakes;
    }
}
