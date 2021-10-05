# GreedySnake
基于Java的小游戏，贪吃蛇，巩固基础练习项目
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