package graphics;

import java.awt.Graphics;

public abstract class TetrisShape
{
	public abstract void draw(Graphics g);

	public abstract void drawNext(Graphics g);
	
	public abstract void drawExoSkeleton(Block[][] grid, Graphics g);

	public abstract boolean checkOverlap(Block[][] grid);

	public abstract int geti();

	public abstract int getj();

	public abstract void seti(int i);

	public abstract void setj(int j);

	public abstract void nextState(Block[][] grid);

	public abstract boolean canMoveDown(Block[][] grid);

	public abstract boolean canMoveLeft(Block[][] grid);

	public abstract boolean canMoveRight(Block[][] grid);

	public abstract void moveDown();

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void fillGrid(Block[][] grid);
}
