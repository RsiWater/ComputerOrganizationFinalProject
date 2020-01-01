package com.ID;

import com.ID.RegIDEX;
import com.ID.Register;
import com.IF.RegIFID;
import com.EX.RegEXMEM;
import com.MEM.RegMEMWB;

//opcode lw 100011(2)=35(10) 10001100000000000000000000000000
//opcode sw 101011(2)=43(10) 10101100000000000000000000000000
//opcode add 000000 function 100000
//opcode sub 000000 function 100010
//opcode beq 000100(2)=4(10) 00010000000000000000000000000000
//11111100000000000000000000000000(2)=-67108864(10) opcode
//111111(2)=63(10) function
//R-format
//11111000000000000000000000(2)=65011712(10) rs
//111110000000000000000(2)=2031616(10) rt
//1111100000000000(2)=63488(10) rd
//11111000000(2)=1984(10) shamt
//I-format
//1111111111111111(2)=65535(10) address/constant
public class ID {
	private RegIDEX idex;
	private Register[] regs;
	private boolean taken;
	private boolean exhazardflag=false;
	private boolean memhazardflag=false;
	private int BA;
	private RegIFID ifid;
	private RegEXMEM exmem;
	private RegMEMWB memwb;
	private String currentInstruction;
	
	public ID(RegIFID ifid, RegIDEX idex,RegEXMEM exmem,RegMEMWB memwb){
		this.ifid = ifid;
		this.idex=idex;
		this.exmem=exmem;
		this.memwb=memwb;
		regs=new Register[32];
		for(int i=0;i<32;i++) {
			regs[i]=new Register();
			//regs[i].setValue(i); //debug
		}
		regs[0].setZero();
	}
	public void resetID() {
		
		for(int i=0;i<32;i++) {
			regs[i].setZero();
		}
		
		taken=false;
		exhazardflag=false;
		memhazardflag=false;
		BA= 0;
		String currentInstruction="";
		
		
	}
	public void startID() {
		if(exhazardflag==true||memhazardflag==true)
		{
			return;
		}
		
		this.currentInstruction = ifid.getCurrentInstruction();
		int opcode=ifid.getInstruction_machineCode()&-67108864;
		int reg1,reg2;
		int addOrCon;
		this.BA=ifid.getPC();
		taken=false;
		int rt,rd,checkreg1=(ifid.getInstruction_machineCode()&65011712)/2097152,checkreg2=(ifid.getInstruction_machineCode()&2031616)/65536;
		//System.out.println(exmem.getReg()+""+checkreg1+""+checkreg2);
		if((checkreg1==exmem.getReg()||checkreg2==exmem.getReg())&&(checkreg1!=0&&checkreg2!=0&&exmem.getReg()!=0)&&(exmem.getRegWrite()==true))
		{
			exhazardflag=true;
//			System.out.println("stall");
		}
		else
		{
		if((checkreg1==memwb.getDisReg()||checkreg2==memwb.getDisReg())&&(checkreg1!=0&&checkreg2!=0&&memwb.getDisReg()!=0)&&(memwb.getRegWrite()==true))
		{
			memhazardflag=true;
//			System.out.println("m-stall");
		}
		}
		reg1=regs[(ifid.getInstruction_machineCode()&65011712)/2097152].getValue();
		reg2=regs[(ifid.getInstruction_machineCode()&2031616)/65536].getValue();
		addOrCon=ifid.getInstruction_machineCode()&65535;
		rt=(ifid.getInstruction_machineCode()&2031616)/65536;
		rd=(ifid.getInstruction_machineCode()&63488)/2048;
		
		//System.out.println("Opcode: "+(instruction&-67108864));
		//System.out.println("Rs Number: "+((instruction&65011712)/2097152));
		//System.out.println("Rt Number: "+((instruction&2031616)/65536));
		//System.out.println("Address or Constant: "+(instruction&65535));
		//System.out.println("Rt Number: "+((instruction&2031616)/65536));
		//System.out.println("Rd Number: "+((instruction&63488)/2048));
		//System.out.println("Function: "+(instruction&63));
		//System.out.println();
		if(opcode==0) {  //add sub
			if((ifid.getInstruction_machineCode()&63) == 32) {
				idex.setRegIDEX(true, 0, false, false, false, false, true, true);
				idex.setCurrentInstruction("add");
				//System.out.println("add");
			}
			else { //sub
				idex.setRegIDEX(true, 1, false, false, false, false, true, true);
				idex.setCurrentInstruction("sub");
				//System.out.println("sub");
			}
		}
		else if(opcode==-2080374784) { //lw
			idex.setRegIDEX(false, 0, true, false, true, false, true, false);
			idex.setCurrentInstruction("lw");
			//System.out.println("lw");
		}
		else if(opcode==-1409286144) { //sw
			idex.setRegIDEX(false, 0, true, false, false, true, false, false);
			idex.setCurrentInstruction("sw");
			//System.out.println("sw");
		}
		else if(opcode==268435456) { //branch
			idex.setRegIDEX(false, 1, false, true, false, false, false, false);
			idex.setCurrentInstruction("branch");
			//System.out.println("branch");
			if(reg1==reg2) {
				taken=true;
			}
		}
		idex.setPassRegIDEX( this.BA, reg1, reg2, addOrCon, rt, rd);
		idex.setCurrentInstruction(ifid.getCurrentInstruction());

		//debug
		//System.out.println(idex.getCurrentInstruction()+" "+this.BA+" "+reg1+" "+reg2+" "+addOrCon+" "+rt+" "+rd);
	}
	public void printCurrentStage()
	{
		if(!this.currentInstruction.equals("null")) System.out.println(this.currentInstruction+": ID");
	}

	public void writeReg(int regNum,int value) {
		regs[regNum].setValue(value);
	}
	
	public int getRegValue(int regNum) {
		return regs[regNum].getValue();
	}
	
	public boolean getTaken() {
		return taken;
	}
	public boolean getexhazardflag()
	{
		return exhazardflag;
	}
	public boolean getmemhazardflag()
	{
		return memhazardflag;
	}
	public void setexhazardflag()
	{
		exhazardflag=false;
	}
	public void setmemhazardflag()
	{
		memhazardflag=false;
	}

}
