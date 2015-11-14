import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import Multiplayer.*;
import HighScoreKeeper.*;
import graphics.*;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.StringTokenizer;
import java.util.Observable;
import java.util.Observer;
import java.util.LinkedList;
import java.util.Date;

/**
 * @author:xiongxianren
 * @description:主窗口
 */
public class mainWindow extends JComponent implements KeyListener, ActionListener, Observer
{
	private static final long serialVersionUID = -6726155958015311167L;

	private Dimension 		componentSize = new Dimension(480, Drawer.boardHeight + 80);
	
	private boolean 		gameOver = true;
	private boolean 		paused = false;
	//设置锁	
	private boolean 		locked = false;
	//初始化下落时间为500	
	private int 			tickCount = 500;
	//增加一级难度，下落时间减少40	
	private final int 		levelShave = 40;
	//临时存储消除的行数	
	private int 			tempLines = 0;
	//二维数组用于保存界面所有方块	
	private Block [][] 		grid;
	
	private Score			gameInformation;
	
	private Timer 			t;
	private TetrisShape [] 	piece = null;
	
	private MultiplayerController mpc;
	private LinkedList<Integer> lineQueue;
	
	private int opponentProgress = 20;
	private int previousProgress = -1;
	
	private final int 		ACTIVE 	= 0;
	private final int		NEXT 	= 1;

	private Tetris tetris = null;
	
	public mainWindow(Tetris t)
	{
		this.tetris = t;
		
		mpc = new MultiplayerController();
		lineQueue = new LinkedList<Integer>();
		gameInformation = new Score();
		
		grid = new Block[10][20];
		
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 20; j++)
				grid[i][j] = new Block(new BlockColorScheme(Color.WHITE, Color.WHITE, Color.WHITE));
		
