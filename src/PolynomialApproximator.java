import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PolynomialApproximator {
    private static float[][] testData;
    private static final int DEGREE_APPROX = 4;
    private static final int trainSize = 5;
    private static float beta =0.9f;
    static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static float cost(float[] params){
        // takes our 3 parameters, and determines the quality of the guess. A bigger number means a worse guess.
        // Our goal is to minimize cost
        float result = 0f;
        for (int i = 0; i< testData.length;i++){
            float x = testData[i][0];
            float y = 0;
            for (int j=0; j<params.length;j++){
                float coeff = params[params.length-1-j];
                float powerTerm = (float) Math.pow(x,j);
                y+= coeff*powerTerm;
            }
            float d = y - testData[i][1];
            float cost = d*d;
            result += cost;
            //System.out.printf("Actual: %.3f, Expected: %d\n",y,testData[i][1]);
        }
        result /= testData.length;
        return result;
    }
    private static float randFloat(){
        return (float) Math.random();
    }
    public static float sigmoid(float x) {
        return (float)(1 / (1 + Math.exp(-x)));
    }
    public static void main(String[] args) {
        // gen initial guesses for the machine learning function parameters

        float[] parameters = new float[DEGREE_APPROX];
        for (int i = 0; i < DEGREE_APPROX; i++) {
            parameters[i] = (randFloat() * 20) - 10;
        }


        // the parameters we are giving the training data, and what we want the model to solve for
        int a = 1;
        int b = 15;
        int c = -1;
        int d = 6;
        //genPolyNomialTest(new int[]{0,4,3,-2,3,5}); // generates the training data, based on f(x)=ax^2+bx+c, as a 2d array, where data[i] = f(i)
        //genPolyNomialTest(new int[]{4,3,5});
        //genExponentialTest();
        genSineTest();
        //System.out.printf("A: %d\nB: %d\nC: %d\n",a,b,c);
        int trainingRounds = (int) (1 * Math.pow(10, 8)); // the amount of rounds to train for
        float eps = 1e-5f; // a very small number!
        float rate = 1e-5f; // the learning rate, where a bigger number is a bigger change to the guess

        System.out.printf("Initial Guess:\n");
        for (float param : parameters) {
            System.out.printf("Initial Guess: %.3f\n", param);
        }
        float[] vwArray = new float[parameters.length];
        for (int i = 0; i < trainingRounds; i++) {
            // apply finite difference to the cost function (https://en.wikipedia.org/wiki/Finite_difference)



            float curCost = cost(parameters);
            /*
            if (curCost < 5E-3) {
                break;
            }
             */

            // List to hold Future results
            List<Future<Float>> futures = new ArrayList<>(parameters.length);
            for (int j = 0; j < parameters.length; j++) {
                final int index = j;
                Callable<Float> task = () -> {
                    float[] alteredParams = deepCopy(parameters);
                    alteredParams[index] += eps;
                    return (cost(alteredParams) - curCost) / eps;
                };
                futures.add(executor.submit(task));
            }

            // Retrieve results and update dwArray
            float[] dwArray = new float[parameters.length];
            for (int j = 0; j < futures.size(); j++) {
                try {
                    dwArray[j] = futures.get(j).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            // Update vwArray
            for (int j = 0; j < parameters.length; j++) {
                vwArray[j] = beta * vwArray[j] + (1 - beta) * dwArray[j];
            }

            // Update parameters
            for (int j = 0; j < parameters.length; j++) {
                parameters[j] -= vwArray[j] * rate;
            }
            if (i % 1000000 == 0){
                System.out.printf("Training round: %.0f%% \n",((double)i/trainingRounds)*100);
            }
        }

        executor.shutdown();

        System.out.printf("Final Guesses:\n");
        System.out.println(getTex(parameters));
        System.out.println("Degree : Coefficent");
        int count = parameters.length-1;
        for (float param: parameters){
            System.out.printf("%d : %.3f\n",count,param);
            count--;
        }
        System.out.printf("Final Cost: %.3f\n",cost(parameters));

    }

    private static void genExponentialTest(){
        float[][] temp = new float[trainSize][2];
        for (int i = 0; i<trainSize;i++){
            temp[i] = new float[] {i, (float) Math.exp(i)};
        }
        testData = temp;
    }
    private static void genSineTest(){
        float[][] temp = new float[trainSize][2];
        for (int i = 0; i<trainSize;i++){
            temp[i] = new float[] {i, (float) Math.sin((Math.PI/2)*i)};
        }
        testData = temp;
        System.out.println(Arrays.deepToString(testData));
    }

    private static void genPolyNomialTest(int[] params){
        float[][] temp = new float[trainSize][2];
        for (int i = 0; i<trainSize;i++){
            float val = 0;
            for (int j=0; j<params.length;j++){
                int coeff = params[params.length-1-j];
                float powerTerm = (float) Math.pow(i,j);
                val+= coeff*powerTerm;
            }
            temp[i] = new float[]{i, val};
        }

        testData = temp;
        System.out.println(Arrays.deepToString(testData));
    }
    public static float[] deepCopy(float[] toCopy){
        float[] outArr = new float[toCopy.length];
        for (int i = 0; i<toCopy.length;i++){
            outArr[i]=toCopy[i];
        }
        return outArr;
    }
    public static String getTex(float[] params){
        String out= "";
        for(int i = 0; i< params.length;i++){
            out+=String.valueOf(params[i])+"x^{"+String.valueOf(params.length-1-i)+"}"+"+";
        }

        return out.substring(0,out.length()-1);
    }

}