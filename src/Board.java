
public class Board {
	
	public Board(int linSize, int colSize)
	{
		this.linSize = linSize;
		this.colSize = colSize;
		
		slots = new Slot[linSize][colSize];
		
		for(int i = 0; i < linSize; i++)
		{
			for(int j = 0; j < colSize; j++)
			{
				slots[i][j] = new Slot();
			}
		}
	}
	
	public void printBoard()
	{
		for(int i = 0; i < linSize; i++)
		{
			for(int j = 0; j < colSize; j++)
			{
				System.out.print(slots[i][j].get + " ");
			}
			System.out.println();
		}
	}
	
	public Slot getSlot(int lin, int col)
	{
		return slots[lin][col];
	}
	
	public boolean hasSlot(int lin, int col, Position p)
	{
		switch(p)
		{
		case TOP_LEFT:
			return lin > 0 && col > 0;
		case TOP:
			return lin > 0;
		case TOP_RIGHT:
			return lin > 0 && col < colSize-1;
		case LEFT:
			return col > 0;
		case RIGHT:
			return col < colSize-1;
		case BOTTOM_LEFT:
			return lin < linSize-1 && col > 0;
		case BOTTOM:
			return lin < linSize-1;
		case BOTTOM_RIGHT:
			return lin < linSize-1 && col < colSize-1;
		default:
			return false;
		}
	}
	
	public Slot getSlot(int lin, int col, Position p)
	{
		switch(p)
		{
		case TOP_LEFT:
			return hasSlot(lin, col, p) ? slots[lin-1][col-1] : null;
		case TOP:
			return hasSlot(lin, col, p) ? slots[lin-1][col] : null;
		case TOP_RIGHT:
			return hasSlot(lin, col, p) ? slots[lin-1][col+1] : null;
		case LEFT:
			return hasSlot(lin, col, p) ? slots[lin][col-1] : null;
		case RIGHT:
			return hasSlot(lin, col, p) ? slots[lin][col+1] : null;
		case BOTTOM_LEFT:
			return hasSlot(lin, col, p) ? slots[lin+1][col-1] : null;
		case BOTTOM:
			return hasSlot(lin, col, p) ? slots[lin+1][col] : null;
		case BOTTOM_RIGHT:
			return hasSlot(lin, col, p) ? slots[lin+1][col+1] : null;
		default:
			return null;
		}
	}
	

	private Slot[][] slots;
	private int linSize, colSize;
}
