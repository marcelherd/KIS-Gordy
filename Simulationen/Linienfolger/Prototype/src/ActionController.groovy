import java.awt.Color

class ActionController {
    int maxActions;
    def alpha, discount, epsilon;
    Car car;
    public BigDecimal[][] Q;

    public ActionController(def alpha, def discount, def epsilon, def qMin, def qMax, Car car) {
        this.maxActions = 3;
        this.alpha = alpha;
        this.discount = discount;
        this.epsilon = epsilon;

        this.car = car;

        Q = new BigDecimal[2 * 2 * 2][maxActions];
        for (int i = 0; i < Q.length; i++) {
            for (int j = 0; j < maxActions; j++) {
                Q[i][j] = Math.random() * qMin + (qMax - qMin);
            }
        }
    }

    public int getState(int colorLeft, int colorMiddle, int colorRight) {
        return (((colorLeft * 2 + colorMiddle) * 2 + colorRight))
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

    public void updateQT() {

        int currentColorLeft = car.getColorAt(-1) == Color.black ? 0 : 1;
        int currentColorMiddle = car.getColorAt(0) == Color.black ? 0 : 1;
        int currentColorRight = car.getColorAt(1) == Color.black ? 0 : 1;

        int currentState = getState(currentColorLeft, currentColorMiddle, currentColorRight);
        int action = getBestAction(currentState);

        car.getNextPosition(action);

        int nextColorLeft = car.getColorAt(-1) == Color.black ? 0 : 1;
        int nextColorMiddle = car.getColorAt(0) == Color.black ? 0 : 1;
        int nextColorRight = car.getColorAt(1) == Color.black ? 0 : 1;
        int nextState = getState(nextColorLeft, nextColorMiddle, nextColorRight);

        def r = 0;
        if (nextColorLeft == 1 && nextColorMiddle == 1 && nextColorRight == 1) {
            r = -1;
        } else if (nextColorMiddle == 0) {
            r = 1;
        }
        int nextBestAction = getBestAction(nextState);
        BigDecimal newQT = Q[currentState][action] + alpha * (r + discount * Q[nextState][nextBestAction] - Q[currentState][action])
        Q[currentState][action] = newQT;


    }
}
