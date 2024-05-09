package startWindow;

import java.io.*;
import java.net.*;

public class Client {
	
	//ATTRIBUTI 
	private Socket server;
	private DataOutputStream toServer;
	private BufferedReader byServer;
	private boolean connected;
	
	//METODI 
	
	// constructor 
	Client()
	{
		try
		{
			connected = true;
			server = new Socket(InetAddress.getLocalHost(), 49200);
			toServer = new DataOutputStream(server.getOutputStream());
			byServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
			
		}
		catch(Exception e) 
		{
			connected = false;
		}
	}
	
	public boolean isConnected() {
		return connected;
	}

	public int sendMsg(String msg)
	{
		if(msg.charAt(msg.length() - 1) != '\n')
		{
			msg += '\n'; //la aggiungo
		}
		
		try
		{
			toServer.writeBytes(msg);
			return 1;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	
	public String readResponse()
	{
		try 
		{
			return byServer.readLine();
		}
		catch(Exception e) 
		{
			return null;
		}
	}
		
		

}
