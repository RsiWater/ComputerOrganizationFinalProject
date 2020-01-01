package com.IF;

public class RegIFID {
    private int Instruction_machineCode;
    private int PC;
    private String currentInstruction;

    public RegIFID()
    {
        this.currentInstruction = "null";
        this.PC = -1;
    }

    public void resetAll()
    {
        this.Instruction_machineCode = 0;
        this.PC = 0;
        this.currentInstruction = "";
    }
    public void setCurrentInstruction(String currentInstruction) { this.currentInstruction = currentInstruction; }
    public String getCurrentInstruction() { return currentInstruction; }
    public int getInstruction_machineCode() { return Instruction_machineCode; }
    public int getPC() { return PC; }
    public void setInstruction_machineCode(int instruction_machineCode) { this.Instruction_machineCode = instruction_machineCode; }
    public void setPC(int PC) { this.PC = PC; }

}
