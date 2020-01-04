package com.IF;

import java.util.ArrayList;


public class IF {
    private final int MEMORY_SIZE = 32;

	private ArrayList<String> rawInstruction;
    private Integer[] instructionMemory = new Integer[MEMORY_SIZE];
    private String[] operationList = new String[MEMORY_SIZE];
    private RegIFID ifid;
    private boolean stallFlag;
    

    public IF(ArrayList<String> rawInstruction, RegIFID ifid)
    {
        for(int i = 0;i < MEMORY_SIZE;i++)
        {
            instructionMemory[i] = 0;
            operationList[i] = "null";
        }
        
        this.ifid = ifid;
        this.rawInstruction = rawInstruction;
        this.ToMachineCode();
        this.stallFlag = false;
    }
    public boolean EOF(int PC)
    {
        if(this.instructionMemory[PC].intValue() != 0) return false;
        else return true;
    }
    public void startIF(int PC)
    {
        if(stallFlag)stallFlag = false;
        ifid.setPC(PC + 1);
        ifid.setCurrentInstruction(this.operationList[ifid.getPC()]);
        ifid.setInstruction_machineCode(this.instructionMemory[ifid.getPC()].intValue());
    }
    public void stallIf(int PC)
    {
        stallFlag = true;
    	ifid.setCurrentInstruction(this.operationList[PC]);
        ifid.setInstruction_machineCode(this.instructionMemory[PC].intValue());
    }
    public void printCurrentStage(int PC)
    {
        if(!this.operationList[PC].equals("null"))  System.out.println(this.operationList[PC] + ": IF");
    }

    public int getPC() { return ifid.getPC(); }
    public void setPC(int PC){ifid.setPC(PC);}

    public void ToMachineCode()
    {
        int i = 0;
        for(String inst : this.rawInstruction)
        {
            if(inst.indexOf(' ') == -1)
            {
                System.err.println("Compile error: Instruction is illegal");
                return;
            }

            String operation = inst.substring(0, inst.indexOf(' '));
            this.operationList[i] = operation;
            int machineCode;

            if(operation.equals("lw") || operation.equals("sw") || operation.equals("beq")) { machineCode = I_Format_StringDivide(inst); }
            else { machineCode = R_Format_StringDivide(inst); }

            this.instructionMemory[i++] = machineCode;
        }
    }
    private int I_Format_StringDivide(String inst)
    {
        String lastInst, operation, rs_string, rt_string, offset_string;
        int opcode = 0, rs = 0, rt = 0, offset = 0, completedInst = 0;
        operation = inst.substring(0, inst.indexOf(' ')).toLowerCase();
        lastInst = inst.substring(inst.indexOf(' ') + 1);

        if(operation.equals("beq"))
        {
            opcode = 4;

            rs_string = lastInst.substring(0, lastInst.indexOf(','));
            lastInst = lastInst.substring(lastInst.indexOf(',') + 1);
            rt_string = lastInst.substring(0, lastInst.indexOf(','));
            offset_string = lastInst.substring(lastInst.indexOf(',') + 1);
        }
        else
        {
            if(operation.equals("lw")) opcode = 33;
            else if(operation.equals("sw")) opcode = 43;

            rt_string = lastInst.substring(0, lastInst.indexOf(','));
            lastInst = lastInst.substring(lastInst.indexOf(',') + 1);
            offset_string = lastInst.substring(0, lastInst.indexOf('('));
            lastInst = lastInst.substring(lastInst.indexOf('(') + 1);
            rs_string = lastInst.substring(0, lastInst.indexOf(')'));
        }

        boolean negativeFlag = false;
        rs = StringToInteger(rs_string.substring(1));
        rt = StringToInteger(rt_string.substring(1));
        if(offset_string.charAt(0) == '-')
        {
            negativeFlag = true;
            offset = StringToInteger(offset_string.substring(1));
            offset *= -1;
        }
        else offset = StringToInteger(offset_string);

        completedInst += opcode;
        completedInst = completedInst << 5;
        completedInst += rs;
        completedInst = completedInst << 5;
        if(negativeFlag) rt++;
        completedInst += rt;
        completedInst = completedInst << 16;
        completedInst += offset;

        return completedInst;
    }
    private int R_Format_StringDivide(String inst)
    {
        String lastInst, operation, rs_string, rt_string, rd_string, shamt_string, funct_string;
        int opcode = 0, rs = 0, rt = 0, rd = 0,shamt = 0, funct = 0, completedInst = 0;
        operation = inst.substring(0, inst.indexOf(' ')).toLowerCase();
        lastInst = inst.substring(inst.indexOf(' ') + 1);

        if(operation.equals("add")) { funct = 32; }
        else { funct = 34; } //sub

        rd_string = lastInst.substring(0, lastInst.indexOf(','));
        lastInst = lastInst.substring(lastInst.indexOf(',') + 1);
        rs_string = lastInst.substring(0, lastInst.indexOf(','));
        rt_string = lastInst.substring(lastInst.indexOf(',') + 1);

        rs = StringToInteger(rs_string.substring(1));
        rt = StringToInteger(rt_string.substring(1));
        rd = StringToInteger(rd_string.substring(1));

        completedInst += opcode;
        completedInst = completedInst << 5;
        completedInst += rs;
        completedInst = completedInst << 5;
        completedInst += rt;
        completedInst = completedInst << 5;
        completedInst += rd;
        completedInst = completedInst << 5;
        completedInst += shamt;
        completedInst = completedInst << 6;
        completedInst += funct;

        return completedInst;
    }
    private int StringToInteger(String str)
    {
        int result = 0;
        for(int i = 0;i < str.length(); i++)
        {
            if(i != 0) result *= 10;
            char c = str.charAt(i);
            result += c - '0';
        }
        return result;
    }
}
