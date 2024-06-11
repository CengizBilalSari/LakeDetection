/**
 * This pattern class is mainly created for board's(matrix) features and its methods.
 * There are lots of methods to update, create ext. the board.
 * The features of the board are mainly scientific notations, MXN matrix and some different type of stored features.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 01.05.2023
 */

import java.util.ArrayList;
import java.util.Date;

public class Pattern {
    /* At the end of the code, it has to determine lakes and show them, so there is board(initial and updated board) and
    final board. There is unified version of locations to check the input whether it is contained in the board or not.
    Also, the max layer number has to be stored because while the lakes are determined, iteration has to occur "this number" times.
     */

    public ArrayList<ArrayList<Integer>> board;
    public ArrayList<ArrayList<String>> finalBoard;
    public ArrayList<String> scientificNotationAlphabet;
    public ArrayList<Integer> scientificNotationNumber;
    public ArrayList<String> scientificNotationForMarkingLakes = new ArrayList<>();
    public ArrayList<ArrayList<String>> unifiedLocations;

    public int rows;
    public int cols;
    public int maxLayerNumber;

    public Pattern() {
    }

    public static void updatingTheBoard(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<String>> boardLocation, String commandLine) {
         /* the board is updated according to the command from the user.
        This method is void method and its  parameters is board which will be updated, board location which will be tested with commandline whether in contains input or not.
         */
        int indexOfRow = 0;
        int indexOfColumn = 0;

        for (ArrayList<String> arrayList : boardLocation) {
            if (arrayList.contains(commandLine)) {
                indexOfColumn = boardLocation.indexOf(arrayList);
                indexOfRow = arrayList.indexOf(commandLine);
            }
        }
        board.get(indexOfRow).set(indexOfColumn, board.get(indexOfRow).get(indexOfColumn) + 1);
    }
    public static ArrayList<ArrayList<String>> unifiedVersionOfBoardNotations(ArrayList<Integer> rows, ArrayList<String> columns) {
         /* The input is unified version of the notation like "a3" etc. so  the code check the validity of the input with unified version.
        This method returns the unified version of the board notations for this job.
         */
        ArrayList<ArrayList<String>> theResult = new ArrayList<>();
        for (String string : columns) {
            ArrayList<String> theRows = new ArrayList<>();
            for (Integer integer : rows) {
                theRows.add(string.concat(String.valueOf(integer)));
            }
            theResult.add(theRows);
        }
        return theResult;
    }
    public static void creatingLayer(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> layer) {
        /* The main algorithm for this project is bfs algorithm.In main, this algorithm is firstly applied to each layer of  the structure.
        For this the layer has to be crated and this method does that.
        While it is doing this, the integer in specific location of the board has to decrease 1(subtract 1).
        */
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Integer> oneCellRow = new ArrayList<>();
            for (int j = 0; j < board.get(i).size(); j++) {
                Integer integer = board.get(i).get(j);
                if (integer > 0) {
                    oneCellRow.add(1);
                    board.get(i).set(j, integer - 1);
                } else if (integer == 0) {
                    oneCellRow.add(0);
                }
            }
            layer.add(oneCellRow);
        }
    }
    public static double calculateTotalVolumesWithSqrt(int[][] volumes, ArrayList<ArrayList<int[]>> locations) {
        /* At the end of the project, the number which is the sum of the square root of the volumes has to be calculated.
        This method is created for this. It traverses the lake locations and sum their square root of the volumes, and return that.
         */
        double theResult = 0;
        for (ArrayList<int[]> list : locations) {
            int sum = 0;
            for (int[] arr : list) {
                sum += volumes[arr[0]][arr[1]];
            }
            theResult += Math.pow(sum, 0.5);
        }
        return theResult;
    }
    public static int max(ArrayList<ArrayList<Integer>> board) {
        // to find the max number of the layers
        int max = 0;
        for (ArrayList<Integer> numbers : board) {
            for (Integer number : numbers) {
                if (max < number) {
                    max = number;
                }
            }
        }
        return max;
    }
    public static boolean willBeBoardModified(String commandLine, ArrayList<ArrayList<String>> boardLocations) {
        // this method checks the validity of the input
        for (ArrayList<String> arrayList : boardLocations) {
            if (arrayList.contains(commandLine))
                return true;
        }
        return false;
    }
    public static void printingTheBoard(ArrayList<ArrayList<Integer>> board, ArrayList<Integer> scientificNotationNumbers, ArrayList<String> scientificNotationAlphabet) {
        // In each iteration of the input taking, board has to be printed. This method prints the board with its scientific notations.
        int indexForNumbers = 0;
        int indexForAlphabet = 0;
        for (ArrayList<Integer> arrayList : board) {
            if (indexForNumbers < 10)
                System.out.print("  " + scientificNotationNumbers.get(indexForNumbers++) + "  ");
            else
                System.out.print(" " + scientificNotationNumbers.get(indexForNumbers++) + "  ");

            for (Integer integer : arrayList) {
                System.out.print(integer);
                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.print("     ");
        for (String string : scientificNotationAlphabet) {
            if (indexForAlphabet < 25) {
                System.out.print(string + "  ");
            } else {
                System.out.print(string + " ");
            }
            indexForAlphabet += 1;
        }
        System.out.println();
    }
    public static void scientificNotation(ArrayList<String> letters, int number, int initialAsciiNumber, int finalAsciiNumber) {
        // method for creating scientific Notation Alphabet
        if (number < 26) {
            for (int i = initialAsciiNumber; i <= initialAsciiNumber + number - 1; i++)
                letters.add(String.valueOf((char) i));
        } else {
            for (int i = initialAsciiNumber; i <= finalAsciiNumber; i++)
                letters.add(String.valueOf((char) i));
            for (int i = initialAsciiNumber; i <= finalAsciiNumber; i++) {
                for (int j = initialAsciiNumber; j <= finalAsciiNumber; j++) {
                    String s = String.valueOf((char) i).concat(String.valueOf((char) j));
                    letters.add(s);
                    if (letters.size() == number) {
                        break;
                    }
                }
                if (letters.size() == number) {
                    break;
                }
            }
        }
    }
    public static void finalPattern(ArrayList<ArrayList<int[]>> locationsOfAdjacentVolumes, ArrayList<ArrayList<String>> finalBoard, ArrayList<String> letters) {
        /* At the end of the process, the lakes has to be marked and board has to show these lakes.
        This method marks the lakes  with letters like "a,b,c,d......,aa,ab,.....zz". Lakes have different location but if the locations in the same lake,
        they have to be marked with same letter.
         */
        int indexForLetters = 0;
        for (ArrayList<int[]> ints : locationsOfAdjacentVolumes) {
            for (int[] cells : ints) {
                finalBoard.get(cells[0]).set(cells[1], letters.get(indexForLetters));
            }
            indexForLetters += 1;
        }
    }
    public ArrayList<ArrayList<String>> changingBoardType(ArrayList<ArrayList<Integer>> board) {
        // The final board has letters, so it has to have String data type inside the array list.
        // This method  creates and returns to the final board.
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (ArrayList<Integer> integers : board) {
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (Integer integer : integers) {
                stringArrayList.add(Integer.toString(integer));
            }
            result.add(stringArrayList);
        }
        return result;
    }
    public static void printingTheFinalBoard(ArrayList<ArrayList<String>> board, ArrayList<Integer> scientificNotationNumbers, ArrayList<String> scientificNotationAlphabet) {
        //This method has same logic as printing the board method, but this time it is for final board with some changes.
        int indexForNumbers = 0;
        int indexForAlphabet = 0;
        for (ArrayList<String> arrayList : board) {
            if (indexForNumbers < 10)
                System.out.print("  " + scientificNotationNumbers.get(indexForNumbers++) + "  ");
            else if (indexForNumbers >= 100) {
                System.out.print(scientificNotationNumbers.get(indexForNumbers++) + "  ");
            } else
                System.out.print(" " + scientificNotationNumbers.get(indexForNumbers++) + "  ");
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.print(arrayList.get(i));
                if (i == arrayList.size() - 1) {
                    System.out.print(" ");
                } else if (arrayList.get(i + 1).length() == 2) {
                    System.out.print(" ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.print("     ");
        for (String string : scientificNotationAlphabet) {
            if (indexForAlphabet < 25) {
                System.out.print(string + "  ");
            } else {
                System.out.print(string + " ");
            }
            indexForAlphabet += 1;
        }
        System.out.println();
    }
}