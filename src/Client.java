import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client implements Connection
{
	public Client()
	{
		message = new ArrayList<String>();
		
		connect();
	}
	
	public void connect()
	{
		while(true)
		{
			try
			{
				System.out.println("Tentando conectar na sala...");
				client = new Socket("127.0.0.1", UPnP.internal);
				System.out.println("Conexão bem sucedida!");
				connected = true;
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
				connected = false;
				break;
			}
			
			if(connected == false)
			{	
				System.out.println("Não foi possível realizar a conexão. Gostaria de tentar novamente?");
				System.out.println("1- Sim.");
				System.out.println("2- Não.");
				
				scanner = new Scanner(System.in);
				String command = scanner.nextLine();
				if(command.matches("[1-2]"))
				{
					if(command.equals("1"))
					{
						UPnP.internal++;
					}
					else if(command.equals("2"))
					{
						System.out.println("Programa terminado.");
						break;
					}
				}
				else
				{
					System.out.println("Comando inválido! Use comandos entre 1 e 2.");
				}
			}
			else
			{
				break;
			}
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
				return mensagem;
			}
			else
			{
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
			if(client != null)
			{
				client.close();
			}
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
			return;
		}
		
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
				break;
			}
		}
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	private Scanner scanner;
	private boolean connected;
	private Socket client;
	private ArrayList<String> message;
}
