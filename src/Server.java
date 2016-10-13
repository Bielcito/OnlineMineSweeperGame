import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread 
{
	public Server(int port)
	{
		this.port = port;
	}
	
	public void run()
	{
		//Checa se a conexão já foi criada, senão, cria:
		if(server == null)
		{
			createServerSocket();
		}
		
		try 
		{
			System.out.println("Servidor: ");
			
			while (true) 
			{
				client = server.accept();
				System.out.println("Nova conexão com o cliente " +
						client.getInetAddress().getHostAddress());
				
				DataInputStream ent = new DataInputStream(
						client.getInputStream()
				);
				message = ent.readUTF();
				System.out.println(message);
				sendConfirmation();
			}
		} 
		catch (IOException e) 
		{
			System.out.println("Erro na escuta: " + e.getMessage());
		}
	}
	
	public void sendConfirmation()
	{
		try
		{
			DataOutputStream sai = new DataOutputStream(
			client.getOutputStream());
			sai.writeUTF("Foi");
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void close()
	{
		try
		{
			server.close();
			client.close();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
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
	int valor;
	String message;
}
