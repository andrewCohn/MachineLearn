import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import java.util.Random;
public class ShuntingTest {
    private static final String[] operators = {"|","&","^","↑","↓"};
    private static final String[] digits = {"0", "1"};
    @Test
    public void testSingle(){
        ShuntingYard shunter = new ShuntingYard();
        String toEval = "((1 | 0) & (1 ↑ 0)) & 1";
        System.out.printf("%s = %d",toEval,shunter.shuntMachineLearn(toEval));
        assertEquals(shunter.shunt(toEval),shunter.shuntMachineLearn(toEval));
    }
    @Test
    public void testMany(){
        ShuntingYard shunter = new ShuntingYard();


        String toEval = "((1 | 0) & (1 ↑ 0)) & 1";
        assertEquals(shunter.shunt(toEval),shunter.shuntMachineLearn(toEval));
    }
    @Test
    public void testRandom(){
        ShuntingYard shunter = new ShuntingYard();
        for (int i = 0; i<5;i++){
            System.out.println(generateRandomLogicString());
        }
    }

    public static String generateRandomLogicString() {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        int length = random.nextInt(10) + 5; // Generate a random length between 5 and 14
        int openParentheses = 0;
        boolean lastWasOperator = false; // Track if last added was an operator

        while (sb.length() < length) {
            if (openParentheses <= 0 || random.nextBoolean()) {
                // Add a random component (operator or digit)
                if (lastWasOperator) {
                    sb.append(digits[random.nextInt(digits.length)]);
                    lastWasOperator = false;
                } else {
                    int choice = random.nextInt(2); // Adjust the range based on your components
                    switch (choice) {
                        case 0:
                            sb.append(operators[random.nextInt(operators.length)]);
                            lastWasOperator = true;
                            break;
                        case 1:
                            sb.append(digits[random.nextInt(digits.length)]);
                            lastWasOperator = false;
                            break;
                        default:
                            break;
                    }
                }
            } else {
                // Add a closing parenthesis if there are open ones
                sb.append(")");
                openParentheses--;
            }

            // Open a new parenthesis randomly
            if (random.nextInt(3) == 0 && sb.length() < length - 1) { // Adjust probability as needed
                sb.append("(");
                openParentheses++;
            }
        }

        // Ensure all open parentheses are closed
        while (openParentheses > 0) {
            sb.append(")");
            openParentheses--;
        }

        return sb.toString();
    }

}
