
public class StringMan {
	//Finds the Nth occurrence of a character in a string
	public int nthOccurrence(String str, char c, int n) {
	    int pos = str.indexOf(c, 0);
	    while (n-- > 0 && pos != -1)
	        pos = str.indexOf(c, pos+1);
	    return pos;
	}
	
	//Removes ".txt" from the end of a string
	public String removeTxt(String Input) {
		
		String Txt = Input.substring(Input.length()-3,Input.length());
		Txt = Txt.toLowerCase();

		if (Txt.equals("txt")){
			return Input.substring(0,Input.length()-4);
		}
		else{
			return Input;
		}
	}
	
	public String soundex(String s) { 
        char[] x = s.toUpperCase().toCharArray();
        char firstLetter = x[0];
        int xLength = x.length;
        
        // convert letters to numeric code
        for (int i = 0; i < xLength; i++) {
            switch (x[i]) {
                case 'B':
                case 'F':
                case 'P':
                case 'V': { x[i] = '1'; break; }

                case 'C':
                case 'G':
                case 'J':
                case 'K':
                case 'Q':
                case 'S':
                case 'X':
                case 'Z': { x[i] = '2'; break; }

                case 'D':
                case 'T': { x[i] = '3'; break; }

                case 'L': { x[i] = '4'; break; }

                case 'M':
                case 'N': { x[i] = '5'; break; }

                case 'R': { x[i] = '6'; break; }

                default:  { x[i] = '0'; break; }
            }
        }

        // remove duplicates
        String output = "" + firstLetter;
        for (int i = 1; i < xLength; i++)
            if (x[i] != x[i-1] && x[i] != '0')
                output += x[i];

        // pad with 0's or truncate
        output = output + "0000";
        return output.substring(0, 4);
    }
}
