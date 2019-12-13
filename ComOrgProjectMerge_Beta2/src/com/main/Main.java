package com.main;

import java.io.*;
import java.util.ArrayList;
import com.IF.*;
import com.ID.*;
import com.EX.*;
import com.MEM.*;
import com.WB.*;
//�ثe��System.out�bMEM.java�MWB.java���ܦh/�Ÿ�����///////////////////////////
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

        InstructionFetch IF = new InstructionFetch(bufferedData); //�غcIF
        RegIDEX idex=new RegIDEX(); //�غcID/EX (Pipeline)
        ID id=new ID(idex); //�غcID
        RegEXMEM exmem=new RegEXMEM(); //�غcEX/MEM (Pipeline)
        EX ex=new EX(idex,exmem); //�غcEX
        RegMEMWB memwb=new RegMEMWB(); //�غcMEM/WB
        MEM mem=new MEM(exmem,memwb); //�غcMEM
        WB wb=new WB(memwb,id);
        
        for(int i=0;i<bufferedData.size();i++) {
        	id.startID(IF.Fetch(i),i);
        	ex.startEX();  //�p�G�Obranch(pipeline)�i�ϥ�idex.getTaken()���o�O�_taken
        	mem.startMEM(); //�p�G�Obranch���O���U�Ӵ��� mem.ifBranch()
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
