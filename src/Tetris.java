import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import javax.swing.*;

import HighScoreKeeper.HighScoreDialog;
import java.awt.BorderLayout;

public class Tetris implements ActionListener, ItemListener
{
	private JFrame frame;
	private mainWindow mw;
	private String version = "v.98 - sizing (+/- keys)";
	private String applicationName = "Tetris/Battle Tetris Clone";
	private JCheckBoxMenuItem exoItem; 
	private JCheckBoxMenuItem blindItem;

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
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
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
		
		frame = new JFrame(applicationName + " " + version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		
		Container c = frame.getContentPane();
		
		this.mw = new mainWindow(this);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu newGameMenu = new JMenu("New Game");
		JMenu battleTetrisMenu = new JMenu("Battle Tetris");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem listenItem = new JMenuItem("Listen for opponent");
		listenItem.addActionListener(mw);
		listenItem.setActionCommand("listen");
		
		JMenuItem connectItem = new JMenuItem("Connect to opponent");
		connectItem.addActionListener(mw);
		connectItem.setActionCommand("connect");
		
		JMenuItem readyItem = new JMenuItem("Ready!");
		readyItem.addActionListener(mw);
		readyItem.setActionCommand("ready");
		
		battleTetrisMenu.add(listenItem);
		battleTetrisMenu.add(connectItem);
		battleTetrisMenu.addSeparator();
		battleTetrisMenu.add(readyItem);
		
		JMenuItem level0 = new JMenuItem("Level 0");
		level0.addActionListener(mw);
		level0.setActionCommand("level 0");
		JMenuItem level1 = new JMenuItem("Level 1");
		level1.addActionListener(mw);
		level1.setActionCommand("level 1");
		JMenuItem level2 = new JMenuItem("Level 2");
		level2.addActionListener(mw);
		level2.setActionCommand("level 2");
		JMenuItem level3 = new JMenuItem("Level 3");
		level3.addActionListener(mw);
		level3.setActionCommand("level 3");
		JMenuItem level4 = new JMenuItem("Level 4");
		level4.addActionListener(mw);
		level4.setActionCommand("level 4");
		JMenuItem level5 = new JMenuItem("Level 5");
		level5.addActionListener(mw);
		level5.setActionCommand("level 5");
		JMenuItem level6 = new JMenuItem("Level 6");
		level6.addActionListener(mw);
		level6.setActionCommand("level 6");
		JMenuItem level7 = new JMenuItem("Level 7");
		level7.addActionListener(mw);
		level7.setActionCommand("level 7");
		JMenuItem level8 = new JMenuItem("Level 8");
		level8.addActionListener(mw);
		level8.setActionCommand("level 8");
		JMenuItem level9 = new JMenuItem("Level 9");
		level9.addActionListener(mw);
		level9.setActionCommand("level 9");
		JMenuItem level10 = new JMenuItem("Level 10");
		level10.addActionListener(mw);
		level10.setActionCommand("level 10");
		
		newGameMenu.add(level0);
		newGameMenu.add(level1);
		newGameMenu.add(level2);
		newGameMenu.add(level3);
		newGameMenu.add(level4);
		newGameMenu.add(level5);
		newGameMenu.add(level6);
		newGameMenu.add(level7);
		newGameMenu.add(level8);
		newGameMenu.add(level9);
		newGameMenu.add(level10);
		
		JMenuItem highScoreItem = new JMenuItem("High Scores");
		highScoreItem.setActionCommand("scores");
		highScoreItem.addActionListener(this);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("exit");
		exitMenuItem.addActionListener(this);
		
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(this);
		aboutItem.setActionCommand("about");
		
		blindItem = new JCheckBoxMenuItem("Play Blind");
		blindItem.setSelected(false);
		blindItem.addItemListener(this);
		
		
		exoItem = new JCheckBoxMenuItem("Draw Exoskeleton");
		exoItem.setSelected(true);
		exoItem.addItemListener(this);
		exoItem.doClick();
		
		helpMenu.add(aboutItem);
		
		gameMenu.add(newGameMenu);
		gameMenu.add(battleTetrisMenu);
		gameMenu.add(highScoreItem);
		gameMenu.addSeparator();
		gameMenu.add(exoItem);
		gameMenu.add(blindItem);
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
			frame.dispose();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("about"))
		{
			String info = 	"Arrow Keys: piece movement\n" +
							"Spacebar: drop\n" +
							"\n" +
							"Multiplayer:\n" +
							"TCP/IP, simple listen & connect\n" +
							"You clear 2 lines: your opponent receives 1 row of blocks with a single gap.\n" +
							"You clear 3 lines: your opponent receives 2 rows of blocks with a single gap.\n" +
							"You clear 4 lines: your opponent receives 4 rows of blocks with a single gap.\n" +
							"\n" +
							"User Interface:\n" +
							"Use the + or - keys to change the window size." +
							"\n\n" +
							"Written by Kerry Famularo\n" + 
							"kfamularo@gmail.com";
			JOptionPane.showMessageDialog(this.mw, info, "Help : About", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getActionCommand().equals("scores"))
		{
			HighScoreDialog ad = new HighScoreDialog(this.frame, "High Scores", true);
			ad.setLocation(frame.getLocation().x, frame.getLocation().y + 127);
			ad.setVisible(true);
		}
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		Object selected = e.getItemSelectable();

		boolean change = (e.getStateChange() == ItemEvent.SELECTED ? true : false);
		
		if(selected == this.exoItem && exoItem.isEnabled())
		{	
			mw.setDrawExoskeleton(change);
		}
		
		if(selected == this.blindItem)
		{
			if(!exoItem.getState())
				exoItem.doClick();
			
			exoItem.setEnabled(!change);

			mw.setDrawBlind(change);
		}
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
