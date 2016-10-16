import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class UPnP {
	 public static void createNewPortMapping()
	{
		 if(UPnP.isExternal)
		 {
			 try {
					String command = "java -jar C:\\Users\\Bielcito\\Documents\\Projetos\\\\\"Eclipse Projects\"\\OnlineMineSweeperGame\\src\\portmapper-2.0.0.jar -add -externalPort "+ UPnP.external.toString() + " -internalPort " + UPnP.internal.toString() + " -ip "+ ip + " -protocol tcp";
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
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
		 }
		 else
		 {
			 try {
					String command = "java -jar C:\\Users\\Bielcito\\Documents\\Projetos\\\\\"Eclipse Projects\"\\OnlineMineSweeperGame\\src\\portmapper-2.0.0.jar -add -externalPort "+ UPnP.external.toString() + " -internalPort " + UPnP.internal.toString() + " -protocol tcp";
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
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
		 }
		
	}
	 
	public static void deletePortMapping()
	{
		try {
			String command = "java -jar C:\\Users\\Bielcito\\Documents\\Projetos\\\\\"Eclipse Projects\"\\OnlineMineSweeperGame\\src\\portmapper-2.0.0.jar -delete -externalPort "+ UPnP.external.toString() + " -protocol tcp";
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Integer internal = 12400;
	public static Integer external = 12400;
	public static String ip;
	public static boolean hasUPnP = false;
	public static boolean isExternal = false;
}
