import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread
{
	public Client(int port)
	{
		this.port = port;
	}
	
	public boolean connect()
	{
		try
		{
			System.out.println("Cliente: ");
			client = new Socket("127.0.0.1", port);
			System.out.println("O cliente se conectou ao servidor!");
			return true;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public void send(int valor)
	{
		if(!connect() == false)
		{
			return;
		}
		
		try
		{
			DataOutputStream sai = new DataOutputStream(client.getOutputStream());
			sai.writeInt(valor);
			answer();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void send(String text)
	{
		if(!connect())
		{
			System.out.println("amo vc");
			return;
		}
		
		try
		{
			DataOutputStream sai = new DataOutputStream(client.getOutputStream());
			sai.writeUTF(text);
			//answer();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public String answer()
	{
		try
		{
			DataInputStream ent = new DataInputStream(client.getInputStream());
			String recebido = ent.readUTF();
			return recebido;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			return "";
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
		
	}
	
	private Socket client;
	private int port;
}
