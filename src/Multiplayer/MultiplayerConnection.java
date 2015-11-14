package Multiplayer;

public abstract class MultiplayerConnection implements Runnable
{
	public abstract void write(Object o);
	public abstract void closeAll();
	public abstract boolean isConnected();
}
