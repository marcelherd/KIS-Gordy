import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.Color
import java.awt.Graphics

class SimulationFrame extends JFrame{

    def thickness;
    def dimensionX;
    def dimensionY;
    def car
    def count = 0;

    Color[] frame;

    def getPos = {x, y ->
        return y*400+x;
    }

    def fillRect(x, y, width, height, color = Color.BLACK){
        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                frame[getPos(i+x, j+y)] = color;
            }
        }
    }

    public SimulationFrame(thickness, width, height, car){
        this.thickness = thickness
        this.dimensionX = width/400
        this.dimensionY = height/400
        this.car = car
        this.title = "Test"
        frame = new Color[width*height];
        for(int i = 0; i<frame.length; i++){
            frame[i] = Color.WHITE;
        }

        fillRect((int)(dimensionX*50), (int)(dimensionY*50), (int)(thickness), (int)(dimensionY*200+thickness));
        fillRect((int)(dimensionX*50), (int)(dimensionY*50), (int)(dimensionX*200+thickness), (int)(thickness));
        fillRect((int)(dimensionX*250), (int)(dimensionY*50), (int)(thickness), (int)(dimensionY*200+thickness));
        fillRect((int)(dimensionX*50), (int)(dimensionY*250), (int)(dimensionX*200+thickness), (int)(thickness));


        this.add(new JPanel(){
            @Override
            public void paintComponent(Graphics g){

                super.paintComponents(g);
                g.fillRect(225, 225, 10, 10);
                g.fillRect((int)(dimensionX*50), (int)(dimensionY*50), (int)(thickness), (int)(dimensionY*200+thickness));
                g.fillRect((int)(dimensionX*50), (int)(dimensionY*50), (int)(dimensionX*200+thickness), (int)(thickness));
                g.fillRect((int)(dimensionX*250), (int)(dimensionY*50), (int)(thickness), (int)(dimensionY*200+thickness));
                g.fillRect((int)(dimensionX*50), (int)(dimensionY*250), (int)(dimensionX*200+thickness), (int)(thickness));
                g.drawString(count + "", 20, 20);
                car.draw(g, dimensionX as int, dimensionY as int, frame)
            }
        })
        setDefaultCloseOperation(EXIT_ON_CLOSE)
        setSize(width, height)
        setVisible(true);
    }



}

