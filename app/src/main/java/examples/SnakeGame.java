package examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class SnakeGame {
  public static void launch() {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Simple Snake Game");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.add(new GamePanel());
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    });
  }

  // Simple game panel
  static class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int TILE_SIZE = 20;
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 20;
    private static final int PANEL_WIDTH = TILE_SIZE * GRID_WIDTH;
    private static final int PANEL_HEIGHT = TILE_SIZE * GRID_HEIGHT;

    private final LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private Direction dir = Direction.RIGHT;
    private Timer timer;
    private boolean running = true;

    enum Direction {
      UP, DOWN, LEFT, RIGHT
    }

    GamePanel() {
      setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
      setBackground(Color.BLACK);
      setFocusable(true);
      addKeyListener(this);

      initGame();
      timer = new Timer(120, this);
      timer.start();
    }

    private void initGame() {
      snake.clear();
      // Start with 4 segments
      int startX = GRID_WIDTH / 2;
      int startY = GRID_HEIGHT / 2;
      for (int i = 0; i < 4; i++) {
        snake.add(new Point(startX - i, startY));
      }
      placeFood();
      dir = Direction.RIGHT;
      running = true;
    }

    private void placeFood() {
      int x, y;
      do {
        x = (int) (Math.random() * GRID_WIDTH);
        y = (int) (Math.random() * GRID_HEIGHT);
        food = new Point(x, y);
      } while (snake.contains(food));
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g.create();

      // Draw grid (optional subtle lines)
      g2.setColor(new Color(30, 30, 30));
      for (int i = 0; i <= GRID_WIDTH; i++) {
        g2.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, PANEL_HEIGHT);
      }
      for (int i = 0; i <= GRID_HEIGHT; i++) {
        g2.drawLine(0, i * TILE_SIZE, PANEL_WIDTH, i * TILE_SIZE);
      }

      // Draw food
      g2.setColor(Color.RED);
      g2.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

      // Draw snake
      g2.setColor(Color.GREEN);
      boolean head = true;
      for (Point p : snake) {
        if (head) {
          g2.setColor(Color.YELLOW);
          g2.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
          head = false;
          g2.setColor(Color.GREEN);
        } else {
          g2.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
      }

      if (!running) {
        g2.setColor(new Color(255, 255, 255, 200));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        FontMetrics fm = g2.getFontMetrics();
        String msg = "Game Over - Press R to restart";
        int tw = fm.stringWidth(msg);
        g2.drawString(msg, (PANEL_WIDTH - tw) / 2, PANEL_HEIGHT / 2);
      }

      g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (running) {
        step();
      }
      repaint();
    }

    private void step() {
      Point head = snake.getFirst();
      Point next = new Point(head.x, head.y);
      switch (dir) {
        case UP:
          next.y -= 1;
          break;
        case DOWN:
          next.y += 1;
          break;
        case LEFT:
          next.x -= 1;
          break;
        case RIGHT:
          next.x += 1;
          break;
      }

      // Wrap-around behavior
      if (next.x < 0)
        next.x = GRID_WIDTH - 1;
      if (next.x >= GRID_WIDTH)
        next.x = 0;
      if (next.y < 0)
        next.y = GRID_HEIGHT - 1;
      if (next.y >= GRID_HEIGHT)
        next.y = 0;

      // Check collision with self
      if (snake.contains(next)) {
        running = false;
        return;
      }

      snake.addFirst(next);

      // Check food
      if (next.equals(food)) {
        placeFood(); // grow
      } else {
        snake.removeLast(); // move
      }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
      int kc = e.getKeyCode();
      if (!running) {
        if (kc == KeyEvent.VK_R) {
          initGame();
        }
        return;
      }
      if (kc == KeyEvent.VK_UP || kc == KeyEvent.VK_W) {
        if (dir != Direction.DOWN)
          dir = Direction.UP;
      } else if (kc == KeyEvent.VK_DOWN || kc == KeyEvent.VK_S) {
        if (dir != Direction.UP)
          dir = Direction.DOWN;
      } else if (kc == KeyEvent.VK_LEFT || kc == KeyEvent.VK_A) {
        if (dir != Direction.RIGHT)
          dir = Direction.LEFT;
      } else if (kc == KeyEvent.VK_RIGHT || kc == KeyEvent.VK_D) {
        if (dir != Direction.LEFT)
          dir = Direction.RIGHT;
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
  }
}
