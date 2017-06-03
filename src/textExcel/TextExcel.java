package textExcel;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextExcel
{

	public static void main(String[] args)
	{
		Spreadsheet x = new Spreadsheet();
		Scanner in = new Scanner(System.in);
		String input = "";
		System.out.println("Welcome to TextExcel.");
		System.out.print("Please enter command (Press 'quit' to end): ");
		input = in.nextLine();         
		while(!input.equals("quit")){ 
			System.out.println(x.processCommand(input));
			System.out.print("Please enter another command: ");
			input = in.nextLine();
		}
		System.out.println("Loop Finished");
		//Measure to check for Loop End
	}
}
