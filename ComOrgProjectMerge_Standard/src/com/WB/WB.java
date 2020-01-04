package com.WB;

import com.ID.*;
import com.MEM.RegMEMWB;

public class WB {
	private RegMEMWB memwb;
	private ID id;
	private String signalString;
	private String currentInstruction;
	
	public WB(RegMEMWB memwb,ID id) {
		this.memwb=memwb;
		this.id=id;
	}
	
	public void startWB() {
		if(memwb.getCurrentInstruction().equals("null")) this.currentInstruction = "null";
		else this.currentInstruction = memwb.getCurrentInstruction();
		if(memwb.getRegWrite()) {
			if(memwb.getDisReg()!=0) {
				if(memwb.getMemToReg()) {
					id.writeReg(memwb.getDisReg(), memwb.getReadData());
//					System.out.println(memwb.getCurrentInstruction()+" "+id.getRegValue(memwb.getDisReg()));
				}
				else {
					id.writeReg(memwb.getDisReg(), memwb.getAluResult());
//					System.out.println(memwb.getCurrentInstruction()+" "+id.getRegValue(memwb.getDisReg()) + "Read");
				}
			}
		}
		///////////////////////////////////////////debug
//		if(memwb.getCurrentInstruction()=="add" || memwb.getCurrentInstruction()=="sub" || memwb.getCurrentInstruction()=="lw") {
//			System.out.println(memwb.getCurrentInstruction()+" "+id.getRegValue(memwb.getDisReg()));
//		}
	}
	public void printCurrentStage()
	{
		if(!this.currentInstruction.equals("null")) {
			this.signalString = memwb.getSignalString().substring(4);
			System.out.println(this.currentInstruction + ": WB "+this.signalString);
		}
	}
	public boolean isNull() {
		if(memwb.getCurrentInstruction().equals("null")) return true;
		else return false;
	}
}
