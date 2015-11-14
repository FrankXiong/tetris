package Multiplayer;

import java.io.*;
import java.net.*;

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
				mpc.log("Error writing data to output stream");
				closeAll();
			}
		}
		else
		{
			mpc.log("Error writing to \"out\"");
		}
	}

	public void run()
	{
		try
		{
			mpc.log("Connecting to \"" + addy + ":" + port + "\"...");
			client = new Socket(addy, port);

			mpc.log("Connected, creating streams...");
			//order of creation of the streams is important, RTFM
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
			mpc.log("Error connecting to server");
			closeAll();
			return;
		}
		
		Object o;
		
		/*
		try
		{
			mpc.log("Waiting for ranking information...");
			boolean rankRequest = in.readBoolean();
			
			switch(JOptionPane.showConfirmDialog(null, "These games will " + (rankRequest ? "" : "not") + " be ranked, do you agree?"))
			{
				case JOptionPane.OK_OPTION:
				{
					mpc.setRanked(rankRequest);
					mpc.log("Sending confirmation...");
					out.writeBoolean(true);
					break;
				}
				case JOptionPane.NO_OPTION:
				case JOptionPane.CANCEL_OPTION:
				{
					closeAll();
					return;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		*/
		
		mpc.log("Good to go!");
		
		try
		{
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
			if(client != null)	client.close();
			if(in != null)		in.close();
			if(out != null)		out.close();
			
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
