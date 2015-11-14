package view;

import java.awt.*;
import javax.swing.*;

import controller.Keeper;
import model.TetrisTableModel;

/**
 * @author:xiongxianren
 * @description:高分榜界面实现
 */
public class HighScoreDialog extends JDialog
{
	private static final long serialVersionUID = -2736054641339427970L;
	
	private Dimension windowSize = new Dimension(480, 225);

	public HighScoreDialog(JFrame owner, String title, boolean modal)
	{
		super(owner, title, modal);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 0));

		JTable highScoreTable = new JTable(new TetrisTableModel(Keeper.readScores()));
		
		JScrollPane scroll = new JScrollPane(highScoreTable);
		
		mainPanel.add(scroll);
		
		this.setResizable(false);
		this.getContentPane().add(mainPanel);
		this.setSize(windowSize);
	}

	public Dimension getPreferredSize()
	{
		return windowSize;
	}
}
