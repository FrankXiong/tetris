package HighScoreKeeper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:xiongxianren
 * @description:玩家游戏数据类
 */
public class Score implements Serializable, Comparable<Score>
{
	private static final long serialVersionUID = -4075968097377219529L;
	
	private String playerName;
	private int level;
	private String levelName; 
	private int score;
	private Date date;
	
	public Score()
	{
	}
	
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	
	public String getPlayerName()
	{
		return this.playerName;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}
	
	public String getLevelName()
	{
		return this.levelName;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public Date getDate()
	{
		return this.date;
	}

	public int compareTo(Score o)
	{
		if(this.score < o.getScore())
			return -1;
		else if(this.score > o.getScore())
			return 1;
		else
			return 0;
	}
}