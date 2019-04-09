import java.awt.Color

class ActionController {

    int redLeftMax, greenLeftMax, blueLeftMax;
    int redRightMax, greenRightMax, blueRightMax;
    int maxActions;
    def alpha, discount, epsilon, minEpsilon, maxEpsilon;
    def qMin, qMax;
    int stateCount;
    Color[] frame;
    def getPos;
    Car car;
    public BigDecimal[][] Q;
    boolean wait = false;
    int gotHalf = 0
    int gotSuc = 0

    int posXBefore = 0;
    int posYBefore = 0;
    int steps = 1;

    boolean canSuccess = false;

    public ActionController(int redLeftMax, int greenLeftMax, int blueLeftMax, int redRightMax, int greenRightMax, int blueRightMax, int maxActions, def alpha, def discount, def minEpsilon, def maxEpsilon, def qMin, def qMax, Color[]frame, getPos, Car car) {
        this.redLeftMax = redLeftMax;
        this.greenLeftMax = greenLeftMax;
        this.blueLeftMax = blueLeftMax;
        this.redRightMax = redRightMax;
        this.greenRightMax = greenRightMax;
        this.blueRightMax = blueRightMax;
        this.maxActions = maxActions;
        this.alpha = alpha;
        this.discount = discount;
        this.epsilon = maxEpsilon;
        this.minEpsilon = minEpsilon;
        this.maxEpsilon = maxEpsilon
        this.qMin = qMin;
        this.qMax = qMax;

        this.frame = frame;
        this.getPos = getPos;
        this.car = car;

        stateCount = redLeftMax * greenLeftMax * blueLeftMax * redRightMax * greenRightMax * blueRightMax * 18;
        Q = new BigDecimal[stateCount][maxActions];
        for (int i = 0; i < Q.length; i++) {
            for (int j = 0; j < maxActions; j++) {
                Q[i][j] = Math.random() * qMin + (qMax - qMin);
            }
        }
    }

    public int getState(int redLeft, int greenLeft, int blueLeft, int redRight, int greenRight, int blueRight, int dir) {
        //println("rl: ${redLeft} gl: ${greenLeft} bl: ${blueLeft} rr: ${redRight}, gr: ${greenRight} br: ${blueRight}")
        return (((((redLeft * greenLeftMax + greenLeft) * blueLeftMax + blueLeft) * redRightMax + redRight) * greenRightMax + greenRight) * blueRightMax + blueRight) * 18 + dir;
    }

    public int getBestAction(int state) {
        if (Math.random() < epsilon) {
            return (int) (Math.random() * maxActions);
        } else {
            int bestAction = 0;
            def bestValue = -1000000;
            for (int i = 0; i < maxActions; i++) {
                if (Q[state][i] > bestValue) {
                    bestAction = i;
                    bestValue = Q[state][i];
                }
            }
            return bestAction;
        }
    }

    public void updateQT(){

        ColorState currentColorState = car.getCurrentColorState(frame)*(1.0/redLeftMax);
        int currentState = getState(
                currentColorState.redLeft, currentColorState.greenLeft, currentColorState.blueLeft,
                currentColorState.redRight, currentColorState.greenRight, currentColorState.blueRight,
                (int)(car.dir/20)
        );
        int action = getBestAction(currentState);

        ColorState colorsOfNextState = car.getColorState(action, frame)*(1.0/redLeftMax);
        int nextState = getState(
                colorsOfNextState.redLeft, colorsOfNextState.greenLeft, colorsOfNextState.blueLeft,
                colorsOfNextState.redRight, colorsOfNextState.greenRight, colorsOfNextState.blueRight,
                (int)(car.dir/20)
        );
        def r = 0;
        if(!canSuccess && car.posX > 220 && car.posY > 220){
            println("Halftime " + car.posX + " " + car.posY + " " + gotHalf + " " + gotSuc);
            canSuccess = true;
            r = 200;
            gotHalf++
            if(gotSuc < 200){
                epsilon = maxEpsilon
            }
        }
        if(car.getDifferenceToLine(frame) > 30){
            if(wait){
               // println(car.getDifferenceToLine(frame, getPos) + " " + car.posX + " " + car.posY)
               // Thread.sleep(500);
            }
            r = -0.00000001
            car.reset();
            canSuccess = false;
            if(gotHalf > 1){
                epsilon = minEpsilon
            }

        }else if(car.isAtStart() && canSuccess){
            println("Sucess " + gotHalf + " " + gotSuc);
            canSuccess = false;
            epsilon = minEpsilon
            r = 1000
            gotSuc++;
        }
        steps++;
        if(steps % 3 == 0){
            steps = 1;

            if(car.posX == posXBefore && car.posY == posYBefore){
                r = -0.1;
            }

            posXBefore = car.posX;
            posYBefore = car.posY;

        }




        int nextBestAction = getBestAction(nextState);
        BigDecimal newQT = Q[currentState][action] + alpha * (r+discount * Q[nextState][nextBestAction] - Q[currentState][action])
        Q[currentState][action] = newQT;



    }
}
