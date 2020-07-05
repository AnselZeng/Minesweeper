/**********************************************************
   File: Minesweeper.java
	Purpose: To clear a board full of hidden mines with the
            help of "number" (coloured pegs).
	Author: Ansel Zeng
	Date: June 24, 2019
	Based on: ICS3U Final Project
   
   *When user clicks on an empty square, a method will check
   and expose the 8 squares surrounding empty square (that
   the user had clicked on). If more empty squares were opened
   simply click on more empty squares to expose even more!*
   
**********************************************************/

// Imports
import java.util.Scanner;
import javax.swing.*;

// Creating the minesweeper class
class Minesweeper
{
   /*
      ------------
      Declarations
      ------------
   */
   
   static boolean gameOver = false;
   
   /*
      -------------------------------
      Method to output a window for
      user to choose which difficulty
      -------------------------------
   */
   
   public static int chooseDifficulty()
   {
      // Outputs the window with options/difficulties
      Object[] options = {"Custom", "Expert", "Intermediate", "Beginner"};
      int option = JOptionPane.showOptionDialog(null,
         "Choose a difficulty:", "Welcome to Minesweeper",
         JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
         null, options, null);
      
      // Returns the difficulty choice made by user to main
      return option;
   }
   
   /*
      --------------------------
      Method to set up the board
      --------------------------
   */
   
   static void setUpBoard(String difficulty, int numOfRows, int numOfCols, int numOfMines)
   {
      // Declaring an array of strings to store the the position of pegs
      String[][] piecePosition = new String[numOfRows][numOfCols];
      
      // Declarating an array of booleans to store if it's a covered peg
      Boolean[][] checkGameWin = new Boolean[numOfRows][numOfCols];
      
      // Creating the minesweeper gameboard
      Board gameBoard = new Board(numOfRows, numOfCols);
      
      // Outputing the board's difficulty and number of mines
      gameBoard.displayMessage(difficulty + " Board - " + numOfMines + " Mines");
      
      // Filling every square with empty to start off with
      for (int i = 0; i < numOfRows; i++)
      {
         for (int k = 0; k < numOfCols; k++)
         {
            piecePosition[i][k] = "empty";
            
            // Filling the board with black pegs to cover everything
            gameBoard.putPeg("black", i, k);
            
            // Making every square "false" (basically has a black peg on it)
            checkGameWin[i][k] = false;
         }
      }
      
      // Calling a method to generate random mines
      minesGenerator(numOfMines, gameBoard, piecePosition, numOfRows, numOfCols);
      
      // Calling a method to calculate the "numbers" around the mines
      numbersGenerator(gameBoard, piecePosition, numOfRows, numOfCols);
      
      // Calling a method to get player input
      getPlayerClick(gameBoard, piecePosition, numOfRows, numOfCols, numOfMines, checkGameWin);
   }
   
   /*
      ---------------------------------
      Method to randomly generate mines
      ---------------------------------
   */
   
   static void minesGenerator (int numOfMines, Board gameBoard, String[][] piecePosition, int maxRow, int maxCol)
   {
      int counter = 0;
      while (counter < numOfMines)
      {
         int randomRow = (int)Math.round(Math.random()*(maxRow-1));
         int randomCol = (int)Math.round(Math.random()*(maxCol-1));
         if (piecePosition[randomRow][randomCol] == "empty")
         {
            piecePosition[randomRow][randomCol] = "mine";
            counter++;
         }
      }
   }
   
   /*
      -----------------------------------------
      Method to generate "numbers" around mines
      -----------------------------------------
   */
   
