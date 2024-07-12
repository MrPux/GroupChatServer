import java.net.Socket;

import java.io.IOException;
import java.io.BufferReder;
import java.io.BufferWriter;

import java.util.Scanner;

public class Client
{

	privcate Socket socket;
	private BufferReader bufferReader;
	private BufferWriter bufferWriter;
	private String username;

	//Insantiate Client variables
	public Client(Socket socket, String username)
	{
		try
			{
				this.socket = socket;
				this.username = username;

				this.bufferReader = new BufferReader(new InputStreamReader(socket.getIntputStream()));
				this.bufferWriter = new BufferWriter(new OutputStreamWriter(socket.getOutputStream()));
			}
		catch(IOException e)
			{
				closeEverything(socket, bufferReder, bufferWriter);
			}
	}	

	public void sendMessage()
	{
		try
			{
				bufferWriter.write(username);
				bufferWriter.newLine();
				bufferWriter.flush();

				Scanner input = new Scanner(System.in);
				
				while(socket.isConnected())
				{
					String message = scanner.nextLine();
					bufferWriter.write(username + ": " + message);
					bufferWriter.newLine();
					buggerWriter.flush();
				}
			}
		catch(IOException)
			{
				closeEverything(socket, bufferReader, bufferWriter);
			}
	}

	public void listenForMessage()
	{
   		 new Thread(new Runnable()
   		 {
   		 	public void run()
   		 	{
   		 		String message;

   		 		while(socket.isConnect())
   		 		{
   		 			try
	   		 			{
	   		 				message = bufferReader.readLine();
   		 					System.out.println(message);
	   		 			}
	   		 		catch(IOException e)
		   		 		{
		   		 			closeEverything(socket, bufferReder, bufferWriter);
		   		 		}
   		 		}
   		 	}
   		 }).start();
	}
 
	public void closeEverything()
	{
		try
			{
				if(socket != null)
				{
					socket.close();
				}
				if(bufferReder != null)
				{
					bufferReader.close();
				}
				if(bufferWriter != null)
				{
					bufferWriter.close();
				}
			}
		catch(IOException e)
			{
				e.printStrackTrace();	
			}
	}

	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Enter Your username for the group caht: ");
		String username = input.nextLine();
		Socket socket = new Socket("localhost", 1122);
		Client client = new Client(socket, username);

		// Run both methods to listen and send message
		client.listenForMessage();
		client.sendMessage();

	}
}