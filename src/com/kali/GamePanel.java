package com.kali;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * 1.设置🐍运动的方向
 * 2.点击空格开始游戏
 * 3.监听键盘事件
 *      空格运行游戏，显示隐藏文字
 *      上下左右控制
 *      移动逻辑
 *      随机坐标，倍数生成
 * 4.碰撞检测
 *
 * z:重生，碰撞🐍身体死亡
 *
 * 简介：
 *      1.监听键盘的事件做对应的行为控制
 *      1.用鼠标控制蛇头的方向去吃食物，蛇头和食物进行碰撞检测
 *      2.蛇和蛇节进行碰撞检测
 *      3.空格键用于开始游戏、暂停游戏，当蛇存活时空格代表暂停
 *  当蛇死亡时，空格代表重开游戏
 *      4.蛇的每一节身体的X,Y坐标分别存储在一个1250的数组中，蛇身的
 *  移动是由后向前推，将后一个移动到前一个，蛇头即0点更新坐标
 *      5.在对蛇做边界处理时，移动到左右边，蛇头X坐标归为0，其他方向思路一样
 *  但期间发生了诡异的事情：移动到最右或者最下均会触发蛇自身的碰撞问题，最后
 *  终于解决了，问题在于，蛇运动时的步长可能在达到边界时，就立即将蛇头移动方向
 *  的交叉轴坐标置为0，导致后一节身体稍微超过了蛇头，所以会触发碰撞。
 *      6.未解决的问题，在游戏界面左上角，诡异的会绘制出一节蛇身，不知道怎么解决
 *  我猜测是图片绘制时的缓冲问题。
 */
public class GamePanel extends JPanel {

    private static int foodX;
    private static int foodY;
    private static int score;
    private static int length;

    private static boolean living = true;
    private static boolean start = false;
   /* private static int snakeX = 180;
    private static int snakeY = 275;*/

    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private static final int[] SNAKE_X = new int[1250];
    private static final int[] SNAKE_Y = new int[1250];

    private static Direction dir;
    private static Rectangle rectH;
    private static Rectangle rectF;

