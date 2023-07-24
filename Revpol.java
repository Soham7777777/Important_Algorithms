import java.util.Scanner;
import java.util.Stack;

public class Revpol {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter an infix expression:");
        String infix = sc.nextLine();

        System.out.println("The Postfix expression is:");
        try {
            System.out.println(convert_to_postfix(infix));
        } catch (InvalidExpressionException expressionError) {
            expressionError.printStackTrace();
        }

        sc.close();
    }

    // The revpol algorithm turns out to be very flexifble because you can add any number of operators in any precedence order and in any assciativity order by just tweaking those functions a little bit!!
    // Yes you can make those functions dynamic !!! and that is only possible with revpol  

    static String convert_to_postfix(String infix) throws InvalidExpressionException {
        // initialize everything
        int size = infix.length(), rank = 0, idx = 0;
        char postfix[] = new char[size];
        Stack<Character> st = new Stack<>();
        st.push('('); // trust me, its really important step
        infix += ")"; // inorder to counter the push in above line

        // the main loop for char by char iteration
        for (char nextChar : infix.toCharArray()) {

            // the stack will be empty here if and only if infix contains extra ')' 
            if (st.isEmpty())
                throw new InvalidExpressionException("In your invalid expression, there is one or more extra ')'.");

            // loop for popping while confition is true
            while (stack_precedence(st.peek()) > input_precedence(nextChar)) {
                char popped = st.pop();
                postfix[idx++] = popped;
                rank += rank_function(popped);

                // this condition asserts that there are no greater or equal number of operators
                // compared to number of operands
                if (rank < 1)
                    throw new InvalidExpressionException(
                            "Number of operator is greater or equal to number of operands.");
            }

            // condition will only be false when top element is '(' and nextChar is ')'.
            if (stack_precedence(st.peek()) != input_precedence(nextChar))
                st.push(nextChar);
            else
                st.pop();
        }
        
        if (!st.isEmpty()) // condition will be true when there is missing one or more ')'
            throw new InvalidExpressionException("invalid expression, Missing one or multiple ')'.");
        else if (rank != 1)
            throw new InvalidExpressionException("There are more operands than it should be.");

        return new String(postfix);
    }

    static int input_precedence(char c) {
        return (c == '+' || c == '-' ? 1 : c == '*' || c == '/' ? 3 : c == '^' || c == '$' ? 6 : c == '(' ? 9 : c == ')' ? 0 : 7);
    }

    static int stack_precedence(char c) {
        return (c == '+' || c == '-' ? 2 : c == '*' || c == '/' ? 4 : c == '^' || c == '$' ? 5 : c == '(' ? 0 : 8);
    }

    static int rank_function(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '$' ? -1 : 1);
    }

    static class InvalidExpressionException extends Exception {
        InvalidExpressionException(String msg) {
            super(msg);
        }
    }
}