		tetris.setWindowSize(Drawer.recalculate(Drawer.blockSize));
	}
	
	public Dimension getPreferredSize()
	{
		return componentSize;
	}
	
	private boolean isLocked()
	{
		return this.locked;
	}
	
	private void setLock(boolean locked)
	{
		this.locked = locked;
	}

	protected void paintComponent(Graphics g)
	{
		this.drawMainBoard(g);
		this.drawNextBoard(g);
		this.drawProgressBoard(g);
		this.drawBlocks(g);
		this.drawActive(g);
		this.drawScoring(g);
		this.drawGameOver(g);
	}
	//绘制主区域边缘及背景
	private void drawMainBoard(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(Drawer.offset_x, Drawer.offset_y, Drawer.boardWidth, Drawer.boardHeight);
		
		g.setColor(new Color(26, 132, 238));
		g.fillRect(Drawer.offset_x - Drawer.borderWidth, Drawer.offset_y, Drawer.borderWidth, Drawer.boardHeight);
		g.fillRect(Drawer.offset_x - Drawer.borderWidth, Drawer.offset_y - Drawer.borderWidth, Drawer.boardWidth + (Drawer.borderWidth * 2), Drawer.borderWidth);
		g.fillRect(Drawer.offset_x + Drawer.boardWidth, Drawer.offset_y, Drawer.borderWidth, Drawer.boardHeight);
		g.fillRect(Drawer.offset_x - Drawer.borderWidth, Drawer.offset_y + Drawer.boardHeight, Drawer.boardWidth + (Drawer.borderWidth * 2), Drawer.borderWidth);
	}
	//绘制下一个方块区域边缘及背景
	private void drawNextBoard(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(Drawer.next_offset_x, Drawer.next_offset_y, Drawer.nextBoardWidth, Drawer.nextBoardHeight);
		
		g.setColor(new Color(26, 132, 238));
		g.fillRect(Drawer.next_offset_x - Drawer.borderWidth, Drawer.next_offset_y, Drawer.borderWidth, Drawer.nextBoardHeight);
		g.fillRect(Drawer.next_offset_x - Drawer.borderWidth, Drawer.next_offset_y - Drawer.borderWidth, Drawer.nextBoardWidth + (Drawer.borderWidth * 2), Drawer.borderWidth);
		g.fillRect(Drawer.next_offset_x + Drawer.nextBoardWidth, Drawer.next_offset_y, Drawer.borderWidth, Drawer.nextBoardHeight);
		g.fillRect(Drawer.next_offset_x - Drawer.borderWidth, Drawer.next_offset_y + Drawer.nextBoardHeight, Drawer.nextBoardWidth + (Drawer.borderWidth * 2), Drawer.borderWidth);
	}
	
	private void drawProgressBoard(Graphics g)
	{
		if(mpc.isGameActive())
		{
			g.setColor(new Color(26, 132, 238));
			g.fillRect(Drawer.progress_offset_x - Drawer.borderWidth, Drawer.progress_offset_y, Drawer.borderWidth, Drawer.boardHeight);
			g.fillRect(Drawer.progress_offset_x - Drawer.borderWidth, Drawer.progress_offset_y - Drawer.borderWidth, Drawer.blockSize + (Drawer.borderWidth * 2), Drawer.borderWidth);
			g.fillRect(Drawer.progress_offset_x + Drawer.blockSize, Drawer.progress_offset_y, Drawer.borderWidth, Drawer.boardHeight);
			g.fillRect(Drawer.progress_offset_x - Drawer.borderWidth, Drawer.progress_offset_y + Drawer.boardHeight, Drawer.blockSize + (Drawer.borderWidth * 2), Drawer.borderWidth);
			
			g.setColor(Color.BLACK);
			g.fillRect(Drawer.progress_offset_x, Drawer.progress_offset_y, Drawer.blockSize, Drawer.boardHeight);
			
			if(opponentProgress < 20)
			{
				for(int j = 19; j >= opponentProgress; j--)
				{
					Drawer.drawOpponentProgress(g, j, PieceGenerator.BCS_RED);
				}
			}
		}
	}

	private boolean checkRow()
	{
		setLock(true);
		
		boolean tickChanged = false;
		//存储被消除的行数		
		ArrayList<Integer> rowList = new ArrayList<Integer>();
		
		for(int k = 19; k >= 0; k--)
		{
			if(		grid[0][k].taken && grid[1][k].taken && 
					grid[2][k].taken && grid[3][k].taken &&
					grid[4][k].taken && grid[5][k].taken && 
					grid[6][k].taken && grid[7][k].taken && 
					grid[8][k].taken && grid[9][k].taken)
			{	
				rowList.add(new Integer(k));
			}
		}
		
		if(rowList.size() > 0)
		{
			int multiplier = 0;
			
			if(rowList.size() > 1)
			{
				if(rowList.size() != 4)
					mpc.writeGameData(new TetrisPacket(Integer.toString(rowList.size() - 1)));
				else if(rowList.size() == 4)
					mpc.writeGameData(new TetrisPacket("4"));
			}
			
			for(int i = 0; i < rowList.size(); i++)
			{
				this.drawRowAnimation(this.getGraphics(), rowList.get(i).intValue());
			}
			
			switch(rowList.size())
			{
				case 1: { multiplier = 40; 	break; 	}
				case 2: { multiplier = 100; 	break; 	}
				case 3: { multiplier = 300; 	break; 	}
				case 4: { multiplier = 1200;	break; 	}
			}
			
			gameInformation.setScore(gameInformation.getScore() + ((gameInformation.getLevel() + 1) * multiplier));
			
			tempLines += rowList.size();
			//消除行数超过10行，游戏难度+1			
			if(!mpc.isGameActive() && tempLines >= 10 && gameInformation.getLevel() < 10)
			{
				tempLines = 0;
				gameInformation.setLevel(gameInformation.getLevel() + 1);
				tickCount -= levelShave;
				tickChanged = true;
			}
			
			for(int n = rowList.size() - 1; n >= 0; n--)
			{
				int row = rowList.get(n);
				
				for(int i = 0; i < 10; i++)
				{
					for(int j = row - 1; j >= 0; j--)
					{
						grid[i][j + 1].taken = grid[i][j].taken;
						grid[i][j + 1].cs_ = grid[i][j].cs_;
					}
				}
				
				for(int i = 0; i < 10; i++)
					grid[i][0] = new Block(new BlockColorScheme(Color.WHITE, Color.WHITE, Color.WHITE));
			}
		}
		
		setLock(false);
		
		return tickChanged;
	}
	
	private void processIncomingLines()
	{
		setLock(true);
		
		while(!lineQueue.isEmpty())
		{
			addTrashLines(lineQueue.getFirst().intValue());
			mpc.log("move lines up " + lineQueue.getFirst().intValue());
			lineQueue.removeFirst();
		}
		
		setLock(false);
	}
	
	private void addTrashLines(int lines)
	{
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < lines; j++)
			{
				if(grid[i][j].taken)
				{
					gameOver = true;
					mpc.writeGameData(new TetrisPacket("win"));
					break;
				}
			}
			
			if(gameOver) break;
		}
		
		for(int i = 0; i < 10; i++)
		{
			int top = 0;
			
			for(int j = lines; j < 20 && top < 20; j++)
			{
				grid[i][top].taken = grid[i][j].taken;
				grid[i][top].cs_ = grid[i][j].cs_;
				++top;
			}
		}
		
		int colGap = PieceGenerator.getRandomNumber(0, 10);
		BlockColorScheme bcs = PieceGenerator.getRandomBlockColorScheme();
		
		for(int i = 0; i < 10; i++)
		{
			for(int j = 20 - lines; j < 20; j++)
			{
				if(i == colGap)
				{
					grid[i][j] = new Block(new BlockColorScheme(Color.WHITE, Color.WHITE, Color.WHITE));
				}
				else
				{
					grid[i][j] = new Block(bcs);
					grid[i][j].taken = true;
				}
			}
		}
	}
	//消除行时的动画	
	private void drawRowAnimation(Graphics g, int col)
	{
		try
		{
			for(int i = 0; i < 10; i++)
			{
				grid[i][col].cs_ = new BlockColorScheme(Color.GRAY, Color.WHITE, Color.BLACK);
				this.drawBlocks(g);
				Thread.sleep(20);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void drawBlocks(Graphics g)
	{
		int low = 20;
		
		for(int j = 19; j >= 0; j--)
		{
			for(int i = 0; i < 10; i++)
			{
				if(grid[i][j].taken)
				{
					if(j < low)
					{ 
						low = j; 
					}
					Drawer.drawTakenBlock(g, i, j, grid[i][j].cs_);
				}
			}
		}
		
		if(mpc.isGameActive() && low != previousProgress)
		{
			previousProgress = low;
			mpc.writeGameData(new TetrisPacket("progress " + low));
		}
	}

	private void drawActive(Graphics g)
	{
		if(piece != null)
		{
			piece[ACTIVE].draw(g);
			piece[NEXT].drawNext(g);
		}
	}
	
	private void drawScoring(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawString("难度系数: " + gameInformation.getLevel(), Drawer.next_offset_x, 280);
		g.drawString("分数: " + gameInformation.getScore(), Drawer.next_offset_x, 310);
		g.drawString("对手分数: " + gameInformation.getScore(), Drawer.next_offset_x, 340);
	}
	
	private void drawGameOver(Graphics g)
	{
		if(gameOver)
		{
			g.setColor(Color.WHITE);
			g.fillRect(Drawer.offset_x + 10, Drawer.offset_y + 175, 180, 50);
			
			g.setColor(Color.BLACK);
			g.drawString("G A M E  O V E R", Drawer.offset_x + 50, Drawer.offset_y + 205);
		}
	}
	
	private void newGame(int chosen_level)
	{
		setLock(true);
		
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 20; j++)
				grid[i][j] = new Block(new BlockColorScheme(Color.WHITE, Color.WHITE, Color.WHITE));
		
		gameInformation = new Score();
		
		gameInformation.setLevel(chosen_level);
		gameInformation.setScore(0);
		
		tickCount 			= 500 - (chosen_level * levelShave);
		tempLines 			= 0;
		opponentProgress 	= 20;
		
		System.out.println("beginning tickCount: " + tickCount);
		
		piece 			= new TetrisShape[2];
		piece[ACTIVE] 	= PieceGenerator.getRandomPiece();
		piece[NEXT] 	= PieceGenerator.getRandomPiece();
		
		lineQueue.clear();

		gameOver = false;

		cancelTimer();
		setLock(false);
		startTimer();
	}

//	判断键盘输入
	public void keyPressed(KeyEvent e)
	{
		
		if(!gameOver && !isLocked())
		{
			if(e.getKeyCode() == KeyEvent.VK_P && !mpc.isGameActive())
			{
				if(!paused)
				{
					paused = true;
					cancelTimer();
				}
				else
				{
					paused = false;
					startTimer();
				}
			}
			else if(!paused)
			{
				switch(e.getKeyCode())
				{
					case KeyEvent.VK_UP:
					{
						piece[ACTIVE].nextState(grid);
						//重绘页面				
						repaint();
		
						break;
					}
					case KeyEvent.VK_DOWN:
					{
						if(piece[ACTIVE].canMoveDown(grid))
						{
							piece[ACTIVE].moveDown();
						}
		
						repaint();
		
						break;
					}
					case KeyEvent.VK_LEFT:
					{
						if(piece[ACTIVE].canMoveLeft(grid))
						{
							piece[ACTIVE].moveLeft();
						}
		
						repaint();
		
						break;
					}
					case KeyEvent.VK_RIGHT:
					{
						if(piece[ACTIVE].canMoveRight(grid))
						{
							piece[ACTIVE].moveRight();
						}
		
						repaint();
		
						break;
					}
					//直线下落					
					case KeyEvent.VK_SPACE:
					{
						setLock(true);
						
						cancelTimer();
						
						while(piece[ACTIVE].canMoveDown(grid))
						{
							piece[ACTIVE].moveDown();
						}
						
						repaint();
						
						setLock(false);
						//下落完成后重启计时器线程						
						startTimer();
						
						break;
					}
					
				} 
			} 
		}
		else
		{
			if(e.getKeyCode() == KeyEvent.VK_R)
			{
				setReady();
			}
		}
	}

	public void keyTyped(KeyEvent e)
	{
		return;
	}

	public void keyReleased(KeyEvent e)
	{
		return;
	}
	//终止计时器线程	
	private void cancelTimer()
	{
		if(t != null)
		{
			t.cancel();
		}
	}
	//启动计时器线程	
	private void startTimer()
	{
		cancelTimer(); 
		
		System.out.println("timer starting with tickCount: " + tickCount);
		t = new Timer();
		t.schedule(new GameTick(), 0, tickCount);
	}
	
	private class GameTick extends TimerTask
	{
		public GameTick()
		{

		}

		public void run()
		{
			if(!isLocked())
			{
				if(piece[ACTIVE].canMoveDown(grid))
				{
					piece[ACTIVE].moveDown();
					processIncomingLines();
				}
				else
				{
					piece[ACTIVE].fillGrid(grid);
					piece[ACTIVE] = piece[NEXT];
					piece[NEXT] = PieceGenerator.getRandomPiece();
					
					if(checkRow())
						startTimer();

					processIncomingLines();
					
					if(piece[ACTIVE].checkOverlap(grid))
					{
						if(!gameOver)
						{
							gameOver = true;
							
							cancelTimer();
							
							if(mpc.isGameActive())
								mpc.writeGameData(new TetrisPacket("win"));
							else
							{
								repaint();
								recordScore();
							}
						}
					}
				}
				repaint();
			}
		}
	}
	
	//记录游戏结果	
	private void recordScore()
	{
		String inputResult;
		
		inputResult = JOptionPane.showInputDialog(this, "玩家姓名:", "(未命名)");
		
		if(inputResult != null)
		{
			gameInformation.setPlayerName(inputResult.trim());
			gameInformation.setDate(new Date());
			
			if(Keeper.addScore(gameInformation))
			{
				JOptionPane.showMessageDialog(this, "你刷新了新的得分记录!", "新的高分", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().startsWith("level") && !mpc.isConnected())
		{
			//StringTokenizer用于字符串分隔解析，第二个参数为分隔符		
			StringTokenizer st = new StringTokenizer(e.getActionCommand(), " ");
			
			if(st.hasMoreTokens())
			{
				if(st.nextToken().equals("level") && st.hasMoreTokens())
				{
					cancelTimer();
					this.newGame(Integer.parseInt(st.nextToken()));
				}
			}
		}
//		else if(e.getActionCommand().equals("listen"))
//		{
//			if(!mpc.isLocked())
//			{
//				String inputResult = JOptionPane.showInputDialog(this, "端口 [1024, 65535]:", "5555");
//				
//				if(inputResult != null)
//				{
//					int port = 0;
//					
//					try
//					{
//						port = Integer.parseInt(inputResult);
//					}
//					catch(NumberFormatException e1)
//					{
//						JOptionPane.showMessageDialog(this, "端口不正确");
//					}
//					
//					if(mpc.startServer(port))
//						mpc.addObserver(this);
//				}
//			}
//		}
		else if(e.getActionCommand().equals("connect"))
		{
			if(!mpc.isLocked())
			{
				String inputResult = JOptionPane.showInputDialog(this, "<IP地址>:<端口>", "localhost:5555");
				
				if(inputResult != null)
				{
								
					StringTokenizer st = new StringTokenizer(inputResult, ":");
					
					String address = null;
					int port = 0;

					if(st.countTokens() == 2)
					{
						address = st.nextToken();
						
						try
						{
							port = Integer.parseInt(st.nextToken());
						}
						catch(NumberFormatException e1)
						{
							JOptionPane.showMessageDialog(this, "端口不正确");
							return;
						}
					}
					else
					{
						JOptionPane.showMessageDialog(this, "格式不正确");
						return;
					}
					
					if(mpc.startClient(address, port))
						//添加观察者						
						mpc.addObserver(this);
				}
			}
		}
		else if(e.getActionCommand().equals("ready"))
		{
			setReady();
		}
	}
	
	private void setReady()
	{
		if(mpc.isConnected() && !mpc.isLocalReady() && !mpc.isGameActive())
		{
			mpc.write(new TetrisPacket("ready"));
			
			if(mpc.isOpponentReady())
			{
				mpc.log("开始游戏...");
				mpc.setGameActive(true);
				this.newGame(0);
			}
			else
			{
				mpc.setLocalReady(true);
				mpc.log("等待对方玩家...");
			}
		}	
	}

	public void update(Observable o, Object arg)
	{
		TetrisPacket tp = ((TetrisPacket)arg);
		
		synchronized(mpc)
		{
			if(tp.getMessage().equals("ready"))
			{
				mpc.setOpponentReady(true);
				
				if(mpc.isLocalReady())
				{
					mpc.log("开始游戏...");
					mpc.setGameActive(true);
					this.newGame(0);
				}
				else
				{
					mpc.log("等待对方玩家...");
				}
			}
			else if(tp.getMessage().equals("win"))
			{
				mpc.writeGameData(new TetrisPacket("lose"));
				processOutcome(true);
			}
			else if(tp.getMessage().equals("lose"))
			{
				processOutcome(false);
			}
			else if(tp.getMessage().startsWith("progress"))
			{
				StringTokenizer st = new StringTokenizer(tp.getMessage(), " ");
				
				if(st.hasMoreTokens() && st.nextToken().equals("progress"))
				{
					if(st.hasMoreTokens())
					{
						opponentProgress = Integer.parseInt(st.nextToken());
					}
					else
					{
						mpc.log("incomplete \"progress\" packet");
					}
				}
			}
			else
			{
				if(mpc.isConnected() && mpc.isGameActive())
				{
					lineQueue.add(new Integer(Integer.parseInt(tp.getMessage())));
				}
			}
		}
	}
	
//	输出游戏结果
	private void processOutcome(boolean win)
	{
		this.gameOver = true;
		
		cancelTimer();
		
		mpc.setGameActive(false);
		mpc.setLocalReady(false);
		mpc.setOpponentReady(false);
		
		repaint();
		
		if(win)
			mpc.addWin();
		else
			mpc.addLoss();
		
		mpc.log("你 " + (win ? "赢" : "输") + "了!");
		mpc.log("比分: " + mpc.getWins() + ":" + mpc.getLosses());
	}
}
