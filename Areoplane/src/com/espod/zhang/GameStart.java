package com.espod.zhang;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by zhang on 2017/3/20.
 */
@SuppressWarnings("serial")
public class GameStart extends Frame {
    public static final int GameWidth = 560;
    public static final int GameHeight = 400;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Background bg = new Background(0, 0, GameWidth, GameHeight, this);
    MyPlane mp = new MyPlane(100, 50, 0, 150, this);
    Image bufferImage = null;
    GameSound gSound = new GameSound();

    List<Image> planetsImage = new ArrayList<Image>();
    List<Planet> planetsList = new ArrayList<Planet>();
    List<Star> starsList = new ArrayList<Star>();

    Long planetCreateTime = System.currentTimeMillis();
    Long starCreateTime = System.currentTimeMillis();

    int score = 0;
    int gFlag = 0;


    //图片资源加载
    //region
    Image backgroundImage = tk.getImage(GameStart.class.getResource("/images/Background.png"));
    Image planeImage = tk.getImage(GameStart.class.getResource("/images/Plane.png"));
    Image starImage = tk.getImage(GameStart.class.getResource("/images/Star.png"));
    Image gameStartImage = tk.getImage(GameStart.class.getResource("/images/GameStart.png"));
    Image gameStartTipsImage = tk.getImage(GameStart.class.getResource("/images/GameStart_Tips.png"));
    Image gameOverImage = tk.getImage(GameStart.class.getResource("/images/GameOver.png"));
    Image gameOverTipsImage = tk.getImage(GameStart.class.getResource("/images/GameOver_Tips.png"));
    Image planet_01 = tk.getImage(GameStart.class.getResource("/images/planet/Planet01.png"));
    Image planet_02 = tk.getImage(GameStart.class.getResource("/images/planet/Planet02.png"));
    Image planet_03 = tk.getImage(GameStart.class.getResource("/images/planet/Planet03.png"));
    Image planet_04 = tk.getImage(GameStart.class.getResource("/images/planet/Planet04.png"));
    Image planet_05 = tk.getImage(GameStart.class.getResource("/images/planet/Planet04.png"));
    //endregion

    //将Planet的照片加入planetsImage中
    {
        planetsImage.add(planet_01);
        planetsImage.add(planet_02);
        planetsImage.add(planet_03);
        planetsImage.add(planet_04);
        planetsImage.add(planet_05);
    }

    //产生Planet
    public void createPlanet() {
        Random rd = new Random(System.currentTimeMillis());
        int y = rd.nextInt(GameHeight - 25 - 120);
        planetsList.add(new Planet(GameWidth + 130, y + 25, 120, 120, this));
    }

    //产生Star
    public void createStar() {
        Random rd = new Random(System.currentTimeMillis());
        int y = rd.nextInt(GameHeight - 25 - 80);
        starsList.add(new Star(GameWidth + 130, y + 25, 80, 80, this));
    }

    //构造函数
    public GameStart() {
        this.setSize(GameWidth, GameHeight);
        this.setTitle("Aeroplane");
        this.setResizable(false);
        this.setLocationRelativeTo(null);  //居中
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                mp.keyPressed(e);
                int keyCode = e.getKeyCode();
                if (gFlag == 0 && keyCode == KeyEvent.VK_ENTER) {
                    gFlag++;
                } else if (gFlag == 2 && keyCode == KeyEvent.VK_ESCAPE) {
                    gFlag = 0;
                    mp.isLive = true;
                    planetsList.removeAll(planetsList);
                    starsList.removeAll(starsList);
                    score = 0;
                    mp.x = 100;
                    mp.y = 50;
                    gSound.playBgSound("./music/background_music.mp3");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                mp.keyReleased(e);
            }

        });
        new Thread(new PaintThread()).start();
        gSound.playBgSound("./music/background_music.mp3");
    }

    //绘图
    public void paint(Graphics g) {
        bg.drawBackground(g);
        if (gFlag == 0) {
            g.drawImage(gameStartImage, GameWidth / 2 - 130, GameHeight / 2 - 130 - 50, 260, 260, this);
            g.drawImage(gameStartTipsImage, GameWidth / 2 - 265, GameHeight / 2 - 58 + 150, 530, 116, this);
        } else if (gFlag == 1) {
            mp.drawMyPlane(g);
            //绘制分数
            g.setColor(Color.white);
            g.setFont(new Font("黑体", Font.PLAIN, 24));
            g.drawString("分数：" + score, 5, 60);

            for (int i = 0; i < planetsList.size(); i++) {
                Planet p = planetsList.get(i);
                p.drawPlanet(g);
                p.collideMyPlane();
            }

            for (int i = 0; i < starsList.size(); i++) {
                Star s = starsList.get(i);
                s.drawStar(g);
                s.collideMyPlane();
            }
        } else if (gFlag == 2) {
            g.drawImage(gameOverImage, GameWidth / 2 - 130, GameHeight / 2 - 130 - 100, 260, 260, this);
            g.drawImage(gameOverTipsImage, GameWidth / 2 - 265, GameHeight / 2 - 58 + 150, 530, 116, this);
        }
    }

    //双缓冲机制
    public void update(Graphics g) {
        if (bufferImage == null) {
            bufferImage = createImage(GameWidth, GameHeight);
            Graphics gp = bufferImage.getGraphics();
            paint(gp);
            g.drawImage(bufferImage, 0, 0, this);
            bufferImage = null;
        }
    }

    //主函数
    public static void main(String[] args) {
        GameStart gs = new GameStart();
    }

    //绘图线程类
    class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (gFlag == 1 && System.currentTimeMillis() - planetCreateTime >= 2000) {
                    createPlanet();
                    planetCreateTime = System.currentTimeMillis();
                } else if (gFlag == 1 && System.currentTimeMillis() - starCreateTime >= 1000) {
                    createStar();
                    starCreateTime = System.currentTimeMillis();
                }

                repaint();

                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
