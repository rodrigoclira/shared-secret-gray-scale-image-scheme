package PaperAlgorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import Basics.IConstants;
import Basics.Step;
import Basics.Util;


public class SecretImageCreate {

	public String CoverePath1 = "";
	public String CoverePath2 = "";
	
	public SecretImageCreate(String coverPath1, String coverPath2){
		CoverePath1 = coverPath1;
		CoverePath2 = coverPath2;
		
	}
	
	
	public List<Integer> execute (double [][] grayImage, double [][][] image1, double [][][] image2)
			throws Exception{

		
		double [][][] sharingSecret1 = new double[grayImage.length*3][grayImage[0].length*3][3];
		double [][][] sharingSecret2 = new double[grayImage.length*3][grayImage[0].length*3][3];

		Step [][] steps = new Step[grayImage.length][grayImage[0].length];

		int r;
		int k[];
		double [] color1;
		double [] color2;
		ArrayList<Integer> listOfR = new ArrayList<Integer>();

		for(int i=0;i<grayImage.length;i+=1){
			for(int j=0;j<grayImage[0].length;j+=1){

				r = randomR();				
				k = toBinary((int)grayImage[i][j]);
				color1 = image1[i][j];
				color2 = image2[i][j];
				listOfR.add(r);

				steps[i][j] = createBlocks(k, color1, color2, r);

			}
		}
								
		int centerX=1;
		int centerY=1;
		int raioFiltro=1;

		for(int i=0;i<steps.length;i+=1){
			for(int j=0;j<steps[0].length;j+=1){

				//mascara;
				for(int iMascara=-raioFiltro; iMascara<=raioFiltro; iMascara+=1){
					for(int jMascara=-raioFiltro; jMascara<=raioFiltro; jMascara+=1){

						sharingSecret1[centerX+iMascara][centerY+jMascara] = 
							(steps[i][j].getB1()[1+iMascara][1+jMascara] == IConstants.ONE) ? steps[i][j].getK1() : new double[] {255.0,255.0,255.0};

							sharingSecret2[centerX+iMascara][centerY+jMascara] =	 
								(steps[i][j].getB2()[1+iMascara][1+jMascara] == IConstants.ONE) ? steps[i][j].getK2() : new double[] {255.0,255.0,255.0};

					}

				}

				centerY+=3;
			}
			centerY=1;
			centerX+=3;
		}

		Util.salvaImagem(CoverePath1, sharingSecret1);
		Util.salvaImagem(CoverePath2, sharingSecret2);

		return listOfR;

	}


	public Step createBlocks(int [] k, double [] color1, double [] color2, int r){
		Step step = new Step(color1,color2);

		int S[][] = new int[3][10];

		//Step1
		//Set all elements in S to be null.
		for(int i=0; i<S[0].length;i+=1)
			S[0][i]=IConstants.DC;

		S[1][0]=IConstants.DC;
		S[2][0]=IConstants.DC;

		for(int i=1;i<S.length;i+=1)
			for(int j=1;j<S[1].length;j+=1)
				S[i][j]=IConstants.NULL;



		//Step 2
		int noOfOne = 0;
		int j = 0;
		for(int i=1;i<=8;i+=1){

			if(k[i]==1 && i<r){

				noOfOne += 1;

				S[1][i] = (noOfOne%2==1) ? 1 : 0;

			}else if(k[i]==1 && i>=r){

				noOfOne += 1;
				j = i + 1;

				S[1][j] = (noOfOne%2==1) ? 1 : 0;
			}

		}

		//Step 3
		if(noOfOne%2==1)
			S[1][r]=0;

		//Step 4
		Vector<Integer> nulles = new Vector<Integer>();
		int countNull=0;

		for(int i=0;i<S[1].length;i+=1){
			if(S[1][i]==IConstants.NULL)
				countNull+=1;
		}

		for(int i=0;i<countNull;i+=1){

			if (i<countNull/2){

				nulles.add(IConstants.ZERO);

			}else{
				nulles.add(IConstants.ONE);
			}
		}

		Collections.shuffle(nulles);

		int currentPosition=0;
		for(int i=1;i<S[1].length;i+=1){

			if(S[1][i] == IConstants.NULL){
				S[1][i] = nulles.get(currentPosition);
				currentPosition+=1;
			}
		}

		//Step 5
		j=0;
		for(int i=1;i<=9;i+=1){

			if(i!=r){

				j+=1;
				S[2][i]= S[1][i] ^ k[j];

			}else{

				if(noOfOne%2==1){

					S[2][i]=1;

				}else{

					S[2][i] = S[1][i] ^ 0;

				}
			}
		}


		//Step 6
		int [][] B1 = {
				{S[1][1],S[1][2],S[1][3]},
				{S[1][4],S[1][5],S[1][6]},
				{S[1][7],S[1][8],S[1][9]}
		};

		int [][] B2 = {
				{S[2][1],S[2][2],S[2][3]},
				{S[2][4],S[2][5],S[2][6]},
				{S[2][7],S[2][8],S[2][9]}
		};

		step.setB1(B1);
		step.setB2(B2);

		return step;
	}


	public static int [] toBinary(int number){
		int [] binary = new int[9];

		String binaryString = Integer.toBinaryString(number);
		for(int i=0; i<binaryString.length();i+=1){

			binary[(binary.length-1)-i] = Character.getNumericValue(binaryString.charAt((binaryString.length()-1)-i));
		}
		
		return binary;
	}


	public int randomR(){
		int r;

		//Min + (int)(Math.random() * ((Max - Min) + 1))
		r=1 + (int) (Math.random() * 8 - 1); 

		return r;
	}
}
