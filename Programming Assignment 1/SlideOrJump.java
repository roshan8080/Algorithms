/** SlideOrJump.java
  * CSCI 3005
  * Programming Assignment 1
  * @author: Roshan Rijal
  * Due Date: 03/09/2018
  * Class that determines optimal sequence of moves.   
  */



public class SlideOrJump
{
   long totalCost = 0;        // variable to store totalcost to move from one end to another end
   private int[] board;       // board array that holds cost of moving from one end to another end 
   int cell = 0;              // variable to store the index value in the array
  
     
   /** A counstructor to initialize array board.
       @ param board - An array that holds the cost of moving from one end to another end.
   */    
   public SlideOrJump(int[] board)
   {
      this.board = board;
   }
   
   
   // Method which calls another method named recursion() passing cell value 0
   public long recSolution()
   {        
      return recursion(0);
   }
  
  
   /** Method to calulate the cost using recursion approach.
       @ param cell  - index value which determines its position in array. 
       @ return - total cost of movement.
   */
   public long recursion(int cell)
   {  
      int arrayLength = board.length;                              // value to store length of board array
      
      // base cases
      if (cell == arrayLength - 1)                                // if size of board array is 1 
         return board[arrayLength - 1];
      else if (cell == arrayLength -2)                            // if size of board array is 2
         return board[arrayLength -2] + board[arrayLength - 1];
      else if (cell == arrayLength - 3)                          // if size of board array is 3
         return board[arrayLength -3] + board[arrayLength -1];    
         
      // recursive case
      else                               
      {  
         if (board[cell+1] < board[cell+2])                      // if preceding value is minimum   
            return board[cell] + recursion(cell+1);              
         else                                                    // if following value is minimum 
            return board[cell] + recursion(cell+2);
      }
   }
   
  
   /** Method to calculate the cost using dynamic approach. 
       @ return - total cost of movement.
   */   
   public long dpSolution()
   {
      int cell = 0;                                // index value in board array
      int arrayLength = board.length;              // value to store length of board array
      int[] board2 = new int[arrayLength - 1];    // new array to store the cost without recalculating partial results as in recSolution()
      int index = 0;                              // index value in board2 array
      board2[index] = 0;                          // intializing first value in board2 array as 0 for dynamic approach
      int count = 0;                              // variable to count the loop iterartions
      while (cell < arrayLength -3)               // executes only when length of board array is more than 3
      {     
         count++;
         if (board[cell+1] < board[cell+2])       // comapares two adjacent array values and executes only if preceding value is minimum
         {  
            if (index == 0)                       // executes only when loops happens for the first time to avoid ArrayIndexOutOfBoundsException   
               board2[index] = board[cell]+ board[cell+1];       
            else
               board2[index] = board2[index-1] + board[cell+1];    // Adding previous cost with the following cost and storing it in array 
            cell++;
            index++;                               
         }
         else
         {  
            if (index == 0)
               board2[index] = board[cell] + board[cell+2];
            else
               board2[index] = board2[index-1] + board[cell+2]; 
            cell += 2;                                  // increasing value of cell by 2 because of Jump 
            index++;
         }
      }
      if (cell == arrayLength -3)                     // if size of board array is 3 or when the position of cell in array reaches to third from the last 
      {   
         count++;
         if (index == 0)                              // if there is no previous value in board2 array
            board2[index] = board[arrayLength-3] + board[arrayLength-1];   
         else                                        // if there is previous value in board2 array i.e it has gone through previous while loop condition 
            board2[index] = board2[index-1] + board[arrayLength -1];
      } 
      else if (cell == arrayLength - 2)               // if size of board array is 2
      {  
         count++;
         if (index == 0)
            board2[index] = board[arrayLength-2] + board[arrayLength-1];   
         else
            board2[index] = board2[index-1] + board[arrayLength - 1];
      }
      else if (cell == arrayLength - 1)               // if size of board array is 1
      {
         count++;  
         board2[index] = board[arrayLength - 1];               
      }
      System.out.println("Number of loop iterations: " + count);
      return board2[index];                         // return the cost referred by index variable 
   }  
    
   
   /** Method to calculate the cost using dynamic approach. 
       @ return - strings that determine whether sequence of movement i.e whether it is slide or jump.
   */
   public String getMoves()
   {
      int arrayLength = board.length;
      String moves = "";                         // variable to store sequence of movement
      int cell = 0;
      while (cell < arrayLength -3)              // 
      {
         if (board[cell+1] < board[cell+2])      // if preceding value in array is minimun
         {
            moves += "S";
            cell++;
         }        
         else                                    // if following value in array is minimum
         {
            moves += "J";
            cell += 2;                           // incrementing cell value by 2 since it skips one cell
         }
      }
      if (cell == arrayLength -3)
      {
         moves += "J";
         cell += 2;
      }
      else if (cell == arrayLength -2)
      {
         moves += "S";
         cell++;
      } 
      else if (cell == arrayLength -1)           // performs no movement if there is only one value in array   
      {
         moves += "";
      }
      return moves;                           
   }
}