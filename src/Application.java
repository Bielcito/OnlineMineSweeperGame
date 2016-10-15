import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
	
	Application()
	{		
		try
		{
			String command = "java -jar C:\\Users\\Bielcito\\Documents\\Projetos\\\\\"Eclipse Projects\"\\OnlineMineSweeperGame\\src\\portmapper-2.0.0 -add -externalPort 12345 -internalPort 12345 -protocol tcp";
			
			/*game = new Game(5, 5, 3);
			game.start();*/
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
			
		}
		catch (Exception e) 
		{
			e.getMessage();
		}
	}
	
	private Game game;
}
