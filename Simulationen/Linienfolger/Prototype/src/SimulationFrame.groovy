import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.Graphics

class SimulationFrame extends JFrame{
    def count = 0;

    public SimulationFrame(double thickness, int width, int height, Car car){
        this.title = "Test"

        this.add(new JPanel(){
            @Override
            public void paintComponent(Graphics g){

                super.paintComponents(g);
                g.fillRect((int)(50), (int)(50), (int)(thickness), (int)(200+thickness));
                g.fillRect((int)(50), (int)(50), (int)(200+thickness), (int)(thickness));
                g.fillRect((int)(250), (int)(50), (int)(thickness), (int)(200+thickness));
                g.fillRect((int)(50), (int)(250), (int)(200+thickness), (int)(thickness));
                g.drawString(count + "", 20, 20);
                car.draw(g)
            }
        })
        setDefaultCloseOperation(EXIT_ON_CLOSE)
        setSize(width, height)
        setVisible(true);
    }



}

