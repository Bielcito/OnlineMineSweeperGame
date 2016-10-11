public class P2PSocket {
	public static void main(String args[])
	{
		Servidor servidor = new Servidor(12345);
		Client cliente = new Client();
		
		servidor.start();
		cliente.start();
	}
}
