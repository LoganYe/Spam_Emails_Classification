import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

public class LR{
	
	//initial w=0
	//manual value eta and lamda
	//for each X in termList, get wi
	
	public static double lgRg(String addr,String addrs,String addrt,String addrts) throws FileNotFoundException, IOException{
		//get all data in ham folder
		ArrayList<String> fileHam = new ArrayList<>();
		fileHam = readFolder.readtxt(addr);
		
		//get all data in spam folder
		ArrayList<String> fileSpam = new ArrayList<>();
		fileSpam = readFolder.readtxt(addrs);
		
		//get num of file in ham folder
		int numHam = readFolder.numFile(addr);
		//System.out.print("num of file in ham: "+ numHam);
		
		//get num of file in spam folder
		int numSpam = readFolder.numFile(addrs);
		//System.out.print("num of file in Spam: "+ numSpam);
		
		ArrayList<String> termList = new ArrayList<>();

		//get termList in ham readtxt
		ArrayList<String> termHam = new ArrayList<>();
		termHam = readFolder.termList(fileHam,termHam);
		
		//get total num of all term in ham and spam
		termList = readFolder.termList(fileSpam,termHam);
		//System.out.print("num term of in Ham: "+termHam.size());
		//System.out.print("num term of in Spam: "+termSpam.size());
		System.out.print("num term of in Ham and Spam: "+termList.size()+"\n");
		
		//read .txt file into ArrayList
		int sizeH=0,sizeS=0;
		ArrayList<ArrayList<String>> fileList=new ArrayList<>();
		for(int f=0;f<numHam;f++){
			ArrayList<String> h=readFolder.readfile(addr,f);
			if(h.size()!=0)
				{
					fileList.add(h);
					sizeH++;
				}
		}
		for(int f=0;f<numSpam;f++){
			ArrayList<String> s=readFolder.readfile(addrs,f);
			if(s.size()!=0)
				{
					fileList.add(s);
					sizeS++;
				}
		}
		System.out.print(numHam+" vs "+sizeH+"\n");
		System.out.print(numSpam+" vs "+sizeS+"\n");
		
		//for each Xi in termList, find wi
		//for spam and ham list, wi parameter is the same for Xi,initial value is 0
		double[] wList=new double[termList.size()+1];
		//get error=Y-Pl(Y|X,W) first
		double[] YP=new double[fileList.size()];

		//get X[k][l]
		int[][] x=new int[termList.size()+1][numHam+numSpam];
		for(int i=0;i<numHam+numSpam;i++){
			x[0][i] = 1;
		}
		//double[] sum=new double[termList.size()+1];
		for(int k=1;k<termList.size()+1;k++){
			//for ham and spam file
			for(int i=0;i<numHam+numSpam;i++){
				ArrayList<String> h=fileList.get(i);
				//if(h.size()==0)
				//	System.out.print("wrong file\n");
				//get occurring times in i file of each term 
				x[k][i]=dealData.timeXk(h, termList.get(k-1));

			}
			//if(x[k][400]!=0)
			//	System.out.print(x[k][400]+" ~ "+(k-1)+"\n");

		}
		/****test xkl
		for(int k=0;k<termList.size();k++){
			if(x[k][400]!=0)
			System.out.print(x[k][400]+"\n");
		}*/
		ArrayList<Integer> timeH = new ArrayList<>();
		ArrayList<Integer> timeS = new ArrayList<>();
		
		for(int i=0;i<numHam;i++){
			ArrayList<String> h=fileList.get(i);
			//get occurring times in i of each term 
			timeH=dealData.timeList(h,termList);
		}
		for(int i=numHam;i<fileList.size();i++){
			ArrayList<String> s=fileList.get(i);
			//get occurring times in i of each term
			timeS=dealData.timeList(s,termList);
		}
		
		for(int r=0;r<=200;r++){
			//update []YP
			YP=YP(fileList,termList,wList,numHam,timeH,timeS);
			System.out.print(YP[400]+"~ "+r+"\n");

			//calculate weight for each term in termlist
			for(int k=0;k<termList.size()+1;k++){
				//******************eta and lamda
				double eta=0.001;
				double lamda=5.0;
				double s=0;
				for(int i=0;i<fileList.size();i++){
					s+=x[k][i]*YP[i];
				}
				wList[k]=wList[k]+eta*s-eta*lamda*wList[k];
			}
		}
		double accLR =0.0;
		
		//get num of file in test ham folder
		int tnoHam = readFolder.numFile(addrt);
		//get num of file in test spam folder
		int tnoSpam = readFolder.numFile(addrts);
		//read .txt file into ArrayList
		int sizH=0,sizS=0;
		ArrayList<ArrayList<String>> testList=new ArrayList<>();
		for(int f=0;f<tnoHam;f++){
			ArrayList<String> h=readFolder.readfile(addrt,f);
			if(h.size()!=0)
				{
					testList.add(h);
					sizH++;
				}
		}
		for(int f=0;f<tnoSpam;f++){
			ArrayList<String> s=readFolder.readfile(addrts,f);
			if(s.size()!=0)
				{
					testList.add(s);
					sizS++;
				}
		}
		System.out.print(sizH+" test size H  + test size S "+sizS);
		System.out.print("\n"+tnoHam+" test size Ham  + test size Spam "+tnoSpam);
		System.out.print("\n test size "+testList.size());

		//for test ham file
		//get x[k] for each file
		int[] testx=new int[termList.size()+1];
		testx[0]=1;

		double s=0;
		double pt=0;
		double pf=0;
		int t=0,f=0;
		//for test file
		for(int i=0;i<tnoHam + tnoSpam-1;i++){
			ArrayList<String> h=testList.get(i);
			s+=testx[0]*wList[0];
			for(int k=1;k<termList.size()+1;k++){
				testx[k]=dealData.timeXk(h, termList.get(k-1));
				s+=testx[k]*1.0*wList[k];
			}
			if(s<=0)
				t++;
			else
				f++;
		}
		System.out.print("\nt + f = "+t+" + "+f);
		accLR= t*1.0/(t+f);
		//System.out.print("\nSuccessful!!!");
		System.out.print("\naccuracy of LR is "+accLR);
		return accLR;
	}
	//for ham, get Y-Pl(Y=0|X,W) first	
	//for spam, get Y-Pl(Y=1|X,W) first
	public static double[] YP(ArrayList<ArrayList<String>> fileList,ArrayList<String> termList,double[] wList,int numHam,ArrayList<Integer> timeH, ArrayList<Integer> timeS){
		//get Y-Pl(Y=1|X,W) first
		double[] YP=new double[fileList.size()];
		//for ham file
		for(int i=0;i<numHam;i++){
			double wx=wList[0];	//wx=sum of wj*xj
			for(int j=0;j<timeH.size();j++){
				wx+=timeH.get(j)*wList[j+1];
			}
			double e=Math.exp(wx);
			double m=1+e;

			//ham,Y=0
			double Ph=0.0-e/m;
			YP[i]=Ph;
		}
		//for spam file
		for(int i=numHam;i<fileList.size();i++){
			double wx=wList[0];	//wx=sum of wj*xj
			for(int j=0;j<timeS.size();j++){
				wx+=timeS.get(j)*wList[j+1];
			}
			double e=Math.exp(wx);
			//P(Y=0)
			double m=1+e;
			double Ps=e/m;
			YP[i]=1-Ps;
		}
		return YP;
	}
	public static double calP(double[] wList,ArrayList<Integer> timeList){
		double P=0;
		for(int i=0;i<wList.length;i++){
			P+=wList[i]*timeList.get(i);
		}
		return P;
	}
}
