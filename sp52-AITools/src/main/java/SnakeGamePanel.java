import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * 贪吃蛇游戏面板（新增）
 * 核心功能：
 * 1. 实现贪吃蛇基本运动逻辑
 * 2. 处理键盘方向控制
 * 3. 碰撞检测与得分计算
 */
public class SnakeGamePanel extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 10;  // 将格子大小从40减小到10
    private static final int GAME_WIDTH = 600;  // 保持游戏区域宽度不变
    private static final int GAME_HEIGHT = 500;  // 保持游戏区域高度不变
    
    private Timer timer;
    private LinkedList<Point> snake;
    private Point food = new Point(-1, -1); 
    private Direction direction = Direction.RIGHT;
    private boolean isRunning = false;
    private int score = 0;
    
    private enum Direction { UP, DOWN, LEFT, RIGHT }

    public SnakeGamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.WHITE);
        setFocusable(true);
        
        // 初始化游戏控制
        JButton startButton = new JButton("开始游戏");
        startButton.addActionListener(e -> {
            toggleGame();
            requestFocusInWindow(); // 确保面板获取焦点
        });
        add(startButton);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });
    }

    private void toggleGame() {
        if (!isRunning) {
            initGame();
            timer = new Timer(150, this);
            timer.start();
            isRunning = true;
        } else {
            timer.stop();
            isRunning = false;
        }
        repaint(); // 添加重绘以确保界面更新
    }

    private void initGame() {
        // 确保snake被正确初始化
        snake = new LinkedList<>();
        snake.add(new Point(5, 5));
        score = 0;
        direction = Direction.RIGHT;
        
        // 添加双重初始化保护（新增）
        food = new Point(0, 0); // 基础初始化
        do {
            spawnFood();
        } while (snake.contains(food) || food.x < 0 || food.y < 0); // 新增坐标有效性检查
    }

    private void spawnFood() {
        Random rand = new Random();
        int maxTilesX = GAME_WIDTH / TILE_SIZE;
        int maxTilesY = GAME_HEIGHT / TILE_SIZE;
        food = new Point(rand.nextInt(maxTilesX), rand.nextInt(maxTilesY));
    }

    private void handleKeyPress(int keyCode) {
        // 修改：增加游戏运行状态检查
        if (!isRunning) return;

        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:  // 新增：支持W键
                if (direction != Direction.DOWN) direction = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:  // 新增：支持S键
                if (direction != Direction.UP) direction = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:  // 新增：支持A键
                if (direction != Direction.RIGHT) direction = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:  // 新增：支持D键
                if (direction != Direction.LEFT) direction = Direction.RIGHT;
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveSnake();
        checkCollisions();
        repaint();
    }

    private void moveSnake() {
        Point head = snake.getFirst();
        Point newHead = new Point(head);
        
        switch (direction) {
            case UP: newHead.y--; break;
            case DOWN: newHead.y++; break;
            case LEFT: newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }
        
        snake.addFirst(newHead);
        if (!newHead.equals(food)) {
            snake.removeLast();
        } else {
            score += 10;  // 保留计分逻辑，但不再有上限
            spawnFood();
        }
    }

    private void checkCollisions() {
        Point head = snake.getFirst();
        // 边界检测
        if (head.x < 0 || head.x >= GAME_WIDTH/TILE_SIZE || 
            head.y < 0 || head.y >= GAME_HEIGHT/TILE_SIZE) {
            gameOver();
        }
        // 自碰检测
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver();
            }
        }
    }

    private void gameOver() {
        timer.stop();
        isRunning = false;
        JOptionPane.showMessageDialog(this, "游戏结束！得分: " + score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 增加snake空值检查
        if (snake != null) {
            // 使用渐变颜色绘制蛇身
            for (int i = 0; i < snake.size(); i++) {
                Point p = snake.get(i);
                // 蛇头使用深蓝色，蛇身使用渐变色
                Color snakeColor = i == 0 ? new Color(30, 80, 150) : 
                    new Color(70, 130, 180 - (i * 10 % 100));
                g.setColor(snakeColor);
                // 修改：调整蛇的绘制位置，确保节点之间没有间隔
                g.fillRoundRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 8, 8);
                // 添加阴影效果
                g.setColor(new Color(0, 0, 0, 50));
                g.fillRoundRect(p.x * TILE_SIZE + 2, p.y * TILE_SIZE + 2, TILE_SIZE, TILE_SIZE, 8, 8);
            }
        }

        // 强化绘制条件检查（修改）
        if (isRunning && food != null && food.x >= 0 && food.y >= 0) {
            // 使用渐变颜色绘制食物
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 食物渐变效果
            GradientPaint gp = new GradientPaint(
                food.x * TILE_SIZE, food.y * TILE_SIZE, new Color(255, 100, 100),
                food.x * TILE_SIZE + TILE_SIZE, food.y * TILE_SIZE + TILE_SIZE, new Color(200, 50, 50)
            );
            g2d.setPaint(gp);
            // 绘制带阴影的圆形食物
            g2d.fillOval(food.x * TILE_SIZE + 2, food.y * TILE_SIZE + 2, GameConfig.FOOD_SIZE, GameConfig.FOOD_SIZE);
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillOval(food.x * TILE_SIZE + 4, food.y * TILE_SIZE + 4, GameConfig.FOOD_SIZE, GameConfig.FOOD_SIZE);
            g2d.dispose();
        } else if (!isRunning) {
            // 游戏未运行时确保food被正确重置
            food = new Point(-1, -1);
        }
    }
}