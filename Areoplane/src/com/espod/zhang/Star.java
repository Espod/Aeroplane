package com.espod.zhang;

import java.awt.*;

/**
 * Created by zhang on 2017/3/25.
 */
public class Star {
    int x, y;
    int width, height;
    int speed = 2;
    boolean isLive = true;
    long lifeTime;
    GameStart gs;

    //构造方法
    public Star(int x, int y, int width, int height, GameStart gs) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gs = gs;
        lifeTime = System.currentTimeMillis();
    }

    //绘制
    public void drawStar(Graphics g) {
        if (isLive) {
            g.drawImage(gs.starImage, x, y, width, height, gs);
            starMove();
        } else {
            gs.starsList.remove(this);
        }
    }

    //移动
    public void starMove() {
        x -= speed;
        if (x < 0 - width) {
            this.isLive = false;
        }
    }

    //得到Star的Size
    public Rectangle getStarSize() {
        return new Rectangle(x, y, width, height);
    }

    //与MyPlane的碰撞检测
    public void collideMyPlane() {
        if (this.isLive && gs.mp.isLive && this.getStarSize().intersects(gs.mp.getMyPlaneSize())) {
            gs.score += 100;
            this.isLive = false;
            gs.gSound.playSound("./music/GetItem.mp3");
        }
    }

}
