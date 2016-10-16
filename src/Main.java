public class Main {
	public static void main(String args[])
	{
		if(args.length > 0)
		{
			if(args[0].equals("local"))
			{
				UPnP.hasUPnP = true;
				UPnP.isExternal = false;
			}
			else if(args[0].equals("external"))
			{
				UPnP.hasUPnP = true;
				UPnP.isExternal = true;
			}
			
			UPnP.createNewPortMapping();
		}
		
		Application app = new Application();
		app.start();
		
		if(args.length > 0)
		{
			UPnP.deletePortMapping();
		}
	}
}