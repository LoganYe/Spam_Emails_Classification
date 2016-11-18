import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class mainClass {
	
    public static void main(String[] args) throws FileNotFoundException, IOException{
    	/*
    	//get ham folder addr input
    	System.out.print("Please enter ham folder address: ");
    	Scanner sc = new Scanner(System.in);
    	String addr = sc.nextLine();
    	
    	
    	
    	////get spam folder addr input
    	System.out.print("Please enter spam folder address: ");
    	Scanner scc = new Scanner(System.in);
    	String addrs = scc.nextLine();
    	
    	
    	*/
    	
	    String addr = "/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/ham";
    	String addrs = "/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/spam";
    	
    	//get all data in one test file
    	String addrt = "/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/ham";
    	String addrts = "/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/spam";

    	//get accuracy of naive bayes
    	//double NBacc = naiveBayes.nvBayes(addr,addrs,addrt,addrts);
    	//System.out.print("\naccuracy of NB is "+NBacc);
    	
    	//get accuracy of logistic regression
    	double LRacc = LR.lgRg(addr,addrs,addrt,addrts);
    	System.out.print("\naccuracy of LR is "+LRacc);
    	
    	
    	
    	


    	/******************test
    	System.out.print("Data of all files in Ham: ");
    	for(int i=0;i<fileHam.size();i++){
    		System.out.print(fileHam.get(i));
    	}
    	System.out.print("Data of all files in Spam: ");
    	for(int i=0;i<fileHam.size();i++){
    		System.out.print(fileSpam.get(i));
    	}
    	*/

    }
}
