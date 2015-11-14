package Multiplayer;

import java.io.*;
import java.net.*;

/**
 * @author:xiongxianren
 * @description:服务器端类
 */
public class Server extends MultiplayerConnection implements Runnable
{
	private MultiplayerController mpc;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket client = null;
	private ServerSocket server = null;
	private int port;

	public Server(MultiplayerController controller, int port)
	{
		this.mpc = controller;
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
				mpc.log("Error：writing data to output stream");
				closeAll();
			}
		}
		else
		{
			mpc.log("Error：writing to \"out\"");
		}
	}
	
	public void run()
	{
		try
		{
	
			server = new ServerSocket(port, 5);
			mpc.log("Created ServerSocket");
		}
		catch(IOException e)
		{
			mpc.log("Error：Creating ServerSocket");
			closeAll();
			return;
		}
		
		try
		{
			mpc.log("Listening for connection...");
			//	创建socket连接			
			client = server.accept();
			mpc.log("Connected, creating streams...");
			//输入流中读取对象类数据
			out = new ObjectOutputStream(client.getOutputStream());
			//将对象类型的数据写入到底层输入流
			in = new ObjectInputStream(client.getInputStream());
			mpc.log("Streams created");
		}
		catch(SocketException e)
		{
			mpc.log("Error：ServerSocket closed manually");
			closeAll();
			return;
		}
		catch(IOException e)
		{
			mpc.log("Error：Listening for connections");
			closeAll();
			return;
		}
	
		Object o;
		//发送数据		
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
	//关闭连接	
	public void closeAll()
	{
		try
		{
			if(client != null) 	
				client.close();
			if(server != null) 	
				server.close();
			if(in != null) 		
				in.close();
			if(out != null) 	
				out.close();
			mpc.cleanUp();
		}
		catch(IOException e){}
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