   static void numbersGenerator (Board gameBoard, String[][] piecePosition, int maxRow, int maxCol)
   {
      for (int i = 0; i < maxRow; i++)
      {
         for (int k = 0; k < maxCol; k++)
         {
            int mineCount = 0;
            // Checking if it is a mine
            if (piecePosition[i][k] == "mine")
            {
               piecePosition[i][k] = "mine";
            }
            else
            {
               // Checking top left corner
               if (i == 0 && k == 0)
               {
                  if (piecePosition[i][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               // Checking bottom left corner
               else if (i == (maxRow-1) && k == 0)
               {
                  if (piecePosition[i-1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               // Checking top right corner
               else if (i == 0 && k == (maxCol-1))
               {
                  if (piecePosition[i][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               // Checking bottom right corner
               else if (i == (maxRow-1) && k == (maxCol-1))
               {
                  if (piecePosition[i][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               // Checking top row
               else if (i == 0)
               {
                  if (piecePosition[i][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               // Checking bottom row
               else if (i == (maxRow-1))
               {
                  if (piecePosition[i][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               // Checking left column
               else if (k == 0)
               {
                  if (piecePosition[i-1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               // Checking right column
               else if (k == (maxCol-1))
               {
                  if (piecePosition[i+1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               else
               {
                  if (piecePosition[i+1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i+1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k-1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k] == "mine") {
                     mineCount = (mineCount + 1);
                  }
                  if (piecePosition[i-1][k+1] == "mine") {
                     mineCount = (mineCount + 1);
                  }
               }
               outputNumberGenerated(gameBoard, mineCount, piecePosition, i, k);
            }
         }
      }
   }
   
   /*
      -------------------------------------------------------
      Method to output the "numbers" generated onto the board
      -------------------------------------------------------
   */
   
   static void outputNumberGenerated (Board gameBoard, int mineCount, String[][] piecePosition, int i, int k)
   {
      switch (mineCount) {
         case 1: {
            piecePosition[i][k] = "white";
            break;
         }
         case 2: {
            piecePosition[i][k] = "yellow";
            break;
         }
         case 3: {
            piecePosition[i][k] = "orange";
            break;
         }
         case 4: {
            piecePosition[i][k] = "red";
            break;
         }
         case 5: {
            piecePosition[i][k] = "pink";
            break;
         }
         case 6: {
            piecePosition[i][k] = "cyan";
            break;
         }
         case 7: {
            piecePosition[i][k] = "blue";
            break;
         }
         case 8: {
            piecePosition[i][k] = "green";
         }
      }
   }
   
   /*
      -----------------------------------------
      Method to get a player's click position
      -----------------------------------------
   */
   
   static void getPlayerClick (Board gameBoard, String[][] piecePosition, int maxRow, int maxCol, int numOfMines, Boolean[][] checkGameWin)
   {
      Coordinate clickSpot;
      while (!gameOver)
      {
         clickSpot = gameBoard.getClick();
         int row = clickSpot.getRow();
         int col = clickSpot.getCol();
         if (piecePosition[row][col] == "mine")
         {
            for (int i = 0; i < maxRow; i++)
            {
               for (int k = 0; k < maxCol; k++)
               {
                  if (piecePosition[i][k] == "mine")
                  {
                     gameBoard.removePeg(i, k);
                     gameBoard.putPeg("green", i, k);
                  }
               }
            }
            gameBoard.displayMessage("Boom! You died, the end.");
            gameOver = true;
         }
         else if (checkGameWin(checkGameWin, maxRow, maxCol, numOfMines) == true)
         {
            gameBoard.displayMessage("Yay you win!");
            gameOver = true;
         }
         else
         {
            // If user clicked on a "number" peg
            if (piecePosition[row][col] != "empty")
            {
               placeDownNumberPeg(gameBoard, piecePosition, row, col);
            }
            
            // Guaranteed to only check empty squares clicked
            else
            {
               // Checking top left corner
               if (row == 0 && col == 0)
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col+1);
                  checkGameWin[row][col+1] = true;
                  gameBoard.removePeg(row+1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col+1);
                  checkGameWin[row+1][col+1] = true;
                  gameBoard.removePeg(row+1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col);
                  checkGameWin[row+1][col] = true;
               }
               // Checking bottom left corner
               else if (row == (maxRow-1) && col == 0)
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row-1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col);
                  checkGameWin[row-1][col] = true;
                  gameBoard.removePeg(row-1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col+1);
                  checkGameWin[row-1][col+1] = true;
                  gameBoard.removePeg(row, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col+1);
                  checkGameWin[row][col+1] = true;
               }
               // Checking top right corner
               else if (row == 0 && col == (maxCol-1))
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col-1);
                  checkGameWin[row][col-1] = true;
                  gameBoard.removePeg(row+1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col-1);
                  checkGameWin[row+1][col-1] = true;
                  gameBoard.removePeg(row+1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col);
                  checkGameWin[row+1][col] = true;
               }
               // Checking bottom right corner
               else if (row == (maxRow-1) && col == (maxCol-1))
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col-1);
                  checkGameWin[row][col-1] = true;
                  gameBoard.removePeg(row-1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col-1);
                  checkGameWin[row-1][col-1] = true;
                  gameBoard.removePeg(row-1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col);
                  checkGameWin[row-1][col] = true;
               }
               // Checking top row
               else if (row == 0)
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col-1);
                  checkGameWin[row][col-1] = true;
                  gameBoard.removePeg(row+1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col-1);
                  checkGameWin[row+1][col-1] = true;
                  gameBoard.removePeg(row+1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col);
                  checkGameWin[row+1][col] = true;
                  gameBoard.removePeg(row+1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col+1);
                  checkGameWin[row+1][col+1] = true;
                  gameBoard.removePeg(row, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col+1);
                  checkGameWin[row][col+1] = true;
               }
               // Checking bottom row
               else if (row == (maxRow-1))
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col-1);
                  checkGameWin[row][col-1] = true;
                  gameBoard.removePeg(row-1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col-1);
                  checkGameWin[row-1][col-1] = true;
                  gameBoard.removePeg(row-1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col);
                  checkGameWin[row-1][col] = true;
                  gameBoard.removePeg(row-1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col+1);
                  checkGameWin[row-1][col] = true;
                  gameBoard.removePeg(row, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col+1);
                  checkGameWin[row][col+1] = true;
               }
               // Checking left column
               else if (col == 0)
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row-1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col);
                  checkGameWin[row-1][col] = true;
                  gameBoard.removePeg(row-1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col+1);
                  checkGameWin[row-1][col+1] = true;
                  gameBoard.removePeg(row, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col+1);
                  checkGameWin[row][col+1] = true;
                  gameBoard.removePeg(row+1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col+1);
                  checkGameWin[row+1][col+1] = true;
                  gameBoard.removePeg(row+1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col);
                  checkGameWin[row+1][col] = true;
               }
               // Checking right column
               else if (col == (maxCol-1))
               {
                  gameBoard.removePeg(row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row+1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col);
                  checkGameWin[row+1][col] = true;
                  gameBoard.removePeg(row+1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col-1);
                  checkGameWin[row+1][col-1] = true;
                  gameBoard.removePeg(row, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col-1);
                  checkGameWin[row][col-1] = true;
                  gameBoard.removePeg(row-1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col-1);
                  checkGameWin[row-1][col-1] = true;
                  gameBoard.removePeg(row-1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col);
                  checkGameWin[row-1][col] = true;
               }
               else
               {
                  gameBoard.removePeg(row, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col);
                  checkGameWin[row][col] = true;
                  gameBoard.removePeg(row+1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col-1);
                  checkGameWin[row+1][col-1] = true;
                  gameBoard.removePeg(row+1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col);
                  checkGameWin[row+1][col] = true;
                  gameBoard.removePeg(row+1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row+1, col+1);
                  checkGameWin[row+1][col+1] = true;
                  gameBoard.removePeg(row, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col-1);
                  checkGameWin[row][col-1] = true;
                  gameBoard.removePeg(row, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row, col+1);
                  checkGameWin[row][col+1] = true;
                  gameBoard.removePeg(row-1, col-1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col-1);
                  checkGameWin[row-1][col-1] = true;
                  gameBoard.removePeg(row-1, col);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col);
                  checkGameWin[row-1][col] = true;
                  gameBoard.removePeg(row-1, col+1);
                  placeDownNumberPeg(gameBoard, piecePosition, row-1, col+1);
                  checkGameWin[row-1][col+1] = true;
               }
            }
         }
      }
   }
   
   /*
      -----------------------------------
      Method to place down a "number" peg
      -----------------------------------
   */
   
   static void placeDownNumberPeg (Board gameBoard, String[][] piecePosition, int row, int col)
   {
      if (piecePosition[row][col] == "white") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("white", row, col);
      }
      else if (piecePosition[row][col] == "yellow") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("yellow", row, col);
      }
      else if (piecePosition[row][col] == "orange") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("orange", row, col);
      }
      else if (piecePosition[row][col] == "red") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("red", row, col);
      }
      else if (piecePosition[row][col] == "pink") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("pink", row, col);
      }
      else if (piecePosition[row][col] == "cyan") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("cyan", row, col);
      }
      else if (piecePosition[row][col] == "blue") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("blue", row, col);
      }
      else if (piecePosition[row][col] == "green") {
         gameBoard.removePeg(row, col);
         gameBoard.putPeg("green", row, col);
      }
   }
   
   /*
      ---------------------------------
      Method to check if the player won
      ---------------------------------
   */
   
   static boolean checkGameWin (Boolean[][] checkGameWin, int numOfRows, int numOfCols, int numOfMines)
   {
      int counter = 0;
      for (int i = 0; i < numOfRows; i++)
      {
         for (int k = 0; k < numOfCols; k++)
         {
            if (checkGameWin[i][k] == true)
            {
               counter = counter + 1;
            }
         }
      }
      if (counter == numOfRows*numOfCols - numOfMines)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   /*
      ----
      Main
      ----
   */
   
   public static void main (String[] args)
   {
      // gameBoardBeginner.f
      
      // User chooses a difficulty
      int option = chooseDifficulty();
      
      if (option == 3)
      {
         // Calls the method to set up the beginner board
         setUpBoard("Beginner", 9, 9, 10);
      }
      else if (option == 2)
      {
         // Calls the method to set up the intermediate board
         setUpBoard("Intermediate", 16, 16, 40);
      }
      else if (option == 1)
      {
         // Calls the method to set up the expert board
         setUpBoard("Expert", 16, 30, 99);
      }
      else
      {
         // User enters number of rows
         int numOfRows = 1;
         while (numOfRows < 2)
         {
            String enterNumOfRows = JOptionPane.showInputDialog("Enter the number of rows:");
            Scanner scanNumOfRows = new Scanner(enterNumOfRows);
            numOfRows = scanNumOfRows.nextInt();
         }
         // User enters number of columns
         int numOfCols = 1;
         while (numOfCols < 2)
         {
            String enterNumOfCols = JOptionPane.showInputDialog("Enter the number of columns:");
            Scanner scanNumOfCols = new Scanner(enterNumOfCols);
            numOfCols = scanNumOfCols.nextInt();
         }
         // User enters number of mines
         int numOfMines = 0;
         while (numOfMines < 1 || numOfMines > (numOfRows*numOfCols - 1))
         {
            String enterNumOfMines = JOptionPane.showInputDialog("Enter the number of mines:");
            Scanner scanNumOfMines = new Scanner(enterNumOfMines);
            numOfMines = scanNumOfMines.nextInt();
         }
         
         // Calls the method to set up the custom board
         setUpBoard("Custom", numOfRows, numOfCols, numOfMines);
      }
      
      System.out.print("Thanks for playing!");
   }
}
