package com.espod.zhang;

import java.awt.*;
import java.util.Random;

/**
 * Created by zhang on 2017/3/22.
 */
public class Planet {
    int x, y;
    int width, height;
    int speed = 2;
    boolean isLive = true;
    GameStart gs;
    Random rd = new Random();
    int index = rd.nextInt(5);

    //构造方法
    public Planet(int x, int y, int width, int height, GameStart gs) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gs = gs;
    }

    //绘制
    public void drawPlanet(Graphics g) {
        collideStar();
        if (isLive) {
            g.drawImage(gs.planetsImage.get(index), x, y, width, height, gs);
            planetMove();
        } else {
            gs.planetsList.remove(this);
        }
    }

    //移动
    public void planetMove() {
        x -= speed;
        if (x < 0 - width) {
            isLive = false;
        }
    }

    //得到Planet的Size
    public Rectangle getPlanetSize() {
        return new Rectangle(x, y, width, height);
    }

    //与MyPlane进行碰撞检测
    public void collideMyPlane() {
        if (this.isLive && gs.mp.isLive && this.getPlanetSize().intersects(gs.mp.getMyPlaneSize())) {
            gs.mp.isLive = false;
            gs.gSound.stopPlayBgSound();
            gs.gFlag++;
            gs.gSound.playSound("./music/Gameover.mp3");
        }
    }

    //与Star进行碰撞检测
    public void collideStar() {
        for (int i = 0; i < gs.starsList.size(); i++) {
            Star s = gs.starsList.get(i);
            if (this.isLive && s.isLive && this.getPlanetSize().intersects(s.getStarSize())) {
                s.isLive = false;
                this.isLive = false;
            }
        }
    }
}
