package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.UndoableEditListener;

public class ReadFile {
	public static List<String> readCsv(String path) {
		try { 
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            //reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null; 
            List<String> uList = new ArrayList<>();
            int r=0,c=0;
            
            while((line=reader.readLine())!=null){ 
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                for(int i=0;i<item.length;i++) {
                	uList.add(item[i]);
                }
                c=item.length;
                r++;
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
            }
            uList.add(Integer.toString(r));
            uList.add(Integer.toString(c));
            return uList;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
		return null;
		
	}
}
