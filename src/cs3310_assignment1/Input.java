package cs3310_assignment1;

// Date: 4/22/19
// Description: To assist in validating input
import java.util.Scanner;

class Input
{
	// driver method that test all input validation methods
	public static void main(String[] args)
	{

		String s1 = Input.getString("Input a string: ");
		System.out.printf("String: %s\n\n", s1);

		char c1 = Input.getChar("Input any character: ");
		System.out.printf("Char: %s\n\n", c1);

		char c2 = Input.getChar("Input a character of 'Y' or 'N': ", 'Y', 'N');
		System.out.printf("Char: %s\n\n", c2);

		char c3 = Input.getChar("Input a character in \"ABCDEF\": ", "ABCDEF");
		System.out.printf("Char: %s\n\n", c3);

		int i1 = Input.getInteger("Input any integer: ");
		System.out.printf("Integer: %s\n\n", i1);

		int i2 = Input.getInteger("Input a positive integer: ", true);
		System.out.printf("Integer: %s\n\n", i2);

		int i3 = Input.getInteger("Input a negative integer: ", false);
		System.out.printf("Integer: %s\n\n", i3);

		int i4 = Input.getInteger("Input an integer (10..20): ", 10,20);
		System.out.printf("Integer: %s\n\n", i4);

		int i5 = Input.getInteger("Input an integer not equal to 7: ", 7);
		System.out.printf("Integer: %s\n\n", i5);

		double d1 = Input.getDouble("Input any double: ");
		System.out.printf("Double: %s\n\n", d1);

		double d2 = Input.getDouble("Input a positive double: ", true);
		System.out.printf("Double: %s\n\n", d2);

		double d3 = Input.getDouble("Input a negative double: ", false);
		System.out.printf("Double: %s\n\n", d3);

		double d4 = Input.getDouble("Input a double (10..20): ", 20.0,10.0);
		System.out.printf("Double: %s\n\n", d4);

		double d5 = Input.getDouble("Input an double not equal to 7.0: ", 7.0);
		System.out.printf("Double: %s\n\n", d5);

	}

	/**
	* This getString method input a string.
	* @param prompt - a message display at console.
	* @return string value.
	*/
	public static String getString(String prompt)
	{
		Scanner keyboard = new Scanner(System.in);
		String input ="";
		do
		{
			System.out.print(prompt);
			input = keyboard.nextLine();
			if (input.length() <= 0)
			    System.out.println("ERROR: Input value must at least contain one character. Please re-enter.");
			else if(input.equals("Unknown") || input.equals("UKNOWN") || input.equals("unknown"))
			{
				input = " ";
				break;
			}
			else
				break;
			}
		while (true);
		return input;
	}

