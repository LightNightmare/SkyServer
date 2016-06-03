package kit.labcourse.transformations;

import java.util.regex.Pattern;

public class FGetNearbyObjEq {

	public static String transform (String line) {
		
		String[] partsWithoutFuncName = null;
		//the while exists because some users may have used the same function in the query multiple times
		while (line.contains("fgetnearbyobjeq") || line.contains("dbo.fgetnearbyobjeq")) {
			if (line.contains("dbo.fgetnearbyobjeq")) {
				//splits line into parts at the first occurrence of "dbo.fgetnearbyobjeq" in the line
				partsWithoutFuncName = line.split("dbo.fgetnearbyobjeq", 2);
			}
			else if (line.contains("fgetnearbyobjeq")) {
				partsWithoutFuncName = line.split("fgetnearbyobjeq", 2);
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
			//parameters[0] = @ra, parameters[1] = @dec, parameters[2] = @r
			if (parameterString != "") {
				parameters = parameterString.split(",");
			} 

			//constructing the new line
			if (parameters != null && parameters.length == 3 && partsWithoutFuncParenth != null && partsWithoutFuncParenth.length > 1) {
				line = partsWithoutFuncName[0] + "(select objID, run, camcol, field, rerun, type, cx, cy, cz, htmID, " +
						"(sqrt(square(ra - " + parameters[0] + ") + square(dec - " + parameters[1] + ")) * 60) as distance" +
						" from photoprimary " +
						"where ((square(ra - " + parameters[0] + ") + square(dec - " + parameters[1] + ")) < (square(" + parameters[2] + " * 0.016667)))" +
						")" + partsWithoutFuncParenth[1];
			}
			else
				line = "";
			
		}
		return line;
	}
}
