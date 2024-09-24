public class quadratic {
    private static int[][] testData;

    private static float cost(float w1, float w2, float w3){
        // takes our 3 parameters, and determines the quality of the guess. A bigger number means a worse guess.
        // Our goal is to minimize cost
        float result = 0f;
        for (int i = 0; i< testData.length;i++){
            int x = testData[i][0];
            float y = (w1*x*x)+(w2*x)+w3;
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
        float w1 = randFloat()*10;
        float w2 = randFloat()*10;
        float w3 = randFloat()*10;

        // the parameters we are giving the training data, and what we want the model to solve for
        int a = 1;
        int b = 15;
        int c = -1;
        genParabolaTest(a,b,c); // generates the training data, based on f(x)=ax^2+bx+c, as a 2d array, where data[i] = f(i)

        System.out.printf("A: %d\nB: %d\nC: %d\n",a,b,c);
        int trainingRounds = 150000; // the amount of rounds to train for
        float eps = 1e-5f; // a very small number!
        float rate = 1e-3f; // the learning rate, where a bigger number is a bigger change to the guess

        System.out.printf("Initial Guess:\n");
        System.out.printf("Initial Guess: %.3f\n",w1);
        System.out.printf("Initial Guess: %.3f\n",w2);
        System.out.printf("Initial Guess: %.3f\n",w3);

        for (int i = 0; i<trainingRounds;i++){
            // apply finite difference to the cost function (https://en.wikipedia.org/wiki/Finite_difference)

            float dw1= (cost(w1+eps,w2,w3) - cost(w1,w2,w3))/eps;
            float dw2= (cost(w1,w2+eps,w3) - cost(w1,w2,w3))/eps;
            float dw3= (cost(w1,w2,w3+eps) - cost(w1,w2,w3))/eps;

            // if the dw is negative, the change we made gave us a better result, so we nudge the parameter in that direction
            w1-=dw1*rate;
            w2-=dw2*rate;
            w3-=dw3*rate;
        }
        System.out.printf("Final Guess:\n");
        System.out.printf("A: %.2f\n",w1);
        System.out.printf("B: %.2f\n",w2);
        System.out.printf("C: %.2f\n",w3);
    }
    private static void genParabolaTest(int a, int b, int c){
        int[][] temp = new int[5][2];
        for (int i = 0; i<5;i++){
            temp[i][0] = i;
            temp[i][1]= (a*i*i)+(b*i)+c;
        }
        testData = temp;
        //System.out.println(Arrays.deepToString(testData));
    }
    private static void genExponentialTest(){
        int[][] temp = new int[10][2];
        for (int i = 0; i<10;i++){
            temp[i][0] = i;
            temp[i][1]= (int) Math.exp(i);
        }
        testData = temp;
        //System.out.println(Arrays.deepToString(testData));
    }
}