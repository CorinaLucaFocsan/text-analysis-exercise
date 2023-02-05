package edu.nyu.cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * A program to analyze the use of verbal tics in any transcribed text.
 */
public class App {
  
  public static Scanner scn = new Scanner(System.in);

  /**
   * @param args An array of any command-line arguments.
   */
  // MAIN FUNCTION:
  public static void main(String[] args) throws Exception {

    // Get all input:
    String filepath = getFilepathFromUser(); //            Call getFilePathFromUser to know which file to open:
    
  
    String content = getContentsOfFile(filepath); //       Get contents of file:

    while(content.equals("")){ //                          If getContentsOfFile returns "", no valid file has been found, so loop.
      filepath = getFilepathFromUser();
      content = getContentsOfFile(filepath);
    }

    String[] tics = getTicsFromUser(); //                  Ask user what tics to look for and store in array


    // Analyze data:
    int[] ticCount = new int[tics.length]; //              Create an array length tics.length to keep track of occurrences
    int totalTics = 0; //                                  Create var to track total occurrences

    for(int i = 0; i <tics.length; i++){ //                Loop through tic array 
      String ticString = tics[i].toLowerCase(); //         Make case insensitive
      int count = countOccurrences(ticString, content); // Get occurrence of that particular tic
      ticCount[i] = count; //                              Store it in int array, will be used for output
      totalTics += count; //                               Add to total, will be used for calculating percentage
    
    }

    int[] ticPercentage = new int[tics.length];//          Create an array length tics.length to keep track of int %
    for(int j = 0; j<tics.length; j++){//                  Loop through ticCount array  compare to total amount of tics
      int percentage = calculatePercentage(ticCount[j], totalTics);
      ticPercentage[j] = percentage;//                     Fill in percentage array to keep track of individual %
    }

    double density = calculateTicDensity(tics, content.toLowerCase());//    Get density
    String formattedDensity = String.format("%.2f", density);//             Format to 2 decimal points

    // Present output:
    System.out.println(); 
    System.out.println("...............................Analyzing text.................................");
    System.out.println(); 
    System.out.println("Total number of tics:");
    System.out.println(totalTics);
    System.out.println("Density of tics:");
    System.out.println(formattedDensity);
    System.out.println(); 
    System.out.println("...............................Tic breakdown..................................");
    System.out.println(); 

    for(int k = 0; k < tics.length; k++){
      String tic = tics[k].toLowerCase();//                  Itirate through tics array to print one on each line
      String formattedTic = String.format("%-10s", tic);//   Format to 10 spaces
      
      String ocur = ticCount[k] + " occurrences";//          Itirate through counter array to present individual occurrence
      String formattedOcur = String.format("%-19s", ocur);// Format to 19 spaces since "/ " will make it 20

      // Print this line per tic, also including the percentages stored in the percentage array:
      System.out.println(formattedTic + "/ " + formattedOcur + "/ " + ticPercentage[k] + "% of all tics");
    }
  }

  //____________________________________________________________________________________________________________________________
  /**
   * METHOD 1:
   * getFilepathFromUser method
   * Asks the user to enter the path to the text file they want to analyze.
   */
  public static String getFilepathFromUser() {

    // Print instructions to user:
    System.out.println("What file would you like to open?");

    // Use scanner imported at the beggining of code to obtain answer:
    String filepath = scn.nextLine();

    // Return the string filepath to main function:
    return filepath;
  }
  //_______________________________________________________________________________________________________________________________
  /**
    * METHOD 2:
    * getContentsOfFile method
    * Opens the specified file and returns the text therein.
    * If the file can't be opened, print out the message, "Oh no... can't find the file!"
    * @param filename The path to a text file containing a speech transcript with verbal tics in it.
    * @return The full text in the file as a String.
    */
  public static String getContentsOfFile(String filepath) {

    String fullText = "";
    // opening up a file may fail if the file is not there, so use try/catch to protect against such errors
    try {
      // try to open the file and extract its contents
      Scanner scn = new Scanner(new File(filepath));
      while (scn.hasNextLine()) {
        String line = scn.nextLine();
        fullText += line + "\n"; // nextLine() removes line breaks, so add them back in
      }
      scn.close(); // be nice and close the Scanner
    }
    catch (FileNotFoundException e) {
      // in case we fail to open the file, output a friendly message.
      System.out.println("Oh no... can't find the file!");
    }
    return fullText; // return the full text, if fail, fullText = ""
  }
  //____________________________________________________________________________________________________________________________
  /**
   * METHOD 3:
   * getTicsFromUser method
   * Asks the user to enter a comma-separated list of tics, e.g. "uh,like, um, you know,so", and returns an array containing those tics, e.g. { "uh", "like", "um", "you know", "so" }.
   */

