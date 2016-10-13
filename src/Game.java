import java.util.Random;

public class Game {
	
	public Game(int linSize, int colSize, int mineNum)
	{
		this.linSize = linSize;
		this.colSize = colSize;
		
		board = new Board(linSize, colSize);
		
		placeMines(mineNum);
		
		board.printBoard();
	}
	
	//returns a array with integers from 1 to number of positions on field.
	public int[] generateNumbers()
	{
		int numbers[] = new int[linSize*colSize];
		int temp;
		int randomNumber;
		
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = i+1;
		}
		
		for(int i = 0; i < numbers.length; i++) // do it for each number of array
		{
			//Generate random number;
			randomNumber = random(0, numbers.length);
			//System.out.println("Rand: " + randomNumber);
			
			//Swap positions:
			temp = numbers[i];
			numbers[i] = numbers[randomNumber];
			numbers[randomNumber] = temp;
		}
		
		return numbers;
	}
	
	public void placeMines(int mineNum)
	{
		int[] numbers = generateNumbers();
		
		for(int i = 0; i < mineNum; i++)
		{
			System.out.println("number: " + numbers[i]);
			System.out.println("lin: " + numbers[i] / linSize);
			System.out.println("col: " + numbers[i] % linSize);
			board.getSlot(numbers[i] / linSize, numbers[i] % linSize).setAsMine();
		}
	}
	
	public int random(int low, int high)
	{
		Random random = new Random();
		
		return random.nextInt(high - low) + low;
	}
	
	private Board board;
	private int linSize, colSize;
}