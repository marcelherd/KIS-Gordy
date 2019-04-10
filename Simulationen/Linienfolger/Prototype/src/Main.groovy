import java.awt.Color

static int getPos(int x, int y) {
    return y * 400 + x;
}

static fillRect(int x, int y, int width, int height, Color[] frame, color = Color.BLACK) {
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            frame[getPos(i + x, j + y)] = color;
        }
    }
}

static Color[] buildCarFrame(double thickness, int width, int height, Color backgroundColor = Color.white) {
    Color[] frame = new Color[width * height];
    for (int i = 0; i < frame.length; i++) {
        frame[i] = backgroundColor
    }
    fillRect((int) (50), (int) (50), (int) (thickness), (int) (200 + thickness), frame);
    fillRect((int) (50), (int) (50), (int) (200 + thickness), (int) (thickness), frame);
    fillRect((int) (250), (int) (50), (int) (thickness), (int) (200 + thickness), frame);
    fillRect((int) (50), (int) (250), (int) (200 + thickness), (int) (thickness), frame);
    return frame;
}

Color[] carFrame = buildCarFrame(7, 400, 400);
def car = new Car(7, carFrame, this.&getPos)
def Window = new SimulationFrame(7, 400, 400, car);
def count = 0;
def actionController = new ActionController(0.01, 0.014, 0.0005,
        0.8, 1.2, car);
while (true) {
    if (count > 10000) {
        Window.repaint()
        Thread.sleep(3);
        Window.count = count;
    }
    if(count == 10000){
        println(actionController.Q);
    }
    count++;
    actionController.updateQT();
}