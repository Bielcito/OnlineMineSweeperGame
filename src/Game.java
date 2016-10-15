import java.time.chrono.IsoChronology;
import java.util.Scanner;

public class Game {
	
	public Game(int linSize, int colSize, int mineNum) throws Exception
	{
		if(mineNum > linSize*colSize)
		{
			throw new Exception("N�mero de minas maior que o n�mero de espa�os no campo.");
		}
		
		scanner = new Scanner(System.in);
		board = new Board(linSize, colSize, mineNum);
		myturn = false;
	}
	
	public void connectState()
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Bem vindo ao MineSweeper Online!");
		System.out.println("Selecione uma op��o:");
		System.out.println("1- Criar uma Sala.");
		System.out.println("2- Entrar em uma Sala.");
		
		String escolha = scanner.nextLine();
		
		if(escolha.matches("[1-2]"))
		{
			if(escolha.equals("1"))
			{
				connection = new Server(12345);
				Thread t = new Thread(connection);
				t.start();
				waitForClient();
			}
			else if(escolha.equals("2"))
			{
				connection = new Client(12345);
				Thread t = new Thread(connection);
				t.start();
			}
		}
	}
	
	public synchronized void waitForClient()
	{
		Scanner scanner = new Scanner(System.in);
		while(true)
		{
			if(!connection.isConnected())
			{
				try {
					wait(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Conectado!");
				break;
			}
		}
	}
	
	/**
	 * Neste m�todo ser� decidido quem ir� jogar primeiro.
	 */
	public void turnState()
	{
		int eu, ele;
		String escolha;
		
		System.out.println("Vamos decidir quem ir� jogar primeiro.");
		
		//Envia o comando atual:
		while(true)
		{
			System.out.println("Escolha uma op��o:");
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
					System.out.println("Voc� venceu! Ser� o primeiro a jogar.");
					myturn = true;
					break;
				}
				else
				{
					System.out.println("Voc� perdeu. Ser� o segundo a jogar.");
					myturn = false;
					break;
				}
			}
			else
			{
				System.out.println("Comando inv�lido! Digite um n�mero de 1 a 3");
			}
		}
	}
	
	public void playState()
	{
		while(true)
		{
			System.out.print(board);
			
			if(board.getRodadas() == 0)
			{
				System.out.println("VOC� VENCEU! :) AGORA VAI CATAR COQUINHO");
				break;
			}
			else if(board.getRodadas() < 0)
			{
				System.out.println("VOC� PERDEU! >:( AGORA X� DAQUI CARNI�A.");
				break;
			}
			
			if(myturn)
			{
				System.out.println("Insira o comando: ");
				String text = scanner.nextLine();
				if(text.matches("[1-" + board.getLinSize() + "][ ][1-" + board.getColSize() + "]"))
				{
					String[] command = text.split(" ");
					board.reveal(Integer.parseInt(command[0])-1, Integer.parseInt(command[1])-1);
					connection.send(text);
				}
				else if(text.matches("exit"))
				{
					scanner.close();
					return;
				}
				else
				{
					System.out.println("Comando inv�lido! Tente novamente.");
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
	
	public void start()
	{
		connectState();
		
		turnState();
		
		generateBoard();
		
		playState();
	}
	
	private Board board;
	private Connection connection;
	private boolean myturn;
	private Scanner scanner;
}