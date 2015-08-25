//Romesh Wickramasekera
//c3131416
//24/08/2014
//SENG3400

import java.io.*;
import java.net.*;
import java.nio.CharBuffer;
import java.util.Scanner;

public class TaxClient {
	
	private static int portNumber = 2029;
	private static String hostName = "localhost";
	private static Scanner console = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaxClient client = new TaxClient();
		System.out.println("Would you like to enter server details?(Y/N)");
		String yesNo = console.next();
		if(yesNo.toUpperCase().equals("Y"))
		{
			System.out.println("Enter host name(eg, localhost):");
			hostName = console.next();
			System.out.println("Enter port number(eg, 2345):");
			portNumber = console.nextInt();
		}
		else
		{
			System.out.println("Using default host and port..");
		}
		System.out.println("Connectiong to '" + hostName + "' on port " + portNumber + "....");
		
		client.start(hostName, portNumber);
	}
	
	private char[] copy(char[] from)
	{
		int length = 0;
		for(int j=0; j<from.length;j++)
		{
			if(from[j] != '\0')
			{
				length ++;
			}
		}
		char[] to = new char[length];
		for(int i=0;i<length;i++)
		{
			to[i] = from[i];
		}
		
		return to;
	}
	
	private void start(String host, int port)
	{
		try
		{
			
			Socket soc = new Socket(host, port);
			PrintStream stream = new PrintStream(soc.getOutputStream());
			
			InputStreamReader Init_iReader = new InputStreamReader(soc.getInputStream());
			BufferedReader Init_bReader = new BufferedReader(Init_iReader);
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			
			stream.println("TAX");
			System.out.println("CLIENT: TAX:");
			System.out.println("SERVER: " + Init_bReader.readLine());
			
		
		
			String userIn;
			System.out.println("Enter:\n-QUERY\n-STORE\n-BYE\n-END\n-a number");
			while((userIn = userInput.readLine()) != null)
			{
				
				stream.println(userIn.toUpperCase());
				System.out.println("CLIENT: " + userIn.toUpperCase());
				if(userIn.toUpperCase().equals("STORE"))
				{
					String messageData;
					System.out.println("Enter a lower range:");
					int lowerRange = Integer.parseInt(userInput.readLine());
					stream.print(lowerRange + "\n");
					System.out.println("Enter an upper range:");
					int upperRange = Integer.parseInt(userInput.readLine());
					stream.print(upperRange + "\n");
					System.out.println("Enter the tax payable:");
					int taxPayable = Integer.parseInt(userInput.readLine());
					stream.print(taxPayable + "\n");
					System.out.println("Enter tax over " + upperRange + ": (in cents)");
					int taxOver = Integer.parseInt(userInput.readLine());
					stream.print(taxOver + "\n");
					
					messageData = "CLIENT: " + lowerRange + "\nCLIENT: " + upperRange
							+ "\nCLIENT: " + taxPayable + "\nCLIENT: " + taxOver;
					System.out.println(messageData);
				}
				
				char[] buff = new char[256];
				Init_bReader.read(buff);
				char[] shortBuff = copy(buff);
				System.out.print("SERVER: ");
			    System.out.print(shortBuff);
			    
			    if(userIn.toUpperCase().equals("BYE"))
			    {
			    	soc.close();
					stream.close();
					break;
			    }
			    
			    if(userIn.toUpperCase().equals("END"))
				{
					soc.close();
					stream.close();
					break;
				}
			    
			    System.out.println("Enter:\n-QUERY\n-STORE\n-BYE\n-END\n-a number");
			}
		}
		catch(Exception e)
		{
			System.out.println("Error connecting to Server");
		}
		
		
	}

}
