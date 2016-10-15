import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Connection
{
	public Client(int port)
	{
		this.port = port;
		message = new ArrayList<String>();
		
		connect();
	}
	
	public void connect()
	{
		if(client != null)
		{
			connected = true;
		}
		try
		{
			System.out.println("Cliente: ");
			client = new Socket("127.0.0.1", port);
			System.out.println("O cliente se conectou ao servidor!");
			connected = true;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			connected = false;
		}
	}
	
	public void send(String text)
	{
		try
		{
			DataOutputStream sai = new DataOutputStream(client.getOutputStream());
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
			client.close();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void run()
	{
		if(client == null)
		{
			connect();
		}
		
		System.out.println("Cliente esperando o servidor enviar algo:");
		
		while(true)
		{
			try
			{
				DataInputStream ent = new DataInputStream(client.getInputStream());
				message.add(ent.readUTF());
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	private boolean connected;
	private Socket client;
	private int port;
	private ArrayList<String> message;
}
