import java.awt.Color;

import java.awt.Graphics

class Car {


    double posX = 70;
    double posY = 50;
    double dir = 90;
    double speed;
    double dirSpeed = 10;
    int timeOutOfSight = 0;
    double thickness;
    int lineDifference = 4;
    def getPos;
    Color[] carFrame;

    public Car(double thickness, Color[] carFrame, def getPos, speed = 1) {
        this.speed = speed;
        posX += thickness / 2;
        posY += thickness / 2;
        this.thickness = thickness;
        this.carFrame = carFrame;
        this.getPos = getPos;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN)
        g.fillRect((int)this.posX - 3, (int)this.posY - 3, 7, 7);
        g.setColor(Color.red)
        g.fillRect((int) (this.posX + Math.sin(dir * Math.PI / 180) + Math.cos(dir*Math.PI / 180)*-1*lineDifference),
                (int) (this.posY- Math.cos(dir * Math.PI / 180) + Math.sin(dir*Math.PI / 180)*-1*lineDifference),
                1, 1);
        g.setColor(Color.yellow)
        g.fillRect((int) (this.posX + Math.sin(dir * Math.PI / 180) + Math.cos(dir*Math.PI / 180)*0),
                (int) (this.posY- Math.cos(dir * Math.PI / 180) + Math.sin(dir*Math.PI / 180)*0),
                1, 1);
        g.setColor(Color.blue)
        g.fillRect((int) (this.posX + Math.sin(dir * Math.PI / 180) + Math.cos(dir*Math.PI / 180)*1*lineDifference),
                (int) (this.posY- Math.cos(dir * Math.PI / 180) + Math.sin(dir*Math.PI / 180)*1*lineDifference),
                1, 1);
        g.setColor(Color.BLACK)
        g.fillRect((int)posX, (int)posY, 1, 1);
    }

    public void reset() {
        posX = 70 + thickness / 2;
        posY = 50 + thickness / 2;
        dir = 90;
        timeOutOfSight = 0;
    }


    Color getColorAt(int id) {
        // sin(-id) = y
        int posX = (int) (this.posX + Math.sin(dir * Math.PI / 180) + Math.cos(dir*Math.PI / 180)*id);
        int posY = (int) (this.posY- Math.cos(dir * Math.PI / 180) + Math.sin(dir*Math.PI / 180)*id);
        Color retval = carFrame[getPos(posX, posY)];
        return retval;
    }

    def getNextPosition(int action) {
        switch (action) {
            case 0:
                dir -= dirSpeed;
                break;
            case 2:
                dir += dirSpeed;
                break;
        }
        dir %= 360;
        if (dir < 0) {
            dir += 360;
        }
        this.posX = posX + Math.sin(dir * Math.PI / 180) * speed;
        this.posY = posY - Math.cos(dir * Math.PI / 180) * speed;
        if(this.posX > 395){
            this.posX = 390;
            dir += 180;
            dir %= 360;
        }else if(this.posX < 5){
            this.posX = 10;
            dir += 180;
            dir %= 360;
        }else if(this.posY > 395){
            this.posY = 390;
            dir += 180;
            dir %= 360;
        }else if(this.posY < 5){
            this.posY = 10;
            dir += 180;
            dir %= 360;
        }
    }

}
