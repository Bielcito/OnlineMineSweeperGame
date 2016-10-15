import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Connection
{
	public Server(int port)
	{
		this.port = port;
		message = new ArrayList<String>();
		connected = false;
		
		connect();
	}
	
	public void connect()
	{
		try
		{
			server = new ServerSocket(port);
			System.out.println("Servidor escutando a porta " + port + ".");
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			close();
		}
	}
	
	public void run()
	{
		if(server == null)
		{
			return;
		}
		try 
		{
			while (true) 
			{
				if(connected == false)
				{
					client = server.accept();
					connected = true;
					System.out.println("Nova conexão com o cliente " +
							client.getInetAddress().getHostAddress());
				}
				
				DataInputStream ent = new DataInputStream(
						client.getInputStream()
				);
				message.add(ent.readUTF());
			}
		} 
		catch (IOException e) 
		{
			System.out.println("Erro na escuta: " + e.getMessage());
		}
	}
	
	public void send(String text)
	{
		try
		{
			DataOutputStream sai = new DataOutputStream(
			client.getOutputStream());
			sai.writeUTF(text);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public synchronized String receive()
	{
		while(true)
		{
			if(message.size() > 0)
			{
				String mensagem = message.get(0);
				message.remove(0);
				System.out.println("Recebendo: " + mensagem);
				return mensagem;
			}
			else
			{
				System.out.println(".");
				try {
					wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
	
	public boolean isConnected()
	{
		return connected;
	}

	private int port;
	private ServerSocket server;
	private Socket client;
	private boolean connected;
	int valor;
	ArrayList<String> message;
}
