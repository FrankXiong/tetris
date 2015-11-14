package graphics;

import java.awt.Graphics;

/**
 * @author:xiongxianren
 * @description:形状T
 */
public class T extends TetrisShape
{
	private int i_, j_;
	public BlockColorScheme cs_;
	private int state = 0;

	public T(int i, int j, BlockColorScheme cs)
	{
		this.i_ = i;
		this.j_ = j;
		this.cs_ = cs;
	}
	
	public void draw(Graphics g)
	{
		switch(this.state)
		{
			case 0:
			{
				Drawer.drawTakenBlock(g, i_, j_, cs_);
				Drawer.drawTakenBlock(g, i_ - 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_ + 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_, j_ + 1, cs_);
				break;
			}
			case 1:
			{
				Drawer.drawTakenBlock(g, i_, j_, cs_);
				Drawer.drawTakenBlock(g, i_ - 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_, j_ - 1, cs_);
				Drawer.drawTakenBlock(g, i_, j_ + 1, cs_);
				break;
			}
			case 2:
			{
				Drawer.drawTakenBlock(g, i_, j_, cs_);
				Drawer.drawTakenBlock(g, i_ - 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_ + 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_, j_ - 1, cs_);
				break;
			}
			case 3:
			{
				Drawer.drawTakenBlock(g, i_, j_, cs_);
				Drawer.drawTakenBlock(g, i_, j_ - 1, cs_);
				Drawer.drawTakenBlock(g, i_ + 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_, j_ + 1, cs_);
				break;
			}
		}
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

	public void nextState(Block[][] grid)
	{
		switch(this.state)
		{
			case 0:
			{
				if(		j_ - 1 >= 0 &&
						!grid[i_][j_ - 1].taken)
				{
					state = 1;
				}
				break;
			}
			case 1:
			{
				if(i_ != 9 && !grid[i_ + 1][j_].taken)
				{
					state = 2;
				}
				break;
			}
			case 2:
			{
				if(!grid[i_][j_ + 1].taken)
				{
					state = 3;
				}
				break;
			}
			case 3:
			{
				if(i_ != 0 && !grid[i_ - 1][j_].taken)
				{
					state = 0;
				}
				break;
			}
		}
	}
	
	public boolean canMoveDown(Block[][] grid)
	{
		switch(this.state)
		{
			case 0:
			{
				if(		j_ + 2 <= 19 &&
						!grid[i_][j_ + 2].taken &&
						!grid[i_ - 1][j_ + 1].taken &&
						!grid[i_ + 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 1:
			{
				if(		j_ + 2 <= 19 &&
						!grid[i_][j_ + 2].taken &&
						!grid[i_ - 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 2:
			{
				if(		j_ + 1 <= 19 &&
						!grid[i_][j_ + 1].taken &&
						!grid[i_ - 1][j_ + 1].taken &&
						!grid[i_ + 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 3:
			{
				if(		j_ + 2 <= 19 &&
						!grid[i_][j_ + 2].taken &&
						!grid[i_ + 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
		}
		return false;
	}

	public boolean canMoveLeft(Block[][] grid)
	{
		switch(this.state)
		{
			case 0:
			{
				if(		i_ - 2 >= 0 &&
						!grid[i_ - 2][j_].taken &&
						!grid[i_ - 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 1:
			{
				if(		i_ - 2 >= 0 &&
						!grid[i_ - 2][j_].taken &&
						!grid[i_ - 1][j_ - 1].taken &&
						!grid[i_ - 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 2:
			{
				if(		i_ - 2 >= 0 &&
						!grid[i_ - 2][j_].taken &&
						!grid[i_ - 1][j_ - 1].taken)
				{
					return true;
				}
				break;
			}
			case 3:
			{
				if(		i_ - 1 >= 0 &&
						!grid[i_ - 1][j_].taken &&
						!grid[i_ - 1][j_ - 1].taken &&
						!grid[i_ - 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
		}
		return false;
	}

	public boolean canMoveRight(Block[][] grid)
	{
		switch(this.state)
		{
			case 0:
			{
				if(		i_ + 2 <= 9 &&
						!grid[i_ + 2][j_].taken &&
						!grid[i_ + 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 1:
			{
				if(		i_ + 1 <= 9 &&
						!grid[i_ + 1][j_].taken &&
						!grid[i_ + 1][j_ - 1].taken &&
						!grid[i_ + 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 2:
			{
				if(		i_ + 2 <= 9 &&
						!grid[i_ + 2][j_].taken &&
						!grid[i_ + 1][j_ - 1].taken)
				{
					return true;
				}
				break;
			}
			case 3:
			{
				if(		i_ + 2 <= 9 &&
						!grid[i_ + 2][j_].taken &&
						!grid[i_ + 1][j_ - 1].taken &&
						!grid[i_ + 1][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
		}
		return false;
	}

	public void fillGrid(Block[][] grid)
	{
		switch(this.state)
		{
			case 0:
			{
				grid[i_][j_].taken = true;
				grid[i_ - 1][j_].taken = true;
				grid[i_ + 1][j_].taken = true;
				grid[i_][j_ + 1].taken = true;
				
				grid[i_][j_].cs_ = cs_;
				grid[i_ - 1][j_].cs_ = cs_;
				grid[i_ + 1][j_].cs_ = cs_;
				grid[i_][j_ + 1].cs_ = cs_;
	
				break;
			}
			case 1:
			{
				grid[i_][j_].taken = true;
				grid[i_ - 1][j_].taken = true;
				grid[i_][j_ - 1].taken = true;
				grid[i_][j_ + 1].taken = true;
				
				grid[i_][j_].cs_ = cs_;
				grid[i_ - 1][j_].cs_ = cs_;
				grid[i_][j_ - 1].cs_ = cs_;
				grid[i_][j_ + 1].cs_ = cs_;
				
				break;
			}
			case 2:
			{
				grid[i_][j_].taken = true;
				grid[i_ - 1][j_].taken = true;
				grid[i_][j_ - 1].taken = true;
				grid[i_ + 1][j_].taken = true;
				
				grid[i_][j_].cs_ = cs_;
				grid[i_ - 1][j_].cs_ = cs_;
				grid[i_][j_ - 1].cs_ = cs_;
				grid[i_ + 1][j_].cs_ = cs_;
				
				break;
			}
			case 3:
			{
				grid[i_][j_].taken = true;
				grid[i_][j_ + 1].taken = true;
				grid[i_][j_ - 1].taken = true;
				grid[i_ + 1][j_].taken = true;
				
				grid[i_][j_].cs_ = cs_;
				grid[i_][j_ + 1].cs_ = cs_;
				grid[i_][j_ - 1].cs_ = cs_;
				grid[i_ + 1][j_].cs_ = cs_;
				
				break;
			}
		}
	}

	public int geti()
	{
		return this.i_;
	}

	public int getj()
	{
		return this.j_;
	}

	public void seti(int i)
	{
		this.i_ = i;
	}

	public void setj(int j)
	{
		this.j_ = j;
	}

	public void drawNext(Graphics g)
	{
		Drawer.drawNextBlock(g, 3, 1, cs_);
		Drawer.drawNextBlock(g, 3, 2, cs_);
		Drawer.drawNextBlock(g, 2, 2, cs_);
		Drawer.drawNextBlock(g, 4, 2, cs_);
	}

	public boolean checkOverlap(Block[][] grid)
	{
		return 	grid[i_][j_].taken 		||
				grid[i_ - 1][j_].taken 	||
				grid[i_ + 1][j_].taken 	||
				grid[i_][j_ + 1].taken;
	}
}
