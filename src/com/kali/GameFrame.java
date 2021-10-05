package com.kali;

import javax.swing.*;

public class GameFrame extends JFrame {
    public static final int GAME_WIDTH = 750;
    public static final int GAME_HEIGHT = 750;

    public GameFrame(){
        setBounds(600,200,GAME_WIDTH,GAME_HEIGHT);
        setTitle("Snake");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
        add(panel);
        //最好在窗口设置好之后显示
        setVisible(true);
    }
}
