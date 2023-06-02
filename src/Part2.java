/*
 * ---------------------------
 * Assignment 3   
 * Written by: Robert CHEN 40241709 and Alexandru Ilie 40248696
 * COMP 249 Section S - Winter 2023
 * 27th march  2023
 * --------------------------- */
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Part 2
 * @author Robert CHEN and Alexandru Ilie
 * @version 1.0
 *
 */
public class Part2 {
	
	/**
	 * Part 2, getting rid of semantic error records in each of the newly generated text file
	 * @throws BadIsbn10Exception
	 * @throws BadIsbn13Exception
	 * @throws BadPriceException
	 * @throws BadYearException
	 * 
	 */
	public static void do_part2() {
		//Declare variables for I/O Streams
		BufferedReader [] bf = new BufferedReader[8];
		ObjectOutputStream [] oos = new ObjectOutputStream[bf.length];
		File [] files = new File[bf.length];
		
		try {
			
			//File wrapper object
			files[0]=  new File("src/files/new files/Cartoons_Comics.csv.txt");
			files[1] = new File("src/files/new files/Hobbies_Collectibles.csv.txt");
			files[2] = new File("src/files/new files/Movies_TV_Books.csv.txt");
			files[3] = new File("src/files/new files/Music_Radio_Books.csv.txt");
			files[4] = new File("src/files/new files/Nostalgia_Electic_Books.csv.txt");
			files[5] = new File("src/files/new files/Old_Time_Radio_Books.csv.txt");
			files[6] = new File("src/files/new files/Sports_Sports_Memorabilia.csv.txt");
			files[7] = new File("src/files/new files/Trains_Planes_Automobiles.csv.txt");
			
			//genre-based CSV-formatted input file from part 1
			bf[0]= (BufferedReader) new BufferedReader(new FileReader("src/files/new files/Cartoons_Comics.csv.txt"));
			bf[1] = (BufferedReader)new BufferedReader(new FileReader("src/files/new files/Hobbies_Collectibles.csv.txt"));
			bf[2] =(BufferedReader) new BufferedReader(new FileReader("src/files/new files/Movies_TV_Books.csv.txt"));
			bf[3] =(BufferedReader) new BufferedReader(new FileReader("src/files/new files/Music_Radio_Books.csv.txt"));
			bf[4] =(BufferedReader) new BufferedReader(new FileReader("src/files/new files/Nostalgia_Electic_Books.csv.txt"));
			bf[5] =(BufferedReader) new BufferedReader(new FileReader("src/files/new files/Old_Time_Radio_Books.csv.txt"));
			bf[6] =(BufferedReader) new BufferedReader(new FileReader("src/files/new files/Sports_Sports_Memorabilia.csv.txt"));
			bf[7] =(BufferedReader) new BufferedReader(new FileReader("src/files/new files/Trains_Planes_Automobiles.csv.txt")); 

		  				 
			//genre-based CSV-formatted output files that will be created for part 2		
			oos[0] =(ObjectOutputStream) new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Cartoons_Comics.csv.ser.txt"));
			oos[1]= (ObjectOutputStream) new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Hobbies_Collectibles.csv.ser.txt"));
			oos[2] = (ObjectOutputStream) new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Movies_TV_Books.csv.ser.txt"));
			oos[3] =(ObjectOutputStream)  new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Music_Radio_Books.csv.ser.txt"));
			oos[4] =(ObjectOutputStream)  new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Nostalgia_Electic_Books.csv.ser.txt"));
			oos[5] =(ObjectOutputStream)  new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Old_Time_Radio_Books.csv.ser.txt"));
			oos[6] =(ObjectOutputStream)  new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Sports_Sports_Memorabilia.csv.ser.txt"));
			oos[7] =(ObjectOutputStream)  new ObjectOutputStream(new FileOutputStream("src/files/filespart2/Trains_Planes_Automobiles.csv.ser.txt"));
			PrintWriter sem_error = new PrintWriter(new FileOutputStream("src/files/filespart2/semantic_error_file.txt"));		
			
			
			//Read all files in order to asset the size of the Book array
			int everyLine = 0;
			for (int i = 0; i< bf.length; i++) {
				bf[i].mark(10000000);
				while ( bf[i].readLine() != null) {
					everyLine++;
				}
				bf[i].reset();
			}
			Book [] allData = new Book[everyLine];
			
			//For each valid lines in each, print it in the new files
			String sfilename = "";
			String errorType = "";
			String str = "";
			int counter = 0;
			int serror = 0;
			
			for (int i = 0; i < bf.length; i++) {
				//receive the file's name
				sfilename = files[i].getPath().substring(10);
				str = "";				
				//continuously print from the file until nothing remains
				while ((str = bf[i].readLine()) != null) {
					//verify whether there is an error or not in the file
					boolean isValid = true;
					
					try {
						allData[counter] = verifyLines(str); //Verify if the line is good or not
						//oos[i].writeUTF(str); //Write valid books in their respective object files
						oos[i].writeObject(allData[counter]);
					}
					catch (BadIsbn10Exception e) {
						errorType = "Invalid ISBN-10";
						isValid = false;
					}
					catch (BadIsbn13Exception e) {
						errorType = "Invalid ISBN-13";	
						isValid = false;
					}
					catch (BadPriceException e) {
						errorType = "Invalid price";
						isValid = false;
					}
					catch (BadYearException e) {
						errorType = "Invalid year";
						isValid = false;
					}
					
					//print error in semantic error file text
					if (isValid == false) {
						serror++;
						sem_error.println(serror+". semantic error in file: "+sfilename);
						sem_error.println("=======================");
						sem_error.println("error: "+errorType);
						sem_error.println(str);
						sem_error.println();
					}
					else {
						counter++; //access an array location in the array of Book Objects
					}

					//reset string to empty
					str = "";
				}
				//Close all stream to avoid buffering issues				
				bf[i].close();
				oos[i].flush();
				oos[i].close();
				
			}
			
			//Close printwriter stream to avoid buffering issues
			sem_error.close();

			//Reduce the size of Book Objects
			Book [] newArray = new Book[counter];
			for (int i =0; i < newArray.length; i++) {
				if (i <= counter) {
					newArray[i] = (Book) allData[i].clone();
				}
			}
			
				//Serializing the array of Book Objects into a binary file 
				try {
					ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("src/files/filespart2/abc.csv.ser.txt"));
								
					for (int i = 0; i < newArray.length; i++) {
						oos2.writeObject(newArray[i].toString());
					}
					oos2.flush();
					oos2.close();
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
		catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methods verify each line and checks whether it is good or not, according to the valid years,prices and ISBN Correspondance.
	 * 
	 * @param str a line in a specific text file that represents a record from a single novel
	 * @return a new Book with the six data
	 * 
	 * @throws BadIsbn10Exception
	 * @throws BadIsbn13Exception
	 * @throws BadPriceException
	 * @throws BadYearException
	 */
	public static Book verifyLines(String str) throws BadIsbn10Exception, BadIsbn13Exception, BadPriceException, BadYearException{

		String [] allInfo = new String[6];
		String title = "";
		String genre = "";
		String authors = "";
		int year = 0;
		double price = 0.0;
		String ISBN = "";
		int ISBNSum = 0;

		//Title can contain a comma. To ensure differentiation of title
		String[] titleDif = new String [3];
		String[] test = new String[6];

		if(str.charAt(0) == 34) {
			
			titleDif = str.split("\"",3); 
			String str2 = titleDif[2];
			allInfo[0] = titleDif[1];
			test = str2.split(",",6);
			
			for(int j = 1; j<allInfo.length;j++) {
				allInfo[j] = test[j];
			}
		}
		else { //if there are no commas in book titles
			allInfo = str.split(",",6);
		}
			//implement data to their corresponding fields.
			title = allInfo[0];
			authors = allInfo[1];
			price = Double.parseDouble(allInfo[2]);
			ISBN = allInfo[3];	
			genre = allInfo[4];
			year=  Integer.parseInt(allInfo[5]);
	
			//Add the sum of the digits from ISBN together
			String [] ISBNDigits = ISBN.split("");
			for (int i = 0; i < ISBNDigits.length;i++) {
				try {
					ISBNSum += Integer.parseInt(ISBNDigits[i]);				
				}
				catch (NumberFormatException e) { //if there are letters within the ISBN
					if(ISBNDigits[i].charAt(0) > 47 && ISBNDigits[i].charAt(0) < 58 && ISBN.length() == 10) {
						throw new BadIsbn10Exception();
					} else if(ISBNDigits[i].charAt(0) > 47 && ISBNDigits[i].charAt(0) < 58 && ISBN.length() == 13) {
						throw new BadIsbn13Exception();
					}
				}
			}
			
		
		
		//Validate price, Years and ISBN-10/13
		if (year < 1995 || year > 2010) {
			throw new BadYearException();
		}
		if (price <= 0.0) {
			throw new BadPriceException();
		}
		if (ISBNSum % 11 != 0 && ISBN.length() ==10) {
			throw new BadIsbn10Exception();
		}
		if (ISBNSum % 10 != 0 && ISBN.length() ==13) {
			throw new BadIsbn13Exception();			
		}
		
		
		return new Book(price,year,ISBN,title,authors,genre);
	}
	
	
}

/**
 * Class represents a specific book located within the text files. Displayed only for valid record 
 * 
 * @author Robert CHEN and Alexandru Ilie
 *@version 1.0
 */
class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double price;
	private int year;
	private String ISBN;
	private String title;
	private String authors;
	private String genre;
		
	/**
	 * Default Constructor
	 */
	Book() {
		
	}
	
	/**
	 * parameterized constructord
	 * @param p, a double for price
	 * @param y, an integer for year
	 * @param I, a string for ISBN
	 * @param T, a string for title
	 * @param A, a string for authors
	 * @param G, a string for genre
	 */
	Book(double p, int y, String I, String T, String A, String G) {
		price=p;
		year=y;
		ISBN=I;
		title=T;
		authors=A;
		genre=G;
	}	
	
	/**
	 * Copy Constructor
	 * @param other, the book to be copied
	 */
	Book(Book other){
		price=other.price;
		year=other.year;
		ISBN=other.ISBN;
		title=other.title;
		authors=other.authors;
		genre=other.genre;
	}
	
	/**
	 * change the price
	 * @param x, a double to set for price
	 */
	public void setPrice(double x) {
		price = x;
	}
	/**
	 * change the year the book was published
	 * @param x, an integer to set for year
	 */
	public void setYear(int x) {
		year = x;
	}
	
	/**
	 * change the corresponding ISBN
	 * @param x, a string for ISBN
	 */
	public void setISBN(String x) {
		ISBN = x;
	}
	
	/**
	 *  change the title's name
	 * @param x, a string for title
	 */
	public void setTitle(String x) {
		title = x;
	}
	/**
	 * change the authors' names
	 * @param x, a string for authors
	 */
	public void setAuthors(String x) {
		authors = x;
	}
	
	/**
	 * change the genre
	 * @param x, a string for genre
	 */
	public void setGenre(String x) {
		genre = x;
	}
	
	//Accessors
	
	/**
	 * receive the price the book costs.
	 * @return double price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * receive the year the book was published
	 * @return int year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * receive the ISBN of the book
	 * @return string ISBN
	 */
	public String getISBN() {
		return ISBN;
	}
	/**
	 * receive the title of the book
	 * @return string title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * receive the name of the authors
	 * @return string authors
	 */
	public String getAuthors() {
		return authors;
	}
	/**
	 * get the genre of a book
	 * @return string genre
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	 * Clone method to produce a deep copy when copying it to another book reference
	 * @return deep copy
	 */
	public Book clone() {
		return new Book(this);
	}
		
	/**
	 * Comparing two book objects
	 * @param other, the object of another Book object
	 * @return whether this book object has the same components as the one we wish to compare.
	 */
	public boolean equals(Object other) {
		if (other == null || other.getClass() != this.getClass()) {
			return false;
		}
		if (this == other) {
			return true;
		}
		
		Book b = (Book) other;
		
		return (this.price == b.price && this.year == b.year && this.ISBN.equals(b.ISBN) && this.title.equals(b.title) 
				&& this.authors.equals(b.authors) && this.genre.equals(b.genre));		
	}	
	
	/**
	 * Returning the book object as a String
	 * @return a string representing the 6 data.
	 */
	public String toString() {
		return title+","+authors+","+price+","+ISBN+","+genre+","+year+"\n";
	}
	
}


/**
 * Exception class if the ISBN is not valid for the 10-digit long
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class BadIsbn10Exception extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	BadIsbn10Exception() {
		
	}
}

/**
 * Exception class if the ISBN is not valid for the 13-digit long
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class BadIsbn13Exception extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	BadIsbn13Exception() {
		
	}
}

/**
 * Exception class if the price is, somehow, negative
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class BadPriceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	BadPriceException() {
		
	}
}

/**
 * Exception class if the year is not within the valid range
 * @author Robert CHEN and Alexandru ilie
 * @version 1.0
 * 
 */
class BadYearException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	BadYearException() {
		
	}
}
//
