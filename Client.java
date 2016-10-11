import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread
{
	public void run()
	{
		try
		{
			System.out.println("Cliente: ");
			Socket cliente = new Socket("127.0.0.1", 12350);
			System.out.println("O cliente se conectou ao servidor!");
			DataOutputStream sai = new DataOutputStream(cliente.getOutputStream());
			sai.writeInt(15);
			DataInputStream ent = new DataInputStream(cliente.getInputStream());
			String recebido = ent.readUTF();
			System.out.println("Recebido do servidor: " + recebido);
			cliente.close();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
