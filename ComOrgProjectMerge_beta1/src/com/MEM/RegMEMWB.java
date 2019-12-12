package com.MEM;

public class RegMEMWB {
	private int readData,aluResult,disReg;
	private boolean regWrite,memToReg;
	
	public RegMEMWB() {
		
	}
	
	public void setControl(boolean regWrite,boolean memToReg) {
		this.regWrite=regWrite;
		this.memToReg=memToReg;
	}
	
	public void setPassValue(int readData,int aluResult,int disReg) {
		this.readData=readData;
		this.aluResult=aluResult;
		this.disReg=disReg;
	}
	
	public boolean getRegWrite() { return regWrite; }
	public boolean getMemToReg() { return memToReg; }
	public int getReadData() { return readData; }
	public int getAluResult() { return aluResult; }
	public int getDisReg() { return disReg; }
}
