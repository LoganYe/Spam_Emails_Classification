import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class naiveBayes {
	
	
	//predict Ham or Spam for each file
	//for file in ham, if pHam>pSpam,T++,else,F++
	public static double nvBayes(String addr,String addrs,String addrt,String addrts) throws FileNotFoundException, IOException{
		//get all data in ham folder
		ArrayList<String> fileHam = new ArrayList<>();
		fileHam = readFolder.readtxt(addr);
		
		//get all data in spam folder
		ArrayList<String> fileSpam = new ArrayList<>();
		fileSpam = readFolder.readtxt(addrs);
		
		//get num of file in ham folder
		int numHam = readFolder.numFile(addr);
		System.out.print("num of file in ham: "+ numHam);
		
		//get num of file in spam folder
		int numSpam = readFolder.numFile(addrs);
		System.out.print("num of file in Spam: "+ numSpam);
		
		ArrayList<String> termList = new ArrayList<>();

		//get termList in ham readtxt
		ArrayList<String> termHam = new ArrayList<>();
		termHam = readFolder.termList(fileHam,termHam);
		
		//get termList in spam readtxt
		ArrayList<String> termSpam = new ArrayList<>();
		termSpam = readFolder.termList(fileSpam,termSpam);
		
		//get total num of all term in ham and spam
		termList = readFolder.termList(fileSpam,termHam);
		System.out.print("num term of in Ham: "+termHam.size());
		System.out.print("num term of in Spam: "+termSpam.size());
		System.out.print("num term of in Ham and Spam: "+termList.size()+"\n");
		
		
		//get occurring times in ham of each term 
		ArrayList<Integer> timeHam = new ArrayList<>();
		timeHam=dealData.timeList(fileHam,termHam);
		
		
		//get occurring times in spam of each term 
		ArrayList<Integer> timeSpam = new ArrayList<>();
		timeSpam=dealData.timeList(fileSpam,termSpam);
		
		//test data
				//System.out.print("\nterm list of Spam in traning:");
				//for(int i =0;i<20;i++){
				//	System.out.print(termSpam.get(i)+" and occurring time is  "+timeSpam.get(i)+"\n");
				//}
		
		//get length_Ham
		int lengthHam = fileHam.size();
		
		//get length_Spam
		int lengthSpam = fileSpam.size();
		
		System.out.print("\nlength of Ham: "+lengthHam);
		System.out.print("\nlength of Spam: "+lengthSpam);

		
		//get pHam and pSpam
		
		double pHam= numHam+numSpam;
		pHam= numHam/pHam;
		//to avoid -infinity value log(double 0.7333333333333)
		//pHam = Math.floor(pHam*1000)/1000;

    	double pSpam=numHam+numSpam;
    	pSpam= numSpam/pSpam;
		//pSpam = Math.floor(pSpam*1000)/1000;

    	System.out.print("\n"+pHam+"\n");
		System.out.print(pSpam+"\n");
		
		pHam = Math.log(pHam);
    	pSpam = Math.log(pSpam);

    	//pHam = 0;
    	//pSpam = 0;
		System.out.print(pHam+"\n"+Math.log(0.733));
		System.out.print(pSpam+"\n"+Math.log(0.266));

		int t=0,f=0;

		//get all data of file in test ham folder
		//read all data of certain file in folder by File
		/*******************************/
		File folder = new File(addrt);
		File[] listOfFiles = folder.listFiles();
		int num = listOfFiles.length;
		//int t=0,f=0;
		for (int k = 0; k < num; k++) {
			File file = listOfFiles[k];
			double Ham=pHam;
			double Spam=pSpam;
			//for each txt file in test Ham
			if (file.isFile() && file.getName().endsWith(".txt")) {
				ArrayList<String> testH = new ArrayList<>();
		    	testH = readFolder.readfile(addrt,k);
		    	for(int i=0;i<testH.size();i++){
		    		//System.out.print(testHam1.get(i));
		    		String word = testH.get(i);
		    		if(word!=null){
		    			//get Pt in Ham
			    		//get occurring time of t in class c

			    		double time = dealData.time(termHam,timeHam,word);
			    		double logPtc = dealData.logPtc(time,lengthHam,termList.size());
			    		
			    		//store logPtc in ArrayList,get sum and plus logPc
			    		ArrayList<Double> pcList = new ArrayList<>();
			    		pcList.add(logPtc);
			    		
			    		//test
			    		//System.out.print(logPtc+"\n");
			    		Ham +=logPtc;
			    		
			    		//get Pt in Spam
			    		double timeS = dealData.time(termSpam,timeSpam,word);
			    		double logPtS = dealData.logPtc(timeS,lengthSpam,termList.size());
			    		
			    		//store logPt in ArrayList,get sum and plus logPs
			    		ArrayList<Double> psList = new ArrayList<>();
			    		psList.add(logPtS);
			    		
			    		//test
			    		//System.out.print(logPtS+"\n");
			    		
			    		Spam += logPtS;
		    		}
		    		
		    	}
		    	//System.out.print("pHam = "+pHam+"\n");
		    	//System.out.print("pSpam = "+pSpam);
		    	if(Ham>=Spam)
		    		t++;
		    	else
		    		f++;
		    	
		  }
			else
				  System.out.print("wrong file: "+file.getName());
		}
		System.out.print("\nafter ham,t="+t+", f="+f+" , test ham num= "+num);
		
		//get all data of file in test spam folder
		//read all data of certain file in folder by File
		File folders = new File(addrts);
		File[] listOfFiless = folders.listFiles();
		int nums = listOfFiless.length;
		//double t=0,f=0;
		for (int k = 0; k < nums; k++) {
			File file = listOfFiless[k];
			double Ham=pHam;
			double Spam=pSpam;
			//for each txt file in test Spam
			if (file.isFile() && file.getName().endsWith(".txt")) {
				ArrayList<String> testS = new ArrayList<>();
		    	testS = readFolder.readfile(addrts,k);
		    	for(int i=0;i<testS.size();i++){
		    		//System.out.print(testHam1.get(i));
		    		String word = testS.get(i);
		    		if(word!=null){
		    			//get Pt in Ham
			    		//get occurring time of t in class c

			    		double time = dealData.time(termHam,timeHam,word);
			    		double logPtc = dealData.logPtc(time,lengthHam,termList.size());
			    		
			    		//store logPtc in ArrayList,get sum and plus logPc
			    		ArrayList<Double> pcList = new ArrayList<>();
			    		pcList.add(logPtc);
			    		
			    		Ham +=logPtc;
			    		
			    		//get Pt in Spam
			    		double timeS = dealData.time(termSpam,timeSpam,word);
			    		double logPtS = dealData.logPtc(timeS,lengthSpam,termList.size());
			    		
			    		//store logPt in ArrayList,get sum and plus logPs
			    		ArrayList<Double> psList = new ArrayList<>();
			    		psList.add(logPtS);
			    		
			    		Spam += logPtS;
			    		
			    	}
		    		}
		    		
		    	//System.out.print("pHam = "+Ham+"\n");
		    	//System.out.print("pSpam = "+Spam);
		    	if(Ham<=Spam)
		    		t++;
		    	else
		    		f++;
		    	
		  }
			else
				  System.out.print("wrong file: "+file.getName());
		}
		System.out.print("\nafter spam,t="+t+", f="+f+" , test spam num= "+nums);

		double accNB = t*1.0/(t+f);
		return accNB;
				  	
				  	
				
    	
	}
}
