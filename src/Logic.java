public class Logic {
    private static int[][] or_train = {
            {0,0,0},
            {0,1,1},
            {1,0,1},
            {1,1,1},
    };
    private static int[][] and_train = {
            {0,0,0},
            {0,1,0},
            {1,0,1},
            {1,1,1},
    };
    static private float or_w1;
    static private float or_w2;
    static private float or_w3;

    private static float cost(float a, float b, float c, int[][] trainingSet){
        // takes our 3 parameters, and determines the quality of the guess. A bigger number means a worse guess.
        // Our goal is to minimize cost
        float result = 0f;
        for (int i = 0; i< trainingSet.length;i++){
            int x1 = trainingSet[i][0];
            int x2 = trainingSet[i][1];
            float y = sigmoid(a*x1+b*x2+c);
            float d = y - trainingSet[i][2];
            float cost = d*d;
            result += cost;
            //System.out.printf("Actual: %.3f, Expected: %d\n",y,testData[i][1]);
        }
        result /= trainingSet.length;
        return result;
    }
    private static float randFloat(){
        return (float) Math.random();
    }
    public static float sigmoid(float x) {
        return (float)(1 / (1 + Math.exp(-x)));
    }
    public static float or(int a,int b){
        return sigmoid(or_w1*a+or_w2*b+or_w3);
    }


    public static void main(String[] args) {
        // gen initial guesses for the machine learning function parameters
        or_w1 = randFloat();
        or_w2 = randFloat();
        or_w3 = randFloat();
        int trainingRounds = 1500000; // the amount of rounds to train for
        float eps = 1e-5f; // a very small number!
        float rate = 1e-3f; // the learning rate, where a bigger number is a bigger change to the guess

        //System.out.printf("Initial Guess:\n");
        //System.out.printf("Initial Guess: %.3f\n",or_w1);
        //System.out.printf("Initial Guess: %.3f\n",or_w2);
        //System.out.printf("Initial Guess: %.3f\n",or_w3);

        for (int i = 0; i<trainingRounds;i++){
            // apply finite difference to the cost function (https://en.wikipedia.org/wiki/Finite_difference)

            float or_dw1= (cost(or_w1+eps,or_w2,or_w3,or_train) - cost(or_w1,or_w2,or_w3,or_train))/eps;
            float or_dw2= (cost(or_w1,or_w2+eps,or_w3,or_train) - cost(or_w1,or_w2,or_w3,or_train))/eps;
            float or_dw3= (cost(or_w1,or_w2,or_w3+eps,or_train) - cost(or_w1,or_w2,or_w3,or_train))/eps;



            // if the dw is negative, the change we made gave us a better result, so we nudge the parameter in that direction
            or_w1-=or_dw1*rate;
            or_w2-=or_dw2*rate;
            or_w3-=or_dw3*rate;
        }
        System.out.printf("Or:\n");
        System.out.printf("Final Cost: %.5f\n---------\n",cost(or_w1,or_w2,or_w3,or_train));
        for (int i=0;i<=1;i++){
            for (int j=0;j<=1;j++){
                System.out.printf("%d | %d = %.0f\n",i,j,or(i,j));
            }
        }
    }
}
