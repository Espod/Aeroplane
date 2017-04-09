package com.espod.zhang;

import java.awt.Graphics;

/**
 * Created by zhang on 2017/3/20.
 */
public class Background {
    int bg_x, bg_y;
    int bg_x_next;
    int bg_width, bg_height;
    GameStart gs;

    //构造方法
    public Background(int bg_x, int bg_y, int bg_width, int bg_height, GameStart gs) {
        super();
        this.bg_x = bg_x;
        this.bg_y = bg_y;
        this.bg_width = bg_width;
        this.bg_height = bg_height;
        this.gs = gs;
        this.bg_x_next = bg_x - bg_width;
    }

    //绘制
    public void drawBackground(Graphics g) {
        g.drawImage(gs.backgroundImage, bg_x, bg_y, bg_width, bg_height, gs);
        g.drawImage(gs.backgroundImage, bg_x_next, bg_y, bg_width, bg_height, gs);
        bgMove();
    }

    //移动
    public void bgMove() {
        bg_x += 2;
        bg_x_next += 2;

        //第一张背景已经超出边界
        if (bg_x >= bg_width) {
            bg_x = bg_x_next - bg_width;
        }
        //第二张背景已经超出边界
        if (bg_x_next >= bg_width) {
            bg_x_next = bg_x - bg_width;
        }
    }
}
