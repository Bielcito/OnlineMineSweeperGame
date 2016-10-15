import java.util.Random;

public class Board{
	
	public Board(int linSize, int colSize, int mineNum)
	{
		this.linSize = linSize;
		this.colSize = colSize;
		this.mineNum = mineNum;
		this.rodadas = linSize*colSize - mineNum;
		
		slots = new Slot[linSize][colSize];
		
		for(int i = 0; i < linSize; i++)
		{
			for(int j = 0; j < colSize; j++)
			{
				slots[i][j] = new Slot(i, j);
			}
		}
	}
	
	public String toString()
	{
		String result = new String();
		
		result += "   ";
		for(int i = 0; i < colSize; i++)
		{
			result += i+1 + " ";
		}
		result += "\n";
		
		for(int i = 0; i < linSize; i++)
		{
			result += i+1 + " ";
			for(int j = 0; j < colSize; j++)
			{
				if(slots[i][j].isRevealed())
				{
					if(slots[i][j].getHasMine())
					{
						result += "|O";
					}
					else
					{
						int aux = slots[i][j].getMinesAround();
						result += "|" + (aux > 0 ? aux : " ");
					}
				}
				else
				{
					result += "|X";
				}
			}
			result += "|\n";
		}
		
		return result;
	}
	
	public void revealAll()
	{
		for(int i = 0; i < linSize; i++)
		{
			for(int j = 0; j < colSize; j++)
			{
				slots[i][j].reveal();
			}
		}
	}
	
	public Slot getSlot(int lin, int col) throws Exception
	{
		if(lin >= linSize)
		{
			throw new Exception("Parâmetro lin maior que o máximo permitido.");
		}
		if(col >= colSize)
		{
			throw new Exception("Parâmetro col maior que o máximo permitido.");
		}
		
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
	
	public Slot getSlot(Slot slot, Position p) throws Exception
	{
		int lin = slot.getLin();
		int col = slot.getCol();
		
		if(lin >= linSize)
		{
			throw new Exception("Parâmetro lin maior que o máximo permitido.");
		}
		if(col >= colSize)
		{
			throw new Exception("Parâmetro col maior que o máximo permitido.");
		}
		
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
	
	public void placeMines()
	{
		try
		{
			for(int i = 0; i < mineNum; i++)
			{
				getSlot(numbers[i] / linSize, numbers[i] % linSize).setAsMine();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void placeNumbers()
	{
		int contador;
		Slot pivo, aux;
		
		for(int i = 0; i < linSize; i++)
		{
			for(int j = 0; j < colSize; j++)
			{
				try
				{
					contador = 0;
					
					pivo = getSlot(i, j);
					
					for(Position p : Position.values())
					{
						aux = getSlot(pivo, p);
						if(aux != null && aux.getHasMine())
						{
							contador++;
						}
					}
					
					pivo.setMinesAround(contador);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	//returns a array with integers from 1 to number of positions on field.
	public void generateNumbers()
	{
		numbers = new int[linSize*colSize];
		int temp;
		int randomNumber;
		
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = i;
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
	}
	
	public int random(int low, int high)
	{
		Random random = new Random();
		
		return random.nextInt(high - low) + low;
	}
	
	public void reveal(int lin, int col)
	{
		try
		{
			Slot aux = getSlot(lin, col);
			if(aux != null)
			{
				if(!aux.isRevealed())
				reveal(aux, false);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void reveal(Slot slot, boolean terminal)
	{
		try
		{
			slot.reveal();
			rodadas--;
			
			if(slot.getHasMine())
			{
				revealAll();
				rodadas = -1;
				return;
			}
			else if(slot.getMinesAround() > 0)
			{
				return;
			}
			
			if(!terminal)
			{
				Position[] nearest = {Position.TOP, Position.BOTTOM, Position.LEFT, Position.RIGHT};
				Slot aux;
				for(int i = 0; i < nearest.length; i++)
				{
					aux = getSlot(slot, nearest[i]);
					if(aux != null && !aux.isRevealed())
					{
						if(aux.getHasMine())
						{
							return;
						}
						else if(aux.getMinesAround() > 0)
						{
							reveal(aux, true);
						}
						else
						{
							reveal(aux, false);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public int getLinSize()
	{
		return linSize;
	}
	
	public int getColSize()
	{
		return colSize;
	}
	
	public int getRodadas()
	{
		return rodadas;
	}
	
	public String getNumbers()
	{
		String result = new String();
		
		for(int i = 0; i < numbers.length; i++)
		{
			result += numbers[i] + " ";
		}
		
		return result;
	}
	
	public void setNumbers(int[] numbers)
	{
		this.numbers = numbers;
	}

	private Slot[][] slots;
	private int linSize, colSize;
	private int rodadas;
	private int mineNum;
	private int[] numbers;
}
