package HighScoreKeeper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.io.*;

public class Keeper
{
	private static File scoreFile = new File("scores.dat");
	
	public static boolean addScore(Score s)
	{
		boolean isHighScore = false;
		ArrayList<Score> list = readScores();
		
		if(list != null)
		{
			if(list.size() < 10)
			{
				list.add(s);
				isHighScore = true;
			}
			else
			{
				Iterator<Score> itr = list.iterator();
				
				while(itr.hasNext())
				{
					if(s.getScore() > itr.next().getScore())
					{
						list.add(s);
						isHighScore = true;
						break;
					}
				}
			}
			
			writeScores(list);
		}
		
		return isHighScore;
	}
	
	public static ArrayList<Score> readScores()
	{
		ArrayList<Score> list = new ArrayList<Score>();
		
		FileInputStream fs = null;
		ObjectInputStream in = null;
		
		if(!scoreFile.exists())
		{
			try
			{
				scoreFile.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		try
		{
			fs = new FileInputStream(scoreFile);
			in = new ObjectInputStream(fs);
			
			Score s;
			
			while((s = ((Score)in.readObject())) != null)
			{
				list.add(s);
			}
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
		catch(ClassNotFoundException e){}
		finally
		{
			try
			{
				if(in != null)
					in.close();
				if(fs != null)
					fs.close();
			}
			catch(IOException e){}
		}

		return list;
	}
	
	/*
	 * only writes the top 10 scores
	 */
	private static void writeScores(ArrayList<Score> list)
	{
		FileOutputStream fs = null;
		ObjectOutputStream out = null;
		
		if(scoreFile.exists())
		{
			scoreFile.delete();
		}
		
		try
		{
			scoreFile.createNewFile();
			
			fs = new FileOutputStream(scoreFile);
			out = new ObjectOutputStream(fs);
			
			Collections.sort(list, Collections.reverseOrder());
			
			for(int i = 0; i < list.size() && i < 10; i++)
			{
				out.writeObject(list.get(i));
			}
		}
		catch(IOException e){}
		finally
		{
			try
			{
				if(out != null)
					out.close();
				if(fs != null)
					fs.close();
			}
			catch(IOException e){}
		}
	}
	
}
