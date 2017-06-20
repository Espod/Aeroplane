package com.espod.zhang;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by zhang on 2017/3/22.
 */
public class MyPlane {
    int width, height;
    int x, y;
    int speed = 4;
    boolean isLive = true;
    boolean isUp, isRight, isDown, isLeft;
    Direction dir = Direction.STOP;
    GameStart gs;

    //构造方法
    public MyPlane(int width, int height, int x, int y, GameStart gs) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.gs = gs;
    }

    //绘制
    public void drawMyPlane(Graphics g) {
        if (isLive) {
            g.drawImage(gs.planeImage, x, y, width, height, gs);
            myPlaneMove();
        }
    }

    //移动
    public void myPlaneMove() {
        getDirection();
        if (dir == Direction.UP) {
            y -= speed;
        } else if (dir == Direction.DOWN) {
            y += speed;
        } else if (dir == Direction.LEFT) {
            x -= speed;
        } else if (dir == Direction.RIGHT) {
            x += speed;
        } else if (dir == Direction.Left_UP) {
            x -= speed;
            y -= speed;
        } else if (dir == Direction.Left_Down) {
            x -= speed;
            y += speed;
        } else if (dir == Direction.Right_UP) {
            x += speed;
            y -= speed;
        } else if (dir == Direction.Right_Down) {
            x += speed;
            y += speed;
        }

        if (y >= gs.GameHeight - height) {
            y = gs.GameHeight - height;
        }
        if (x >= gs.GameWidth - width) {
            x = gs.GameWidth - width;
        }
        if (y <= 25) {
            y = 25;
        }
        if (x <= 0) {
            x = 0;
        }
    }

    //得到MyPlane的Size
    public Rectangle getMyPlaneSize() {
        return new Rectangle(x, y, width, height);
    }

    //按键
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                isUp = true;
                break;
            case KeyEvent.VK_RIGHT:
                isRight = true;
                break;
            case KeyEvent.VK_DOWN:
                isDown = true;
                break;
            case KeyEvent.VK_LEFT:
                isLeft = true;
                break;
        }
    }

    //抬键
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                isUp = false;
                break;
            case KeyEvent.VK_RIGHT:
                isRight = false;
                break;
            case KeyEvent.VK_DOWN:
                isDown = false;
                break;
            case KeyEvent.VK_LEFT:
                isLeft = false;
                break;
        }
    }

    //得到MyPlane的方向
    public void getDirection() {
        if (isUp && !isDown && !isLeft && !isRight) {
            dir = Direction.UP;
        } else if (!isUp && isDown && !isLeft && !isRight) {
            dir = Direction.DOWN;
        } else if (!isUp && !isDown && isLeft && !isRight) {
            dir = Direction.LEFT;
        } else if (!isUp && !isDown && !isLeft && isRight) {
            dir = Direction.RIGHT;
        } else if (isUp && !isDown && isLeft && !isRight) {
            dir = Direction.Left_UP;
        } else if (!isUp && isDown && isLeft && !isRight) {
            dir = Direction.Left_Down;
        } else if (isUp && !isDown && !isLeft && isRight) {
            dir = Direction.Right_UP;
        } else if (!isUp && isDown && !isLeft && isRight) {
            dir = Direction.Right_Down;
        } else if (!isUp && !isDown && !isLeft && !isRight) {
            dir = Direction.STOP;
        }
    }

}
