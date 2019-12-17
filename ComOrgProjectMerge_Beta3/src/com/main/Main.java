package com.main;

import java.io.*;
import java.util.ArrayList;
import com.IF.*;
import com.ID.*;
import com.EX.*;
import com.MEM.*;
import com.WB.*;

public class Main {
	public static void main (String[] args)
    {
        ArrayList<String> bufferedData;
        try
        {
            bufferedData = ReadFile();
        } catch (IOException e) {
            System.out.println("Loading error, can't find the path of file.");
            return;
        }

        RegIFID ifid = new RegIFID();
        IF If = new IF(bufferedData, ifid);
        RegIDEX idex=new RegIDEX();
        ID id=new ID(ifid,idex);
        RegEXMEM exmem=new RegEXMEM();
        EX ex=new EX(idex,exmem);
        RegMEMWB memwb=new RegMEMWB();
        MEM mem=new MEM(exmem,memwb);
        WB wb=new WB(memwb,id);

        for(int cycle = 1;(cycle <= 5)||(!wb.isNull()) ;cycle++)
        {
            System.out.println("Cycle "+ cycle);
            wb.startWB();
            wb.printCurrentStage();

            mem.startMEM();
            mem.printCurrentStage();

            ex.startEX();
            ex.printCurrentStage();

            id.startID();
            id.printCurrentStage();
            
            If.startIF(If.getPC());
            If.printCurrentStage(If.getPC());
        }
//
//        while(If.EOF(If.getPC())) {
//            If.startIF(If.getPC());
//        	id.startID();
//        	ex.startEX();
//        	mem.startMEM();
//        	if(mem.ifBranch()) {
//        		If.setPC(mem.getInsAdd());
//        	}
//        	wb.startWB();
//        }
    }

    public static ArrayList<String> ReadFile() throws IOException
    {
        File file = new File("res/input.txt");
        BufferedReader in = new BufferedReader(new FileReader(file));
        ArrayList<String> strList = new ArrayList<>();

        for(String str = in.readLine(); str != null; str = in.readLine())
        {
            strList.add(str);
        }

        return strList;
    }
    public static String Debug_toBinary_32bit(int input)
    {
        StringBuilder str = new StringBuilder();
        boolean isNegative = false;
        if(input < 0)
        {
            isNegative = true;
            input = input & (2147483647);
        }
        for(int i = 0;i < 32;i++)
        {
            if(input % 2 == 1)str.append(1);
            else str.append(0);
            input /= 2;
        }
        str.reverse();
        String result;
        if(isNegative)
        {
            str.setCharAt(0, '1');
        }
        result = str.toString();
        return result;
    }
}
