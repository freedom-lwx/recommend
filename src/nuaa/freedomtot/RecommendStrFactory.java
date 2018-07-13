package nuaa.freedomtot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class RecommendStrFactory {

	//助(小孩 成年人)  孝(小孩 成年人) 勇(成年人)
	
	
	private Map<Integer,List<String>> forKids;
	private Map<Integer,List<String>> forAdults;
	
	private void init() {
		forKids=new HashMap<>();
		forAdults=new HashMap<>();
		for(int i=1;i<6;i++) {
			forKids.put(i, new LinkedList<String>());
			forAdults.put(i, new LinkedList<String>());
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Map<String,Integer> map=ReadFromExcel.readExcel("data.xls");
		RecommendStrFactory rsf= new RecommendStrFactory();
		rsf.readData();
		String ans;
		for (String name : map.keySet()) {
			if(map.get(name)>=1995) {
				ans=name+rsf.getKidRecStr(name);
			}
			else {
				ans=name+rsf.getAdultRecStr(name);
			}
			System.out.println(ans);
		}
		
	}
	
	
	
	private  String getAdultRecStr(String name) {
		// TODO Auto-generated method stub
		int type=name.hashCode()%5+1;
		List<String> list=null;
		list=forAdults.get(type);
		while((list.size()<=0)) {
			type++;
			type=type%5+1;
			list=forAdults.get(type);
		}
		
		int index=name.hashCode()%list.size();
		return list.get(index);
	}
	private  String getKidRecStr(String name) {
		// TODO Auto-generated method stub
		int type=name.hashCode()%5+1;
		List<String> list=null;
		list=forKids.get(type);
		while((list.size()<=0)) {
			type++;
			type=type%5+1;
			list=forKids.get(type);
		}
		
		int index=name.hashCode()%list.size();
		return list.get(index);
	}
	private void readData() throws FileNotFoundException {
		init();
		InputStream in=new FileInputStream("data4adults.txt");
		Scanner scanner=new Scanner(in);
		while (scanner.hasNextLine()) {
			String str=scanner.nextLine();
			String[] strs=str.split("，");
			String str2=str.substring(strs[0].length());	
			forAdults.get(parseStyle(strs[2].substring(0, 1))).add(str2);
			//System.out.println(parseStyle(strs[2].substring(0, 1))+" "+str2);
			//System.out.println(str2);
		}
		
		InputStream in2=new FileInputStream("data.txt");
		Scanner scanner2=new Scanner(in2);
		while (scanner2.hasNextLine()) {
			String str=scanner2.nextLine();
			String[] strs=str.split("，");
			String str2=str.substring(strs[0].length());	
			forKids.get(parseStyle(strs[2].substring(0, 1))).add(str2);
			//System.out.println(parseStyle(strs[2].substring(0, 1))+" "+str2);
			//System.out.println(str2);
		}
		
		scanner.close();
		scanner2.close();
		//System.out.println(forAdults.get(1));
	}
	
	private static int parseStyle(String str) {
		switch (str) {
		case "助":
			return 1;
		case "勇":
			return 2;
		case "信":
			return 3;
		case "敬":
			return 4;
		case "孝":
			return 5;
		case "诚":
			return 3;
		}
		
		return -1;
	}

}
