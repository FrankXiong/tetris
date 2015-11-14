package graphics;

import java.awt.Graphics;

/**
 * @author:xiongxianren
 * @description:形状I
 */
public class I extends TetrisShape
{
	private int i_, j_;
	public BlockColorScheme cs_;
	//标识当前形状	
	private int state = 0;

	public I(int i, int j, BlockColorScheme cs)
	{
		this.i_ = i;
		this.j_ = j;
		this.cs_ = cs;
	}

	public void draw(Graphics g)
	{
		switch(this.state)
		{
			// |	
			case 0:
			{
				Drawer.drawTakenBlock(g, i_, j_, cs_);
				Drawer.drawTakenBlock(g, i_ - 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_ + 1, j_, cs_);
				Drawer.drawTakenBlock(g, i_ + 2, j_, cs_);
				
				break;
			}
			// ——			
			case 1:
			{
				Drawer.drawTakenBlock(g, i_, j_, cs_);
				Drawer.drawTakenBlock(g, i_, j_ - 1, cs_);
				Drawer.drawTakenBlock(g, i_, j_ + 1, cs_);
				Drawer.drawTakenBlock(g, i_, j_ + 2, cs_);
				
				break;
			}
		}
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
		switch(state)
		{
			case 0:
			{
				//先判断当前状态能否进行变换形状				
				if(		j_ + 2 <= 19 &&
						j_ - 1 >= 0 &&
						!grid[i_][j_ - 1].taken &&
						!grid[i_][j_ + 1].taken &&
						!grid[i_][j_ + 2].taken)
				{
					state = 1;
				}
				break;
			}
			case 1:
			{
				//先判断当前状态能否进行变换形状
				if(		i_ - 1 >= 0 &&
						i_ + 2 <= 9 &&
						!grid[i_ - 1][j_].taken &&
						!grid[i_ + 2][j_].taken &&
						!grid[i_ + 1][j_].taken)
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
				if(		j_ + 1 <= 19 &&
						!grid[i_ - 1][j_ + 1].taken &&
						!grid[i_][j_ + 1].taken &&
						!grid[i_ + 1][j_ + 1].taken &&
						!grid[i_ + 2][j_ + 1].taken)
				{
					return true;
				}
				break;
			}
			case 1:
			{
				if(		j_ + 3 <= 19 &&
						!grid[i_][j_ + 3].taken)
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
						!grid[i_ - 2][j_].taken)
				{
					return true;
				}
				break;
			}
			case 1:
			{
				if(		i_ - 1 >= 0 &&
						!grid[i_ - 1][j_ - 1].taken &&
						!grid[i_ - 1][j_].taken &&
						!grid[i_ - 1][j_ + 1].taken &&
						!grid[i_ - 1][j_ + 2].taken)
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
				if(		i_ + 3 <= 9 &&
						!grid[i_ + 3][j_].taken)
				{
					return true;
				}
				break;
			}
			case 1:
			{
				if(		i_ + 1 <= 9 &&
						!grid[i_ + 1][j_ - 1].taken &&
						!grid[i_ + 1][j_].taken &&
						!grid[i_ + 1][j_ + 1].taken &&
						!grid[i_ + 1][j_ + 2].taken)
				{
					return true;
				}
				break;
			}
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
		switch(this.state)
		{
			case 0:
			{
				grid[i_ - 1][j_].taken = true;
				grid[i_][j_].taken = true;
				grid[i_ + 1][j_].taken = true;
				grid[i_ + 2][j_].taken = true;
				
				grid[i_ - 1][j_].cs_ = this.cs_;
				grid[i_][j_].cs_ = this.cs_;
				grid[i_ + 1][j_].cs_ = this.cs_;
				grid[i_ + 2][j_].cs_ = this.cs_;
				
				
				break;
			}
			case 1:
			{
				grid[i_][j_ - 1].taken = true;
				grid[i_][j_].taken = true;
				grid[i_][j_ + 1].taken = true;
				grid[i_][j_ + 2].taken = true;
				
				grid[i_][j_ - 1].cs_ = this.cs_;
				grid[i_][j_].cs_ = this.cs_;
				grid[i_][j_ + 1].cs_ = this.cs_;
				grid[i_][j_ + 2].cs_ = this.cs_;
				
				break;
			}
		}
	}

	public void drawNext(Graphics g)
	{
		Drawer.drawNextBlock(g, 1, 2, cs_);
		Drawer.drawNextBlock(g, 3, 2, cs_);
		Drawer.drawNextBlock(g, 2, 2, cs_);
		Drawer.drawNextBlock(g, 4, 2, cs_);
	}

	public boolean checkOverlap(Block[][] grid)
	{
		return	grid[i_ - 1][j_].taken 	||
				grid[i_][j_].taken 		||
				grid[i_ + 1][j_].taken 	||
				grid[i_ + 2][j_].taken;
	}
	
}
