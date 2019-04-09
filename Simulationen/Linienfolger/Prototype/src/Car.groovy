import java.awt.Color;

import java.awt.Graphics
import java.awt.Point
import java.awt.image.BufferedImage

class Car {


    float posX = 70;
    float posY = 50;
    float dir = 90;
    float speed = 1;
    float dirSpeed = 90;
    float dimensionX, dimensionY;
    def thickness;
    int dif = 10;
    def getPos = {x, y ->
        return y*400+x;
    }

    public Car(thickness, float dimensionX, float dimensionY, speed = 1) {
        this.speed = speed;
        posX += thickness / 2;
        posY += thickness / 2;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.thickness = thickness;
    }

    public void draw(Graphics g, int dimensionX, int dimensionY, Color[] frame) {
        int x = posX * dimensionX
        int y = posY * dimensionY
        g.setColor(Color.GREEN)
        g.fillRect(x - 3, y - 3, 7, 7);
        g.setColor(Color.red)
        g.fillRect(x + (int) (Math.sin(dir * Math.PI / 180) * 20), y - (int) (Math.cos(dir * Math.PI / 180) * 20+1), 6, 6)
        g.setColor(Color.BLACK)
    }

    public void reset() {
        posX = 70 + thickness / 2;
        posY = 50 + thickness / 2;
        dir = 90;
    }

    public int getDistance(int x, int y) {
        return (int) Math.sqrt(Math.pow(x - posX, 2) + Math.pow(y - posY, 2))
    }

    public boolean isAtStart() {
        return (posX == 50 + thickness / 2) && (posY = 50 + thickness / 2);
    }

    int getDifferenceToLine(Color[] frame) {
        boolean foundNoDif = true;
        def minDistance = 10000;
        int x = posX;
        int y = posY;
        while (true) {
            Color col = frame[(int) getPos(x, y)]
            if (col == Color.black) {
                int dis = x - posX;
                if (dis < minDistance) {
                    minDistance = dis;
                    foundNoDif = false;
                }
                break;
            } else if (x > 390) {
                break;
            }
            x++;
        }
        x = posX;
        while (true) {
            Color col = frame[(int) getPos(x, y)]
            if (col == Color.black) {
                int dis = posX - x;
                if (dis < minDistance) {
                    minDistance = dis;
                    foundNoDif = false;
                }
                break;
            } else if (x < 0) {
                break;
            }
            x--;
        }
        x = posX;
        while (true) {
            Color col = frame[(int) getPos(x, y)]
            if (col == Color.black) {
                int dis = y - posY;
                if (dis < minDistance) {
                    minDistance = dis;
                    foundNoDif = false;
                }
                break;
            } else if (y > 390) {
                break;
            }
            y++;
        }
        y = posY;
        while (true) {
            Color col = frame[(int) getPos(x, y)]
            if (col == Color.black) {
                int dis = posY - y;
                if (dis < minDistance) {
                    minDistance = dis;
                    foundNoDif = false;
                }
                break;
            } else if (y < 0) {
                break;
            }
            y--;
        }

        return minDistance
    }

    ColorState getCurrentColorState(Color[] frame) {
        int posX = (int) posX;
        int posY = (int) posY;
        Color leftPos;
        Color rightPos;
        switch (dir) {
            case 0.0..89.9:
                leftPos = frame[getPos(posX - dif, posY)];
                rightPos = frame[getPos(posX + dif, posY)];
                break;
            case 90.0..179.9:
                leftPos = frame[getPos(posX, posY - dif)];
                rightPos = frame[getPos(posX, posY + dif)];
                break;
            case 180.0..269.9:
                leftPos = frame[getPos(posX + dif, posY)];
                rightPos = frame[getPos(posX - dif, posY)];
                break;
            case 270.0..360:
                leftPos = frame[getPos(posX, posY + dif)];
                rightPos = frame[getPos(posX, posY - dif)];
                break;
        }
        return new ColorState(leftPos.getRed(), leftPos.getGreen(), leftPos.getBlue(), rightPos.getRed(), rightPos.getGreen(), rightPos.getBlue());
    }

    def logDrive(String s) {
        //println(s);
    }

