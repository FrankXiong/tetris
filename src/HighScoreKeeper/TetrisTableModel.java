package HighScoreKeeper;

import javax.swing.table.*;
import java.util.ArrayList;

public class TetrisTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = -5713741427415389345L;
	
	private String [] columnNames = {"Player Name", "Level", "Score", "Lines", "Date"};
	private Object [][] data = null;
	
	public TetrisTableModel(ArrayList<Score> list)
	{
		data = new Object[list.size()][columnNames.length];
		
		for(int i = 0; i < list.size(); i++)
		{
			data[i][0] = list.get(i).getPlayerName();
			data[i][1] = list.get(i).getLevel();
			data[i][2] = list.get(i).getScore();
			data[i][3] = list.get(i).getLines();
			data[i][4] = list.get(i).getDate();
		}
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	public int getColumnCount()
	{
		return columnNames.length;
	}

	public int getRowCount()
	{
		return data.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return data[rowIndex][columnIndex];
	}

	public String getColumnName(int column)
	{
		return columnNames[column];
	}

	public Class<?> getColumnClass(int columnIndex)
	{
		return getValueAt(0, columnIndex).getClass();
	}
}
