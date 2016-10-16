import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server implements Connection
{
	public Server()
	{
		message = new ArrayList<String>();
		connected = false;
		scanner = new Scanner(System.in);
		
		connect();
	}
	
	public void connect()
	{
		while(true)
		{
			try
			{
				System.out.println("Criando a sala...");
				server = new ServerSocket(UPnP.internal);
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
			
			if(server == null)
			{
				System.out.println("Não foi possível criar a sala. Gostaria de tentar novamente?");
				System.out.println("1- Sim.");
				System.out.println("2- Não.");
				
				String escolha = scanner.nextLine();
				
				if(escolha.matches("[1-2]"))
				{
					if(escolha.equals("1"))
					{
						if(UPnP.hasUPnP)
						{
							UPnP.deletePortMapping();
							UPnP.internal++;
							UPnP.external++;
							UPnP.createNewPortMapping();
						}
						else
						{
							UPnP.internal++;
						}
					}
					if(escolha.equals("2"))
					{
						System.out.println("Programa terminado.");
						break;
					}
				}
				else
				{
					System.out.println("Comando inválido! Tente novamente.");
				}
			}
			else
			{
				System.out.println("Sala criada.");
				break;
			}
		}
	}
	
	public void run()
	{
		if(connected == false)
		{
			try 
			{
				client = server.accept();
				connected = true;
				System.out.println("Nova conexão com o cliente " +
					client.getInetAddress().getHostAddress());
				while (true) 
				{
					try {
						DataInputStream ent = new DataInputStream(
								client.getInputStream()
						);
						message.add(ent.readUTF());
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
				return;
			}
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
		connected = false;
		try
		{
			if(server != null)
			{
				server.close();
			}
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
	
	public boolean isConnected()
	{
		return connected;
	}

	private ServerSocket server;
	private Socket client;
	private boolean connected;
	int valor;
	ArrayList<String> message;
	Scanner scanner;
}
