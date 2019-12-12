package com.WB;

import com.ID.ID;
import com.MEM.RegMEMWB;

public class WB {
	RegMEMWB memwb;
	ID id;
	
	public WB(RegMEMWB memwb,ID id) {
		this.memwb=memwb;
		this.id=id;
	}
	
	public void startWB() {
		if(memwb.getRegWrite()) {
			if(memwb.getDisReg()!=0) { //不能改寫0號暫存器
				if(memwb.getMemToReg()) {
					id.writeReg(memwb.getDisReg(), memwb.getAluResult());
				}
				else {
					id.writeReg(memwb.getDisReg(), memwb.getReadData());
				}
			}
		}
	}
}
