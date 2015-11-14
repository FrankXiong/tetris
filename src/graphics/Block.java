package graphics;

public class Block
{
	public boolean taken;
	public BlockColorScheme cs_;
	
	public Block(BlockColorScheme cs)
	{
		this.taken = false;
		this.cs_ = cs;
	}
}
