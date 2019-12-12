package com.IF;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class InstructionFetch {
	private ArrayList<String> rawInstruction;
    private Integer[] instructionMemory = new Integer[32];
    public InstructionFetch(ArrayList<String> rawInstruction)
    {
        this.rawInstruction = rawInstruction;
        this.ToMachineCode();
    }
    public int Fetch(int PC)
    {
        return this.instructionMemory[PC].intValue();
    }
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

        rs = StringToInteger(rs_string.substring(1));
        rt = StringToInteger(rt_string.substring(1));
        offset = StringToInteger(offset_string);

        completedInst += opcode;
        completedInst = completedInst << 5;
        completedInst += rs;
        completedInst = completedInst << 5;
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
