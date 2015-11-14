package graphics;

import java.awt.Graphics;

/**
 * @author:xiongxianren
 * @description:方块抽象类，所有形状的方块继承自它
 */
public abstract class TetrisShape
{
	public abstract void draw(Graphics g);

	public abstract void drawNext(Graphics g);

	public abstract boolean checkOverlap(Block[][] grid);

	public abstract int geti();

	public abstract int getj();

	public abstract void seti(int i);

	public abstract void setj(int j);
	//切换形状	
	public abstract void nextState(Block[][] grid);
	//判断是否可以进行移动	
	public abstract boolean canMoveDown(Block[][] grid);
	public abstract boolean canMoveLeft(Block[][] grid);
	public abstract boolean canMoveRight(Block[][] grid);
	//下移	
	public abstract void moveDown();
	//左移
	public abstract void moveLeft();
	//右移
	public abstract void moveRight();
	//填充方块
	public abstract void fillGrid(Block[][] grid);
}
