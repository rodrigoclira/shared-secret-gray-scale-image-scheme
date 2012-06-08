package Basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Step {

	public int [][] B1;
	public int [][] B2;
	public double [] K1;
	public double [] K2;
	
	public Step(int [][] B1, int [][] B2, double [] K1, double [] K2){
		this.B1 = B1;
		this.B2 = B2;
		this.K1 = K1;
		this.K2 = K2;
	}
	
	public Step(int [][] B1, int [][] B2){
		this(B1,B2,null,null);
	}
	
	public Step(double [] K1, double [] K2){
		this(null,null, K1, K2);
	}

	public int[][] getB1() {
		return B1;
	}

	public void setB1(int[][] b1) {
		B1 = b1;
	}

	public int[][] getB2() {
		return B2;
	}

	public void setB2(int[][] b2) {
		B2 = b2;
	}

	public double[] getK1() {
		return K1;
	}

	public void setK1(double[] k1) {
		K1 = k1;
	}

	public double[] getK2() {
		return K2;
	}

	public void setK2(double[] k2) {
		K2 = k2;
	}

	public int[] getB2ArrayRepresentation() {
		return this.getBlockArrayRepresentation(this.B2);
		
	}
	
	public int[] getB1ArrayRepresentation() {
		return this.getBlockArrayRepresentation(this.B1);
	}
	
	private int[] getBlockArrayRepresentation(int[][] block ){
		
		int[] ret = new int[(block.length * block[0].length) + 1];
		ret[0] = IConstants.DC;
		ArrayList<Integer> auxList = new ArrayList<Integer>();
		auxList.clear();
		auxList.add(IConstants.DC);		
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {			
				auxList.add(block[i][j]);
			}
		}		
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = auxList.get(i);
		}
		
		
		return  ret;
	}

	
	
	
}
