package kit.labcourse.transformations;

import java.util.regex.Pattern;

public class FGetNearestFrameIdEq {

public static String transform (String line) {
		
		String[] partsWithoutFuncName = null;
		//the while exists because some users may have used the same function in the query multiple times
		while (line.contains("fgetnearestframeideq") || line.contains("dbo.fgetnearestframeideq")) {
			if (line.contains("dbo.fgetnearestframeideq")) {
				//splits line into parts at the first occurrence of "dbo.fgetnearestframeideq" in the line
				partsWithoutFuncName = line.split("dbo.fgetnearestframeideq", 2);
			}
			else if (line.contains("fgetnearestframeideq")) {
				partsWithoutFuncName = line.split("fgetnearestframeideq", 2);
			}
			
			String[] partsWithoutFuncParenth = null;
			//splits the part of original line that had the function's parameters at the first closed parenthesis
			//the first element of the array contains the parameters of the function
			//the second element is everything after the occurrence of the function and it's parenthesis
			//Pattern.quote("(") is used because split works with regular expressions, and parentheses are part of the special characters
			if (partsWithoutFuncName.length > 1) {
				partsWithoutFuncParenth = partsWithoutFuncName[1].split(Pattern.quote(")"), 2);
			}

			String parameterString = "";
			//extracting the parameter - just removing the open parenthesis
			if (partsWithoutFuncParenth != null && partsWithoutFuncParenth[0].length() > 1) {
				parameterString = partsWithoutFuncParenth[0].substring(1);
			}
			
			String[] parameters = null;
			//now each parameter is its own separate variable
			//parameters[0] = @ra, parameters[1] = @dec, parameters[2] = @zoom
			if (parameterString != "") {
				parameters = parameterString.split(",");
			} 

			//constructing the new line
			if (parameters != null && parameters.length == 3 && partsWithoutFuncParenth != null && partsWithoutFuncParenth.length > 1) {
				line = partsWithoutFuncName[0] + "(select fieldid from frame where sqrt(square(ra - " + 
						parameters[0] + ") + square(dec - " + parameters[1] + ")) <= (select min(sqrt(square(ra - " + 
						parameters[0] + ") + square(dec - " + parameters[1] + "))) from frame) and zoom = " + parameters[2] +
						")" + partsWithoutFuncParenth[1];
			}
			else
				line = "";
			
		}
		return line;
	}
}
