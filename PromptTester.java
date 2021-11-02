/**
 *	Test the Prompt class
 *	@author	Mr Greenstein
 *	@since	August 8, 2018
 */

public class PromptTester {

	public static void main(String[] args) {
		String str = Prompt.getString("Provide me a string");
		System.out.println("Here it is -> " + str);
		
		int a = Prompt.getInt("Give me an integer");
		System.out.println("Here it is -> " + a);
		
		a = Prompt.getInt("Give me an integer", 20, 40);
		System.out.println("Here it is -> " + a);

		double b = Prompt.getDouble("Give me a double");
		System.out.println("Here it is -> " + b);

		b = Prompt.getDouble("Give me an double", 20, 40);
		System.out.println("Here it is -> " + b);

		char c = Prompt.getChar("Give me a char");
		System.out.println("Here it is -> " + c);
	}

}