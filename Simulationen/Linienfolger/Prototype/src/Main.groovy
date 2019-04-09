import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage

Car car = new Car(5, 1, 1, 10)
def frame = new SimulationFrame(15, 400, 400, car);
def count = 0;
def inWait = false;
def maxCounts = 2;
ActionController ac = new ActionController(
        maxCounts, maxCounts, maxCounts,
        maxCounts, maxCounts, maxCounts,
        8, 0.01f, 0.8f, 0.001f, 0.25f,
        0.00008f, 0.00012f,
        frame.frame, frame.getPos, car);
while (true) {
    if(count > 100000){
        count = 0;
        inWait = true;
        println("Wait");
        ac.wait = true;
    }else if(inWait && count > 10000){
        inWait = false;
        count = 0;
        ac.wait = false;
    }
    if(inWait){
        frame.repaint()
        Thread.sleep(1);
        frame.count = count;
        //println("X: ${car.posX} Y: ${car.posY} DIR: ${car.dir}")
    }
    count++;
    ac.updateQT();
}