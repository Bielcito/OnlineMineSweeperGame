
interface Connection extends Runnable{
	
	abstract void connect();
	abstract void send(String text);
	abstract String receive();
	abstract boolean isConnected();
}
