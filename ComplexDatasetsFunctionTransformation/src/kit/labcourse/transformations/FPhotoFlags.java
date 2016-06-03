package kit.labcourse.transformations;

import java.util.regex.Pattern;

public class FPhotoFlags {

	public static String transform (String line) {
		
		String[] partsWithoutFuncName = null;
		//the while exists because some users may have used the same function in the query multiple times
		while (line.contains("fphotoflags") || line.contains("dbo.fphotoflags")) {
			if (line.contains("dbo.fphotoflags")) {
				//splits line into parts at the first occurrence of "dbo.fphotoflags" in the line
				partsWithoutFuncName = line.split("dbo.fphotoflags", 2);
			}
			else if (line.contains("fphotoflags")) {
				partsWithoutFuncName = line.split("fphotoflags", 2);
			}
				
			//splits the part of original line that had the function's parameters at the first closed parenthesis
			//the first element of the array contains the parameters of the function
			//the second element is everything after the occurrence of the function and it's parenthesis
			//Pattern.quote("(") is used because split works with regular expressions, and parentheses are part of the special characters
			String[] partsWithoutFuncParenth = partsWithoutFuncName[1].split(Pattern.quote(")"), 2);
			
			//extracting the parameter - just removing the open parenthesis
			String parameter = partsWithoutFuncParenth[0].substring(1);
			
			//constructing the new line
			line = partsWithoutFuncName[0] + "(select value from photoflags where name = "
					+ parameter + ")" + partsWithoutFuncParenth[1];

		}
		return line;
	}
}
