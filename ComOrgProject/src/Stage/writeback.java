public class writeback 
{
private int DstRd;

public void setDstRd(int DstRd)
{
	
		this.DstRd=DstRd;		
}
public int getDstRd()
{
	
		return this.DstRd;
}


	private boolean mux=false;

	
	
	public int writeback(boolean mux,int Datamem,int addr)
	{
		if(mux==true)
			
			return Datamem;
			
		else	
				return addr;
	
			
	}
	
	
}
	

	
	
		
	
	