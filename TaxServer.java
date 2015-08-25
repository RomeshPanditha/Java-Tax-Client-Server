//Romesh Wickramasekera
//c3131416
//24/08/2014
//SENG3400

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TaxServer {

	private static Scanner console = new Scanner(System.in);
	private static TaxProtocol protocol = new TaxProtocol();
	private static int portNumber = 2029;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaxServer server = new TaxServer();
		
		System.out.println("Would you like to enter the port?(Y/N)");
		String yesNo = console.next();
		if(yesNo.toUpperCase().equals("Y"))
		{
			System.out.println("Enter port number(eg, 2345):");
			portNumber = console.nextInt();
		}
		else
		{
			System.out.println("Using default port..");
		}
		System.out.println("Starting server on port " + portNumber + "....");
		
		server.start(portNumber);
	}
	
	private void start(int portNum)
	{
		try
		{
				ServerSocket servSoc = new ServerSocket(portNum);
				System.out.println("Server started!");
				while(!servSoc.isClosed())
				{
					Socket soc = servSoc.accept();
					
					InputStreamReader iReader = new InputStreamReader(soc.getInputStream());
					BufferedReader bReader = new BufferedReader(iReader);
					PrintStream client = new PrintStream(soc.getOutputStream());
					
					
					if(bReader.readLine().toUpperCase().equals("TAX"))
					{	
						client.println("TAX: OK");
						String input, output;
						
						while((input = bReader.readLine()) != null)
						{
							//receive input
							output = protocol.processMessage(input);
							System.out.println("CLIENT: " + input);
							
							//handle the "STORE" operation
							if(input.toUpperCase().equals("STORE"))
							{
								String checkFinish = "";
								while(!checkFinish.equals("STORE: OK\n"))
								{
									String entry = bReader.readLine();
									checkFinish = protocol.setRange(entry);
									System.out.println("CLIENT: " + entry);
								}	
								output = checkFinish;
							}
							
							//send return message
							client.print(output);
							System.out.print("Response: " + output);
							
							// close the server
							if(input.toUpperCase().equals("END"))
							{
								servSoc.close();
								soc.close();
								break;
							}
						}
					}
			}
		}
		catch(Exception e)
		{
			System.out.println("abruptly closed");
		}
		
	}

}
