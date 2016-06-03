package kit.labcourse.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import kit.labcourse.transformations.FGetNearbyObjEq;
import kit.labcourse.transformations.FGetObjFromRect;
import kit.labcourse.transformations.FPhotoFlags;
import kit.labcourse.transformations.FPhotoType;
import kit.labcourse.transformations.FPrimTarget;
import kit.labcourse.transformations.FSpecClass;
import kit.labcourse.transformations.FSpecLineNames;

public class MainClass {

	public static void main(String[] args) {
		try {
			BufferedReader fileWithFunc = new BufferedReader(new FileReader(new File(".\\testSample.csv")));
			BufferedWriter transformedFile = new BufferedWriter (new FileWriter (new File(".\\transformed.csv"), true));
			
			String line = "";
			String oldLine = "";
			
			while((line = fileWithFunc.readLine()) != null) {
				oldLine = line;
				if (line.contains("fphotoflags"))
					line = FPhotoFlags.transform(line);
				if (line.contains("fgetnearbyobjeq"))
					line = FGetNearbyObjEq.transform(line);
				if (line.contains("fgetobjfromrect"))
					line = FGetObjFromRect.transform(line);
				if (line.contains("fphototype"))
					line = FPhotoType.transform(line);
				if (line.contains("fspecclass"))
					line = FSpecClass.transform(line);
				if (line.contains("fprimtarget"))
					line = FPrimTarget.transform(line);
				if (line.contains("fspeclinenames"))
					line = FSpecLineNames.transform(line);
				if (!line.isEmpty())
					transformedFile.write(line + "\n");
			}
			
			fileWithFunc.close();
			transformedFile.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Problem with opening the first task!");
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
