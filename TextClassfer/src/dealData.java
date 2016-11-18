import java.util.ArrayList;

public class dealData {
	//get occurring times in ham or spam of each term 
	public static ArrayList<Integer> timeList(ArrayList<String> fileList,ArrayList<String> termList){
		ArrayList<Integer> timeList = new ArrayList<>();
		//for each term in termList, search through fileList, count occurring times for it as element in timeList
		for(int i=0;i<termList.size();i++){
			String term = termList.get(i);
			int time=0;
			for(int j=0;j<fileList.size();j++){
				String word = fileList.get(j);
				if(term.equals(word))
					time++;
			}
			//System.out.print("time for "+i+" = "+time+"\n");
			timeList.add(time);
		}
		return timeList;
	}
	
	public static int timeXk(ArrayList<String> fileList,String x){
		int time=0;
		for(int j=0;j<fileList.size();j++){
			String word = fileList.get(j);
			if(x.equals(word))
				time++;
		}
		return time;
	}
	//for each word in a test file
	//get occurring time of t in class c
	public static double time(ArrayList<String> termList,ArrayList<Integer> timeList,String word){
		//search word in term list, get index back
		int index=-1;
		double time;
		for(int i = 0;i<termList.size();i++){
			String temp=termList.get(i);
			if(word.equals(temp)){
				index=i;
				break;
			}
		}
		if(index>=0){
			Integer tmpt=timeList.get(index);
			time=tmpt;
		}
		else
			time=0;
		return time;
	}
	//calculate P(t|c), get log return to avoid floating underflow
	public static double logPtc(double timet,int lengthc,int numTerm){
		double Ptc=(timet+1)*1.0/(lengthc+numTerm);
		double logPtc=Math.log(Ptc);
		return logPtc;
	}
	
	//get occurring time for l th training example
	public static ArrayList<Integer> timeLR(ArrayList<String> fileList,ArrayList<String> termList){
		ArrayList<Integer> timeList = new ArrayList<>();
		//for each term in termList, search through fileList, count occurring times for it as element in timeList
		for(int i=0;i<termList.size();i++){
			String term = termList.get(i);
			int time=0;
			for(int j=0;j<fileList.size();j++){
				String word = fileList.get(j);
				if(term.equals(word))
					time++;
			}
			//System.out.print("time for "+i+" = "+time+"\n");
			timeList.add(time);
		}
		return timeList;
	}
}
