package kit.labcourse.transformations;

import java.util.regex.Pattern;

public class FGetObjFromRect {

	public static String transform (String line) {
		
		String[] partsWithoutFuncName = null;
		//the while exists because some users may have used the same function in the query multiple times
		while (line.contains("fgetobjfromrect") || line.contains("dbo.fgetobjfromrect")) {
			if (line.contains("dbo.fgetobjfromrect")) {
				//splits line into parts at the first occurrence of "dbo.fgetobjfromrect" in the line
				partsWithoutFuncName = line.split("dbo.fgetobjfromrect", 2);
			}
			else if (line.contains("fgetobjfromrect")) {
				partsWithoutFuncName = line.split("fgetobjfromrect", 2);
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
			//parameters[0] = @ra1, parameters[1] = @ra2, parameters[2] = @dec1, parameters[3] = @dec2
			if (parameterString != "") {
				parameters = parameterString.split(",");
			} 

			//constructing the new line
			if (parameters != null && parameters.length == 4 && partsWithoutFuncParenth != null && partsWithoutFuncParenth.length > 1) {
				line = partsWithoutFuncName[0] + "(select objID, run, camcol, field, rerun, type, cx, cy, cz, htmID from photoobj " +
						"where ra between " + parameters[0] + " and " + parameters[1] + " and " +
						"dec between " + parameters[2] + " and " + parameters[3] +
						")" + partsWithoutFuncParenth[1];
			}
			else
				line = "";
			
		}
		return line;
	}
}
