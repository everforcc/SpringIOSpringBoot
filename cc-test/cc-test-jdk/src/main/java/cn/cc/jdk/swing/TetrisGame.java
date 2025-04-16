package cn.cc.jdk.swing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * 豆包生成
 */
public class TetrisGame extends JPanel {

    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 22;
    private static final int BLOCK_SIZE = 20;

    private Timer timer;
    private int delay = 300;

    private int[][] board;
    private TetrisGame.Shape currentPiece;
    private Random random;

    public TetrisGame() {
        initGame();
    }

    private void initGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);

        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        random = new Random();

        addKeyListener(new TetrisGame.TAdapter());
        newPiece();

        timer = new Timer(delay, new TetrisGame.GameCycle());
        timer.start();
    }

    private void newPiece() {
        currentPiece = new TetrisGame.Shape(random.nextInt(7));
        currentPiece.setX(BOARD_WIDTH / 2 - 1);
        currentPiece.setY(0);

        if (!tryMove(currentPiece, currentPiece.getX(), currentPiece.getY())) {
            currentPiece = null;
            timer.stop();
        }
    }

    private boolean tryMove(TetrisGame.Shape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newX + newPiece.getX(i);
            int y = newY + newPiece.getY(i);

            if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                return false;
            }
            if (y >= 0 && board[y][x] != 0) {
                return false;
            }
        }

        currentPiece = newPiece;
        currentPiece.setX(newX);
        currentPiece.setY(newY);
        repaint();
        return true;
    }

    private void clearLines() {
        int numLines = 0;
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean isLineFull = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == 0) {
                    isLineFull = false;
                    break;
                }
            }
            if (isLineFull) {
                numLines++;
                for (int k = i; k > 0; k--) {
                    System.arraycopy(board[k - 1], 0, board[k], 0, BOARD_WIDTH);
                }
            }
        }
    }

    private void dropDown() {
        if (!tryMove(currentPiece, currentPiece.getX(), currentPiece.getY() + 1)) {
            pieceDropped();
        }
    }

    private void pieceDropped() {
        for (int i = 0; i < 4; i++) {
            int x = currentPiece.getX() + currentPiece.getX(i);
            int y = currentPiece.getY() + currentPiece.getY(i);
            board[y][x] = currentPiece.getType() + 1;
        }
        clearLines();
        newPiece();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] != 0) {
                    drawBlock(g, j * BLOCK_SIZE, i * BLOCK_SIZE, board[i][j]);
                }
            }
        }
        if (currentPiece != null) {
            for (int i = 0; i < 4; i++) {
                int x = currentPiece.getX() + currentPiece.getX(i);
                int y = currentPiece.getY() + currentPiece.getY(i);
                drawBlock(g, x * BLOCK_SIZE, y * BLOCK_SIZE, currentPiece.getType() + 1);
            }
        }
    }

    private void drawBlock(Graphics g, int x, int y, int colorIndex) {
        Color colors[] = {
                new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[colorIndex];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + BLOCK_SIZE - 1, x, y);
        g.drawLine(x, y, x + BLOCK_SIZE - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
        g.drawLine(x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + 1);
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (currentPiece == null) {
                return;
            }
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    tryMove(currentPiece, currentPiece.getX() - 1, currentPiece.getY());
                    break;
                case KeyEvent.VK_RIGHT:
                    tryMove(currentPiece, currentPiece.getX() + 1, currentPiece.getY());
                    break;
                case KeyEvent.VK_DOWN:
                    tryMove(currentPiece, currentPiece.getX(), currentPiece.getY() + 1);
                    break;
                case KeyEvent.VK_UP:
                    tryMove(currentPiece.rotate(), currentPiece.getX(), currentPiece.getY());
                    break;
                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;
            }
        }
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dropDown();
        }
    }

    private static class Shape {
        private int[][][] coords;
        private int[][][] rotCoords;
        private int type;
        private int x;
        private int y;

        public Shape(int type) {
            this.type = type;
            coords = new int[7][4][2];
            rotCoords = new int[7][4][2];
            initShape();
        }

        private void initShape() {
            int[][][] shapes = {
                    {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                    {{0, 0}, {-1, 0}, {1, 0}, {2, 0}},
                    {{0, 0}, {-1, 0}, {0, 1}, {0, 2}},
                    {{0, 0}, {1, 0}, {1, 1}, {0, 1}},
                    {{0, 0}, {1, 0}, {0, 1}, {0, 2}},
                    {{0, 0}, {-1, 0}, {0, 1}, {1, 1}},
                    {{0, 0}, {1, 0}, {1, -1}, {0, 1}}
            };
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 2; k++) {
                        coords[i][j][k] = shapes[i][j][k];
                    }
                }
            }
        }

        public int getType() {
            return type;
        }

        public int getX(int index) {
            return coords[type][index][0];
        }

        public int getY(int index) {
            return coords[type][index][1];
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public TetrisGame.Shape rotate() {
            TetrisGame.Shape rotated = new TetrisGame.Shape(type);
            for (int i = 0; i < 4; i++) {
                rotated.rotCoords[type][i][0] = -coords[type][i][1];
                rotated.rotCoords[type][i][1] = coords[type][i][0];
            }
            rotated.coords = rotated.rotCoords;
            return rotated;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("jdk------");
                    EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TetrisGame());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
//            Desktop.getDesktop().open(new File("ftp:127.0.0.1"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
