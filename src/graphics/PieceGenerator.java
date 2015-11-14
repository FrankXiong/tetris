package graphics;

import java.awt.Color;
import java.util.Random;
import java.util.Stack;

/**
 * @author:xiongxianren
 * @description:颜色配置类
 */
public class PieceGenerator
{
	private final static int numberOfPieces = 7;
	
	public final static BlockColorScheme BCS_RED = new BlockColorScheme(
			new Color(255, 0, 0), 
			new Color(255, 128, 128), 
			new Color(166, 0, 0));
	
	private final static BlockColorScheme BCS_GREEN = new BlockColorScheme(
			new Color(0, 255, 0), 
			new Color(128, 255, 128), 
			new Color(0, 166, 0));
	
	private final static BlockColorScheme BCS_BLUE = new BlockColorScheme(
			new Color(0, 0, 255), 
			new Color(128, 128, 255), 
			new Color(0, 0, 166));
	
	private final static BlockColorScheme BCS_YELLOW = new BlockColorScheme(
			new Color(255, 255, 0), 
			new Color(255, 255, 187), 
			new Color(183, 183, 0));
	
	private final static BlockColorScheme BCS_ORANGE = new BlockColorScheme(
			new Color(251, 148, 55), 
			new Color(252, 183, 120), 
			new Color(187, 91, 4));
	
	private final static BlockColorScheme BCS_PURPLE = new BlockColorScheme(
			new Color(128, 0, 255), 
			new Color(197, 138, 255), 
			new Color(91, 0, 183));
	
	private final static BlockColorScheme BCS_GRAY = new BlockColorScheme(
			new Color(214, 214, 214), 
			new Color(255, 255, 255), 
			new Color(130, 130, 130));
	
	public static Stack<Integer> getPieceStack()
	{
		Random r = new Random();
		Stack<Integer> s = new Stack<Integer>();
		
		for(int i = 0; i < 1000; i++)
		{
			s.push(new Integer(r.nextInt(7)));
		}
		
		return s;
	}
	
	public static int getRandomNumber(int low, int high)
	{
		Random r = new Random();
		
		return low + r.nextInt(high);
	}
	
	public static BlockColorScheme getRandomBlockColorScheme()
	{
		Random r = new Random();
		
		switch(r.nextInt(7))
		{
			case 0: return BCS_BLUE;
			case 1: return BCS_RED;
			case 2: return BCS_GREEN;
			case 3: return BCS_YELLOW;
			case 4: return BCS_PURPLE;
			case 5: return BCS_ORANGE;
			case 6: return BCS_GRAY;
		}
		
		return BCS_GRAY;
	}
	
	//随机生成方块
	public static TetrisShape getRandomPiece()
	{
		TetrisShape s = null;
		Random r = new Random();
		
		switch(r.nextInt(numberOfPieces))
		{
			case 0:
			{
				s = new O(4, 0, BCS_BLUE);
				break;
			}
			case 1:
			{
				s = new I(4, 0, BCS_RED);
				break;
			}
			case 2:
			{
				s = new T(4, 0, BCS_GREEN);
				break;
			}
			case 3:
			{
				s = new S(4, 0, BCS_YELLOW);
				break;
			}
			case 4:
			{
				s = new Z(4, 0, BCS_PURPLE);
				break;
			}
			case 5:
			{
				s = new L(4, 0, BCS_ORANGE);
				break;
			}
			case 6:
			{
				s = new J(4, 0, BCS_GRAY);
				break;
			}
		}
		
		return s;
	}
}
