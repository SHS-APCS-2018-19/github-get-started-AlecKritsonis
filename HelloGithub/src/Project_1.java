import java.util.*;

public class Project_1 {

	public static void main(String[] args) {
		String input = "";
		String[] operators = {"+", "-", "/", "x"};
		while (!input.equals("quit")) {
			input = getInput();							//takes input

			try {

				String[] values = new String[3];			//creates empty array
				organize(input, values); 					//puts string into array
				echo(values);								//shows the parts of the equation
				
				String fraction1 = convertToImpartial(values[0]);						//converts to improper fraction
				System.out.println();
				String fraction2 = convertToImpartial(values[2]);						//converts to improper fraction
	//			System.out.println(fraction1 + " " + values[1] + " " + fraction2); 		//Print fraction
				
	//			System.out.println("Numerator1: " + (fraction1.substring(0, fraction1.indexOf("/"))));							//Debug giant line of mess below
	//			System.out.println("Denominator1: " + (fraction1.substring(fraction1.indexOf("/") + 1, fraction1.length())));	//  |
	//			System.out.println("Operator: " + Arrays.binarySearch(operators, values[1]));									//  |
	//			System.out.println("Numerator2: " + (fraction2.substring(0, fraction2.indexOf("/"))));							//  |
	//			System.out.println("Denominator2: " + (fraction2.substring(fraction2.indexOf("/") + 1, fraction2.length())));	//	V
				String improperFinal = doMaths(Integer.parseInt(fraction1.substring(0, fraction1.indexOf("/"))), Integer.parseInt(fraction1.substring(fraction1.indexOf("/") + 1, fraction1.length())), Arrays.binarySearch(operators, values[1]), Integer.parseInt(fraction2.substring(0, fraction2.indexOf("/"))), Integer.parseInt(fraction2.substring(fraction2.indexOf("/") + 1, fraction2.length())));
				System.out.println("Improper Final: " + improperFinal);
				System.out.println("Simplified Final: " + simplify(improperFinal));
				System.out.println();
			}
			
			catch (Exception e) {			//catches wrong user input
				if (input.equals("quit")) {
					
				} else { 
					System.out.println("Improper forrat!! ");
				}
			}	
		}
	}
	
