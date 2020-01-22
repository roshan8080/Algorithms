import java.util.Arrays;

/** 
	Program to test the functionality of the SlideOrJump class.
*/
public class Big0Test1
{
	public static void main(String [] args)
	{   
		
		int [] test0 = {0, 5, 75, 7, 43, 11, 12, 45, 32, 21, 45, 21, 76, 43, 23, 42, 7, 5, 20, 0, 5, 75, 7, 43, 11, 12, 45, 32, 21, 45, 21, 76, 43, 23, 42, 7, 5, 20, 0, 5, 75, 7, 43, 11, 12, 45, 32, 21, 45, 21, 76, 43, 23, 42, 7, 5, 20, 0, 5, 75, 7, 43, 11, 12, 45, 32, 21, 45, 21, 76, 43, 23, 42, 7, 5, 20};
		SlideOrJump board0;
		      
		      
	   for (int i = 1; i <= test0.length; i++)
      {
      
      int [] test1 = Arrays.copyOf(test0, i);
      board0 = new SlideOrJump(test1);
      //System.out.print("\n\nnumber of elements   "+ i); 
		//System.out.print("\tDyn Prog:  " + board0.dpCount());
      //System.out.print("\tRec Prog:  " + board0.recCount());
      System.out.println(i);
		      

      }

		
	}
}