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
import java.io.IOException;
import java.io.File;
import java.io.EOFException;
import java.io.ObjectInputStream;
/**
 * Part 3
 * @author Robert CHEN and Alexandru Ilie
 * @version 1.0
 *
 */
public class Part3 {
	/**
	 * Part 3 that displays an interactive menu and allows the user to select and view records from each file
	 */
	public static void do_part3() {
		
		try {
			FileInputStream[] fis = new FileInputStream[8];
			File [] files = new File[8];
			ObjectInputStream[] ois = new ObjectInputStream[8];
			
			//File wrapper object
			files[0]=  new File("src/files/filespart2/Cartoons_Comics.csv.txt");
			files[1] = new File("src/files/filespart2/Hobbies_Collectibles.csv.txt");
			files[2] = new File("src/files/filespart2/Movies_TV_Books.csv.txt");
			files[3] = new File("src/files/filespart2/Music_Radio_Books.csv.txt");
			files[4] = new File("src/files/filespart2/Nostalgia_Electic_Books.csv.txt");
			files[5] = new File("src/files/filespart2/Old_Time_Radio_Books.csv.txt");
			files[6] = new File("src/files/filespart2/Sports_Sports_Memorabilia.csv.txt");
			files[7] = new File("src/files/filespart2/Trains_Planes_Automobiles.csv.txt");
			
			//FileInputStream
			fis[0] = new FileInputStream("src/files/filespart2/Cartoons_Comics.csv.ser.txt");
			fis[1] = new FileInputStream("src/files/filespart2/Hobbies_Collectibles.csv.ser.txt");
			fis[2] = new FileInputStream("src/files/filespart2/Movies_TV_Books.csv.ser.txt");
			fis[3] = new FileInputStream("src/files/filespart2/Music_Radio_Books.csv.ser.txt");
			fis[4] = new FileInputStream("src/files/filespart2/Nostalgia_Electic_Books.csv.ser.txt");
			fis[5] = new FileInputStream("src/files/filespart2/Old_Time_Radio_Books.csv.ser.txt");
			fis[6] = new FileInputStream("src/files/filespart2/Sports_Sports_Memorabilia.csv.ser.txt");
			fis[7] = new FileInputStream("src/files/filespart2/Trains_Planes_Automobiles.csv.ser.txt");
			
			//ObjectInputStream to read
			ois[0] = new ObjectInputStream(fis[0]);
			ois[1] = new ObjectInputStream(fis[1]);
			ois[2] = new ObjectInputStream(fis[2]);
			ois[3] = new ObjectInputStream(fis[3]);
			ois[4] = new ObjectInputStream(fis[4]);
			ois[5] = new ObjectInputStream(fis[5]);
			ois[6] = new ObjectInputStream(fis[6]);
			ois[7] = new ObjectInputStream(fis[7]);
			
			int[] objectCounter = new int[8];
			//Counting the number of objects
			for(int i = 0; i< 8;i++) {
				Book junk = null;
				while(fis[i].available()>0) {
					junk = (Book) ois[i].readObject();
					objectCounter[i]++;					
				}
			}
			//resetting the ois
			fis[0] = new FileInputStream("src/files/filespart2/Cartoons_Comics.csv.ser.txt");
			fis[1] = new FileInputStream("src/files/filespart2/Hobbies_Collectibles.csv.ser.txt");
			fis[2] = new FileInputStream("src/files/filespart2/Movies_TV_Books.csv.ser.txt");
			fis[3] = new FileInputStream("src/files/filespart2/Music_Radio_Books.csv.ser.txt");
			fis[4] = new FileInputStream("src/files/filespart2/Nostalgia_Electic_Books.csv.ser.txt");
			fis[5] = new FileInputStream("src/files/filespart2/Old_Time_Radio_Books.csv.ser.txt");
			fis[6] = new FileInputStream("src/files/filespart2/Sports_Sports_Memorabilia.csv.ser.txt");
			fis[7] = new FileInputStream("src/files/filespart2/Trains_Planes_Automobiles.csv.ser.txt");
			
			ois[0] = new ObjectInputStream(fis[0]);
			ois[1] = new ObjectInputStream(fis[1]);
			ois[2] = new ObjectInputStream(fis[2]);
			ois[3] = new ObjectInputStream(fis[3]);
			ois[4] = new ObjectInputStream(fis[4]);
			ois[5] = new ObjectInputStream(fis[5]);
			ois[6] = new ObjectInputStream(fis[6]);
			ois[7] = new ObjectInputStream(fis[7]);
			
			//Creating the Arrays that will store all the Book objects
			Book[][] allRecords = new Book[8][];
			allRecords[0] = new Book[objectCounter[0]];
			allRecords[1] = new Book[objectCounter[1]];
			allRecords[2] = new Book[objectCounter[2]];
			allRecords[3] = new Book[objectCounter[3]];
			allRecords[4] = new Book[objectCounter[4]];
			allRecords[5] = new Book[objectCounter[5]];
			allRecords[6] = new Book[objectCounter[6]];
			allRecords[7] = new Book[objectCounter[7]];
			
			//Input Book objects into each array  
			int counter = 0;
			for (int i =0; i < ois.length; i++) {
				counter = 0;
				while (true) {
						try {
							allRecords[i][counter] = (Book) ois[i].readObject();
							counter++;
						}
						catch (ClassNotFoundException e2) {
							System.out.println("wrong class");
						}
						catch (EOFException e3) {
							break;
						}
					}
				}
			
			
			
			//Create main menu and sub-menu
			boolean isValid = true;
			String sfilename = files[0].getPath().substring(21);
			int getRecords = objectCounter[0];
			int selRecord = 0;
			
			do {
				isValid = true;
				int scounter = 0;
				Book info;
				Scanner keyboard = new Scanner(System.in);
				
				while (isValid) {
					System.out.println("\n------------------------");
					System.out.println("\tMain Menu");
					System.out.println("------------------------");
					System.out.println("v View the selected file: "+sfilename +" (" + getRecords + " records)");
					System.out.println("s Select a file to view");
					System.out.println("x Exit");
					System.out.println("------------------------");
					
					System.out.print("\nEnter Your Choice: ");
					String test1 = keyboard.nextLine();
					char choice = test1.charAt(0);
					choice = Character.toUpperCase(choice);
					//Handling the entry "x"
					if(choice == 'X') {
						System.out.println("\nThank you for using this application!");
						keyboard.close();
						//Close all files
						for (int i =0; i < ois.length; i++) {
							ois[i].close();
							fis[i].close();
						}
						System.exit(0);
					}
					//Handling the entry "S"
					else if(choice == 'S') {
						//Displaying the file names and the amount of entries in each file
						System.out.println("\n------------------------");
						System.out.println("\tFile Sub-Menu");
						System.out.println("------------------------");
						System.out.println("1 Cartoons_Comics.csv.ser.txt\t(" + objectCounter[0]+ " records)");
						System.out.println("2 Hobbies_Collectibles.csv.ser.txt\t(" + objectCounter[1]+ " records)");
						System.out.println("3 Movies_TV_Books.csv.ser.txt\t(" + objectCounter[2]+ " records)");
						System.out.println("4 Music_Radio_Books.csv.ser.txt\t(" + objectCounter[3]+ " records)");
						System.out.println("5 Nostalgia_Electic_Books.csv.ser.txt\t(" + objectCounter[4]+ " records)");
						System.out.println("6 Old_Time_Radio_Books.csv.ser.txt\t(" + objectCounter[5]+ " records)");
						System.out.println("7 Sports_Sports_Memorabilia.csv.ser.txt\t(" + objectCounter[6]+ " records)");
						System.out.println("8 Trains_Planes_Automobiles.csv.ser.txt\t(" + objectCounter[7]+ " records)");
						System.out.println("9\tExit");
						System.out.println("---------------------------------");
						System.out.print("Enter Your Choice: ");
						
						int select = keyboard.nextInt();
						keyboard.nextLine();
						
						//give new record value according to user's input
						for (int i =0; i < objectCounter.length; i++) {
							if (select-1 == i) {

								getRecords = objectCounter[select-1];
								
								//give the index of the file that the user wishes to see
								selRecord = select-1;
								
								//Showcase the name of the new file
								sfilename = files[select-1].getPath().substring(21);					
								break;
							}
							else if (select <= 0 || select > 9) {
								System.out.println("The input is not valid for this program. Please retry.");
							}
							else if (select ==9) {
								System.out.println("\nThank you for using this application.");
								keyboard.close();
								System.exit(0);
							}
						}
						
			
						
						
					}
					//Handling the entry "V"
					else if (choice == 'V') {
						System.out.println("viewing: "+sfilename+"\t("+getRecords+" records)");
						Book[] viewing = null;
						viewing = allRecords[selRecord];
						
						
						try {
							int index = 0;
							while(true) {
								System.out.print("\nUp to which range of records do you wish to view from this specific file?(Enter 0 to exit viewing session) ");
								int n = keyboard.nextInt();
								int n_below = 0;
								
								keyboard.nextLine();
		
							
								//Handling the case in which the input for n is "0" --> in this case we want to go back to the main menu
								if (n ==0) {
									System.out.println("\nExiting viewing session...\n");
									break; //UNSURE but just restart from the beginning (main menu)
								}
								//Handling the case in which the input for n is negative --> in this case we want to show the entries
								//from before the current index to the current index
								else if (n < 0) {
									n *=-1;
									n_below = n-1;
									if(index-n_below < 0) {
										System.out.println("\nBOF has been reached");
										n_below = index;
										
									}
									if(n == -1) {
										System.out.println("\n"+viewing[index]);
										continue;
									}
									index-=n_below;
									for(int i = 0; i<=n_below;i++) {
										System.out.println("\n" + viewing[index + i]);
									}
									
								}
								//Handling the case in which the input for n is positive --> in this case we want to show the entries
								//from the current index to after the current index
								else if (n > 0) {
									//display book records from the specific file
									n_below = n-1;
									if(index+n_below > viewing.length-1) {
										System.out.println("\nEOF has been reached");
										n_below = viewing.length - index - 1;
									}
									if(n == 1) {
										System.out.println("\n"+viewing[index]);
										continue;
									}
									
									for(int i = 0; i<=n_below;i++) {
										System.out.println("\n" + viewing[index + i]);
									}
									index+=n_below;
								} 
							}
							
							
						} catch(java.util.InputMismatchException e) {
							System.out.println("\nInvalid input. Exiting viewing session...\n");
							continue;
						}
						

					}
				}
				
				keyboard.close();	
				
			} while(true);
			
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
			
		
	}
	
	
}
