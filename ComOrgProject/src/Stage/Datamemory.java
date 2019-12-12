import java.util.*;
public class Datamemory 
{

	private int addr;
	private int datavalue;
	private int datamem[];
	
	boolean MemWrite = false;
	boolean MemRead = false;
	private boolean pcsrc=false;
	private boolean branch = false;
	private boolean zero = false;
	
	public Datamemory() 
	{
		
	 datamem= new int[32];	
	}
	
	public int Datamem(int addr,int datavalue,boolean MemWrite,boolean MemRead)
	{
		if(MemWrite == false&&MemRead==true)
			return this.datamem[addr];
		
		else if(MemWrite == true&&MemRead==false)
		this.datamem[addr]=datavalue;
		return 0;
	}
	
	public boolean branchoper(boolean branch,boolean zero)
	{
		if(branch == true && zero==true)
			{pcsrc=true;
			return this.pcsrc;}
		else	
		{
		
			pcsrc=false;
			return this.pcsrc;
		}
	}
	
	
}
	