	/**
	* This getChar method validates input char type.
	* @param prompt - a message display at console.
    * @return valid char value.
	*/
	public static char getChar(String prompt)
	{
		Scanner keyboard = new Scanner(System.in);

		char input = ' ';
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextLine())
			{
			    System.out.println("ERROR: Input value must be a char. Please re-enter.");
			    keyboard.nextLine();
			}
			else
			{
				String temp = keyboard.nextLine();
				if ( temp.length() == 0)
				{
				    System.out.println("ERROR: Input value must not be empty. Please re-enter.");
				}
				else	
				{
					input = temp.charAt(0);
					break;
				}
				
			}
		}
		while (true);
		return input;
	}

	/**
	* This getChar method validates input char type of 'Y' or 'N'
	* @param prompt - a message display at console.
    * @return valid char value.
	*/
	public static char getChar(String prompt, char y, char n)
	{
		Scanner keyboard = new Scanner(System.in);

		char input = ' ';
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextLine())
			{
			    System.out.printf("ERROR: Input value must be a char of '%s' or '%s'. Please re-enter.\n", Character.toUpperCase(y), Character.toUpperCase(n));
			    keyboard.next();
			}
			else
			{
				String temp = keyboard.nextLine();
				if ( temp.length() == 0)
				{
				    System.out.println("ERROR: Input value must not be empty. Please re-enter.");
				}
				else	
				{
					input = Character.toUpperCase(temp.charAt(0));
					if (input != Character.toUpperCase(y) && input != Character.toUpperCase(n))
						System.out.printf("ERROR: Input value must be a char of '%s' or '%s'. Please re-enter.\n", y, n);
					else
						break;
				}
			}
		}
		while (true);
		return input;
	}

	/**
	* This getChar method validates input char type in specified string
	* @param prompt - a message display at console.
    * @return valid char value.
	*/
	public static char getChar(String prompt, String specValue)
	{
		Scanner keyboard = new Scanner(System.in);

		String input = "";
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextLine())
			{
			    System.out.printf("ERROR: Input value must be a char in \"%s\". Please re-enter.\n", specValue);
			    keyboard.next();
			}
			else
			{
				String temp = keyboard.nextLine();
				if ( temp.length() == 0)
				{
				    System.out.println("ERROR: Input value must not be empty. Please re-enter.");
				}
				else	
				{
					input = temp.toUpperCase().substring(0,1);
					if (!specValue.contains(input))
					    System.out.printf("ERROR: Input value must be a char in \"%s\". Please re-enter.\n", specValue);
					else
						break;
				}
			}
		}
		while (true);
		return input.charAt(0);
	}

	/**
	* This getInteger method validates input integer type.
	* @param prompt - a message display at  console.
    * @return valid integer value.
	*/
	public static int getInteger(String prompt)
	{
		Scanner keyboard = new Scanner(System.in);

		int input = 0;
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextInt())
			{
			    System.out.println("ERROR: Input value must be an integer. Please re-enter.");
			    keyboard.next();
			}
			else
			{
				input = keyboard.nextInt();
				break;
			}
		}
		while (true);
		return input;
	}

	/**
	* This getInteger method validates input integer type (positive or negative).
	* @param prompt - a message display at  console.
	* @param flag - true (positive) or false (negative)
	* @return valid integer value.
	*/
	public static int getInteger(String prompt, boolean flag)
	{
		Scanner keyboard = new Scanner(System.in);
		int input = 0;
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextInt())
			{
				if (flag)
			    	System.out.println("ERROR: Input value must be a positive integer. Please re-enter.");
			    else
			    	System.out.println("ERROR: Input value must be a negative integer. Please re-enter.");
			    keyboard.next();
			}
			else
			{
				input = keyboard.nextInt();
				if (input < 0 && flag == true)
				    System.out.println("ERROR: Input value must be a positive integer. Please re-enter.");
				else if (input > 0 && flag == false)
				    System.out.println("ERROR: Input value must be a negative integer. Please re-enter.");
				else
					break;
			}
		}
		while (true);
		return input;
	}

	/**
	* This getInteger method validates input integer type within range.
	* @param prompt - a message display at  console.
	* @param startValue - starting integer
	* @param endValue - ending integer
	* @return valid integer value.
	*/
	public static int getInteger(String prompt, int startValue, int endValue)
	{
		Scanner keyboard = new Scanner(System.in);
		int input = 0;

		int temp = startValue;
		if (startValue > endValue)
		{
			startValue = endValue;
			endValue = temp;
		}

		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextInt())
			{
				    System.out.printf("ERROR: Input value must be an integer between %d..%d. Please re-enter.\n", startValue, endValue);
			    keyboard.next();
			}
			else
			{
				input = keyboard.nextInt();
				keyboard.nextLine();
				if (input < startValue || input > endValue)
				    System.out.printf("ERROR: Input value must be an integer between %d..%d. Please re-enter.\n", startValue, endValue);
				else
					break;
			}
		}
		while (true);
		return input;
	}

	/**
	* This getInteger method validates input integer cannot be the specified value.
	* @param prompt - a message display at  console.
	* @param specValue - Specified integer
	* @return valid integer value.
	*/
	public static int getInteger(String prompt, int specValue)
	{
		Scanner keyboard = new Scanner(System.in);
		int input = 0;
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextInt())
			{
			    System.out.printf("ERROR: Input value must be an integer not equal to %. Please re-enter.\n", specValue);
			    keyboard.next();
			}
			else
			{
				input = keyboard.nextInt();
				keyboard.nextLine();
				if (input == specValue)
				    System.out.printf("ERROR: Input value must be an integer not equal to %d. Please re-enter.\n", specValue);
				else
					break;
			}
		}
		while (true);
		return input;
	}

	/**
	* This getDouble method validates input double type.
	* @param prompt - a message display at  console.
    * @return valid double value.
	*/
	public static double getDouble(String prompt)
	{
		Scanner keyboard = new Scanner(System.in);

		double input = 0.0;
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextDouble())
			{
			    System.out.println("ERROR: Input value must be a double value. Please re-enter.");
			    keyboard.next();
			}
			else
			{
				input = keyboard.nextDouble();
				break;
			}
		}
		while (true);
		return input;
	}

	/**
	* This getDouble method validates input double type (positive or negative).
	* @param prompt - a message display at  console.
	* @param flag - true (positive) or false (negative)
	* @return valid double value.
	*/
	public static double getDouble(String prompt, boolean flag)
	{
		Scanner keyboard = new Scanner(System.in);
		double input = 0.0;
		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextDouble())
			{
				if (flag)
			    	System.out.println("ERROR: Input value must be a positive double. Please re-enter.");
			    else
			    	System.out.println("ERROR: Input value must be a negative double. Please re-enter.");
			    keyboard.next();
			}
			else
			{
				input = keyboard.nextDouble();
				if (input < 0.0 && flag == true)
				    System.out.println("ERROR: Input value must be a positive double. Please re-enter.");
				else if (input > 0.0 && flag == false)
				    System.out.println("ERROR: Input value must be a negative double. Please re-enter.");
				else
					break;
			}
		}
		while (true);
		return input;
	}

	/**
	* This getDouble method validates input double type within range.
	* @param prompt - a message display at  console.
	* @param startValue - starting double
	* @param endValue - ending double
	* @return valid double value.
	*/
	public static double getDouble(String prompt, double startValue, double endValue)
	{
		Scanner keyboard = new Scanner(System.in);
		double input = 0;

		double temp = startValue;
		if (startValue > endValue)
		{
			startValue = endValue;
			endValue = temp;
		}

		do
		{
			System.out.print(prompt);
			if (!keyboard.hasNextDouble())
			{
				    System.out.printf("ERROR: Input value must be an double between %.2f..%.2f. Please re-enter.\n", startValue, endValue);
			    keyboard.next();
			}
			else
			{
				input = keyboard.nextDouble();
				keyboard.nextLine();
				if (input < startValue || input > endValue)
				    System.out.printf("ERROR: Input value must be an double between %.2f..%.2f. Please re-enter.\n", startValue, endValue);
				else
					break;
			}
		}
		while (true);
		return input;
	}

		/**
		* This getDouble method validates input double cannot be the specified value.
		* @param prompt - a message display at console.
		* @param specValue - Specified double
		* @return valid double value.
		*/
		public static double getDouble(String prompt, double specValue)
		{
			Scanner keyboard = new Scanner(System.in);
			double input = 0.0;

			do
			{
				System.out.print(prompt);
				if (!keyboard.hasNextDouble())
				{
					    System.out.printf("ERROR: Input value must be a double not equal to %.2f. Please re-enter.\n", specValue);
				    keyboard.next();
				}
				else
				{
					input = keyboard.nextDouble();
					keyboard.nextLine();
					if (input == specValue)
					    System.out.printf("ERROR: Input value must be a double not equal to %.2f. Please re-enter.\n", specValue);
					else
						break;
				}
			}
			while (true);
			return input;
		}
}
