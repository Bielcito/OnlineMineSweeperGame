import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread 
{
	public Servidor(int port)
	{
		this.port = port;
	}
	public void run()
	{
		try 
		{
			System.out.println("Servidor: ");
			
			while (true) 
			{
				client = server.accept();
				System.out.println("Nova conexão com o cliente " +
				client.getInetAddress().getHostAddress());
				
				DataInputStream ent = new DataInputStream(
				client.getInputStream());
				message = ent.readInt();
				System.out.println("O Servidor recebeu do cliente: "
				+ message);
				DataOutputStream sai = new DataOutputStream(
				client.getOutputStream());
				sai.writeUTF("Foi");
				client.close();
				server.close();
			}
		} 
		catch (IOException e) 
		{
			System.out.println("Erro na escuta: " + e.getMessage());
		}
	}
	
	public void createServerSocket()
	{
		try
		{
			server = new ServerSocket(port);
			System.out.println(" Servidor na porta " + port);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	private int port;
	private ServerSocket server;
	private Socket client;
	int message;
}
