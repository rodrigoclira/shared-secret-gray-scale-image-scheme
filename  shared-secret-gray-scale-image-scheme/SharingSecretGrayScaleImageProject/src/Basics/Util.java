package Basics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import PaperAlgorithms.SecretImageCreate;

public class Util {

	public static String MAINFOLDER = "Images"+File.separator;
	public static String COVERIMAGE1 = MAINFOLDER+"coverimage1.bmp";
	public static String COVERIMAGE2 = MAINFOLDER+"coverimage2.bmp";
	
	
	public static String caminho = "C:/Documents and Settings/Owner.YOUR-27405A18B6/My Documents/uast/minicurso_pi/imagens/";
	
	public static String formatoJPG = ".jpg";
	
	public static String formatoBMP = ".bmp";
	
	public static double[][] lerImagem(String caminho) throws Exception {
		File f = new File(caminho);
		BufferedImage image = ImageIO.read(f);
		double[][] retorno = new double[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				double[] tmp = new double[1];
				image.getRaster().getPixel(i, j, tmp);
				retorno[j][i] = tmp[0];
			}
		}
		return retorno;
	}
	
	public static double[][] lerImagem(BufferedImage image) throws Exception {
		double[][] retorno = new double[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				double[] tmp = new double[1];
				image.getRaster().getPixel(i, j, tmp);
				retorno[j][i] = tmp[0];
			}
		}
		return retorno;
	}
	
	public static double[][][] lerImagemColorida(String caminho) throws Exception {
		File f = new File(caminho);
		BufferedImage image = ImageIO.read(f);
		double[][][] retorno = new double[image.getHeight()][image.getWidth()][3];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				double[] tmp = new double[3];
				image.getRaster().getPixel(i, j, tmp);
				retorno[j][i][0] = tmp[0];
				retorno[j][i][1] = tmp[1];
				retorno[j][i][2] = tmp[2];
			}
		}
		return retorno;
	}
	
	public static void salvaImagem(String caminho,
			double[][] imagem) throws Exception {
		try {
			BufferedImage image = new BufferedImage(imagem[0].length, imagem.length, BufferedImage.TYPE_BYTE_GRAY);
			for (int y = 0; y < imagem.length; ++y) {
				for (int x = 0; x < imagem[0].length; ++x) {
					double[] tmp = new double[] { imagem[y][x] };
					image.getRaster().setPixel(x, y, tmp);
				}
			}
			ImageIO.write(image, "bmp", new File(caminho));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double[][] retornaImagemCinza(double[][][] imagem) throws Exception {
		try {
			BufferedImage image = new BufferedImage(imagem[0][0].length, imagem[0].length, BufferedImage.TYPE_BYTE_GRAY);
			for (int y = 0; y < imagem[0].length; ++y) {
				for (int x = 0; x < imagem[0][0].length; ++x) {
					double[] tmp = new double[] { imagem[0][y][x], imagem[1][y][x], imagem[2][y][x] };
					image.getRaster().setPixel(x, y, tmp);
				}
			}
			return lerImagem(image);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void salvaImagem(String caminho,
			double[][][] imagem) throws Exception {
		try {
			BufferedImage image = new BufferedImage(imagem[0].length, imagem.length, 5);
			for (int y = 0; y < imagem.length; ++y) {
				for (int x = 0; x < imagem[0].length; ++x) {
					double[] tmp = new double[] { imagem[y][x][0], imagem[y][x][1], imagem[y][x][2] };
					image.getRaster().setPixel(x, y, tmp);
				}
			}
			ImageIO.write(image, "bmp", new File(caminho));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isColored(double[] rgbVector){
		boolean hasColor = false;				
		for(int index = 0 ; index < rgbVector.length ; index++){
			if(rgbVector[index] < 255.00){
				hasColor = true;
				break;
			}						
		}
		return hasColor;
	}
	
	public static int contarBrancos(double [][][] imagem){
		int count=0;
		for(int i=0;i<imagem[0].length; i+=1){
			for(int j=0;j<imagem[0].length; j+=1 ){
				double [] rgb = imagem[i][j];
				
				if(!isColored(rgb)){
					count+=1;
				}
				
			}
		}
		return count; 
	}
	
	public static void main(String[] args) {
		float[] hsi = new float[3];
		Color.RGBtoHSB(100, 170, 125, hsi);
		DecimalFormat decimalformat = new DecimalFormat("0.000");
		for (int i = 0; i < hsi.length; i++) {
			System.out.println(decimalformat.format(hsi[i]*255));
		}
	}

	public static boolean MatrixValidation(double[][] grayImage,Step[][] steps,
			ArrayList<Integer> listOfR) {
		
		boolean ret = true;
		int index = 0;
		String K = "";
		
		for (int x = 0; x < steps.length; x++) {
			for (int y = 0; y < steps[x].length; y++) {
				int[] b1 = steps[x][y].getB1ArrayRepresentation();
				int[] b2 = steps[x][y].getB2ArrayRepresentation();
				int r2 = listOfR.get(index);
				index++;
				for(int i = 1; i < (b1.length - 1); i++ ){				
					int j = i < r2 ? i : (i+1);								
										
					K =  K + (b1[j] ^ b2[j]);				
				}											
				int grayValue = Integer.parseInt(K, 2);
				
				
				System.out.println("*****************************************************************");
				System.out.println("(posx,posy,random,grayimagePixel,binaryrrp)" +"(" + x + "," + y +","+ r2 + "," + grayImage[x][y] + "," + Arrays.toString(SecretImageCreate.toBinary((int)grayImage[x][y])));
				System.out.println("Valor de validação:" + K + "  " + grayValue);
				K = "";
				if(grayValue != grayImage[x][y])
					ret = false;
			}
		}
		
		return ret;
		
	}
	
}
