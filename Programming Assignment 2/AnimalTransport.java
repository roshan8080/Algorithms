/** AnimalTransport.java
  * CSCI 3005
  * Programming Assignment 2
  * @author: Roshan Rijal
  * Due Date: 04/13/2018
  * Class that finds smallest number of containers needed to complete the shipment using graphcoloring backtracking algorithm.   
  */




//imports
import java.util.*;
import java.io.*;





// A class that finds smallest number of containers needed to complete the shipment using graphcoloring backtracking algorithm.  
public class AnimalTransport
{
   String speciesName = "";                        // variable to store species name
   String classification = "";                     // variable to store classification
   String conservationStatus = "";                 // variable to store conservation status
   int[][] matrix;                                 // 2-D array for the adjacency matrix
   int[] minContainerArray;                        // Array to hold the number of containers needed
   Map<String,String> aMap = new HashMap<>();      // A map that stores species name and classifcation
   Map<String,String> bMap = new HashMap<>();      // A map that stores species name and conservation status 
  
   /** A counstructor to read the data from a text file containing information about all species.
    @ param filename - the file which is to be read.
   */
   public AnimalTransport(String filename)
   {
      File infile = new File(filename);          // Constructing a File object 
      
      try                                       //try catch block to handle exception
      {
         Scanner input = new Scanner(infile);          // Reading from file
         while (input.hasNextLine())          // Execute only if there is next line in the given file 
         {
            Scanner eachLine = new Scanner(input.nextLine());         // Reading each line of a file
            eachLine.useDelimiter(",");                    // Spliting each word after every comma ","
            int count =0;
             
            while(eachLine.hasNext())            // Execute only if there is next word in the given line      
            {
               String val = eachLine.next();     // Traversing through each word of the line 
               if (count==0)                     // Storing first word of the line as species name           
                  speciesName = val;                     
               if (count==1)                     // Storing second word of the line as classification
                  classification = val;
               if (count==3)                     // Storing fourth word of the line as conservation status
                  conservationStatus = val;
               count++;      
            }
            aMap.put(speciesName,classification);                // Storing species name and classification as key and value
            bMap.put(speciesName,conservationStatus);            // Storing species name and conservation status as key and value
         }
         input.close();             // Closing file
      }
      catch(FileNotFoundException e)
      {
         System.out.println("File not found");
      }
   }

   /** Method to calculate the minimum number of containers needed to transport the animals in the string which is passed as argument.
       @ param animals  - the string with name of some animals of whose minimum containes required for them is to be calculated. 
       @ return - minimum number of containers.
   */
   public int minContainers(String animals)
   {
      Scanner in = new Scanner(animals);
      in.useDelimiter(",");
      ArrayList<String> arrayList = new ArrayList <String>();         // ArrayList to store each animals of the variable "animals" which is passed as argument
      matrix(animals);                 
       
      while (in.hasNext())
      {
         String data = in.next();
         arrayList.add(data);        // Adding each animals in arrayList 
      }
      
      String[] array  = new String[arrayList.size()];      // Array to store values of arrayList
      
      for (int i=0;i<arrayList.size();i++)
      {
         array[i] = arrayList.get(i);            // Adding values of arrayList to array
      }
     
      minContainerArray = new int[array.length];         // Array named "minContainerArray" to store number of containers needed   
      mColoring(-1,minContainerArray);          // Calling mColoring method passing -1 and minContainerArray as arguments
      int n = minContainerArray[0];
      int count = 1;
      String k ="";
      Set<Integer> aSet = new HashSet<>();        // Set to add the list of animals that can be carried in a single container
      for(int i = 0;i<minContainerArray.length;i++)
      {
         aSet.add(minContainerArray[i]);
      
      }      
           
      return aSet.size();            // return number of containers needed 
   }
  
   /** Method to determine ways to in which animals can be addded.
       @ param i  - int value representing index.
       @ param container - container array which store list of animals that can be carried in a single container 
   */
   public void mColoring(int i,int[] container)
   {
      int color;
      if(promising(i,container))         
      {
         if(i == container.length-1)
         {
            minContainerArray = Arrays.copyOf(container,container.length);          // Copy values of container array into a new array named "minContainerArray"
         }
         else
         {
            for(color = 0;color<container.length;color++)             
            {
               container[i+1]=color;
               mColoring(i+1,container);
            } 
         }
      } 
   }
    
