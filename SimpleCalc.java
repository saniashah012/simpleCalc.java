import java.util.List;		// used by expression evaluator
import java.util.ArrayList;

/**
 *	<Description SimpleCalc is a calculator function that handles
 * 				 basic math operations (+, -, ^, *, /, %, (, )) using 2 stacks
 * 				 one for storing th e operation and another for storing the number
 * 				 values. Using the precedence rules of mathematics when performing
 * 				 the calculations.>
 *
 *	@author	Sania Shah
 *	@since	April 4th, 2021
 */
public class SimpleCalc 
{
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack
	private List<String> tokens;	

	// constructor	
	public SimpleCalc() 
	{
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		tokens = new ArrayList<String>();	

	}
	
	public static void main(String[] args) 
	{
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	/**
	 *  Run method used to call different methods which carry out 
	 *	the calculator functionss and print welcome and goodbye messages
	 */
	public void run() 
	{
		System.out.println("\nWelcome to SimpleCalc!!!\n" + "Works like a calculator, enter q to exit\n");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompts the user to enter in expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() 
	{
		String input = "";

		// keep going until input is "q"
		do 
		{
			// keep prompting for input until text is entered
			do 
			{
				input = Prompt.getString("");
			}
			while(input.length() == 0);
			
			// if "h", then print help message
			if (input.equals("h"))
				printHelp();

			// if no quit, then evaluate expression
			else if (!input.equals("q"))
				System.out.println(evaluateExpression(utils.tokenizeExpression(input)));
		} 
		while (!input.equals("q"));
	}
	
	/**	
	 *	Print help menu
	 */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'\n");
	}
	
	/**
	 *	Evaluates the expression and returns the value by popping and pushing values
	 *	and operations onto the valueStack and the operatorStack.
	 *	Uses the hasPrecedence method to determine order of operations and 
	 *	performs calculations accordingly.
	 *	@param tokens	arithmetic expression tokenized into a List of tokens
	 *	@return			answer to given expression
	 */
	public double evaluateExpression(List<String> tokens) 
	{
		double value = 0;
		double answer = 0;
		int index = 0;
		
		// while still not at list's end
		while (index < tokens.size()) 
		{
			String token = tokens.get(index);
			
			// if token is number
			if(!utils.isOperator(token.charAt(0))) 
				valueStack.push(Double.parseDouble(token));
			// if token is left parenthesis
			else if(token.equals("("))
				operatorStack.push("(");
			// if token is right parenthesis
			else if(token.equals(")")) 
			{
				while (!operatorStack.peek().equals("("))
					valueStack.push(calculate());
				operatorStack.pop();
			}
			// if token is operator
			else if (utils.isOperator(token.charAt(0))) 
			{
				while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek()))
					valueStack.push(calculate());
				operatorStack.push(token);
			}
			// increment index
			index++;
		}
		// make sure all remaining elements in stacks are calculated
		while (!operatorStack.isEmpty())
			valueStack.push(calculate());
		
		// return final answer
		return valueStack.pop();
	}
	
	/**
	 * 	Pops the first two elements in the valueStack and the first element in the operatorStack,
	 *  and returns the answer.
	 * 	@return		answer
	 */
	public double calculate() 
	{
		String op = operatorStack.pop();
		double first = valueStack.pop();
		double second = valueStack.pop();
		double answer = 0.0;
		// depending on the operator
		switch (op) 
		{
			case "^":  answer = Math.pow(second,first); break;
			case "*":  answer = first * second; 		 break;
			case "/":  answer = second / first; 		 break;
			case "%":  answer = second % first; 		 break;
			case "+":  answer = first + second; 		 break;
			case "-":  answer = second - first; 		 break;
		}
		return answer;
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) 
	{
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
}