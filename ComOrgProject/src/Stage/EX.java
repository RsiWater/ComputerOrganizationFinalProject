package computer;


public class EX
{
   private int BA,reg1,reg2,address,regnum1,regnum2,ALUop,funct=0;
   private boolean ALUSrc,RegDst;
   private int[] EXMEMbuffer=new int[5];
   
   public void setBA(int BA) {
	   this.BA=BA;
	   
   }
   public void setreg1(int reg1) {
	   this.reg1=reg1;
	   
   }
   public void setreg2(int reg2) {
	   this.reg2=reg2;
	   
   }
   public void setaddress(int address) {
	   this.address=address;
	   
   }
   public void setregnum1(int regnum1) {
	   this.regnum1=regnum1;
	   
   }
   public void setregnum2(int regnum2) {
	   this.regnum2=regnum2;
	   
   }
   public void setALUop(int ALUop) {
	   this.ALUop=ALUop;
	   
   }
   public void setALUSrc(boolean ALUSrc) {
	   this.ALUSrc=ALUSrc;
	   
   }
   public void setRegDst(boolean RegDst) {
	   this.RegDst=RegDst;
	   
   }
   
   
   public int getALUresult() {
	   if(ALUop==0) {
		   if(ALUSrc==false)
		   {
			   EXMEMbuffer[2]=reg1+reg2;
			  
		   }
		   else
		   {
			   EXMEMbuffer[2]=reg1+address/4;   //lw rt,offset(rs)
		   }
	   }
	   else if(ALUop==1)
	   {
		   if(ALUSrc==false)
		   {
			   EXMEMbuffer[2]=reg1-reg2;
			  
		   }
		   else
		   {
			   EXMEMbuffer[2]=reg1-address;
		   }
	   }
	   else
	   {
		   funct=address & 63;
		   if(funct==32)
		   {
			   if(ALUSrc==false)
			   {
				   EXMEMbuffer[2]=reg1+reg2;
				  
			   }
			   else
			   {
				   EXMEMbuffer[2]=reg1+address;
			   }
		   }
		   else
		   {
			   if(ALUSrc==false)
			   {
				   EXMEMbuffer[2]=reg1-reg2;
				  
			   }
			   else
			   {
				   EXMEMbuffer[2]=reg1-address;
			   }
		   }
	   }
	   if(EXMEMbuffer[2]==0)
	   {
		   EXMEMbuffer[1]=1;
	   }
	   else
	   {
		   EXMEMbuffer[1]=0;
	   }
	   return  EXMEMbuffer[2];
   }

   public int getBranch()
   {
	   EXMEMbuffer[0]=BA+address*4;
	   return EXMEMbuffer[0];
   }
   
   public int getzero()
   {
	  
	   return EXMEMbuffer[1];
   }
   
   public int getreg2()
   {
	   EXMEMbuffer[3]=reg2;
	   return EXMEMbuffer[3];
   }
   
   public int getregnum()
   {
	   if(RegDst==false)
	   {
		   EXMEMbuffer[4]=regnum1;
	   }
	   else
	   {
		   EXMEMbuffer[4]=regnum2;
	   }
	   
	   return EXMEMbuffer[4];
   }
}

/*public class EX
{
	public static void main(String argv[])
	{
		ex ex1=new ex();
		
	}
}*/