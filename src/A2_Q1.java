import java.util.Scanner;
/**
 * 
 * @author Mr. Daschner
 *
 */
public class A2_Q1 {

	public static void main(String[] args) 
	{
		//Get user input.
		getInput();
	}
	
	/**
	 * Gets the input of the user and converts it to a hexidecimal if possible or requests a new number.
	 */
	private static void getInput()
	{
		int a;
		Scanner in = new Scanner(System.in);
		try
		{
			System.out.println("Enter an integer between -255 and 255: ");
		    a = in.nextInt();
		    System.out.println("The number you entered converted to hex is " + decToHex(decToTwo(a)));
		}
		catch (Exception e)
		{
			System.out.println("You entered an invalid integer, try again: ");
			getInput();
		}
		in.close();
	}
	/**
	 * Converts the decimal to two's complement.
	 * @param num The number to convert.
	 * @return Returns the string format of two's complement.
	 */
	private static String decToTwo(int num)
	{
		if(num >= 0)
		{
			return padZero(decToBinary(num));
		}
		else
		{
			return addOne(flipDigits(padZero(decToBinary(((-1)*num)))));
		}
	}
	
	/**
	 * Converts binary to hex.
	 * @param binary Binary to convert.
	 * @return Returns hexidecimal number.
	 */
	private static String decToHex(String binary)
	{	
		if(binary.equalsIgnoreCase("1"))
			return "1";
		else if(binary.equalsIgnoreCase("0"))
			return "0";
		else if(binary.equalsIgnoreCase("11"))
			return "3";
		else
			return convertSets(convertToStringArray(binary), (binary.length()-1)/4);	
	}
	
	/**
	 * Converts a set of a binary numbers to hexadecimal.
	 * @param binary Binary segments to convert.
	 * @param setIndex Starting point of the index to convert.
	 * @return Returns the hexadecimal representation of the digit set.
	 */
	private static String convertSets(String[] binary, int setIndex)
	{
		String answer = "";
		int maxIndex = (setIndex*4);
		int minIndex = (setIndex*4)+2;
		System.out.println("Range: " + maxIndex + "-" + minIndex);
		if(binary.length > minIndex)
		{

			int length = binary.length - 1 ;
			int currentPositions = length%4;
			String[] newBinary = {"0","0","0","0"};
			
			for(int i = 0; i < currentPositions+1; i++)
			{
				newBinary[3 - i] = binary[currentPositions - i];
			}
			answer = convertHex(newBinary, 3);
			
		}
		else
		{
			answer = convertSets(binary, setIndex - 1) + convertHex(binary, minIndex-1);
		}
		return answer;
	}
	
	/**
	 * Converts individual segments to from binary to hexadecimal.
	 * @param binary Binary segment to convert.
	 * @param min The min index to start converting to.
	 * @return Returns the letter representation of the 4 digit bits.
	 */
	private static String convertHex(String[] binary, int min)
	{
		String answer = "";
		for(int i = 0; i < 4; i++)
		{
			answer = binary[min-i]+ answer;
		}
		switch (answer) 
		{
        	case "0000":  answer = "0";
                break;
        	case "0001":  answer = "1";
                break;
        	case "0010":  answer = "2";
        		break;
        	case "0011":  answer = "3";
    			break;
        	case "0100":  answer = "4";
    			break;
        	case "0101":  answer = "5";
    			break;
        	case "0110":  answer = "6";
    			break;
        	case "0111":  answer = "7";
    			break;
        	case "1000":  answer = "8";
    			break;
        	case "1001":  answer = "9";
    			break;
        	case "1010":  answer = "A";
    			break;
        	case "1011":  answer = "B";
    			break;
        	case "1100":  answer = "C";
    			break;
        	case "1101":  answer = "D";
    			break;
        	case "1110":  answer = "E";
    			break;
        	case "1111":  answer = "F";
    			break;
        	default: answer = "?";
        		break;
		}
		
		return answer;
	}	
	
	/**
	 * Converts decimal to binary.
	 * @param num Number to convert.
	 * @return Returns the regular binary expression of the absolute value of num.
	 */
	private static String decToBinary(int num)
	{
		String answer = String.valueOf(num%2);
		int newNum = num/2;
		
		if(newNum == 0)
		{
			return answer;
		}
		else
		{
			return decToBinary(newNum) + answer ;
		}
	}
	
	/**
	 * Flips the digits to convert to two's.
	 * @param flip String of binary to flip for two's.
	 * @return Returns the flipped version of the binary string.
	 */
	private static String flipDigits(String flip)
	{
		String answer = "";
		String[] flipped = flipPosition(convertToStringArray(flip), flip.length()-1);
		for(int i = 0; i < flipped.length; i++)
		{
			answer = answer + flipped[i];
		}
		return answer;
	}
	/**
	 * Adds zeros to the binary form to pad it with needed zeros.
	 * @param numStr The string to pad zeros to.
	 * @return Returns the padded binary string.
	 */
	private static String padZero(String numStr)
	{
		return "0" + numStr;
	}
	/**
	 * Flips individual string positions at each character from 1 to 0 or 0 to 1.
	 * @param chars Chars to flip.
	 * @param index Index of the char to flip.
	 * @return Returns a flipped set of the char.
	 */
	private static String[] flipPosition(String[] chars, int index)
	{
		if(chars[index].equals("0"))
		{
			chars[index] = "1";
		}
		else
		{
			chars[index] = "0";
		}
		if(index != 0)
		{
			return flipPosition(chars, index - 1);
		}
		else
		{
			return chars;
		}
	}
	/**
	 * Breaks the string up into characters.
	 * @param s String to split up.
	 * @return Returns the array of the split up string or null if the string isn't a proper string.
	 */
	public static String[] convertToStringArray(String s) 
	{
		   if ( s == null ) 
		   {
		     return null;
		   }

		   String[] array = s.split("");
		   return array;
	}
	
	/**
	 * Adds one to the binary for two's complement.
	 * @param s Binary string to add 1 to.
	 * @return Returns the new two's complement.
	 */
	private static String addOne(String s)
	{
		String answer = "";
		String[] added = addPosition(convertToStringArray(s), s.length()-1);
		for(int i = 0; i < added.length; i++)
		{
			answer = answer + added[i];
		}
		return answer;
	}
	
	/**
	 * Adds 1 to each individual position.
	 * @param chars Char set of the binary term you are adding to.
	 * @param index Index of the char to add to.
	 * @return Returns the added 1 to the char set.
	 */
	private static String[] addPosition(String[] chars, int index) 
	{
		if(chars[index].equals("0"))
		{
			chars[index] = "1";
		}
		else
		{
			if((index - 1) >= 0)
			chars = addPosition(chars, index - 1);
		}
		return chars;

	}

}
