package kit.labcourse.transformations;

import java.util.regex.Pattern;

public class FPhotoType {

	public static String transform (String line) {
		
		String[] partsWithoutFuncName = null;
		//the while exists because some users may have used the same function in the query multiple times
		while (line.contains("fphototype") || line.contains("dbo.fphototype")) {
			if (line.contains("dbo.fphototype")) {
				//splits line into parts at the first occurrence of "dbo.fphototype" in the line
				partsWithoutFuncName = line.split("dbo.fphototype", 2);
			}
			else if (line.contains("fphototype")) {
				partsWithoutFuncName = line.split("fphototype", 2);
			}
				
			String[] partsWithoutFuncParenth = null;
			//splits the part of original line that had the function's parameters at the first closed parenthesis
			//the first element of the array contains the parameters of the function
			//the second element is everything after the occurrence of the function and it's parenthesis
			//Pattern.quote("(") is used because split works with regular expressions, and parentheses are part of the special characters
			if (partsWithoutFuncName.length > 1) {
				partsWithoutFuncParenth = partsWithoutFuncName[1].split(Pattern.quote(")"), 2);
			}

			String parameter = "";
			//extracting the parameter - just removing the open parenthesis
			if (partsWithoutFuncParenth != null && partsWithoutFuncParenth[0].length() > 1) {
				parameter = partsWithoutFuncParenth[0].substring(1);
			}

			//constructing the new line
			if (parameter != "" && partsWithoutFuncParenth != null && partsWithoutFuncParenth.length > 1) {
				line = partsWithoutFuncName[0] + "(select value from phototype where name = "
						+ parameter + ")" + partsWithoutFuncParenth[1];
			}
			else
				line = "";

		}
		return line;
	}
	
}
