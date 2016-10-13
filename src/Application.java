import java.util.Scanner;

public class Application {
	
	Application()
	{
		sair = false;
		server = new Server(12345);
		client = new Client(12345);
		game = new Game(3, 3, 1);
		
		server.start();
		start();
	}
	
	public Server Server()
	{
		return server;
	}
	
	public Client Client()
	{
		return client;
	}
	
	public void close()
	{
		client.close();
		server.interrupt();
		server.close();
	}
	
	public void start()
	{
		Scanner scanner = new Scanner(System.in);
		int valor;
		while(!sair)
		{
			valor = scanner.nextInt();
			scanner.nextLine();
			if(valor == 1)
			{
				client.send(scanner.nextLine());
			}
			else if(valor == 2)
			{
				sair = true;
			}
		}
		
		scanner.close();
		close();
	}
	
	private Client client;
	private Server server;
	private Game game;
	private boolean sair;
}
