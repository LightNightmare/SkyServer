package kit.labcourse.transformations;

import java.util.regex.Pattern;

public class FSpecLineNames {
	
	public static String transform (String line) {

		String[] partsWithoutFuncName = null;
		//the while exists because some users may have used the same function in the query multiple times
		while (line.contains("fspeclinenames") || line.contains("dbo.fspeclinenames")) {
			if (line.contains("dbo.fspeclinenames")) {
				//splits line into parts at the first occurrence of "dbo.fspeclinenames" in the line
				partsWithoutFuncName = line.split("dbo.fspeclinenames", 2);
			}
			else if (line.contains("fspeclinenames")) {
				partsWithoutFuncName = line.split("fspeclinenames", 2);
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
				line = partsWithoutFuncName[0] + "(select value from speclinenames where name = "
						+ parameter + ")" + partsWithoutFuncParenth[1];
			}
			else
				line = "";

		}
		return line;
	}
}
