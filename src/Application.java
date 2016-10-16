public class Application {
	
	Application()
	{		
		try
		{
			game = new Game(5, 5, 3);
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void start()
	{
		game.start();
	}
	
	private Game game;
}
