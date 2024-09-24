package gates;

public class AND {
    public float w1;
    public float w2;
    public float w3;
    public static float cost;
    private static int[][] and_train = {
            {0,0,0},
            {0,1,0},
            {1,0,0},
            {1,1,1},
    };
    public static float sigmoid(float x) {
        return (float)(1 / (1 + Math.exp(-x)));
    }

    private float cost(float a, float b, float c){
        // takes our 3 parameters, and determines the quality of the guess. A bigger number means a worse guess.
        // Our goal is to minimize cost
        float result = 0f;
        for (int i = 0; i< and_train.length;i++){
            int x1 = and_train[i][0];
            int x2 = and_train[i][1];
            float y = sigmoid(a*x1+b*x2+c);
            float d = y - and_train[i][2];
            float cost = d*d;
            result += cost;
            //System.out.printf("Actual: %.3f, Expected: %d\n",y,testData[i][1]);
        }
        result /= and_train.length;
        this.cost = result;
        return result;
    }

    public AND(){
        // gen initial guesses for the machine learning function parameters
        w1 = randFloat();
        w2 = randFloat();
        w3 = randFloat();
        int trainingRounds = 1500000; // the amount of rounds to train for
        float eps = 1e-5f; // a very small number!
        float rate = 1e-3f; // the learning rate, where a bigger number is a bigger change to the guess

        //System.out.printf("Initial Guess:\n");
        //System.out.printf("Initial Guess: %.3f\n",w1);
        //System.out.printf("Initial Guess: %.3f\n",w2);
        //System.out.printf("Initial Guess: %.3f\n",w3);

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
    }
    public int exec(float a, float b){
        return sigmoid(w1*a+w2*b+w3)< 0.5 ? 0 : 1;
    }

    private static float randFloat(){
        return (float) Math.random();
    }
}
