import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
	
	Application()
	{		
		try
		{
			game = new Game(5, 5, 3);
			game.start();
			
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void createNewPortMapping(String externalPort, String internalPort)
	{
		try {
			String command = "java -jar C:\\Users\\Bielcito\\Documents\\Projetos\\\\\"Eclipse Projects\"\\OnlineMineSweeperGame\\src\\portmapper-2.0.0 -add -externalPort "+ externalPort + " -internalPort " + internalPort + " -protocol tcp";
			ProcessBuilder builder = new ProcessBuilder(
			        "cmd.exe", "/c", command);
			    builder.redirectErrorStream(true);
			    Process p = builder.start();
			    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    String line;
			    while (true) {
			        line = r.readLine();
			        if (line == null) { break; }
			        System.out.println(line);
			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createNewPortMapping(String externalPort, String internalPort, String ip)
	{
		try {
			String command = "java -jar C:\\Users\\Bielcito\\Documents\\Projetos\\\\\"Eclipse Projects\"\\OnlineMineSweeperGame\\src\\portmapper-2.0.0 -add -externalPort "+ externalPort + " -internalPort " + internalPort + " -ip "+ ip + " -protocol tcp";
			ProcessBuilder builder = new ProcessBuilder(
			        "cmd.exe", "/c", command);
			    builder.redirectErrorStream(true);
			    Process p = builder.start();
			    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    String line;
			    while (true) {
			        line = r.readLine();
			        if (line == null) { break; }
			        System.out.println(line);
			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Game game;
}
