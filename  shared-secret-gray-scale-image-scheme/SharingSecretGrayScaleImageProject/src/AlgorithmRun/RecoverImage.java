package AlgorithmRun;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import Basics.Util;
import PaperAlgorithms.SecretImageRecover;

public class RecoverImage {

	public static void main(String[] args) {
		
		
		SecretImageRecover sir = new SecretImageRecover();
		
		String coverImage1Path = Util.MAINFOLDER + "churchCovered1.bmp";
		String coverImage2Path = Util.MAINFOLDER + "churchCovered2.bmp";
		String recoveredImagePath = Util.MAINFOLDER + "recoveredSecretImage.bmp";

		try {
					
					
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("listOfR.ser"));
			ArrayList<Integer> randoms = (ArrayList<Integer>) in.readObject();
			in.close();
		
			double [][][] image1 = Util.lerImagemColorida(coverImage1Path);
			double [][][] image2 = Util.lerImagemColorida(coverImage2Path);
						
			double [][] grayImage = sir.execute(image1, image2, randoms);
			Util.salvaImagem(recoveredImagePath, grayImage);
						

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
