package com.espod.zhang;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * Created by zhang on 2017/3/20.
 */
public class GameSound {
    BgSoundThread bst = null;
    //播放背景音乐
    @SuppressWarnings("deprecation")
    public void playBgSound(String mp3) {
        if (bst != null) {
            bst.stop();
        }

        bst = new BgSoundThread(mp3);
        bst.start();
    }

    //停止播放音乐
    public void stopPlayBgSound(){
        bst.stop();
    }
    //播放其他声音
    public void playSound(String mp3) {
        SoundThread st = new SoundThread(mp3);
        st.start();
    }

    //循环播放背景声音
    class BgSoundThread extends Thread {
        public String mp3Url;

        public BgSoundThread(String mp3Url) {
            this.mp3Url = mp3Url;
        }

        public void run() {
            while (true) {
                InputStream in = GameSound.class.getClassLoader().getResourceAsStream(mp3Url);
                try {
                    AdvancedPlayer ad = new AdvancedPlayer(in);
                    ad.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //只播放一次其他声音
    class SoundThread extends Thread {
        public String mp3Url;

        public SoundThread(String mp3Url) {
            this.mp3Url = mp3Url;
        }

        public void run() {
            InputStream in = GameSound.class.getClassLoader().getResourceAsStream(mp3Url);
            try {
                AdvancedPlayer ad = new AdvancedPlayer(in);
                ad.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

}
