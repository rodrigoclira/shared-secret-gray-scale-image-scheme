package AlgorithmRun;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import Basics.Util;
import PaperAlgorithms.SecretImageCreate;

public class HideImage {

	
	public static void main(String[] args) {
		
		

		String image1Path = Util.MAINFOLDER + "coverImageShell.bmp";
		String image2Path = Util.MAINFOLDER + "coverImageF16.bmp";
		String grayImagePath = Util.MAINFOLDER + "secretGrayScaleImage.bmp";
		
		String CoveredResultImagePath1 = Util.MAINFOLDER + "churchCovered1.bmp";
		String CoveredResultImagePath2 = Util.MAINFOLDER + "churchCovered2.bmp";

		try {

			SecretImageCreate sic = new SecretImageCreate(CoveredResultImagePath1, CoveredResultImagePath2);
			
			double [][][] image1 = Util.lerImagemColorida(image1Path);
			double [][][] image2 = Util.lerImagemColorida(image2Path);
			double [][] grayImage = Util.lerImagem(grayImagePath);

			List<Integer> l = sic.execute(grayImage, image1, image2);

			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream("listOfR.ser"));
			out.writeObject(l);
			out.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}