	public static String getInput() {									//Takes input
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter an expression separated by spaces using operators +, -, x, or / (or \"quit\"): ");
		String input = scanner.nextLine();
		
		return input;
	}
	public static String[] organize(String input, String[] result) {	//Places input in array
		StringTokenizer tokenizer = new StringTokenizer(input);

		String Fraction1 = tokenizer.nextToken();
		String Operator = tokenizer.nextToken();
		String Fraction2 = tokenizer.nextToken();		
		
		
		result[0] = Fraction1;
		result[1] = Operator;
		result[2] = Fraction2;
		
		return result;
	}
	public static void echo(String[] array) {							//Prints out array
		System.out.println();
		System.out.println("Fraction 1: " + array[0]);
		System.out.println("Operand: " + array[1]);
		System.out.println("Fraction 2: " + array[2]);		
	}
	public static String convertToImpartial(String fraction) {			//Converts to Impartial fraction
		String numerator = "";
		String denominator = "";
		String coefficient = "";
		int finalNumerator = 0;
		int finalDenominator = 0;
		
		if (fraction.indexOf("/") < 0) {			//if not a fraction at all
			numerator = fraction;
			denominator = "1";
			return (numerator + "/" + denominator);
			
		} else {
			
			if (fraction.indexOf("_") > 0) {			//if mixed fraction
				numerator = fraction.substring(fraction.indexOf("_") + 1, fraction.indexOf("/"));			
				denominator = fraction.substring(fraction.indexOf("/") + 1, fraction.length());
				coefficient = fraction.substring(0, fraction.indexOf("_"));
				
				if (fraction.indexOf("-") >= 0) {		//if negative fraction
					finalNumerator = (Integer.parseInt(numerator) - (Integer.parseInt(coefficient) * Integer.parseInt(denominator))) * -1;
			
				} else {
					finalNumerator = Integer.parseInt(numerator) + (Integer.parseInt(coefficient) * Integer.parseInt(denominator));
				}
				
																//if denominator is negative -> send negative to numerator
				finalDenominator = Integer.parseInt(denominator);
				if (finalDenominator < 0) {
					finalNumerator *= -1;
					finalDenominator *= -1;
				}

			} else {
				finalNumerator = Integer.parseInt(fraction.substring(0, fraction.indexOf("/")));
				denominator = fraction.substring(fraction.indexOf("/") + 1, fraction.length());
				finalDenominator = Integer.parseInt(denominator);

			}
			return (finalNumerator + "/" + finalDenominator);
		}
	}
	public static String doMaths(int numerator1, int denominator1, int operation, int numerator2, int denominator2) {	//Does the math
		int resultNumerator = 0;
		int resultDenominator = 0;

																							// Addition
		if (operation == 0) {
																						//if denominators are different
			if (denominator1 != denominator2) {
				resultDenominator = denominator1 * denominator2;
				resultNumerator = (numerator1 * denominator2) + (numerator2 * denominator1);
				
			} else {
				resultDenominator = denominator1;
				resultNumerator = numerator1 + numerator2;
			}
			
																							// Subtraction
		} else if (operation == 1) {
																						//if denominators are different
			if (denominator1 != denominator2) {
				resultDenominator = denominator1 * denominator2;
				resultNumerator = (numerator1 * denominator2) - (numerator2 * denominator1);
				
			} else {
				resultDenominator = denominator1;
				resultNumerator = numerator1 - numerator2;
			}
			
																							// Division
		} else if (operation == 2) {
			resultNumerator = numerator1 * denominator2;
			resultDenominator = numerator2 * denominator1;
			
																							//Multiplication
		} else if (operation == 3) {
			resultNumerator = numerator1 * numerator2;
			resultDenominator = denominator1 * denominator2;
		}
		
		String resultFraction = resultNumerator + "/" + resultDenominator;
		return resultFraction;
	}
	public static String simplify(String improper) {
		String simplifiedFinal = "";
		int numerator = Integer.parseInt(improper.substring(0, improper.indexOf("/")));
		int denominator = Integer.parseInt(improper.substring(improper.indexOf("/") + 1, improper.length()));
		int coefficient = 0;
		
		if (numerator % denominator == 0) {				//if they can be simplified to whole number
			simplifiedFinal = "" + numerator/denominator;
			
		} else {										//needs further simplifying

			if (numerator < 0 && denominator < 0) {			//if the signs can be reduced
				numerator *= -1;
				denominator *= -1;
			}

			coefficient = (int) (numerator/denominator);

			numerator = (numerator % denominator);

			for (int i = 1; i < denominator; i++) {				//finds greatest common factor
				if (denominator % i == 0 && numerator % i == 0) {
					numerator /= i;
					denominator /= i;
					i = 1;
				}
			}

			if (coefficient == 0) {								//returns mixed or impartial
				simplifiedFinal = numerator + "/" + denominator;
			} else {
				simplifiedFinal = coefficient + "_" + numerator + "/" + denominator;
			}

		}
		
		return simplifiedFinal;
	}
}

/* ================= TEST CASES ================
 
ADDITION:
  	1 point: Simple fractions, same denominator: 1/7 + 3/7  = 4/7
	1 point: Simple fractions, different denominators: 1/3 + 1/5 = 8/15
	1 point: Simple fractions, result reduced: 1/2 + -5/6 = -1/3
	1 point: Whole numbers: 103 + 20 = 123
	1 point: Mixed fractions: 4_15/16 + -3_11/16 = 1_1/4
	1 point: Improper fraction, zero result: 1_3/10 + -13/10 = 0
	
SUBTRACTION:
	1 point: Simple fractions, same denominator: 1/7 - -3/7  = 4/7
	1 point: Simple fractions, different denominators: 1/3 - -1/5 = 8/15
	1 point: Simple fractions, result reduced: 1/2 - 5/6 = -1/3
	1 point: Whole numbers: 103 - -20 = 123
	1 point: Mixed fractions: 4_15/16 - 3_11/16 = 1_1/4
	1 point: Improper fraction, zero result: 1_3/10 - 13/10 = 0
	
MULTIPLICATION:
	1 point: Simple fractions, same denominator: 1/7 x 3/7 = 3/49
	1 point: Simple fractions, different denominators: 1/3 x -1/5 = -1/15
	1 point: Simple fractions, result reduced: 1/2 x 2/3 = 1/3
	1 point: Whole numbers: 3 x -1 = -3
	1 point: Mixed fractions: 1_11/25 x 3_1/8 = 4_1/2
	1 point: Improper fraction, unit result: 3/10 x 10/3 = 1
	
DIVISION:
	1 point: Simple fractions, same denominator: 1/7 / 7/3 = 3/49
	1 point: Simple fractions, different denominators: 1/3 / 5/-1 = -1/15
	1 point: Simple fractions, result reduced: 1/2 / 3/2 = 1/3
	1 point: Whole numbers: 3 / -1 = -3
	1 point: Mixed fractions: 1_11/25 / 8/25 = 4_1/2
	1 point: Improper fraction, unit result: 3/10 / 3/10 = 1


*/

