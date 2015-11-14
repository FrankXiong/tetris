package Multiplayer;

import java.io.*;
import java.net.*;

/**
 * @author:xiongxianren
 * @description:客户端类
 */
public class Client extends MultiplayerConnection implements Runnable
{
	private MultiplayerController mpc;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket client;
	private int port;
	private String addy;
	
	public Client(MultiplayerController controller, String address, int port)
	{
		this.mpc = controller;
		this.addy = address;
		this.port = port;
	}

	public void write(Object o)
	{
		if(out != null && client != null && client.isConnected())
		{
			try
			{
				out.writeObject(o);
			}
			catch(IOException e)
			{
				mpc.log("Error: writing data to output stream");
				closeAll();
			}
		}
		else
		{
			mpc.log("Error: writing to \"out\"");
		}
	}

	public void run()
	{
		try
		{
			mpc.log("Connecting to \"" + addy + ":" + port + "\"...");
			client = new Socket(addy, port);

			mpc.log("Connected, creating streams...");
			
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			mpc.log("Streams created");
		}
		catch(UnknownHostException e)
		{
			mpc.log("Error: Unknown host");
			closeAll();
			return;
		}
		catch(IOException e)
		{
			mpc.log("Error: Can not connect to server");
			closeAll();
			return;
		}
		
		Object o;
		//发送数据		
		try
		{
			//从ObjectInputStream中读取对象			
			while((o = in.readObject()) != null)
			{
				mpc.processData(o);
			}
		}
		catch(Exception e)
		{
			mpc.log("Connection closed");
		}
		finally
		{
			closeAll();
			mpc.log("cleaned up: thread terminating");
		}
	}
	
	public void closeAll()
	{
		try
		{
			if(client != null)	
				client.close();
			if(in != null)		
				in.close();
			if(out != null)		
				out.close();
			
			mpc.cleanUp();
		}
		catch(IOException e) {}
		finally
		{
			mpc.log("Closed all sockets/streams");
		}
	}

	public boolean isConnected()
	{
		if(client != null)
			return client.isConnected();
		return false;
	}
}
