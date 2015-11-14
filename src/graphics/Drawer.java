package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Drawer
{
	public static int blockSize = 20;

	public static int rows = 20;
	public static int columns = 10;
	public static int borderWidth = 5;
	
	public static int bevelWidth = blockSize - (blockSize - 2);
	
	public static int boardWidth = blockSize * columns;
	public static int boardHeight = blockSize * rows;
	
	public static int nextBoardWidth = blockSize * 6;
	public static int nextBoardHeight = blockSize * 4;
	
	public static int offset_x = 50;
	public static int offset_y = 40;
	
	public static int progress_offset_x = 10;
	public static int progress_offset_y = offset_y;
	public static int progressWidth = blockSize + (borderWidth * 2);
	
	public static int next_offset_x = offset_x + boardWidth + 15;
	public static int next_offset_y = offset_y;
	
	public static Dimension recalculate(int blockSizeRequest)
	{
		int w, h;
		
		blockSize = blockSizeRequest;
		
		bevelWidth = blockSize - (blockSize - 2);
		
		progressWidth = blockSize + (borderWidth * 2);
		
		boardWidth = blockSize * columns;
		boardHeight = blockSize * rows;
		
		nextBoardWidth = blockSize * 6;
		nextBoardHeight = blockSize * 4;
		
		offset_x = progressWidth + 15;
		next_offset_x = offset_x + boardWidth + 15;
		
		w = Drawer.boardWidth + Drawer.nextBoardWidth + Drawer.offset_x + (2 * (Drawer.next_offset_x - (Drawer.offset_x + Drawer.boardWidth)));
		h = Drawer.boardHeight + (2 * (Drawer.offset_y + 10));
		
		return new Dimension(w, h);
	}
	
	public static void drawExo(Graphics g, int col, int row, Color c)
	{
		g.setColor(c);
		g.drawRect(offset_x + (col * blockSize) + 1, offset_y + (row * blockSize) + 1, blockSize - 3, blockSize - 3);
	}
	
	private static void drawBlock(Graphics g, int col, int row, BlockColorScheme colorScheme, int offx, int offy)
	{
		g.setColor(colorScheme.getMainColor());
		g.fillRect(offx + (col * blockSize), offy + (row * blockSize), blockSize, blockSize);
		
		g.setColor(colorScheme.getLightColor());
		g.fillRect(offx + (col * blockSize), offy + (row * blockSize), bevelWidth, blockSize);
		g.fillRect(offx + (col * blockSize), offy + (row * blockSize), blockSize - bevelWidth, bevelWidth);
		
		g.setColor(colorScheme.getDarkColor());
		g.fillRect(offx + (col * blockSize) + bevelWidth, offy + (row * blockSize) + (blockSize - bevelWidth), (blockSize - bevelWidth), bevelWidth);
		g.fillRect(offx + (col * blockSize) + (blockSize - bevelWidth), offy + (row * blockSize), bevelWidth, blockSize);
	}
	
	public static void drawTakenBlock(Graphics g, int col, int row, BlockColorScheme colorScheme)
	{
		drawBlock(g, col, row, colorScheme, offset_x, offset_y);
	}
	
	public static void drawNextBlock(Graphics g, int col, int row, BlockColorScheme colorScheme)
	{
		drawBlock(g, col, row, colorScheme, next_offset_x, next_offset_y);
	}
	
	public static void drawOpponentProgress(Graphics g, int row, BlockColorScheme colorScheme)
	{
		drawBlock(g, 0, row, colorScheme, progress_offset_x, progress_offset_y);
	}
}