    public GamePanel(){
        init();
        setBounds(600,200,GameFrame.GAME_WIDTH,GameFrame.GAME_HEIGHT);
        setFocusable(true);//定位焦点在面板上
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP     : dir = Direction.UP;break;
                    case KeyEvent.VK_DOWN   : dir = Direction.DOWN;break;
                    case KeyEvent.VK_LEFT   : dir = Direction.LEFT;break;
                    case KeyEvent.VK_RIGHT  : dir = Direction.RIGHT;break;
                    case KeyEvent.VK_SPACE  :
                        if(!living){
                            init();
                            living = true;
                        }else{
                            start = !start;
                        }
                        break;
                    default:break;
                }
                //repaint();
            }
        });

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (start && living) {
                    for (int i = length - 1; i > 0; i--) {
                        SNAKE_X[i] = SNAKE_X[i - 1];
                        SNAKE_Y[i] = SNAKE_Y[i - 1];
                    }

                    switch (dir) {
                        case UP:
                            SNAKE_Y[0] -= 10;
                            break;
                        case DOWN:
                            SNAKE_Y[0] += 10;
                            break;
                        case LEFT:
                            SNAKE_X[0] -= 10;
                            break;
                        case RIGHT:
                            SNAKE_X[0] += 10;
                            break;
                        default:
                            break;
                    }

                    rectH = new Rectangle(SNAKE_X[0], SNAKE_Y[0], WIDTH, HEIGHT);
                    rectF = new Rectangle(foodX, foodY, WIDTH, HEIGHT);

                    eatFood();
                    checkBounds();
                    collideWithSelf();
                    repaint();
                }
            }
        });

        timer.start();
    }

    public void init(){
        length = 3;
        dir = Direction.RIGHT;

        SNAKE_X[0] = 180;
        SNAKE_Y[0] = 275;

        SNAKE_X[1] = 165;
        SNAKE_Y[1] = 275;

        SNAKE_X[2] = 150;
        SNAKE_Y[2] = 275;

        foodX = 300;
        foodY = 200;
    }

    public void eatFood(){
        if(rectH.intersects(rectF)){
            foodX = new Random().nextInt(30) * 20;
            foodY = new Random().nextInt(30) * 20;
            //更新食物的坐标
            rectF.x = foodX;
            rectF.y = foodY;

            score ++;
            length ++;
        }
    }

    /*回头会撞死自己*/
    public void collideWithSelf(){
        for(int i = 1;i < length;i++){
            if (SNAKE_X[0] == SNAKE_X[i] && SNAKE_Y[0] == SNAKE_Y[i]) {
                living = false;
                break;
            }
        }
    }

    public void checkBounds(){
        //right
        if(SNAKE_X[0] >= GameFrame.GAME_WIDTH){
            SNAKE_X[0] = 1;
        }
        //left
        if(SNAKE_X[0] <= 0){
            SNAKE_X[0] = GameFrame.GAME_WIDTH;
        }
        //down
        if(SNAKE_Y[0] >= GameFrame.GAME_HEIGHT){
            SNAKE_Y[0] = 1;
        }
        //up
        if(SNAKE_Y[0] <= 0){
            SNAKE_Y[0] = GameFrame.GAME_HEIGHT;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawSnake(g);

        if(!living){
            drawEnd(g);
        }

        if(start){
            drawFood(g);
            drawScore(g);
        }

        if(!start){
            drawStart(g);
        }

    }

    public void drawFood(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.PINK);
        g.fillRect(foodX,foodY,WIDTH,HEIGHT);
        g.setColor(color);
    }

    public void drawEnd(Graphics g){
        Color color = g.getColor();
        g.setColor(new Color(114,98,255));
        g.setFont(new Font("微软雅黑",Font.BOLD,30));
        g.drawString("游戏结束，点击空格开始",GameFrame.GAME_WIDTH/2 - 150,GameFrame.GAME_HEIGHT/2 - 20);
        g.setColor(color);
    }

    public void drawScore(Graphics g){
        Color color = g.getColor();
        g.setColor(new Color(114,98,255));
        g.setFont(new Font("微软雅黑",Font.BOLD,20));
        g.drawString("SCORE " + score * 2,20,30);
        g.setColor(color);
    }

    public void drawStart(Graphics g){
        Color color = g.getColor();
        g.setColor(new Color(114,98,255));
        g.setFont(new Font("微软雅黑",Font.BOLD,40));
        g.drawString("点击空格开始",GameFrame.GAME_WIDTH/2 - 120,GameFrame.GAME_HEIGHT/2 - 20);
        g.setColor(color);
    }

    public void drawSnake(Graphics g){
        switch (dir){
            case UP    : g.drawImage(ImageUtil.getInstance().hU, SNAKE_X[0], SNAKE_Y[0],null);break;
            case DOWN  : g.drawImage(ImageUtil.getInstance().hD, SNAKE_X[0], SNAKE_Y[0],null);break;
            case LEFT  : g.drawImage(ImageUtil.getInstance().hL, SNAKE_X[0], SNAKE_Y[0],null);break;
            case RIGHT : g.drawImage(ImageUtil.getInstance().hR, SNAKE_X[0], SNAKE_Y[0],null);break;
            default:break;
        }

        for(int i = 1;i < length;i++){
            g.drawImage(ImageUtil.getInstance().body, SNAKE_X[i], SNAKE_Y[i],null);
        }
    }

    public void drawBackground(Graphics g){
        int x = 0;
        int y = 0;
        // 平铺背景图片
        while (true) {
            g.drawImage(ImageUtil.getInstance().bg, x, y, null);
            if (x > GameFrame.GAME_WIDTH && y > GameFrame.GAME_HEIGHT)
                break;
            if (x > GameFrame.GAME_WIDTH) {
                x = 0;
                y += ImageUtil.getInstance().bg.getHeight();
            } else{
                x += ImageUtil.getInstance().bg.getWidth();
            }
        }
    }
}
