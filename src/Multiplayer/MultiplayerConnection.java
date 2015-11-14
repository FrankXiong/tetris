package Multiplayer;

public abstract class MultiplayerConnection implements Runnable
{
	//传递数据	
	public abstract void write(Object o);
	//退出时调用，关闭连接，释放资源	
	public abstract void closeAll();
	//判断是否连接	
	public abstract boolean isConnected();
}
