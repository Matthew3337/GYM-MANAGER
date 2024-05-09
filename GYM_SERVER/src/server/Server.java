package server;

import java.io.IOException;
import java.net.*;

public class Server {
	
	private ServerSocket server;
	private Socket client;
	private boolean attivo;
	//METODI
	
	//costruttore
	Server()
	{
		try {
			server = new ServerSocket(49200);
			attivo = true; //server attivio
		} catch (IOException e) {
			attivo = false; //server non attivo 
		}
	}
	
	public void run()
	{
		while(true)
		{
			try {
				client = server.accept();
			} catch (IOException e) {} //la porta di benvenuto accetta il client
			
			//crea thread e passa il client al thread
			ServerThread s = new ServerThread(client);
			s.start();
		}
	}

	public boolean isAttivo() {
		return attivo;
	}

}
