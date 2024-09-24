import java.util.Queue;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import gates.*;


public class ShuntingYard {
    static Character[] operators = {'|','&','^','↑','↓'};
    private static XOR xor = new XOR();
    private static NAND nand = new NAND();
    private static NOR nor = new NOR();
    private static OR or = new OR();
    private static AND and = new AND();
    public int shuntMachineLearn(String s){
        Queue<Character> outQueue = new LinkedList<>();
        Stack<Character> opStack = new Stack<>();
        for (Character c: s.toCharArray()){
            if (Character.isDigit(c)){
                outQueue.add(c);
            } else if (c=='(') {
                opStack.add(c);
            } else if(c==')'){
                assert !opStack.isEmpty();
                while (opStack.peek()!='('){
                    outQueue.add(opStack.pop());
                }
                assert opStack.peek() == '(';
                opStack.pop();
            }
            else if (isOperator(c)){
                while (!opStack.isEmpty()&&!opStack.peek().equals('(')){
                    outQueue.add(opStack.pop());
                }
                opStack.add(c);
            }
        }
        while (!opStack.isEmpty()){
            assert opStack.peek() != '(';
            outQueue.add(opStack.pop());
        }
        // Evaluate the RPN expression
        Stack<Integer> evalStack = new Stack<>();
        while (!outQueue.isEmpty()) {
            char c = outQueue.poll();
            if (Character.isDigit(c)) {
                evalStack.push(Character.getNumericValue(c));
            } else if (isOperator(c)) {
                int b =  evalStack.pop();
                int a =  evalStack.pop();
                evalStack.push(evaluateMachine(c, a, b));
            }
        }
        return evalStack.pop();
    }
    public int shunt(String s){
        Queue<Character> outQueue = new LinkedList<>();
        Stack<Character> opStack = new Stack<>();
        for (Character c: s.toCharArray()){
            if (Character.isDigit(c)){
                outQueue.add(c);
            } else if (c=='(') {
                opStack.add(c);
            } else if(c==')'){
                assert !opStack.isEmpty();
                while (opStack.peek()!='('){
                    outQueue.add(opStack.pop());
                }
                assert opStack.peek() == '(';
                opStack.pop();
            }
            else if (isOperator(c)){
                while (!opStack.isEmpty()&&!opStack.peek().equals('(')){
                    outQueue.add(opStack.pop());
                }
                opStack.add(c);
            }
        }
        while (!opStack.isEmpty()){
            assert opStack.peek() != '(';
            outQueue.add(opStack.pop());
        }
        // Evaluate the RPN expression
        Stack<Integer> evalStack = new Stack<>();
        while (!outQueue.isEmpty()) {
            char c = outQueue.poll();
            if (Character.isDigit(c)) {
                evalStack.push(Character.getNumericValue(c));
            } else if (isOperator(c)) {
                int b =  evalStack.pop();
                int a =  evalStack.pop();
                evalStack.push(evaluate(c, a, b));
            }
        }
        return evalStack.pop();
    }


    public boolean isOperator(Character c){
        for (Character elem : operators){
            if (elem.equals(c)){
                return true;
            }
        }
        return false;
    }
    private int evaluateMachine(Character c,int a,int b) {
        //{'|','&','^','↑','↓'}
        return switch (c) {
            case '|' -> or.exec(a,b);
            case '&' -> and.exec(a,b);
            case '^' -> xor.exec(a,b);
            case '↑' -> nand.exec(a,b);
            case '↓' -> nor.exec(a,b);
            default -> Integer.MIN_VALUE;
        };
    }
    private int evaluate(Character c,int a,int b) {
        //{'|','&','^','↑','↓'}
        return switch (c) {
            case '|' -> a | b;
            case '&' -> a & b;
            case '^' -> a^b;
            case '↑' -> ~(a&b) & 1;
            case '↓' -> ~(a|b) & 1;
            default -> Integer.MIN_VALUE;
        };
    }
        

}
