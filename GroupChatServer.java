import java.io.IOExcpetion;
import java.net.ServerSocket;
import java.net.Socket;

public class GroupChatServer
{

	ServerSocket serverSocket;

	public GroupChatServer(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
	}

	public void startServer(){

		try
		{
			While(!serverSocket.isClosed())
			{
				Socket socket = serverSocket.accept();
				System.out.println("New Client has connected!");

				ClientHandler clientHandler = new ClientHandler(socket);

				Thread thread = new Thread(clientHandler);
				thread.start();

			}


		} catch(IOException e)
		{


		}

	}

	public void closeServerSocket()
	{
		try
		{
			if(serverSocket != null)
			{
				serverSocket.close();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}




	public static void main(String[] args) thows IOException
	{
		ServerSocket serverSocket = new ServerSocket(1122); 
		GroupChatServer server = new GroupChatServer(serverSocket);
		server.startServer();
	}
}