   /** Method to determine if a animal can be added in the current container.
       @ param i  - int value representing index.
       @ param container - container array which store list of animals that can be carried in a single container  
       @ return - true if animal can be added in the container and false if it cannot be added in the container.
   */
   public boolean promising(int i, int[] container)
   {
      int j = 0;
      boolean flag = true;
      while(j<i && flag)            
      {
         if(matrix[i][j]==1 && container[i]==container[j])
         {
            flag = false;
         }
         j++;
      }
      return flag; 
   }   
   
   /** Method to determine if the animal can be added to container by creating adjacence matrix.
       @ param animals  - the string with name of some animals of whose minimum containes required for them is to be calculated. 
       @ return - matrix value.
   */
   public int[][] matrix(String animals)
   { 
      Scanner in = new Scanner(animals);
      in.useDelimiter(",");
     
      ArrayList<String> arrayList = new ArrayList <String>();
      
      while (in.hasNext())
      {
         String data = in.next();
         arrayList.add(data);
      }
      
      String[] array  = new String[arrayList.size()];
      
      for (int i=0;i<arrayList.size();i++)
      {
         array[i] = arrayList.get(i);
      }
      
      matrix = new int[array.length][array.length];              // 2-D matrix of length of array named "array" 
      int n = 0;
      while(in.hasNext())
      {
         array[n] = in.next();
         n++;    
      }      
       
      for (int row=0;row<array.length;row++)                  
      {
         for (int column=0;column<array.length;column++)
         {
            if (bMap.get(array[row]).equals("en") || bMap.get(array[column]).equals("en"))
            {
               matrix[row][column] = 1;                 // Cannot be added with other animals 
            }
            else
            {
               if (aMap.get(array[row]).equals("carnivore"))
               {     
                  if (!(bMap.get(array[column]).equals("do"))|| !(bMap.get(array[row]).equals("do")))
                  {
                     if (aMap.get(array[column]).equals("herbivore"))
                     {
                        matrix[row][column] = 1;               // cannot be added to same container if both animals are carbivore and herbivore unless they are domesticated. 
                     }
                  }
                  if (bMap.get(array[column]).equals("vu") || bMap.get(array[column]).equals("nt"))
                  {
                     matrix[row][column] = 1;               // cannot be added near threatened or vulnerarble categories with other animals
                  } 
               }
               else if (aMap.get(array[row]).equals("herbivore"))
               {
                  if(!(bMap.get(array[column]).equals("do")) || !(bMap.get(array[row]).equals("do")))
                  {
                     if(aMap.get(array[column]).equals("carnivore"))
                     {
                     
                        matrix[row][column] = 1;          // // cannot be added to same container if both animals are herbivore and carnivore unless they are domesticated. 
                     }
                  }
               }     
            }         
         }
      }  
      return matrix;
   }
   
   /** Method that determines listings of animals in each container.
       @ param animals  - the string with name of some animals of whose listings is to be found. 
       @ return - sample listings of animals in each container.
   */
   public String getListing(String animals)
   {
      Scanner in = new Scanner(animals);
      in.useDelimiter(",");
     
      ArrayList<String> arrayList = new ArrayList <String>();
   
      while (in.hasNext())
      {
         String data = in.next();
         arrayList.add(data);
      }
      
      String[] array  = new String[arrayList.size()];
      
      for (int i=0;i<arrayList.size();i++)
      {
         array[i] = arrayList.get(i);
      }
   
      Map<String,Integer> cMap = new HashMap<>();       // a Map that determines in which container the respective animals should be transported  
      for(int i = 0;i<array.length;i++)
      {
         cMap.put(array[i],minContainerArray[i]);      
      } 
      
      Set<Integer> bSet = new HashSet<>();             // Set to determine which animals goes to which container
      
      for(int i = 0;i<minContainerArray.length;i++)
      {
         bSet.add(minContainerArray[i]);
      }
      
      String result="";        
      Iterator<Integer> iter = bSet.iterator();      // Iterator to travesre through set named "bSet"
      int count = 1;
      System.out.println();
      
      while(iter.hasNext())
      {
         String r="";
         int item = iter.next();
         for(int i = 0;i<array.length;i++)
         {
            if(cMap.get(array[i])==item)
            {
               r+=array[i]+"    ";
            }
         }
         result+="Container "+count+ ": "+r+"\n";
         count++;  
      }
      return result;
   }
}