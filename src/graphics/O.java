package graphics;

import java.awt.Graphics;

/**
 * @author:xiongxianren
 * @description:形状O
 */
public class O extends TetrisShape
{
	private int i_, j_;
	public BlockColorScheme cs_;

	public O(int i, int j, BlockColorScheme cs)
	{
		this.i_ = i;
		this.j_ = j;
		this.cs_ = cs;
	}

	public void draw(Graphics g)
	{
		Drawer.drawTakenBlock(g, i_, j_, cs_);
		Drawer.drawTakenBlock(g, i_ + 1, j_, cs_);
		Drawer.drawTakenBlock(g, i_, j_ + 1, cs_);
		Drawer.drawTakenBlock(g, i_ + 1, j_ + 1, cs_);
	}

	public int geti()
	{
		return i_;
	}

	public int getj()
	{
		return j_;
	}

	public void seti(int i)
	{
		i_ = i;
	}

	public void setj(int j)
	{
		j_ = j;
	}

	public void nextState(Block[][] grid)
	{
		return;
	}

	public boolean canMoveDown(Block[][] grid)
	{
		if(		j_ + 2 < 20 && 
				!grid[i_][j_ + 2].taken &&
				!grid[i_ + 1][j_ + 2].taken)
		{
			return true;
		}
		return false;
	}

	public boolean canMoveLeft(Block[][] grid)
	{
		if(i_ - 1 >= 0 && !grid[i_ - 1][j_].taken
				&& !grid[i_ - 1][j_ + 1].taken)
		{
			return true;
		}
		return false;
	}

	public boolean canMoveRight(Block[][] grid)
	{
		if(i_ + 2 < 10 && !grid[i_ + 2][j_].taken
				&& !grid[i_ + 2][j_ + 1].taken)
		{
			return true;
		}
		return false;
	}

	public void moveDown()
	{
		this.j_++;
	}

	public void moveLeft()
	{
		this.i_--;
	}

	public void moveRight()
	{
		this.i_++;
	}

	public void fillGrid(Block[][] grid)
	{
		grid[i_][j_].taken = true;
		grid[i_ + 1][j_].taken = true;
		grid[i_][j_ + 1].taken = true;
		grid[i_ + 1][j_ + 1].taken = true;

		grid[i_][j_].cs_ = this.cs_;
		grid[i_ + 1][j_].cs_ = this.cs_;
		grid[i_][j_ + 1].cs_ = this.cs_;
		grid[i_ + 1][j_ + 1].cs_ = this.cs_;
	}

	public void drawNext(Graphics g)
	{
		Drawer.drawNextBlock(g, 3, 1, cs_);
		Drawer.drawNextBlock(g, 3, 2, cs_);
		Drawer.drawNextBlock(g, 2, 2, cs_);
		Drawer.drawNextBlock(g, 2, 1, cs_);
	}

	public boolean checkOverlap(Block[][] grid)
	{
		return 	grid[i_][j_].taken ||
				grid[i_ + 1][j_].taken ||
				grid[i_][j_ + 1].taken ||
				grid[i_ + 1][j_ + 1].taken;
	}

}