  public static String[] getTicsFromUser(){
    System.out.println("What words would you like to search for?");
    String tics = scn.nextLine();

    // Split string according to either ",", " ", or any combination of those two 
    String[] ticsList = tics.split("[ ,]+");
    
    // Return string array:
    return ticsList;
  }
  //____________________________________________________________________________________________________________________________

   /**
   * METHOD 4:
   * countOccurrences method
   * Counts how many times a given string (the needle) occurs within another string (the haystack), ignoring case.
   * @param needle The String to search for.
   * @param haystack The String within which to search.
   * @return THe number of occurrences of the "needle" String within the "haystack" String, ignoring case.
   */

  public static int countOccurrences(String needle, String haystack){

    // Split by space ( ), line break (\n), tab (\t), period (.), comma (,), question mark (?), or exclamation mark (!)
    String[] haystackSplit = haystack.split("[ \n\t.,?!]+");
    
    int count = 0; 
    for(int j=0; j < haystackSplit.length; j++){
      if (haystackSplit[j].toLowerCase().equals(needle)){ // Compare each lowercase word from full text to current tic
        count++; //                                          Keep track of repetitions with counter
      }
    }
      return count;
  }
  //____________________________________________________________________________________________________________________________
    /**
     * METHOD 5:
     * calculatePercentage method
     * Calculates the equivalent percentage from the proportion of one number to another number.
     * @param num1 The number to be converted to a percentage.  i.e. the numerator in the ratio of num1 to num2.
     * @param num2 The overall number out of which the num1 number is taken.  i.e. the denominator in the ratio of num1 to num2.
     * @return The percentage that rum1 represents out of the total of num2, rounded to the nearest integer.
     */

    public static int calculatePercentage(int num1, int num2){
      float percentage = num1 * 100 / num2;
      int formatPercentage = Math.round(percentage); // Use Math.round to round to nearest int
      return formatPercentage;
    }
    
  //____________________________________________________________________________________________________________________________
  /**
   * METHOD 6:
   * calculateTicDensity method
   * Calculates the "density" of tics in the text.  In other words, the proportion of tic words to the total number of words in a text.
   * @param tics An array of tic words to analyze.
   * @param fullText The full text.
   * @return The proportion of the number of tic words present in the text to the total number of words in the text, as a double.
   */
  
  public static double calculateTicDensity(String[] tics, String fulltext){

    String[] textSplit = fulltext.split("[ \n\t.,?!]+");//                                    Split again by given separators
    double count = 0.0; //                                                                    Initialize counter

    for(int i = 0; i < tics.length; i++){ //                 Nested loop to itirate both through tics array and textSplit array
      for (int j = 0; j<textSplit.length; j++){
        if (tics[i].toLowerCase().equals(textSplit[j].toLowerCase())){ //                      Case insensitivity and compare
          count++;
        }
      }
    }
    
    System.out.println(count + " " + textSplit.length);
    double density = count / textSplit.length; //                         Once we have total of tics, divide by amount of words
    return density;
  }
  
} // end of class
