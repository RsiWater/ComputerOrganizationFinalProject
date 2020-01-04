package com.MEM;

import com.EX.RegEXMEM;

public class MEM 
{

	private int datamem[];
	private int readData=0;
	
	private boolean pcsrc=false;
	private boolean branch = false;
	private boolean zero = false;
	private boolean checkBranch=false;
	
	private String currentInstruction;
	
	RegEXMEM exmem;
	RegMEMWB memwb;
	
	public MEM(RegEXMEM exmem,RegMEMWB memwb) 
	{	
		datamem= new int[32];
		for(int i=0;i<32;i++) {
		 datamem[i]=1;
		}
		this.exmem=exmem;
		this.memwb=memwb;
	}
	
	public void startMEM() {
		if(exmem.getCurrentInstruction().equals("null")) this.currentInstruction = "null";
		else this.currentInstruction = exmem.getCurrentInstruction();
		checkBranch=false;
		memwb.setCurrentInstruction(exmem.getCurrentInstruction());
		if(branchoper(exmem.getBranch(),exmem.getBranZero())) {
			checkBranch=true;
		}
		if(exmem.getMemWrite()==false&&exmem.getMemRead()==true) {
			if(exmem.getAluResult()>=0 && exmem.getAluResult()<32) {
				readData=datamem[exmem.getAluResult()];
			}
		}
		else if(exmem.getMemWrite()==true&&exmem.getMemRead()==false) {
			if(exmem.getAluResult()>=0 && exmem.getAluResult()<32) {
				datamem[exmem.getAluResult()]=exmem.getWriteData();
			}
		}
		memwb.setControl(exmem.getRegWrite(), exmem.getMemToReg());
		memwb.setPassValue(readData, exmem.getAluResult(), exmem.getReg());
		
		if(exmem.getCurrentInstruction()=="sw") {////////////////////////////////////////////////////
			System.out.println(exmem.getCurrentInstruction()+" "+datamem[exmem.getAluResult()]);
		}//debug
	}
	public void printCurrentStage()
	{
		if(!this.currentInstruction.equals("null"))
		{
			this.memwb.setSignalString(this.exmem.getSignalString().substring(3));
			System.out.println(this.currentInstruction+": MEM "+this.memwb.getSignalString());
		}
	}
	public boolean ifBranch() {
		return checkBranch;
	}
	
	public int getInsAdd() {
		return exmem.getBA();
	}
	public void resetCurrentInstruction() { this.currentInstruction = "null"; }

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
	public boolean isNull() {
		if(exmem.getCurrentInstruction().equals("null")) return true;
		else return false;
	}
}