    ColorState getColorState(int action, Color[] frame, apply = true) {
        def nextDir = dir;
        def motorSpeed = 0;
        switch (action) {
            case 1:
                motorSpeed = 2;
                logDrive("forward");
                break;
            case 0:
                nextDir += dirSpeed;
                motorSpeed = 1;
                logDrive("Turn Right");
                break;
            case 2:
                nextDir += dirSpeed * 2;
                motorSpeed = 1;
                logDrive("turnHeavyRight forward");
                break;
            case 3:
                nextDir -= dirSpeed;
                motorSpeed = 1;
                logDrive("turnLeft");
                break;
            case 4:
                nextDir += dirSpeed;
                motorSpeed = -1;
                logDrive("Backwards; turn Right")
                break;
            case 5:
                nextDir += dirSpeed * 2;
                motorSpeed = 1;
                logDrive("TurnHeavyRight back")
                break;
            case 6:
                nextDir -= dirSpeed;
                motorSpeed = 1;
                logDrive("Backwards; turn Left")
                break;
            case 7:
                motorSpeed = 2;
                logDrive("Heavy Backwards")
                break;
        }
        nextDir %= 360;
        if (nextDir < 0) {
            nextDir += 360;
        }
        motorSpeed *= speed;
        int posX = posX + Math.sin(nextDir * Math.PI / 180)*motorSpeed;
        int posY = posY - Math.cos(nextDir * Math.PI / 180)*motorSpeed;
        Color leftPos;
        Color rightPos;


        switch (nextDir) {
            case 0.0..89.9:
                leftPos = frame[getPos(posX - dif, posY)];
                rightPos = frame[getPos(posX + dif, posY)];
                break;
            case 90.0..179.9:
                leftPos = frame[getPos(posX, posY - dif)];
                rightPos = frame[getPos(posX, posY + dif)];
                break;
            case 180.0..269.9:
                leftPos = frame[getPos(posX + dif, posY)];
                rightPos = frame[getPos(posX - dif, posY)];
                break;
            case 270.0..560:
                leftPos = frame[getPos(posX, posY + dif)];
                rightPos = frame[getPos(posX, posY - dif)];
                break;
            default:
                println("Something wen wrong ${nextDir}")
        }
        if (posX < 5 | posY < 5 | posY > 395 * dimensionY | posX > 395 * dimensionX) {
            nextDir += 180;
            nextDir %= 360;
            if (nextDir < 0) {
                nextDir += 360;
            }
        }
        if (posX < 5) {
            posX = 5;
        }
        if (posY < 5) {
            posY = 5;
        }
        if (posX > 395 * dimensionX) {
            posX = 395;
        }
        if (posY > 395 * dimensionY) {
            posY = 395;
        }

        if (apply) {
            this.dir = nextDir;
            this.posX = posX;
            this.posY = posY;
        }
        return new ColorState(leftPos.getRed(), leftPos.getGreen(), leftPos.getBlue(), rightPos.getRed(), rightPos.getGreen(), rightPos.getBlue());

    }

    void step(Color[] frame) {
        Color leftPos;
        Color rightPos;
        def posX = (int) posX
        def posY = (int) posY

        switch (dir) {
            case 0.0..89.9:
                leftPos = frame[getPos(posX - dif, posY)];
                rightPos = frame[getPos(posX + dif, posY)];
                break;
            case 90.0..179.9:
                leftPos = frame[getPos(posX, posY - dif)];
                rightPos = frame[getPos(posX, posY + dif)];
                break;
            case 180.0..269.9:
                leftPos = frame[getPos(posX + dif, posY)];
                rightPos = frame[getPos(posX - dif, posY)];
                break;
            case 270.0..360:
                leftPos = frame[getPos(posX, posY + dif)];
                rightPos = frame[getPos(posX, posY - dif)];
                break;
        }

        println("LEFT: ${leftPos == Color.BLACK} RIGH: ${rightPos == Color.BLACK}")

        def left = -1;
        def right = -1;
        if (leftPos == Color.BLACK) {
            if (rightPos != Color.BLACK) {
                dir += dirSpeed
            }

        } else {
            if (rightPos == Color.BLACK) {
                dir -= dirSpeed
            } else {
                dir += 2 * dirSpeed
            }
        }

        dir %= 360;
        if (dir < 0) {
            dir += 360;
        }
        this.posX += Math.sin(dir * Math.PI / 180) * speed
        this.posY -= Math.cos(dir * Math.PI / 180) * speed


    }
}
