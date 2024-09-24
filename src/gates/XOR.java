package gates;

public class XOR {

    private static int[][] xor_train = {
            {0,0,0},
            {0,1,1},
            {1,0,1},
            {1,1,0},
    };
    public float w1 = randFloat();
    public float w2= randFloat();
    public float w3= randFloat();
    public float w4= randFloat();
    public float w5= randFloat();
    public float w6= randFloat();
    public float w7= randFloat();
    public float w8= randFloat();
    public float w9= randFloat();
    private float v_w1, v_w2, v_w3, v_w4, v_w5, v_w6, v_w7, v_w8, v_w9;
    private float beta = 0.9f;
    public float cost;
    public static float sigmoid(float x) {
        return (float)(1 / (1 + Math.exp(-x)));
    }

    private float cost(float a, float b, float c, float d, float e, float f, float g, float h, float j){
        // takes our 3 parameters, and determines the quality of the guess. A bigger number means a worse guess.
        // Our goal is to minimize cost
        float result = 0f;
        for (int i = 0; i< xor_train.length;i++){
            int x1 = xor_train[i][0];
            int x2 = xor_train[i][1];
            float y = forward(x1,x2,a,b,c,d,e,f,g,h,j);
            float diff = y - xor_train[i][2];
            float cost = diff*diff;
            result += cost;
            //System.out.printf("Actual: %.3f, Expected: %d\n",y,testData[i][1]);
        }
        result /= xor_train.length;
        this.cost = result;
        return result;
    }


    public XOR(){
            //initializeWeights();
            int trainingRounds = 500000; // the amount of rounds to train for
            float eps = 1e-5f; // a very small number!
            float rate = 1e-2f; // the learning rate, where a bigger number is a bigger change to the guess

            //System.out.printf("Initial Guess:\n");
            //System.out.printf("Initial Guess: %.3f\n",w1);
            //System.out.printf("Initial Guess: %.3f\n",w2);
            //System.out.printf("Initial Guess: %.3f\n",w3);

            float curCost = cost(w1, w2, w3, w4, w5, w6, w7, w8, w9);
            for ( int i = 0; i<trainingRounds;i++){
                // apply finite difference to the cost function (https://en.wikipedia.org/wiki/Finite_difference)
                curCost = cost(w1, w2, w3, w4, w5, w6, w7, w8, w9);
                float dw1 = (cost(w1 + eps, w2, w3, w4, w5, w6, w7, w8, w9) - curCost) / eps;
                float dw2 = (cost(w1, w2 + eps, w3, w4, w5, w6, w7, w8, w9) - curCost) / eps;
                float dw3 = (cost(w1, w2, w3 + eps, w4, w5, w6, w7, w8, w9) - curCost) / eps;
                float dw4 = (cost(w1, w2, w3, w4 + eps, w5, w6, w7, w8, w9) - curCost) / eps;
                float dw5 = (cost(w1, w2, w3, w4, w5 + eps, w6, w7, w8, w9) - curCost) / eps;
                float dw6 = (cost(w1, w2, w3, w4, w5, w6 + eps, w7, w8, w9) - curCost) / eps;
                float dw7 = (cost(w1, w2, w3, w4, w5, w6, w7 + eps, w8, w9) - curCost) / eps;
                float dw8 = (cost(w1, w2, w3, w4, w5, w6, w7, w8 + eps, w9) - curCost) / eps;
                float dw9 = (cost(w1, w2, w3, w4, w5, w6, w7, w8, w9 + eps) - curCost) / eps;

                // if the dw is negative, the change we made gave us a better result, so we nudge the parameter in that direction
                v_w1 = beta * v_w1 + (1 - beta) * dw1;
                v_w2 = beta * v_w2 + (1 - beta) * dw2;
                v_w3 = beta * v_w3 + (1 - beta) * dw3;
                v_w4 = beta * v_w4 + (1 - beta) * dw4;
                v_w5 = beta * v_w5 + (1 - beta) * dw5;
                v_w6 = beta * v_w6 + (1 - beta) * dw6;
                v_w7 = beta * v_w7 + (1 - beta) * dw7;
                v_w8 = beta * v_w8 + (1 - beta) * dw8;
                v_w9 = beta * v_w9 + (1 - beta) * dw9;

                w1 -= rate * v_w1;
                w2 -= rate * v_w2;
                w3 -= rate * v_w3;
                w4 -= rate * v_w4;
                w5 -= rate * v_w5;
                w6 -= rate * v_w6;
                w7 -= rate * v_w7;
                w8 -= rate * v_w8;
                w9 -= rate * v_w9;
            }
    }
    private static float randFloat(){
        return (float) Math.random();
    }
    private float forward(float x1, float x2,float a,float b,float c,float d,float e,float f,float g,float h,float j){
        float or = sigmoid(x1*a+x2*b+c);
        float and = sigmoid(x1*d+x2*e+f);
        return sigmoid(or*g+and*h+j);
    }
    public int exec(float a, float b){
        float or = sigmoid(a*w1+b*w2+w3);
        float and = sigmoid(a*w4+b*w5+w6);
        return sigmoid(or*w7+and*w8+w9) < 0.5 ? 0 : 1;
    }

}
