package com.ID;

public class RegIDEX {
	private boolean regDst; //EX
	private boolean branch;
	private boolean memRead;
	private boolean memToReg;
	private int aluOp; //EX
	private boolean memWrite;
	private boolean aluSrc; //EX
	private boolean regWrite;

	private int BA;
	private int reg1;

	private int reg2;
	private int addOrCon;
	private int rt;
	private int rd;
	
	private String currentInstruction;
	
	public RegIDEX() {
		regDst=false;
		branch=false;
		memRead=false;
		memToReg=false;
		aluOp=0;
		memWrite=false;
		aluSrc=false;
		regWrite=false;
		reg1=0;
		reg2=0;
		addOrCon=0;
		rt=0;
		rd=0;
		currentInstruction = "null";
	}
	
	public void resetRegIDEX() {
		regDst=false;
		branch=false;
		memRead=false;
		memToReg=false;
		aluOp=0;
		memWrite=false;
		aluSrc=false;
		regWrite=false;
		currentInstruction = "null";
		reg1=0;
		reg2=0;
		addOrCon=0;
		rt=0;
		rd=0;
		currentInstruction="";
	}
	
	public void setCurrentInstruction(String currentInstruction) {
		this.currentInstruction=currentInstruction;
	}
	
	public void setRegIDEX(boolean regDst,int aluOp,boolean aluSrc,boolean branch,boolean memRead,boolean memWrite,boolean regWrite,boolean memToReg) {
		this.regDst=regDst;
		this.branch=branch;
		this.memRead=memRead;
		this.memToReg=memToReg;
		this.aluOp=aluOp;
		this.memWrite=memWrite;
		this.aluSrc=aluSrc;
		this.regWrite=regWrite;
	}
	
	public void setPassRegIDEX(int BA,int reg1,int reg2,int addOrCon,int rt, int rd) {
		this.BA=BA;
		this.reg1=reg1;
		this.reg2=reg2;
		this.addOrCon=addOrCon;
		this.rt=rt;
		this.rd=rd;
	}

	public void setReg1(int reg1) { this.reg1 = reg1; }
	public void setReg2(int reg2) { this.reg2 = reg2; }
	public boolean getRegDst() { return regDst; }
	public boolean getBranch() { return branch;	}
	public boolean getMemRead() { return memRead; }
	public boolean getMemToReg() { return memToReg;	}
	public int getAluOp() {	return aluOp; }
	public boolean getMemWrite() { return memWrite;	}
	public boolean getAluSrc() { return aluSrc;	}
	public boolean getRegWrite() { return regWrite;	}
	public int getBA() { return BA; }
	public int getReg1() { return reg1; }
	public int getReg2() { return reg2; }
	public int getSignExtend() { return addOrCon; }
	public int getRt() { return rt; }
	public int getRd() { return rd; }
	public String getCurrentInstruction() { return currentInstruction; }
}
