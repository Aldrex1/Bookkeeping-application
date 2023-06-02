/*
 * ---------------------------
 * Assignment 3   
 * Written by: Robert CHEN 40241709 and Alexandru Ilie 40248696
 * COMP 249 Section S - Winter 2023
 * 27th march  2023
 * --------------------------- */

import java.io.FileInputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;

/**
 * Part 1
 * @author Robert CHEN and Alexandru Ilie
 * @version 1.0
 *  
 */
public class Part1 {

	/**
	 * Part 1, getting rid of syntax error records in each text file
	 */
    public static void do_part1() {
    	
        //Throw try-catch method 
    	try {
			//Find the number of files
    		Scanner input = new Scanner(new FileInputStream("src/files/part1_input_file_names.txt"));

    		
			String junk = input.nextLine();
			int n = Integer.parseInt(junk);
			
			
			//Making an array that stores all the theoretical names of the files
			String[] books_file_name = new String[n];
			
			for(int i = 0; i < n; i++) {
				books_file_name[i] = input.nextLine();
			}
			
			input.close();
			
			File[] books_test = new File[n];
			
			int counter = 0;
			//books_test in this for loop is used to check if the names of the actual files are valid, if not then books_test[i] is null
			for(int i = 0; i<n; i++) {
				books_test[i] = new File("src/files/" + books_file_name[i]);
				if(!(books_test[i].exists() && books_test[i].canRead())) {
					System.out.println("the book at directory " + books_test[i] + " does not exist.");
					books_test[i] = null;
					counter++;
				}
				
			}
			
			

			BufferedReader[] books_0 = new BufferedReader[n-counter];
			File[] books_0_file_path = new File[n-counter];
			
			//Making a reader and linking each element to the correct file
			counter = 0;
			for (int i = 0; i < books_0.length; i++) {
				for(int j = 0; j < books_0.length;j++) {
					if(books_test[counter] == null) {
						counter++;
					} else {
						break;
					}
				}
				books_0[i] = new BufferedReader(new FileReader(books_test[counter].getPath()));
				books_0_file_path[i] = new File(books_test[counter].getPath());
				
				counter++;
			}
			
			
			//Checking if any files are empty
			counter = 0;
			for(int i = 0; i<books_0.length;i++) {
				books_0[i].mark(1000000);
			}
			for(int i = 0; i<books_0.length;i++) {
				if(books_0[i].readLine() == null) {
					books_0[i] = null;
					counter++;
				} else {
					continue;
				}
			}
			for(int i = 0; i<books_0.length;i++) {
				if(books_0[i] == null) {
					continue;
				}
				books_0[i].reset();
			}
			
			
			
			
			//Final readers with correct files and the files linked are not empty
			BufferedReader[] books = new BufferedReader[books_0.length-counter];
			File[] books_file_path = new File[books_0.length-counter];
			if(counter>0) {
				counter = 0;
				for(int i = 0; i<books.length; i++) {
					if(books_0[counter] == null) {
						counter++;
					}
					books[i] = books_0[counter];
					books_file_path[i] = books_0_file_path[counter]; 
					counter++;
				}
				
			} else {
				books = books_0.clone();
				books_file_path = books_0_file_path.clone();
			}
			
			//genre-based CSV-formatted output file
			PrintWriter ccb = new PrintWriter(new FileOutputStream("src/files/new files/Cartoons_Comics.csv.txt"));
			PrintWriter hcb = new PrintWriter(new FileOutputStream("src/files/new files/Hobbies_Collectibles.csv.txt"));
			PrintWriter mtv = new PrintWriter(new FileOutputStream("src/files/new files/Movies_TV_Books.csv.txt"));
			PrintWriter mrb = new PrintWriter(new FileOutputStream("src/files/new files/Music_Radio_Books.csv.txt"));
			PrintWriter neb = new PrintWriter(new FileOutputStream("src/files/new files/Nostalgia_Electic_Books.csv.txt"));
			PrintWriter otr = new PrintWriter(new FileOutputStream("src/files/new files/Old_Time_Radio_Books.csv.txt"));
			PrintWriter ssm = new PrintWriter(new FileOutputStream("src/files/new files/Sports_Sports_Memorabilia.csv.txt"));
			PrintWriter tpa = new PrintWriter(new FileOutputStream("src/files/new files/Trains_Planes_Automobiles.csv.txt"));
			PrintWriter serror = new PrintWriter(new FileOutputStream("src/files/new files/syntax_error_file.txt"));
			
			
			for(int i = 0; i<books.length;i++) {
					
				Scanner scan = new Scanner(books_file_path[i].getPath());
				int scount = 0;
				
				books[i].mark(10000000);
				
				
				while(scan.hasNextLine()) {
					
					
					String str1 = books[i].readLine();
					String[] allInfo = new String[6];
					String[] titleDif = new String [3];
					String[] test = new String[6];
					if(str1 == null) {
						break;
					}
					if(str1.charAt(0) == 34) {
						
						titleDif = str1.split("\"",3); 
						String str2 = titleDif[2];
						allInfo[0] = titleDif[1];
						test = str2.split(",",6);
						int ccount = 0;
						
						for(int j = 1; j<allInfo.length;j++) {
							allInfo[j] = test[j];
						}
						for(int j = 0; j<str2.length();j++) {
							if(str2.charAt(j) == ',') {
								ccount++;
							}
						}
						
						try {
							
							if(ccount > 5) {
								throw new TooManyFieldsException();
							} else if(ccount < 5) {
								throw new TooFewFieldsException();
							}
							
							for(int j = 0; j<allInfo.length;j++) {
									if(allInfo[j] == null || allInfo[j].equals("")) {
										throw new MissingFieldException();
									}
							}	
							
								
						}
						catch(TooManyFieldsException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(10);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							serror.println("Error: too many fields" + "\nRecord: " + str1+"\n");
							continue;
						} catch(TooFewFieldsException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(10);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							serror.println("Error: too few fields" + "\nRecord: " + str1+"\n");
							continue;
						} catch(MissingFieldException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(10);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							if(allInfo[0] == null || allInfo[0].equals("")) {
								serror.println("Error: missing title" + "\nRecord: " + str1+"\n");
							} else if(allInfo[1] == null || allInfo[1].equals("")) {
								serror.println("Error: missing author" + "\nRecord: " + str1+"\n");
							} else if(allInfo[2] == null || allInfo[2].equals("")) {
								serror.println("Error: missing price" + "\nRecord: " + str1+"\n");
							} else if(allInfo[3] == null || allInfo[3].equals("")) {
								serror.println("Error: missing isbn" + "\nRecord: " + str1+"\n");
							} else if(allInfo[4] == null || allInfo[4].equals("")) {
								serror.println("Error: missing genre" + "\nRecord: " + str1+"\n");
							} else if(allInfo[5] == null || allInfo[5].equals("")) {
								serror.println("Error: missing year" + "\nRecord: " + str1+"\n");
							} 
							continue;
						} 
						
						try {
							if(allInfo[4].equals("CCB")) {
								ccb.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("HCB")) {
								hcb.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("MTV")) {
								mtv.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("MRB")) {
								mrb.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("NEB")) {
								neb.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("OTR")) {
								otr.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("SSM")) {
								ssm.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("TPA")) {
								tpa.println(("\"" + allInfo[0] + "\"" + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else {
								throw new UnknownGenreException();
							}
						} catch(UnknownGenreException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(10);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							serror.println("Error: invalid genre" + "\nRecord: " + str1+"\n");
							continue;
						}
						
						
						
					} else {
						allInfo = str1.split(",",6);
						
						int ccount = 0;
						
						for(int j = 0; j<str1.length();j++) {
							if(str1.charAt(j) == ',') {
								ccount++;
							}
						}
						
						try {
							
							if(ccount > 5) {
								throw new TooManyFieldsException();
							} else if(ccount < 5) {
								throw new TooFewFieldsException();
							}
							for(int j = 0; j<allInfo.length;j++) {
									if(allInfo[j] == null || allInfo[j].equals("")) {
										throw new MissingFieldException();
									}
							}	
					
						}
						catch(TooManyFieldsException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(10);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							serror.println("Error: too many fields" + "\nRecord: " + str1+"\n");
							continue;
						} catch(TooFewFieldsException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(10);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							serror.println("Error: too few fields" + "\nRecord: " + str1+"\n");
							continue;
						} catch(MissingFieldException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(10);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							if(allInfo[0] == null || allInfo[0].equals("")) {
								serror.println("Error: missing title" + "\nRecord: " + str1+"\n");
							} else if(allInfo[1] == null || allInfo[1].equals("")) {
								serror.println("Error: missing author" + "\nRecord: " + str1+"\n");
							} else if(allInfo[2] == null || allInfo[2].equals("")) {
								serror.println("Error: missing price" + "\nRecord: " + str1+"\n");
							} else if(allInfo[3] == null || allInfo[3].equals("")) {
								serror.println("Error: missing isbn" + "\nRecord: " + str1+"\n");
							} else if(allInfo[4] == null || allInfo[4].equals("")) {
								serror.println("Error: missing genre" + "\nRecord: " + str1+"\n");
							} else if(allInfo[5] == null || allInfo[5].equals("")) {
								serror.println("Error: missing year" + "\nRecord: " + str1+"\n");
							} 
							continue;
						} 
						
						try {
						
							if(allInfo[4].equals("CCB")) {
								ccb.println((allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("HCB")) {
								hcb.println((allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("MTV")) {
								mtv.println((allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("MRB")) {
								mrb.println((allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("NEB")) {
								neb.println((allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("OTR")) {
								otr.println((allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("SSM")) {
								ssm.println((allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else if(allInfo[4].equals("TPA")) {
								tpa.println(( allInfo[0] + "," + allInfo[1] + "," + allInfo[2] + "," + allInfo[3] + "," + allInfo[4] + "," + allInfo[5]));
							} else {
								throw new UnknownGenreException();
							}
						} catch(UnknownGenreException e) {
							if(scount == 0) {
								String spath = books_file_path[i].getPath().substring(9);
								scount++;
								serror.println("syntax error in file: " + spath + "\n====================");
							}
							serror.println("Error: invalid genre" + "\nRecord: " + str1+"\n");
							continue;
						}
						
						
						
					}
				}
				
				books[0].reset();
				
				
				
			}
			
			ccb.close();
			hcb.close();
			mtv.close();
			mrb.close();
			neb.close();
			otr.close();
			ssm.close();
			tpa.close();
			serror.close();
			
		} 
    	catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} 

    	catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
}

/**
 * Exception class if there are more than 6 data added in a book record
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class TooManyFieldsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	TooManyFieldsException() {
		
	}
}

/**
 * Exception class if there are lessthan 6 data added in a book record
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class TooFewFieldsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	TooFewFieldsException() {
		
	}
}

/**
 * Exception class if a specific data is missing 
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class MissingFieldException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	MissingFieldException() {
		
	}
}

/**
 * Exception class if the genre is invalid according to the valid ones.
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class UnknownGenreException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	UnknownGenreException() {
		
	}
}

