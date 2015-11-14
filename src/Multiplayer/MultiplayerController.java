package Multiplayer;

import java.util.Observable;

public class MultiplayerController extends Observable
{
	//private final int DEFAULT_PORT = 5555;
	
	private MultiplayerWindow window;
	private MultiplayerConnection connection;
	private Thread connectionThread;
	
	private boolean locked = false;
	
	//application specific
	private boolean gameActive = false;
	private boolean opponentReady = false;
	private boolean localReady = false;
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
	
	/* do not call from within closeAll in Client or Server */
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
		
		/*investigate*/
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
	
	public synchronized void processData(Object o)
	{
		setChanged();
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
