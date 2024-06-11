# LakeDetection
The Radegast's Game project is an implementation of a Java-based game that involves lake detection and volume calculation. It utilizes object-oriented programming principles to create an interactive and engaging gaming experience. The project consists of two main classes: the Lake class and the Pattern class, along with the primary application class.

## Key Features
  ### Pattern Class:  
                  Responsible for managing the game board's attributes and methods.  
                  Handles the creation, updating, and printing of the game board.
                  Utilizes scientific notation for rows and columns.
                  Includes arrays for representing lakes and their adjacent volumes.
  ### Lake Class: 
                  Focuses on determining lakes' locations and calculating their volumes.
                  Contains methods for creating a volume array of the lake.
                  Implements crucial algorithms for lake detection and volume calculation using breadth-first search (BFS).
  ### Main Class:   
              Orchestrates the overall functioning of the game.
              Initializes the Pattern class and reads the game board from an input text file.
              Allows user modifications to alter the board.
              Calculates lakes' volumes using the methods from the Lake class.
              Generates the final game board and calculates the final score.
