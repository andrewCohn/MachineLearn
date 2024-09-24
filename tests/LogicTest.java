import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import gates.*;
import org.junit.Test;

public class LogicTest {
    @Test
    public void testOR(){
        OR or = new OR();
        System.out.println("Or Initialized");
        for (int i=0;i<=1;i++){
            for (int j=0;j<=1;j++){
                System.out.printf("%d | %d = %d\n",i,j,or.exec(i,j));
            }
        }
        System.out.printf("Cost: %.4f\n",or.cost);
    }

    @Test
    public void testNOR(){
        NOR nor = new NOR();
        System.out.println("NOR Initialized");
        for (int i=0;i<=1;i++){
            for (int j=0;j<=1;j++){
                System.out.printf("%d | %d = %d\n",i,j,nor.exec(i,j));
            }
        }
        System.out.printf("Cost: %.4f\n",nor.cost);
    }
    @Test
    public void testAND(){
        AND and = new AND();
        System.out.println("AND Initialized");
        for (int i=0;i<=1;i++){
            for (int j=0;j<=1;j++){
                System.out.printf("%d & %d = %d\n",i,j,and.exec(i,j));
            }
        }
        System.out.printf("Cost: %.4f\n",AND.cost);
    }

    @Test
    public void testNAND(){
        NAND nand = new NAND();
        System.out.println("NAND Initialized");
        for (int i=0;i<=1;i++){
            for (int j=0;j<=1;j++){
                System.out.printf("%d & %d = %d\n",i,j,nand.exec(i,j));
            }
        }
        System.out.printf("Cost: %.4f\n",NAND.cost);
    }
    @Test
    public void testXOR(){
        XOR xor = new XOR();
        System.out.println("XOR Initialized");
        for (int i=0;i<=1;i++){
            for (int j=0;j<=1;j++){
                System.out.printf("%d ^ %d = %d\n",i,j,xor.exec(i,j));
            }
        }
        System.out.printf("Cost: %.4f\n",xor.cost);
    }
}
