
public class Slot {
	public Slot(int lin, int col)
	{
		hasMine = false;
		revealed = false;
		this.lin = lin;
		this.col = col;
	}
	
	public void setMinesAround(int num)
	{
		minesAround = num;
	}
	
	public void setAsMine()
	{
		hasMine = true;
	}
	
	public boolean getHasMine()
	{
		return this.hasMine;
	}
	
	public int getMinesAround()
	{
		return minesAround;
	}
	
	public boolean isRevealed()
	{
		return revealed;
	}
	
	public void reveal()
	{
		revealed = true;
	}
	
	public int getLin()
	{
		return lin;
	}
	
	public int getCol()
	{
		return col;
	}
	
	private boolean hasMine;
	private int minesAround;
	private boolean revealed;
	private int lin, col;
}
