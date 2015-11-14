package HighScoreKeeper;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable, Comparable<Score>
{
	private static final long serialVersionUID = -4075968097377219529L;
	
	private String playerName_;
	private int level_;
	private int score_;
	private int lines_;
	private Date date_;
	
	public Score()
	{
		
	}
	
	public void setPlayerName(String playerName)
	{
		this.playerName_ = playerName;
	}
	
	public String getPlayerName()
	{
		return this.playerName_;
	}
	
	public void setLevel(int level)
	{
		this.level_ = level;
	}
	
	public int getLevel()
	{
		return this.level_;
	}
	
	public void setScore(int score)
	{
		this.score_ = score;
	}
	
	public int getScore()
	{
		return this.score_;
	}
	
	public void setLines(int lines)
	{
		this.lines_ = lines;
	}
	
	public int getLines()
	{
		return this.lines_;
	}
	
	public void setDate(Date date)
	{
		this.date_ = date;
	}
	
	public Date getDate()
	{
		return this.date_;
	}

	public int compareTo(Score o)
	{
		if(this.score_ < o.getScore())
			return -1;
		else if(this.score_ > o.getScore())
			return 1;
		else
			return 0;
	}
}