import java.net.Socket;

import java.io.IOException;
import java.io.BufferReder;
import java.io.BufferWriter;

import java.util.ArrayList;


public class ClientHandler implements Runnable
{

	//Keep track of all clients, EX: client sends message loop through clients for other clients to recieve it.
	//Static = belong to the class. Not each object of the class.
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();	

	private Socket socket;
	private BufferReader bufferReader;
	private BufferWriter bufferWriter;
	private String clientUsername;

	public CLientHandler(Socket socket)
	{
		try
			{
				this.socket = socket;
				this.bufferReader = new BufferReader(new InputStreamReader(socket.getInputStream()));
				this.bufferWriter = new BufferWriter(new OutputStreamWriter(socket.getOutPutStream()));
				this.clientUsername = bufferReader.readLine();
				clientHandler.add(this);
				broadcastMessage("Server: " + clientUsername + " has enter the chat!");
			}
		catch(IOExcpetion e)
			{
				closeEverything(this.socket, this.bufferReader, this.bufferWriter);
			}
	}



	@Override
	public void run()
	{

		String messageFromClient;

		while(socket.isConnected())
		{
			try 
				{

					messageFromClient = bufferReder.readLine();
					broadcastMessage(messageFromClient);

				}
			catch(IOException e)
				{
					closeEverything(socket, bufferReader, bufferWriter);
					break;	
				}
		}

	}

	public void broadcastMessage(String message)
	{
		for(ClientHandler clientHandler : clientHandlers)
		{
			try
				{
					if(!clientHandler.clientUsername.equals(clientUsername))
					{
						clientHandler.bufferWriter.write(message);
						clientHandler.bufferWriter.newLine();
						clientHandler.bufferWriter.flush();
					}
				}
			catch(IOException e)
				{
					closeEverything(socket, bufferReader, bufferWriter);
				}
		}	
	}

	public void removeClientHandler()
	{
		clientHandlers.remove(this); 
		broadcastMessage("Server: " + clientUsername + " has left the chat!");
	}

	public void closeEverything(Socket socket, BufferReader bufferReader, BufferWriter bufferWriter)
	{
		removeCLientHandler();
		try
			{
				if(socket != null)
				{
 					socket.close();					
				}
				if(bufferReader != null)
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
				e.printStackTrace();	
			}
	}

}