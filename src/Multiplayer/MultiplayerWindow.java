package Multiplayer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MultiplayerWindow implements ActionListener
{
	private JFrame frame;
	private JList list;
	private DefaultListModel listModel;
	private MultiplayerController mpc;
	
	public MultiplayerWindow(MultiplayerController controller)
	{
		this.mpc = controller;
		makeInterface();
	}
	
	public void makeInterface()
	{
		frame = new JFrame("俄罗斯方块多人对战");
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		
		listModel = new DefaultListModel();
		listModel.ensureCapacity(100);
		
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		
		JButton exitButton = new JButton("Disconnect/Exit");
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 125));

		frame.getContentPane().add(listScroller, BorderLayout.CENTER);
		frame.getContentPane().add(exitButton, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(false);
	}

	public void addElement(String element)
	{
		listModel.add(0, element);
		list.setSelectedIndex(0);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("exit"))
		{
			mpc.log("exiting...");
			mpc.closeAll();
		}
	}
	
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}
}
