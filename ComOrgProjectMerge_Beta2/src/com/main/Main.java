package com.main;

import java.io.*;
import java.util.ArrayList;
import com.IF.*;
import com.ID.*;
import com.EX.*;
import com.MEM.*;
import com.WB.*;
//目前的System.out在MEM.java和WB.java的很多/符號附近///////////////////////////
public class Main {
	public static void main (String[] args)
    {
        ArrayList<String> bufferedData = new ArrayList<>();
        try
        {
            bufferedData = ReadFile();
        } catch (IOException e) {
            System.out.println("Loading error, can't find the path of file.");
            return;
        }

        InstructionFetch IF = new InstructionFetch(bufferedData); //建構IF
        RegIDEX idex=new RegIDEX(); //建構ID/EX (Pipeline)
        ID id=new ID(idex); //建構ID
        RegEXMEM exmem=new RegEXMEM(); //建構EX/MEM (Pipeline)
        EX ex=new EX(idex,exmem); //建構EX
        RegMEMWB memwb=new RegMEMWB(); //建構MEM/WB
        MEM mem=new MEM(exmem,memwb); //建構MEM
        WB wb=new WB(memwb,id);
        
        for(int i=0;i<bufferedData.size();i++) {
        	id.startID(IF.Fetch(i),i);
        	ex.startEX();  //如果是branch(pipeline)可使用idex.getTaken()取得是否taken
        	mem.startMEM(); //如果是branch指令接下來測試 mem.ifBranch()
        	if(mem.ifBranch()) {
        		i=mem.getInsAdd();
        	}
        	wb.startWB();
        }
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
