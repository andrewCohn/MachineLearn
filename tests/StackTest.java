import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class StackTest {
    @Test
    public void quickieTest(){
        int n = 50;
        Stack<Integer> testStack = new Stack<>();
        for (int i = 0; i<=n;i++){
            testStack.add(i);
        }
        for (int i = n; i>=0;i--){
            assertEquals(i,(int) testStack.peek());
            assertEquals(i,(int) testStack.pop());
        }
    }
}
