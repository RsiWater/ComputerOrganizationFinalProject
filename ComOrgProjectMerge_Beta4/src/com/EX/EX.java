package com.EX;

import com.ID.*;

public class EX
{
   private int BA,reg1,reg2,address,ALUop,regnum1,regnum2,funct=0;
   private boolean ALUSrc,RegDst;
   private int[] EXMEMbuffer=new int[5];
   private StringBuilder signalString;
   private String currentInstruction;
   
   RegIDEX idex;
   RegEXMEM exmem;
   ID id;
   
   public EX(RegIDEX idex,RegEXMEM exmem,ID id) {
	   this.id=id;
	   this.idex=idex;
	   this.exmem=exmem;
	   this.signalString = new StringBuilder();
   }
   
   public void startEX() {
	   
	   if(id.getexhazardflag()==true||id.getmemhazardflag()==true)
	   {
		   return;
	   }
	   
	   this.currentInstruction = idex.getCurrentInstruction();
	   exmem.setCurrentInstruction(idex.getCurrentInstruction());
	   setBA(idex.getBA());
	   setreg1(idex.getReg1());
	   setreg2(idex.getReg2());
	   setaddress(idex.getSignExtend());
	   setregnum1(idex.getRt());
	   setregnum2(idex.getRd());
	   setALUop(idex.getAluOp());
	   setALUSrc(idex.getAluSrc());
	   setRegDst(idex.getRegDst());
	   getALUresult();
	   getBranch();
	   getreg2();
	   getregnum();
	   exmem.setControl(idex.getBranch(), idex.getMemRead(), idex.getMemWrite(), idex.getRegWrite(), idex.getMemToReg());
	   exmem.setPassRegIDEX(EXMEMbuffer[0], intToBoolean(EXMEMbuffer[1]), EXMEMbuffer[2], EXMEMbuffer[3], EXMEMbuffer[4]);

	   this.buildSignalString();
	   
	   //System.out.println(idex.getCurrentInstruction()+" "+EXMEMbuffer[2]);
   }
   public void printCurrentStage()
   {
	   if(!this.currentInstruction.equals("null")) System.out.println(this.currentInstruction+": EX "+exmem.getSignalString());
   }

   public void buildSignalString()
   {
   	if(this.signalString.length() != 0)  this.signalString.delete(0, this.signalString.length());
	if(idex.getCurrentInstruction().equals("sw") || idex.getCurrentInstruction().equals("beq")) this.signalString.append('X');
	else
	{
		if(idex.getRegDst()) this.signalString.append('1');
		else this.signalString.append('0');
	}
	if(idex.getAluSrc()) this.signalString.append('1');
	else this.signalString.append('0');

	this.signalString.append(' ');

	if(idex.getBranch()) this.signalString.append('1');
	else this.signalString.append('0');
	if(idex.getMemRead()) this.signalString.append('1');
	else this.signalString.append('0');
	if (idex.getMemWrite()) this.signalString.append('1');
	else this.signalString.append('0');

	this.signalString.append(' ');

	if(idex.getRegWrite()) this.signalString.append('1');
	else this.signalString.append('0');
	if((idex.getCurrentInstruction().equals("sw") || idex.getCurrentInstruction().equals("beq"))) this.signalString.append('X');
	else
	{
		if(idex.getMemToReg()) this.signalString.append('1');
		else this.signalString.append('0');
	}
	exmem.setSignalString(this.signalString.toString());
   }
   public void setBA(int BA) { this.BA=BA; }
   public void setreg1(int reg1) { this.reg1=reg1; }
   public void setreg2(int reg2) { this.reg2=reg2; }
   public void setaddress(int address) { this.address=address; }
   public void setregnum1(int regnum1) { this.regnum1=regnum1; }
   public void setregnum2(int regnum2) { this.regnum2=regnum2; }
   public void setALUop(int ALUop) { this.ALUop=ALUop; }
   public void setALUSrc(boolean ALUSrc) { this.ALUSrc=ALUSrc; }
   public void setRegDst(boolean RegDst) { this.RegDst=RegDst; }
   
   private boolean intToBoolean(int myBoolean) {
	   if(myBoolean==1) return true;
	   else return false;
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

   public void getBranch()
   {
	   EXMEMbuffer[0]=BA+address;
   }
   
   public void getreg2()
   {
	   EXMEMbuffer[3]=reg2;
   }
   
   public void getregnum()
   {
	   if(RegDst==false)
	   {
		   EXMEMbuffer[4]=regnum1;
	   }
	   else
	   {
		   EXMEMbuffer[4]=regnum2;
	   }
	   
   }
   
}