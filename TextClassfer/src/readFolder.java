import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;

public class readFolder {
	//read all txt files in ham or spam folder 
	//get num of file in a folder
	public static int numFile(String addr) throws FileNotFoundException,IOException{
		//read all txt in folder by File
		File folder = new File(addr);
		File[] listOfFiles = folder.listFiles();
				
		//get Nc for total num of files in ham or spam
		int num = listOfFiles.length;
		return num;
	}
	
	//since each txt file has different size, I prefer ArrayList to Array
	//input addr by system.in as ass1
	public static ArrayList<String> readtxt(String addr) throws FileNotFoundException,IOException{
		//ArrayList<ArrayList<term>> rdfolder = new ArrayList<>();
		ArrayList<String> readtxt = new ArrayList<>();
		
		//avoid not useful sign
		List<String> nword = asList("-", ",", ".","/","@");
		//read all txt in folder by File
		File folder = new File(addr);
		File[] listOfFiles = folder.listFiles();
		
		//get Nc for total num of files in ham or spam
		int num = listOfFiles.length;
		int t=0;
		int t1=0;
		System.out.print(num+"\n");
		for (int i = 0; i < num; i++) {
		  File file = listOfFiles[i];	//listOfFiles[i] is each training examples in ham or spam
		  if (file.isFile() ){
			  t1++;
			  if( file.getName().endsWith(".txt")) {
				  //System.out.print("\nfile: "+file.getName());
				  t++;
			    //tring content = FileUtils.readFileToString(file);
				//read .txt file
			    BufferedReader rd = new BufferedReader(new FileReader(file.getAbsolutePath()));
			    String line;
			    while((line=rd.readLine())!=null){
			    	//how to split into each character
			    	String[] rowData = line.split(" ");
			    	//read element of rowData into readtxt
			    	for(int j=0;j<rowData.length;j++){
			    		if(rowData[j]!=null&&(!nword.contains(rowData[j])))
			    			readtxt.add(rowData[j]);
			    	}
			    }
			    rd.close();
			  } 
			  else
				  System.out.print("\nwrong file: "+file.getName());
		  }
			  
		}
		System.out.print("\n"+t+" t1 "+t1);
		//get length of all doc in ham
		return readtxt;
	}
	
	//get extract term list
	public static ArrayList<String> termList(ArrayList<String> readtxt,ArrayList<String> termList){
		ArrayList<String> newList = new ArrayList<>();
		newList.addAll(termList);
		
		//avoid not useful sign
		List<String> nword = asList("-", ",", ".","/","@");
		for(int i = 0;i<readtxt.size();i++){
			String temp = readtxt.get(i);
			if(temp!=null&&(!nword.contains(temp))){
				if(!newList.contains(temp))
					newList.add(temp);
			}
		}
		return newList;
	}
	
	//get all data of file [i] in addr test
	public static ArrayList<String> readfile(String addr,int i) throws FileNotFoundException,IOException{
		//ArrayList<ArrayList<term>> rdfolder = new ArrayList<>();
		ArrayList<String> readfile = new ArrayList<>();
		
		//read all data of certain file in folder by File
		File folder = new File(addr);
		File[] listOfFiles = folder.listFiles();
		
		//get Nc for total num of files in ham or spam
		//int num = listOfFiles.length;

		//if want to read i th txt file
		//liseOfFiles[i+1]
		File file = listOfFiles[i];
		if (file.isFile() && file.getName().endsWith(".txt")) {
		  //tring content = FileUtils.readFileToString(file);
	      //read .txt file
		  BufferedReader rd = new BufferedReader(new FileReader(file.getAbsolutePath()));
		  String line;
		//avoid not useful sign
		List<String> nword = asList("-", ",", ".","/","@");
		  while((line=rd.readLine())!=null){
		    //how to split into each character
		    String[] rowData = line.split(" ");
		    //read element of rowData into readtxt
		    for(int j=0;j<rowData.length;j++){
	    		if(rowData[j]!=null&&(!nword.contains(rowData[j])))
	    			readfile.add(rowData[j]);
		    }
		  }
		  rd.close();
		} 
		
		//get length of all doc in ham
		return readfile;
	}
	/*
	public static ArrayList<ArrayList<Integer>> timeArray(String addr,String addrs,ArrayList<String> termList) throws FileNotFoundException, IOException{
		//get num of file in ham folder
		int numHam = numFile(addr);
		//get num of file in spam folder
		int numSpam = numFile(addrs);
		
		ArrayList<ArrayList<Integer>> timeXY=new ArrayList<>();
		//for ham file
		for(int i=0;i<numHam;i++){
			ArrayList<String> h=readfile(addr,i);
			//get occurring times in i of each term 
			ArrayList<Integer> timeHam = new ArrayList<>();
			timeHam=dealData.timeList(h,termList);
			timeHam.add(0);
			//System.out.print(timeHam.size()+"\n");
			timeXY.add(timeHam);
		}
		//for spam file
		for(int i=0;i<numSpam;i++){
			ArrayList<String> s=readfile(addrs,i);
			//get occurring times in i of each term 
			ArrayList<Integer> timeSpam = new ArrayList<>();
			timeSpam=dealData.timeList(s,termList);
			timeSpam.add(1);
			//System.out.print(timeSpam.size()+"\n");
			timeXY.add(timeSpam);
		}
		return timeXY;
	}*/
}
