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
	
	public boolean ifBranch() {
		return checkBranch;
	}
	
	public int getInsAdd() {
		return exmem.getBA();
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