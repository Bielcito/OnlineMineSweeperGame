import java.io.IOException;
import java.io.InputStream;
import java.time.chrono.IsoChronology;
import java.util.Scanner;

public class Game {
	
	public Game(int linSize, int colSize, int mineNum) throws Exception
	{
		if(mineNum > linSize*colSize)
		{
			throw new Exception("Número de minas maior que o número de espaços no campo.");
		}
		
		scanner = new Scanner(System.in);
		board = new Board(linSize, colSize, mineNum);
		myturn = false;
	}
	
	public void connectState()
	{
		System.out.println("Bem vindo ao MineSweeper Online!");
		System.out.println("Selecione uma opção:");
		System.out.println("1- Criar uma Sala.");
		System.out.println("2- Entrar em uma Sala.");
		
		String escolha = scanner.nextLine();
		
		if(escolha.matches("[1-2]"))
		{
			if(escolha.equals("1"))
			{
				connection = new Server();
				t = new Thread(connection);
				t.start();
				waitForClient();
			}
			else if(escolha.equals("2"))
			{
				connection = new Client();
				t = new Thread(connection);
				t.start();
			}
		}
	}
	
	/**
	 * Neste método será decidido quem irá jogar primeiro.
	 */
	public void turnState()
	{
		int eu, ele;
		String escolha;
		
		System.out.println("Vamos decidir quem irá jogar primeiro.");
		
		//Envia o comando atual:
		while(true)
		{
			System.out.println("Escolha uma opção:");
			System.out.println("1- Pedra.");
			System.out.println("2- Papel.");
			System.out.println("3- Tesoura.");
			escolha = scanner.nextLine();
			
			if(escolha.matches("[1-3]"))
			{
				eu = Integer.parseInt(escolha);
				if(eu == 1)
				{
					connection.send("1");
				}
				else if(eu == 2)
				{
					connection.send("2");
				}
				else
				{
					connection.send("3");
				}
				
				//Pega o comando do outro:
				ele = Integer.parseInt(connection.receive());
				
				if(eu == ele)
				{
					System.out.println("Empate!");
					System.out.println();
				}
				else if(eu == 1 && ele == 3 || eu == 2 && ele == 1 || eu == 3 && ele == 2)
				{
					System.out.println("Você venceu! Será o primeiro a jogar.");
					myturn = true;
					break;
				}
				else
				{
					System.out.println("Você perdeu. Será o segundo a jogar.");
					myturn = false;
					break;
				}
			}
			else
			{
				System.out.println("Comando inválido! Digite um número de 1 a 3");
			}
		}
	}
	
	public boolean gameIsFinished()
	{
		if(myturn)
		{
			if(board.getRodadas() == 0)
			{
				System.out.println("Parece que vocês dois são inteligentes! EMPATE!");
				return true;
			}
			else if(board.getRodadas() < 0)
			{
				System.out.println("O seu inimigo explodiu. Você VENCEU!");
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(board.getRodadas() == 0)
			{
				System.out.println("Parece que vocês dois são inteligentes! EMPATE!");
				return true;
			}
			else if(board.getRodadas() < 0)
			{
				System.out.println("OPS! Você explodiu, que pena. Você PERDEU!");
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	public void playState()
	{
		while(true)
		{
			System.out.print(board);
			
			if(gameIsFinished())
			{
				return;
			}
			
			if(myturn)
			{
				while(true)
				{
					System.out.println("Insira um comando: ");
					String text = scanner.nextLine();
					if(text.matches("[1-" + board.getLinSize() + "][ ][1-" + board.getColSize() + "]"))
					{
						String[] command = text.split(" ");
						board.reveal(Integer.parseInt(command[0])-1, Integer.parseInt(command[1])-1);
						connection.send(text);
						break;
					}
					else if(text.matches("exit"))
					{
						scanner.close();
						return;
					}
					else
					{
						System.out.println("Comando inválido! Tente novamente.");
					}
				}
			}
			else
			{
				System.out.println("Esperando o outro jogador jogar...");
				String text = connection.receive();
				
				System.out.println("O outro jogador enviou: " + text);
				String[] command = text.split(" ");
				board.reveal(Integer.parseInt(command[0])-1, Integer.parseInt(command[1])-1);
			}
			
			myturn = !myturn;
		}
	}
	
	public void generateBoard()
	{
		if(myturn)
		{
			board.generateNumbers();
			board.placeMines();
			board.placeNumbers();
			connection.send(board.getNumbers());
		}
		else
		{
			String text = connection.receive();
			String[] texts = text.split(" ");
			int[] numbers = new int[texts.length];
			for(int i = 0; i < texts.length; i++)
			{
				numbers[i] = Integer.parseInt(texts[i]);
			}
			
			board.setNumbers(numbers);
			board.placeMines();
			board.placeNumbers();
		}
	}
	
	public synchronized void waitForClient()
	{
		System.out.println("Esperando o outro jogador entrar..."); 
		while(true)
		{
			if(!connection.isConnected())
			{
				try {
					wait(1000);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
			else
			{
				System.out.println("Conectado!");
				break;
			}
		}
	}
	
	public void start()
	{
		connectState();
		
		if(connection.isConnected() == false)
		{
			return;
		}
		
		turnState();
		
		generateBoard();
		
		playState();
	}
	
	Thread t;
	private Board board;
	private Connection connection;
	private boolean myturn;
	private Scanner scanner;
}