
public class Slot {
	public Slot()
	{
		hasMine = false;
	}
	
	public void setMinesAround(int num)
	{
		minesAround = num;
	}
	
	public void setAsMine()
	{
		hasMine = true;
	}
	
	public int getMinesAround()
	{
		return minesAround;
	}
	
	private boolean hasMine;
	private int minesAround;
}
