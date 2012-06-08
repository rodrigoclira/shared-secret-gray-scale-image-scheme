package PaperAlgorithms;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;



import Basics.IConstants;
import Basics.Step;
import Basics.Util;

public class SecretImageRecover {

	private boolean isColored(double[] rgbVector){
		boolean hasColor = false;				
		for(int index = 0 ; index < rgbVector.length ; index++){
			if(rgbVector[index] != 255.00){
				hasColor = true;
				break;
			}						
		}
		return hasColor;
	}

	private Step[] getBlocksArray(double[][][] imageCover1, double[][][] imageCover2 ){

		int stepArraySize = imageCover1.length/3 * imageCover1[0].length/3;
		int stepArrayCount = 0;
		Step[] arrayOfSteps = new Step[stepArraySize];
				
		int filterRadius = 1;
		int imageXCentral = 1;
		int imageYCentral = 1;
		
		int [][] block1;
		int [][] block2;
		
		while(imageXCentral < imageCover1.length){
			while(imageYCentral < imageCover1[0].length){
				block1 = new int[3][3];
				block2 = new int[3][3];
				
				for(int s = -filterRadius ; s <=filterRadius  ; s++ ){
					for(int t = -filterRadius ; t <= filterRadius ; t++){
						double[] rgbColor = null;
						rgbColor = imageCover1[imageXCentral+s][imageYCentral+t];
						block1[s+filterRadius][t+filterRadius] = (this.isColored(rgbColor) ? IConstants.ONE : IConstants.ZERO);

						rgbColor = imageCover2[imageXCentral+s][imageYCentral+t];
						block2[s+filterRadius][t+filterRadius] = (this.isColored(rgbColor) ? IConstants.ONE : IConstants.ZERO);
																	
					}
				}
				
				arrayOfSteps[stepArrayCount] = new Step(block1, block2);
				stepArrayCount++;
				
				imageYCentral += 3;
			}

			imageYCentral = 1;
			imageXCentral += 3;

		}
		return arrayOfSteps;
	}

	public double[][] execute(double[][][] camouflageImage1, double[][][] camouflageImage2, ArrayList<Integer> randomCreated){

		int index = 0;
		int heigth = camouflageImage1.length / 3;
		int width = camouflageImage1[0].length / 3;

		double[] arrayRepresentationHiddenImage = new double[heigth * width];
		double[][] hiddenImage = new double[heigth][width];

		Step[] steps = this.getBlocksArray(camouflageImage1,camouflageImage2);

		//d�vida? qual � o mecanismo correto no calculo final do k?				
		for (int r = 0; r < steps.length ; r++) {

			int[] S1 = steps[r].getB1ArrayRepresentation();
			int[] S2 = steps[r].getB2ArrayRepresentation();		
			
			String K = ""; 
			
			 
			//Aten��o: Entender como occorre pois pode ocorrer ArrayOutOfBounds 
			int r2 = randomCreated.get(index);
			index++;
			for(int i = 1; i < (S1.length - 1); i++ ){				
				int j = i < r2 ? i : (i+1);								
				
				
				K =  K + (S1[j] ^ S2[j]);				
			}								
											
			int grayLevel = Integer.parseInt(K, 2);						
				
			arrayRepresentationHiddenImage[r] = (double) grayLevel; 

		}
		
		int x = -1;		 		
		for(int y = 0 ; y< arrayRepresentationHiddenImage.length ; y++){
			int yOffset = y % width;			
			if( yOffset == 0)
				x++;

			hiddenImage[x][yOffset] = arrayRepresentationHiddenImage[y];			
		}
				
		return hiddenImage;
	}

}
