package Multiplayer;

import java.util.Observable;

public class MultiplayerController extends Observable
{
	
	private MultiplayerWindow window;
	private MultiplayerConnection connection;
	private Thread connectionThread;
	
	private boolean locked;
	//游戏状态	
	private boolean gameActive = false;
	private boolean opponentReady = false;
	private boolean localReady = false;
	//双方的输赢次数
	private int wins, losses;
	
	public MultiplayerController()
	{
		this.window = new MultiplayerWindow(this);
	}
	
	public boolean startServer(int port)
	{
		if(!isLocked())
			setLock(true);
		else
			return false;
		
		connection = new Server(this, port);
		//创建客户端和服务器端的连接线程		
		connectionThread = new Thread(connection);
		connectionThread.setPriority(Thread.MAX_PRIORITY);
		connectionThread.start();
		
		window.setVisible(true);
		
		return true;
	}
	
	public boolean startClient(String remoteAddress, int port)
	{
		if(!isLocked())
			setLock(true);
		else
			return false;
		
		connection = new Client(this, remoteAddress, port);
		
		connectionThread = new Thread(connection);
		connectionThread.setPriority(Thread.MAX_PRIORITY);
		connectionThread.start();
		
		window.setVisible(true);
		
		return true;
	}
	
	//关闭客户端和服务器端的连接，释放资源，当退出时调用	
	public void closeAll()
	{	
		if(connection != null)
			connection.closeAll();
		
		cleanUp();
		
		window.setVisible(false);
	}
	
	public void cleanUp()
	{
		deleteObservers();
		setLock(false);
		
		wins = losses = 0;
		
		connection = null;
		connectionThread = null;
	}
	
	public boolean isConnected()
	{
		if(connection != null)
			return connection.isConnected();
		return false;
	}
	
	public void write(Object o)
	{
		if(connection != null && connection.isConnected())
		{
			connection.write(o);
		}
	}
	//传递数据	
	public void writeGameData(Object o)
	{
		if(connection != null && connection.isConnected() && isGameActive())
		{
			connection.write(o);
		}
	}
	
	public boolean isOpponentReady()
	{
		return this.opponentReady;
	}
	
	public void setOpponentReady(boolean r)
	{
		this.opponentReady = r;
	}
	
	public boolean isLocalReady()
	{
		return this.localReady;
	}
	
	public void setLocalReady(boolean r)
	{
		this.localReady = r;
	}
	//异步发送数据	
	public synchronized void processData(Object o)
	{
		setChanged();
		//当对象发生改变，通知观察者		
		notifyObservers(o);
	}
	
	public synchronized void log(String s)
	{
		window.addElement(s);
	}
	
	public boolean isLocked()
	{
		return locked;
	}
	
	public void setLock(boolean lock)
	{
		locked = lock;
	}
	
	public void setGameActive(boolean active)
	{
		this.gameActive = active;
	}
	
	public boolean isGameActive()
	{
		return this.gameActive;
	}
	
	public void addWin()
	{
		++this.wins;
	}
	
	public void addLoss()
	{
		++this.losses;
	}
	
	public int getWins()
	{
		return this.wins;
	}
	
	public int getLosses()
	{
		return this.losses;
	}
}
