
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
	RegIDEX idex;
	Register[] regs;
	
	public ID(RegIDEX idex){ //instruction 改成IF/ID物件
		this.idex=idex;
		regs=new Register[32];
		for(int i=0;i<32;i++) {
			regs[i]=new Register();
		}
		regs[0].setZero();
	}
	public void startID(int instruction) { //instruction 改成IF/ID物件 或 使用本身的IF/ID
		int opcode=instruction&-67108864;
		int reg1,reg2;
		int addOrCon;
		Register rt,rd;
		reg1=regs[(instruction&65011712)/2097152].getValue();
		reg2=regs[(instruction&2031616)/65536].getValue();
		addOrCon=instruction&65535;
		rt=regs[(instruction&2031616)/65536];
		rd=regs[(instruction&63488)/2048];
		
		//System.out.println("Opcode: "+(instruction&-67108864));
		//System.out.println("Rs Number: "+((instruction&65011712)/2097152));
		//System.out.println("Rt Number: "+((instruction&2031616)/65536));
		//System.out.println("Address or Constant: "+(instruction&65535));
		//System.out.println("Rt Number: "+((instruction&2031616)/65536));
		//System.out.println("Rd Number: "+((instruction&63488)/2048));
		//System.out.println("Function: "+(instruction&63));
		if(opcode==0) {  //add sub
			if((instruction&63) == 32) {
				idex.setRegIDEX(1, 2, 0, 0, 0, 0, 1, 0); //add 第二位設置aluOp也就是設置要做add或sub運算
				idex.setCurrentInstruction("add");
			}
			else { //sub
				idex.setRegIDEX(1, 3, 0, 0, 0, 0, 1, 0);
				idex.setCurrentInstruction("sub");
			}
		}
		else if(opcode==-1946157056) { //lw
			idex.setRegIDEX(0, 0, 1, 0, 1, 0, 1, 1);
			idex.setCurrentInstruction("lw");
		}
		else if(opcode==-1409286144) { //sw
			idex.setRegIDEX(0, 0, 1, 0, 0, 1, 0, 0); //首末為x
			idex.setCurrentInstruction("sw");
		}
		else if(opcode==268435456) { //branch
			idex.setRegIDEX(0, 1, 0, 1, 0, 0, 0, 0); //首末為x
			idex.setCurrentInstruction("branch");
		}
		idex.setPassRegIDEX(reg1, reg2, addOrCon, rt, rd);
	}
}
