package com.EX;

public class RegEXMEM {
	private boolean branch,memRead,memWrite,regWrite,memToReg;
	private boolean branZero;
	private int BA,aluResult,writeData;
	private int regNum;
	private String currentInstruction;
	private String signalString;

	public RegEXMEM() {
		currentInstruction = "null";
		signalString = "null";
	}
	
	public void resetRegEXMEM() {
		branch=false;
		memRead=false;
		memWrite=false;
		regWrite=false;
		memToReg=false;
		branZero=false;
		currentInstruction = "null";
		signalString = "null";
		BA=0;
		aluResult=0;
		writeData=0;
		regNum=0;
	}
	
	public void setCurrentInstruction(String currentInstruction) {
		this.currentInstruction=currentInstruction;
	}
	
	public void setControl(boolean branch,boolean memRead,boolean memWrite,boolean regWrite, boolean memToReg) {
		this.branch=branch;
		this.memRead=memRead;
		this.memWrite=memWrite;
		this.regWrite=regWrite;
		this.memToReg=memToReg;
	}
	
	public void setPassRegIDEX(int BA,boolean branZero,int aluResult,int writeData,int regNum) {
		this.branZero=branZero;
		this.BA=BA;
		this.aluResult=aluResult;
		this.writeData=writeData;
		this.regNum=regNum;
	}
	
	public boolean getBranch() { return branch; }
	public boolean getMemRead() { return memRead; }
	public boolean getMemWrite() { return memWrite; }
	public boolean getRegWrite() { return regWrite; }
	public boolean getMemToReg() { return memToReg; }
	public boolean getBranZero() { return branZero; }
	public int getBA() { return BA; }
	public int getAluResult() { return aluResult; }
	public int getWriteData() { return writeData; }
	public int getReg() { return regNum; }
	public String getCurrentInstruction() { return currentInstruction; }
	public void setSignalString(String signalString) { this.signalString = signalString; }
	public String getSignalString() { return signalString; }
}
