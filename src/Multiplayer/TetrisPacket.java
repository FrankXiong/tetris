package Multiplayer;

import java.io.Serializable;
import java.util.Stack;

public class TetrisPacket implements Serializable
{
	private static final long serialVersionUID = -6156214738132350271L;
	
	private String msg;
	private Stack<Integer> s;
	
	public TetrisPacket(String message)
	{
		this.msg = message;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public void setStack(Stack<Integer> stk)
	{
		this.s = stk;
	}
	
	public Stack<Integer> getStack()
	{
		return this.s;
	}
}
