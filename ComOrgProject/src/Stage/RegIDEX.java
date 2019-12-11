/* �ϥ�getRegDst()����ƥi���oControl
 * �ϥ�getReg1() ����ƥi���oID�ǹL�h��reg1 reg2���ȡBrt rd���O����(�YRegister����)
 * �ϥ�getCurrentInstruction �i���o�{�b���檺���O�r��
 */
public class RegIDEX {
	private int regDst;
	private int branch;
	private int memRead;
	private int memToReg;
	private int aluOp;
	private int memWrite;
	private int aluSrc;
	private int regWrite;
	
	private int reg1;  //Chapter_04 ��87�� //�n�g�]�m�o�Ǫ����
	private int reg2;
	private int addOrCon;
	private Register rt;
	private Register rd;
	
	private String currentInstruction;
	
	public RegIDEX() {
		regDst=0;
		branch=0;
		memRead=0;
		memToReg=0;
		aluOp=0;
		memWrite=0;
		aluSrc=0;
		regWrite=0;
		reg1=0;
		reg2=0;
		addOrCon=0;
		rt=null;
		rd=null;
		currentInstruction="";
	}
	
	public void resetRegIDEX() {
		regDst=0;
		branch=0;
		memRead=0;
		memToReg=0;
		aluOp=0;
		memWrite=0;
		aluSrc=0;
		regWrite=0;
		reg1=0;
		reg2=0;
		addOrCon=0;
		rt=null;
		rd=null;
		currentInstruction="";
	}
	
	public void setCurrentInstruction(String currentInstruction) {
		this.currentInstruction=currentInstruction;
	}
	
	public void setRegIDEX(int regDst,int aluOp,int aluSrc,int branch,int memRead,int memWrite,int regWrite,int memToReg) {
		this.regDst=regDst;
		this.branch=branch;
		this.memRead=memRead;
		this.memToReg=memToReg;
		this.aluOp=aluOp;
		this.memWrite=memWrite;
		this.aluSrc=aluSrc;
		this.regWrite=regWrite;
	}
	
	public void setPassRegIDEX(int reg1,int reg2,int addOrCon,Register rt, Register rd) {
		this.reg1=reg1;
		this.reg2=reg2;
		this.addOrCon=addOrCon;
		this.rt=rt;
		this.rd=rd;
	}
	
	public int getRegDst() { return regDst; }
	public int getBranch() { return branch;	}
	public int getMemRead() { return memRead; }
	public int getMemToReg() { return memToReg;	}
	public int getAluOp() {	return aluOp; }
	public int getMemWrite() { return memWrite;	}
	public int getAluSrc() { return aluSrc;	}
	public int getRegWrite() { return regWrite;	}
	public int getReg1() { return reg1; }
	public int getReg2() { return reg2; }
	public int getSignExtend() { return addOrCon; }
	public Register getRt() { return rt; }
	public Register getRd() { return rd; }
	public String getCurrentInstruction() { return currentInstruction; }
}
