package model;

import java.awt.Color;

public class BlockColorScheme
{
	private Color cM, cL, cD;
	
	public BlockColorScheme(Color mainColor, Color lightColor, Color darkColor)
	{
		this.cM = mainColor;
		this.cL = lightColor;
		this.cD = darkColor;
	}
	
	public Color getMainColor()
	{
		return this.cM;
	}
	
	public Color getLightColor()
	{
		return this.cL;
	}
	
	public Color getDarkColor()
	{
		return this.cD;
	}
}
