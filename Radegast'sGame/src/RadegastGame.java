/**
 * This class is the main class of the project which is created to execute program.
 * It uses the features of the lake and pattern class and does the job.
 * First part of the class is storing the data of the input text file.
 * Second part is taking input from a user.
 * At the end of the project, it calculates lakes and gives the final score.
 * @author Cengiz Bilal Sarı
 * @since Date: 01.05.2023
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class RadegastGame {
    public static void main(String[] args) throws FileNotFoundException {
        //construct the pattern
        Pattern pattern = new Pattern();
        pattern.board = new ArrayList<>();

        //Scanner and reading file part
        String fileName = "input.txt"; // filename of the input file
        File file = new File(fileName); //  file object is required to open the file
        Scanner inputFile = new Scanner(file); // scanner object is required to read the contents of the file
        Scanner userInput = new Scanner(System.in);

        // read the first line and store row and column data in pattern's variables.
        String firstLine = inputFile.nextLine();
        String[] matrixSizes = firstLine.split(" ");
        pattern.cols = Integer.parseInt(matrixSizes[0]);
        pattern.rows = Integer.parseInt(matrixSizes[1]);


        int counterOfWhileLoop = pattern.rows; // take input until rows are ended
        int counterForScientificNotation = pattern.cols; // to create the arrayList of scientific notations

        // initialize array lists of scientific notations
        pattern.scientificNotationNumber = new ArrayList<>(pattern.rows);
        pattern.scientificNotationAlphabet = new ArrayList<>(pattern.cols);

        // create array lists of scientific notations and their unified version to control the correctness of the input
        Pattern.scientificNotation(pattern.scientificNotationAlphabet, counterForScientificNotation, 97, 122);
        for (int i = 0; i < pattern.rows; i++) {
            pattern.scientificNotationNumber.add(i);
        }
        pattern.unifiedLocations = Pattern.unifiedVersionOfBoardNotations(pattern.scientificNotationNumber, pattern.scientificNotationAlphabet);


        // read the remaining lines and store row x column matrix
        while (counterOfWhileLoop > 0) {
            String line = inputFile.nextLine();
            String[] arrayOfTheLine = line.split(" ");
            ArrayList<Integer> theRow = new ArrayList<>();
            for (String s : arrayOfTheLine)
                theRow.add(Integer.parseInt(s));
            pattern.board.add(theRow);
            counterOfWhileLoop -= 1;
        }
        inputFile.close();  // The job of the scanner is ended

        // the user has 10 modifications right to alter board. The pattern will be showed in each alteration whether input is valid or not.
        int reaminingModificationRights = 1;
        Pattern.printingTheBoard(pattern.board, pattern.scientificNotationNumber, pattern.scientificNotationAlphabet);
        while (reaminingModificationRights <= 10) {
            System.out.println("Add stone " + reaminingModificationRights + " / 10 to coordinate:");
            String commandLine = userInput.next();
            if (Pattern.willBeBoardModified(commandLine, pattern.unifiedLocations)) {
                Pattern.updatingTheBoard(pattern.board, pattern.unifiedLocations, commandLine);
                reaminingModificationRights += 1;
                Pattern.printingTheBoard(pattern.board, pattern.scientificNotationNumber, pattern.scientificNotationAlphabet);
                System.out.println("---------------");
            } else {
                System.out.println("Not a valid step!");
            }
        }

        // create the deep copy of board to change the type of the board at the end without changing first board
        ArrayList<ArrayList<Integer>> boardCopy = new ArrayList<>();
        for (int i = 0; i < pattern.board.size(); i++) {
            ArrayList<Integer> subList = new ArrayList<>();
            for (int j = 0; j < pattern.board.get(i).size(); j++) {
                subList.add(pattern.board.get(i).get(j));
            }
            boardCopy.add(subList);
        }

        pattern.maxLayerNumber = Pattern.max(pattern.board); // after board modifications is ended,  the code can assign the max number of layer.
        ArrayList<Lake> Lakes = new ArrayList<>(); // to store lakes
        // Add lakes to Lakes arrayList
        for (int i = 0; i < pattern.maxLayerNumber; i++) {
            ArrayList<ArrayList<Integer>> layer = new ArrayList<>();
            Pattern.creatingLayer(pattern.board, layer);
            Lake lake = new Lake();
            lake.locationsOfLake = lake.findAndStoreLakes(layer);
            Lakes.add(lake);
        }


        // Lakes have volume and location of adjacent volumes features
        Lake.volumes = new int[pattern.board.size()][pattern.board.get(0).size()];
        Lake.creatingVolumesArray(Lakes);
        Lake.locationsOfAdjacentVolumes = Lake.findAdjacentVolumes(Lake.volumes);
        int counterForScientificNotationOfMarkedLakes = Lake.locationsOfAdjacentVolumes.size();
        Pattern.scientificNotation(pattern.scientificNotationForMarkingLakes, counterForScientificNotationOfMarkedLakes, 65, 90);


        // there are strings in the final board, so firstly the code change the type of the content of the board to string from int
        pattern.finalBoard = pattern.changingBoardType(boardCopy);
        Pattern.finalPattern(Lake.locationsOfAdjacentVolumes, pattern.finalBoard, pattern.scientificNotationForMarkingLakes);

        // At the end of the program, print the final board and give the final score of the game for user.
        Pattern.printingTheFinalBoard(pattern.finalBoard, pattern.scientificNotationNumber, pattern.scientificNotationAlphabet);
        System.out.printf("Final score:" + "%.2f", Pattern.calculateTotalVolumesWithSqrt(Lake.volumes, Lake.locationsOfAdjacentVolumes));
    }
}