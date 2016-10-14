import java.util.Scanner;

public class Game {
	
	public Game(int linSize, int colSize, int mineNum) throws Exception
	{
		if(mineNum > linSize*colSize)
		{
			throw new Exception("Número de minas maior que o número de espaços no campo.");
		}
		
		board = new Board(linSize, colSize, mineNum);
	}
	
	public void start()
	{
		Scanner scanner = new Scanner(System.in);
		
		while(true)
		{
			System.out.print(board);
			
			if(!(board.getRodadas() > 0))
			{
				System.out.println("O JOGO ACABOU! :) AGORA VAI CATAR COQUINHO");
			}
			
			System.out.println("Insira o comando: ");
			String text = scanner.nextLine();
			if(text.matches("[1-" + board.getLinSize() + "][ ][1-" + board.getColSize() + "]"))
			{
				String[] command = text.split(" ");
				board.reveal(Integer.parseInt(command[0])-1, Integer.parseInt(command[1])-1);
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
	
	private Board board;
}