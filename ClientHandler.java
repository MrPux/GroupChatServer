import java.io.IOException;
import java.util.ArrayList;
import java.net.Socket;


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
				this.bufferWritter = new BufferWritter(new OutputStreamWritter(socket.getOutPutStream()));
				this.clientUsername = bufferReader.readLine();
				clientHandler.add(this);
				broadcastMessage("Server: " + clientUsername + " has enter the chat!");
			}
		catch(IOExcpetion e)
			{
				closeEverything(this.socket, this.bufferReader, this.bufferWritter);
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
					closeEverything(socket, bufferReader, bufferWritter);
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
						clientHandler.bufferWritter.write(message);
						clientHandler.bufferWritter.newLine();
						clientHandler.bufferWritter.flush();
					}
				}
			catch(IOException e)
				{
					closeEverything(socket, bufferReader, bufferWritter);
				}
		}	
	}

	public void removeClientHandler()
	{
		clientHandlers.remove(this); 
		broadcastMessage("Server: " + clientUsername + " has left the chat!");
	}

	public void closeEverything(Socket socket, BufferReader bufferReader, BufferWriter bufferWritter)
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
				if(bufferWritter != null)
				{
					bufferWritter.close(); 
				}
				
			}
		catch(IOException e)
			{
				e.printStackTrace();	
			}
	}

}