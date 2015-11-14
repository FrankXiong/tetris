package view;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import javax.swing.*;

import controller.mainWindow;

import java.awt.BorderLayout;

public class Tetris implements ActionListener, ItemListener
{
	private JFrame frame;
	private mainWindow mw;
	private String version = "v-1.0";
	private String applicationName = "俄罗斯方块大战";

	public Tetris()
	{

	}

	public void play()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				makeInterface();
			}
		});
	}

	private void makeInterface()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(InstantiationException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		frame = new JFrame(applicationName);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		
		Container c = frame.getContentPane();
		
		this.mw = new mainWindow(this);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("开始");
		JMenu newGameMenu = new JMenu("新游戏");
		JMenu battleTetrisMenu = new JMenu("联网对战");
		JMenu helpMenu = new JMenu("帮助");
		
//		JMenuItem listenItem = new JMenuItem("Listen for opponent");
//		listenItem.addActionListener(mw);
//		listenItem.setActionCommand("listen");
		
		JMenuItem connectItem = new JMenuItem("连接到对方玩家");
		connectItem.addActionListener(mw);
		connectItem.setActionCommand("connect");
		
		JMenuItem readyItem = new JMenuItem("Ready!");
		readyItem.addActionListener(mw);
		readyItem.setActionCommand("ready");
		
//		battleTetrisMenu.add(listenItem);
		battleTetrisMenu.add(connectItem);
		battleTetrisMenu.addSeparator();
		battleTetrisMenu.add(readyItem);
		
		JMenuItem level0 = new JMenuItem("平民级");
		level0.addActionListener(mw);
		level0.setActionCommand("level 2");
		JMenuItem level1 = new JMenuItem("冒险级");
		level1.addActionListener(mw);
		level1.setActionCommand("level 4");
		JMenuItem level2 = new JMenuItem("勇士级");
		level2.addActionListener(mw);
		level2.setActionCommand("level 6");
		JMenuItem level3 = new JMenuItem("王者级");
		level3.addActionListener(mw);
		level3.setActionCommand("level 8");
		JMenuItem level4 = new JMenuItem("英雄级");
		level4.addActionListener(mw);
		level4.setActionCommand("level 10");
		
		newGameMenu.add(level0);
		newGameMenu.add(level1);
		newGameMenu.add(level2);
		newGameMenu.add(level3);
		newGameMenu.add(level4);
		
		JMenuItem highScoreItem = new JMenuItem("高分榜");
		highScoreItem.setActionCommand("scores");
		highScoreItem.addActionListener(this);
		
		JMenuItem exitMenuItem = new JMenuItem("退出");
		exitMenuItem.setActionCommand("exit");
		exitMenuItem.addActionListener(this);
		
		JMenuItem aboutItem = new JMenuItem("使用说明");
		aboutItem.addActionListener(this);
		aboutItem.setActionCommand("about");
		
		helpMenu.add(aboutItem);
		
		gameMenu.add(newGameMenu);
		gameMenu.add(battleTetrisMenu);
		gameMenu.add(highScoreItem);
		gameMenu.addSeparator();
		gameMenu.add(exitMenuItem);

		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		c.add(this.mw, BorderLayout.CENTER);
		
		frame.setJMenuBar(menuBar);
		frame.addKeyListener(mw);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("exit"))
		{
			//释放屏幕资源			
			frame.dispose();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("about"))
		{
			String info = 	"版本:" +version+"\n\n"
							+"改变方块形状:arrow up\n"
							+"左右移动:arrow left,arrow right\n"
							+"下落一格:arrow down\n"
							+"直线下落:space\n"
							+"暂停：p"
							+"准备对战：r"
							;
			JOptionPane.showMessageDialog(this.mw, info, "使用说明", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getActionCommand().equals("scores"))
		{
			HighScoreDialog ad = new HighScoreDialog(this.frame, "高分榜", true);
			ad.setLocation(frame.getLocation().x, frame.getLocation().y + 127);
			ad.setVisible(true);
		}
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		Object selected = e.getItemSelectable();
		boolean change = (e.getStateChange() == ItemEvent.SELECTED ? true : false);
	}
	
	public void setWindowSize(Dimension windowSize)
	{
		frame.setPreferredSize(windowSize);
		frame.pack();
		frame.repaint();
	}
	
	public static void main(String[] args)
	{
		Tetris t = new Tetris();
		t.play();
	}
}
